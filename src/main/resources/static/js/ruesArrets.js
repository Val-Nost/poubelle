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
        // Charger les données
        let arrets = await fetchData('/arrets');
        console.log(arrets);
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

            let rammassages = await fetchData(`/rammassages/${userId}/arrets`);
            console.log("rammassages", rammassages)

            const itinerairesCyclistera = rammassages.map(ramassage => {
                return {
                    id: ramassage.id,
                    ramasse: ramassage.ramasse
                };
            });
            console.log("itinerairesCyclistera", itinerairesCyclistera)
            // Supprimer les doublons basés sur l'ID
            const uniqueitinerairesCyclistera = Array.from(new Set(itinerairesCyclistera.map(a => a.id)))
                .map(id => {
                    return itinerairesCyclistera.find(a => a.id === id);
                });

            console.log("uniqueitinerairesCyclistera    ", uniqueitinerairesCyclistera);

            const itinerairesCycliste = rammassages.map(ramassage => ramassage.id);
            console.log("itinerairesCycliste", itinerairesCycliste)
            // Convertir le tableau en Set pour éliminer les doublons
            const uniqueItinerairesCycliste = [...new Set(itinerairesCycliste)];
            console.log("uniqueItinerairesCycliste", uniqueItinerairesCycliste)
            // Afficher l'itinéraire spécifique
            afficherItineraire(map, rueArrets, arretsCoords, itinerairesCycliste, uniqueItinerairesCycliste);
        }
    } catch (error) {
        console.error('Erreur lors du chargement des données:', error);
    }
});

async function fetchData(url) {
    const response = await fetch(url);
    return await response.json();
}

function afficherItineraire(map, rueArrets, arretsCoords, itinerairesCycliste, uniqueItinerairesCycliste) {
    const groupedPoints = [];
    const allPoints = [];
    const markers = [];
    const polylines = [];
    const baseId = 161; // ID de la base

    // Utilisation de uniqueItinerairesCycliste pour l'affichage des numéros des arrêts
    uniqueItinerairesCycliste.forEach((arretId, index) => {
        const arret = rueArrets.find(rueArret => rueArret.arret.id === arretId);
        const coords = arretsCoords[arretId];

        if (arret && coords) {
            groupedPoints.push({
                coords: [coords.lat, coords.lon],
                arretLibelle: arret.arret.libelle,
                rueLibelle: arret.rue.libelle,
                order: index + 1, // Utilisation de l'index pour numéroter les arrêts
                arretId: arretId
            });

            allPoints.push([coords.lat, coords.lon]);
        } else {
            console.warn(`Coordonnées non trouvées pour l'arrêt : ${arretId}`);
        }
    });

    // Création des polylines et des marqueurs avec les numéros d'arrêts de uniqueItinerairesCycliste
    const points = groupedPoints.map(point => point.coords);
    const color = '#00FF00'; // Vert pour les polylines de l'itinéraire

    const polyline = L.polyline(points, { color: color, pane: 'polylinesPane' }).addTo(map);
    polylines.push(polyline);

    // Affichage des marqueurs numérotés (utilise uniqueItinerairesCycliste pour les numéros)
    groupedPoints.forEach(pointData => {
        const isBase = pointData.arretId === baseId;
        const iconColor = isBase ? '#FF0000' : color; // Rouge pour la base, vert sinon

        // Icône de l'arrêt avec le numéro (utilise uniqueItinerairesCycliste pour les numéros)
        const numberIcon = L.divIcon({
            className: 'number-icon',
            html: `<div style="
                width: 30px;
                height: 30px;
                background-color: ${iconColor};
                color: black;
                text-align: center;
                line-height: 30px;
                border-radius: 50%;
                border: 2px solid #000;
                font-weight: bold;
                font-size: 14px;">${pointData.order}</div>`, // Utilise l'index pour définir le numéro
            iconSize: [30, 30],
            iconAnchor: [15, 15]
        });

        const marker = L.marker(pointData.coords, { icon: numberIcon, pane: 'itinerairePane' }).addTo(map);
        markers.push({ marker, arretId: pointData.arretId });
    });

    // Affichage des arrêts dans la liste de droite avec uniqueItinerairesCycliste
    const arretsList = document.getElementById('arrets-list');
    arretsList.innerHTML = '';

    let currentRue = null;
    groupedPoints.forEach(pointData => {
        const { arretLibelle, rueLibelle, arretId } = pointData;

        if (rueLibelle !== currentRue) {
            const rueListItem = document.createElement('li');
            rueListItem.textContent = rueLibelle;
            rueListItem.classList.add('changement-de-rue'); // Classe pour le style
            arretsList.appendChild(rueListItem);
            currentRue = rueLibelle;
        }

        const arretListItem = document.createElement('li');
        arretListItem.textContent = arretLibelle;

        if (arretId === baseId) {
            arretListItem.textContent += ' (Retour à la base)';
            arretListItem.style.fontWeight = 'bold'; // Mise en gras
            arretListItem.style.color = '#FF0000'; // Rouge pour la base
        }

        arretsList.appendChild(arretListItem);
    });

    // Ajustement des bounds de la carte pour inclure tous les points
    if (allPoints.length > 0) {
        const bounds = L.latLngBounds(allPoints);
        map.fitBounds(bounds, { padding: [50, 50] });
    }

    // Appel à la fonction d'animation avec itinerairesCycliste
    animerItineraire(map, itinerairesCycliste, markers, polylines, uniqueItinerairesCycliste);
}

