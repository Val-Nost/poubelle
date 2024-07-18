const itineraire = [1, 3, 5, 7, 333, 284];
document.addEventListener("DOMContentLoaded", function() {
    const map = L.map('mynetwork').setView([48.8566, 2.3522], 12);

    // Créer des panes personnalisés pour gérer l'ordre de dessin
    map.createPane('polylinesPane');
    map.createPane('markersPane');
    map.createPane('itinerairePane');

    // Définir le z-index des panes
    map.getPane('polylinesPane').style.zIndex = 400;
    map.getPane('markersPane').style.zIndex = 450;
    map.getPane('itinerairePane').style.zIndex = 500;

    const arretsCoords = {};
    arrets.forEach(arret => {
        arretsCoords[arret.id] = { lat: arret.latitude, lon: arret.longitude, libelle: arret.libelle };
    });

    const ligneColors = {};

    // Couleurs par défaut
    const defaultLineColor = '#A9A9A9'; // Gris clair pour les rues
    const defaultMarkerColor = '#696969'; // Gris foncé pour les arrêts
    const itineraireColor = '#00FF00'; // Vert pour l'itinéraire

    // Regrouper les points par rue
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

    // Fonction pour obtenir les rues associées à un arrêt
    function getRuesForArret(arretId) {
        return rueArrets
            .filter(rueArret => rueArret.arret.id === arretId)
            .map(rueArret => rueArret.rue.libelle);
    }

    // Créer les polylines pour chaque rue
    for (const rue in groupedPoints) {
        const polylinePoints = groupedPoints[rue];
        const color = ligneColors[rue] || defaultLineColor; // Couleur par défaut si non définie

        L.polyline(polylinePoints, { color: color, pane: 'polylinesPane' }).addTo(map)
            .bindPopup(`<span style="color: ${color}">${rue}</span>`); // Afficher le nom de la rue avec sa couleur

        // Ajouter des marqueurs pour chaque point
        polylinePoints.forEach(point => {
            const marker = L.circleMarker(point, {
                radius: 5,
                fillColor: defaultMarkerColor,
                color: '#000',
                weight: 1,
                opacity: 1,
                fillOpacity: 0.8,
                pane: 'markersPane'
            }).addTo(map);

            // Ajouter les informations dans le popup
            const arretId = Object.keys(arretsCoords).find(id =>
                arretsCoords[id].lat === point[0] && arretsCoords[id].lon === point[1]);
            const arretLibelle = arretsCoords[arretId].libelle;
            const ruesAssociees = getRuesForArret(Number(arretId)).join(', ');

            marker.bindPopup(`
                <span style="color: ${defaultMarkerColor}">${arretLibelle}</span><br>
                <strong>Rues associées :</strong> ${ruesAssociees}
            `);
        });
    }

    // Tracer l'itinéraire
    const itinerairePoints = itineraire.map(id => [arretsCoords[id].lat, arretsCoords[id].lon]);
    L.polyline(itinerairePoints, { color: itineraireColor, weight: 5, pane: 'itinerairePane' }).addTo(map);

    // Ajouter des marqueurs pour les arrêts de l'itinéraire
    itinerairePoints.forEach(point => {
        L.circleMarker(point, {
            radius: 7,
            fillColor: itineraireColor,
            color: '#000',
            weight: 2,
            opacity: 1,
            fillOpacity: 0.9,
            pane: 'itinerairePane'
        }).addTo(map)
            .bindPopup(`<span style="color: ${itineraireColor}">${arretsCoords[itineraire.find(id => arretsCoords[id].lat === point[0] && arretsCoords[id].lon === point[1])].libelle}</span>`);
    });

    // Ajouter le point animé qui parcourt l'itinéraire
    let currentIndex = 0;
    const movingMarker = L.circleMarker(itinerairePoints[0], {
        radius: 10,
        fillColor: '#FF0000', // Couleur rouge pour le point animé
        color: '#000',
        weight: 2,
        opacity: 1,
        fillOpacity: 1,
        pane: 'itinerairePane'
    }).addTo(map);

    function moveMarker() {
        currentIndex = (currentIndex + 1) % itinerairePoints.length;
        movingMarker.setLatLng(itinerairePoints[currentIndex]);
    }

    setInterval(moveMarker, 1000); // Déplace le marqueur toutes les secondes*/
});