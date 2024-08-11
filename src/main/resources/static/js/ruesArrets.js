document.addEventListener("DOMContentLoaded", async function() {
    // Initialisation de la carte
    let map = L.map('mynetwork').setView([48.8566, 2.3522], 12);

    // Créer des panes personnalisés pour gérer l'ordre de dessin
    map.createPane('polylinesPane');
    map.createPane('markersPane');
    map.createPane('itinerairePane');

    // Définir le z-index des panes
    map.getPane('polylinesPane').style.zIndex = 400;
    map.getPane('markersPane').style.zIndex = 450;
    map.getPane('itinerairePane').style.zIndex = 500;

    try {
        // Charger les données
        let arrets = await fetchData('/arrets');
        let rueArrets = await fetchData('/arret-rue');

        // Initialiser les coordonnées des arrêts
        let arretsCoords = {};
        arrets.forEach(arret => {
            arretsCoords[arret.id] = { lat: arret.latitude, lon: arret.longitude, libelle: arret.libelle };
        });

        // Extraire le chemin de l'URL
        const pathname = window.location.pathname;

        if (pathname.includes('/ramassage/ramassageByUser/')) {
            // Extraire l'ID utilisateur de l'URL
            const userId = pathname.split('/').pop();

            const itinerairesCycliste = [308, 307, 306, 305, 304, 303, 302, 301, 300];

            // Afficher l'itinéraire spécifique
            afficherItineraire(map, rueArrets, arretsCoords, itinerairesCycliste);
        } else if (pathname.includes('/ramassage/ramassages')) {
            // Afficher la carte globale
            afficherCarteGlobale(map, rueArrets, arretsCoords);
        }
    } catch (error) {
        console.error('Erreur lors du chargement des données:', error);
    }
});

async function fetchData(url) {
    const response = await fetch(url);
    return await response.json();
}

function afficherCarteGlobale(map, rueArrets, arretsCoords) {
    const ligneColors = {};
    const groupedPoints = {};

    rueArrets.forEach(rueArret => {
        const rue = rueArret.rue.libelle;
        const arret = rueArret.arret;
        const coords = arretsCoords[arret.id];

        if (coords) {
            if (!groupedPoints[rue]) {
                groupedPoints[rue] = [];
            }
            groupedPoints[rue].push([coords.lat, coords.lon]);
        } else {
            console.warn(`Coordonnées non trouvées pour l'arrêt : ${arret.id}`);
        }
    });

    // Créer les polylines pour chaque rue
    for (const rue in groupedPoints) {
        const polylinePoints = groupedPoints[rue];
        const color = ligneColors[rue] || '#A9A9A9'; // Gris clair

        L.polyline(polylinePoints, { color: color, pane: 'polylinesPane' }).addTo(map)
            .bindPopup(`<span style="color: ${color}">${rue}</span>`);

        polylinePoints.forEach(point => {
            const marker = L.circleMarker(point, {
                radius: 5,
                fillColor: '#696969', // Gris foncé
                color: '#000',
                weight: 1,
                opacity: 1,
                fillOpacity: 0.8,
                pane: 'markersPane'
            }).addTo(map);

            const arretId = Object.keys(arretsCoords).find(id =>
                arretsCoords[id].lat === point[0] && arretsCoords[id].lon === point[1]);
            const arretLibelle = arretsCoords[arretId].libelle;
            const ruesAssociees = rueArrets
                .filter(rueArret => rueArret.arret.id === Number(arretId))
                .map(rueArret => rueArret.rue.libelle)
                .join(', ');

            marker.bindPopup(`
                <span style="color: #696969">${arretLibelle}</span><br>
                <strong>Rues associées :</strong> ${ruesAssociees}
            `);
        });
    }
}

function afficherItineraire(map, rueArrets, arretsCoords, itinerairesCycliste) {
    const filteredRueArrets = rueArrets.filter(rueArret =>
        itinerairesCycliste.includes(rueArret.arret.id)
    );

    const groupedPoints = {};
    const allPoints = [];

    filteredRueArrets.forEach(rueArret => {
        const rue = rueArret.rue.libelle;
        const arret = rueArret.arret;
        const coords = arretsCoords[arret.id];

        if (coords) {
            if (!groupedPoints[rue]) {
                groupedPoints[rue] = [];
            }
            groupedPoints[rue].push({
                coords: [coords.lat, coords.lon],
                arretLibelle: arret.libelle,
                rueLibelle: rue
            });

            allPoints.push([coords.lat, coords.lon]); // Collecte tous les points pour ajuster le zoom
        } else {
            console.warn(`Coordonnées non trouvées pour l'arrêt : ${arret.id}`);
        }
    });

    // Créer les polylines et marqueurs pour l'itinéraire
    for (const rue in groupedPoints) {
        const points = groupedPoints[rue].map(point => point.coords);
        const color = '#00FF00'; // Vert pour l'itinéraire

        const polyline = L.polyline(points, { color: color, pane: 'itinerairePane' }).addTo(map);

        // Calculer le point central de la polyline pour positionner le nom de la rue à côté
        const midIndex = Math.floor(points.length / 2);
        const midPoint = points[midIndex];

        // Créer un divIcon personnalisé pour afficher le nom de la rue
        const rueLabelIcon = L.divIcon({
            className: 'rue-label-icon',
            html: `<div style="background-color: white; padding: 2px; border: 1px solid #aaa; border-radius: 3px;">${rue}</div>`,
            iconSize: [100, 20], // Taille du conteneur du texte
            iconAnchor: [50, -10] // Positionnement par rapport au point central
        });

        // Ajouter l'icône avec le texte à la carte
        L.marker(midPoint, { icon: rueLabelIcon, pane: 'itinerairePane' }).addTo(map);

        groupedPoints[rue].forEach(pointData => {
            const marker = L.circleMarker(pointData.coords, {
                radius: 7,
                fillColor: color,
                color: '#000',
                weight: 2,
                opacity: 1,
                fillOpacity: 0.9,
                pane: 'itinerairePane'
            }).addTo(map);

            marker.bindTooltip(`
                ${pointData.arretLibelle}<br>
            `, { permanent: true, className: "arret-label", direction: "right" }).openTooltip(); // Affiche le nom de l'arrêt
        });
    }

    // Zoom sur l'itinéraire
    if (allPoints.length > 0) {
        const bounds = L.latLngBounds(allPoints);
        map.fitBounds(bounds, { padding: [50, 50] }); // Ajuste le zoom avec une petite marge
    }
}