function animerItineraire(map, itinerairesCycliste, markers, polylines, uniqueItinerairesCycliste) {
    const baseId = 161; // ID de la base, à ajuster si nécessaire

    // Utiliser itinerairesCycliste pour animer le vélo entre les arrêts
    const points = itinerairesCycliste.map(arretId => markers.find(markerObj => markerObj.arretId === arretId).marker.getLatLng());

    // Créer un marqueur qui se déplacera le long de l'itinéraire
    const marker = L.circleMarker(points[0], {
        radius: 7,
        fillColor: '#FF0000', // Rouge pour rendre le marqueur bien visible
        color: '#000',
        weight: 1,
        opacity: 1,
        fillOpacity: 0.9,
        pane: 'animatedPointPane'
    }).addTo(map);

    let index = 0;
    const speed = 1000; // Durée en ms pour chaque segment de l'itinéraire

    async function updateDatabaseOnLeave() {
        try {
            // Faire une requête POST à votre API
            const response = await fetch('/ramassageDerniersArrets', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            console.log(response);
            if (!response.ok) {
                console.error('Erreur lors de la mise à jour de la base de données.');
            } else {
                console.log('Base de données mise à jour avec succès.');
            }
        } catch (error) {
            console.error('Erreur de connexion à l\'API:', error);
        }
    }

    function moveMarker() {
        if (index < points.length - 1) {
            const start = points[index];
            const end = points[index + 1];
            const duration = speed / 500; // Durée en secondes
            const startTime = performance.now();

            // Appliquer la couleur à l'arrêt actuel AVANT de démarrer l'animation vers le prochain arrêt
            const currentArretId = itinerairesCycliste[index];

            // Si l'arrêt actuel est dans uniqueItinerairesCycliste et n'est pas la base, changer sa couleur
            if (uniqueItinerairesCycliste.includes(currentArretId) && currentArretId !== baseId) {
                const markerObj = markers.find(markerObj => markerObj.arretId === currentArretId);
                if (markerObj) {
                    // Extraire le numéro de l'arrêt correctement depuis markerObj
                    const originalHtml = markerObj.marker.options.icon.options.html;
                    const originalNumberMatch = originalHtml.match(/>\d+</); // Trouver le nombre entre les balises
                    const originalNumber = originalNumberMatch ? originalNumberMatch[0].replace(/[><]/g, '') : '1'; // Extraire le nombre

                    // Changer la couleur du marqueur pour indiquer qu'il a été visité (au moment où il quitte l'arrêt)
                    markerObj.marker.setIcon(L.divIcon({
                        className: 'number-icon',
                        html: `<div style="
                            width: 30px;
                            height: 30px;
                            background-color: #FFA500; /* Orange pour les arrêts quittés */
                            color: black;
                            text-align: center;
                            line-height: 30px;
                            border-radius: 50%;
                            border: 2px solid #000;
                            font-weight: bold;
                            font-size: 14px;">${originalNumber}</div>`,
                        iconSize: [30, 30],
                        iconAnchor: [15, 15]
                    }));
                }
                // Appeler l'API pour mettre à jour la base de données lorsque le cycliste quitte l'arrêt
                updateDatabaseOnLeave();
            }

            // Fonction d'animation
            function animate(time) {
                const elapsed = (time - startTime) / 1000; // Temps écoulé en secondes
                const t = Math.min(elapsed / duration, 1); // Progrès de l'animation (0 à 1)
                const lat = start.lat + t * (end.lat - start.lat);
                const lon = start.lng + t * (end.lng - start.lng);

                marker.setLatLng([lat, lon]);

                if (t >= 1) {
                    // Vérification de l'existence de la polyline à cet index
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
