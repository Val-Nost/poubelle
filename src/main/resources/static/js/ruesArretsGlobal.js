document.addEventListener("DOMContentLoaded", async function() {
    // Initialisation de la carte
    let map = L.map('mynetwork').setView([48.8566, 2.3522], 12);

    // Créer des panes personnalisés pour gérer l'ordre de dessin
    map.createPane('polylinesPane');
    map.createPane('markersPane');
    map.createPane('itinerairePane');
    map.createPane('animatedPointPane');

    // Définir le z-index des panes
    map.getPane('polylinesPane').style.zIndex = 400;
    map.getPane('markersPane').style.zIndex = 450;
    map.getPane('itinerairePane').style.zIndex = 500;
    map.getPane('animatedPointPane').style.zIndex = 550;

    try {
        // Charger les données des arrêts et des rues
        let arrets = await fetchData('/arrets');
        let rueArrets = await fetchData('/arret-rue');

        // Initialiser les coordonnées des arrêts
        let arretsCoords = {};
        arrets.forEach(arret => {
            arretsCoords[arret.id] = { lat: arret.latitude, lon: arret.longitude, libelle: arret.libelle };
        });

        // Ajout des gestionnaires d'événements pour les cartes utilisateur
        document.querySelectorAll('.cycliste-card').forEach(card => {
            card.addEventListener('click', async function() {
                const cyclisteId = this.getAttribute('data-cycliste-id');

                // Charger l'itinéraire du cycliste sélectionné
                let rammassages = await fetchData(`/rammassages/${cyclisteId}/arrets`);
                console.log(rammassages);

                const itinerairesCycliste = rammassages.map(ramassage => ramassage.id);
                console.log(itinerairesCycliste);

                // Convertir le tableau en Set pour éliminer les doublons
                const uniqueItinerairesCycliste = [...new Set(itinerairesCycliste)];

                // Afficher l'itinéraire spécifique
                afficherItineraire(map, rueArrets, arretsCoords, uniqueItinerairesCycliste);
            });
        });

        // Si vous voulez afficher une carte globale par défaut
        const pathname = window.location.pathname;
        if (pathname.includes('/ramassage/ramassages')) {
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
    const groupedPoints = [];
    const allPoints = [];
    const markers = [];
    const polylines = [];

    itinerairesCycliste.forEach((arretId, index) => {
        const arret = rueArrets.find(rueArret => rueArret.arret.id === arretId);
        const coords = arretsCoords[arretId];

        if (arret && coords) {
            groupedPoints.push({
                coords: [coords.lat, coords.lon],
                arretLibelle: arret.arret.libelle,
                rueLibelle: arret.rue.libelle,
                order: index + 1, // Stocker l'ordre pour une utilisation ultérieure
                arretId: arretId // Ajouter l'ID de l'arrêt pour référence
            });

            allPoints.push([coords.lat, coords.lon]);
        } else {
            console.warn(`Coordonnées non trouvées pour l'arrêt : ${arretId}`);
        }
    });

    // Créer les polylines et marqueurs pour l'itinéraire
    const points = groupedPoints.map(point => point.coords);
    const color = '#00FF00'; // Vert pour l'itinéraire

    const polyline = L.polyline(points, { color: color, pane: 'itinerairePane' }).addTo(map);
    polylines.push(polyline); // Ajouter à la liste des polylines pour un changement ultérieur

    groupedPoints.forEach(pointData => {
        // Créer un divIcon personnalisé avec la puce verte et le numéro
        const numberIcon = L.divIcon({
            className: 'number-icon',
            html: `<div style="
                width: 30px;
                height: 30px;
                background-color: ${color};
                color: black;
                text-align: center;
                line-height: 30px;
                border-radius: 50%;
                border: 2px solid #000;
                font-weight: bold;
                font-size: 14px;">${pointData.order}</div>`,
            iconSize: [30, 30], // Taille du conteneur
            iconAnchor: [15, 15] // Positionner le centre de l'icône au point de coordonnées
        });

        // Ajouter le marqueur avec l'icône personnalisée
        const marker = L.marker(pointData.coords, { icon: numberIcon, pane: 'itinerairePane' }).addTo(map);
        markers.push({ marker, arretId: pointData.arretId }); // Stocker le marqueur pour un changement ultérieur
    });

    if (allPoints.length > 0) {
        const bounds = L.latLngBounds(allPoints);
        map.fitBounds(bounds, { padding: [50, 50] }); // Ajuste le zoom avec une petite marge
    }

    // Passer les polylines et marqueurs à la fonction d'animation
    animerItineraire(map, points, markers, polylines);
}

function animerItineraire(map, points, markers, polylines) {
    // Créer un marqueur qui se déplacera le long de l'itinéraire
    const marker = L.circleMarker(points[0], {
        radius: 7,
        fillColor: '#FF0000', // Rouge pour être visible
        color: '#000',
        weight: 1,
        opacity: 1,
        fillOpacity: 0.9,
        pane: 'animatedPointPane'
    }).addTo(map);

    let index = 0;
    const speed = 1000; // Durée en ms pour chaque segment de l'itinéraire

    function moveMarker() {
        if (index < points.length - 1) {
            const start = points[index];
            const end = points[index + 1];
            const duration = speed / 1000; // En secondes
            const startTime = performance.now();

            function animate(time) {
                const elapsed = (time - startTime) / 1000; // Temps écoulé en secondes
                const t = Math.min(elapsed / duration, 1); // Progrès dans l'animation (0 à 1)
                const lat = start[0] + t * (end[0] - start[0]);
                const lon = start[1] + t * (end[1] - start[1]);

                marker.setLatLng([lat, lon]);

                // Vérifier si le point animé a atteint l'arrêt ou la rue
                if (t >= 1) {
                    // Changer la couleur du marqueur atteint
                    const reachedMarker = markers[index];
                    if (reachedMarker) {
                        reachedMarker.marker.setIcon(L.divIcon({
                            className: 'number-icon',
                            html: `<div style="
                                width: 30px;
                                height: 30px;
                                background-color: #FFA500; /* Changer la couleur à orange */
                                color: black;
                                text-align: center;
                                line-height: 30px;
                                border-radius: 50%;
                                border: 2px solid #000;
                                font-weight: bold;
                                font-size: 14px;">${index + 1}</div>`,
                            iconSize: [30, 30],
                            iconAnchor: [15, 15]
                        }));
                    }

                    // Changer la couleur de la polyline
                    if (index < polylines.length) {
                        polylines[index].setStyle({ color: '#FFA500' }); // Changer la couleur à orange
                    }

                    index++;
                    setTimeout(moveMarker, 500); // Petite pause entre les segments
                } else {
                    requestAnimationFrame(animate);
                }
            }

            requestAnimationFrame(animate);
        }
    }

    moveMarker();
}