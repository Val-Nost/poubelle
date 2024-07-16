document.addEventListener("DOMContentLoaded", function() {
    // Vérifiez les données
    console.log('Rues:', rues);
    console.log('Arrets:', arrets);
    console.log('RueArrets:', rueArrets);

    const map = L.map('mynetwork').setView([48.8566, 2.3522], 12); // Centre de Paris

    // Créer des panes personnalisés pour gérer l'ordre de dessin
    map.createPane('polylinesPane');
    map.createPane('markersPane');

    // Définir le z-index des panes
    map.getPane('polylinesPane').style.zIndex = 400;
    map.getPane('markersPane').style.zIndex = 450;

    const arretsCoords = {};
    arrets.forEach(arret => {
        arretsCoords[arret.id] = { lat: arret.latitude, lon: arret.longitude, libelle: arret.libelle };
    });

    const ligneColors = {/*
        "Rue Croix-Baragnon": "#FFCD00",
        "Rue des Arts": "#007852",
        "Rue Pargaminières": "#FBB03B",
        "Rue Saint-Rome": "#704B1C",
        "Rue Saint-Antoine du T": "#6EC4E8",
        "Rue de la Fonderie": "#B53635",
        "Rue Peyrolières": "#62259D",
        "Rue Genty-Magre": "#FF7E2E",
        "Rue d'Alsace-Lorraine": "#C8102E",
        "Rue Peyras": "#D6AADA",
        "Rue du Taur": "#BDB157",
        "Allée Jean Jaurès": "#75A23D",
        "Rue du May": "#C79FE5",
        "Rue des Filatiers": "#00643C",
        "Rue Mage": "#60A0B2",
        "Rue d'Espinasse": "#6F5854",
        "Rue des Gestes": "#82C8E6",
        "Quai de la Daurade": "#007852",
        "Rue Bédelières": "#9457A6",
        "Rue Merlane": "#FFCD00",
        "Rue Vélane": "#6EC4E8",
        "Rue Etroite": "#62259D",
        "Rue des Tourneurs": "#FFCD00",
        "Rue de la Trinité": "#FBB03B",*/
    };

    // Couleurs par défaut
    const defaultLineColor = '#A9A9A9'; // Gris clair pour les rues
    const defaultMarkerColor = '#696969'; // Gris foncé pour les arrêts

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
});