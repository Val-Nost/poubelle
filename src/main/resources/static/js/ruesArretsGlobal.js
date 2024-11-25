document.addEventListener("DOMContentLoaded", async function () {
    // **Initialisation de la carte**
    let map = L.map('mynetwork').setView([48.8566, 2.3522], 12);

    // **Création des panes personnalisés**
    map.createPane('polylinesPane');      // Pane pour les polylignes globales
    map.createPane('markersPane');        // Pane pour les marqueurs globaux
    map.createPane('itinerairePane');     // Pane pour l'itinéraire individuel
    map.createPane('animatedPointPane');  // Pane pour le marqueur animé (cycliste)

    // **Définition du z-index pour chaque pane**
    map.getPane('polylinesPane').style.zIndex = 400;
    map.getPane('markersPane').style.zIndex = 450;
    map.getPane('itinerairePane').style.zIndex = 500;
    map.getPane('animatedPointPane').style.zIndex = 550;

    // **Variables globales pour stocker les éléments courants**
    let currentMarkers = [];          // Marqueurs de l'itinéraire individuel
    let currentPolylines = [];        // Polylignes de l'itinéraire individuel
    let globalMarkers = [];           // Marqueurs de la carte globale
    let globalPolylines = [];         // Polylignes de la carte globale
    let currentAnimatedMarker = null; // Marqueur animé actuel (cycliste)
    let animationFrameId = null;      // ID de l'animation en cours
    const baseId = 161;

    try {
        // **Chargement des données depuis le serveur**
        let arrets = await fetchData('/arrets');
        let rueArrets = await fetchData('/arret-rue');

        // **Construction d'un objet pour accéder rapidement aux coordonnées des arrêts par leur ID**
        let arretsCoords = {};
        arrets.forEach(arret => {
            arretsCoords[arret.id] = {
                lat: arret.latitude,
                lon: arret.longitude,
                libelle: arret.libelle
            };
        });

        // **Affichage de la carte globale par défaut**
        afficherCarteGlobale(map, rueArrets, arretsCoords);

        // **Ajout des gestionnaires d'événements pour les cartes des cyclistes**
        document.querySelectorAll('.cycliste-card').forEach(card => {
            card.addEventListener('click', async function () {
                const cyclisteId = this.getAttribute('data-cycliste-id');

                // Charger l'itinéraire du cycliste sélectionné
                let rammassages = await fetchData(`/rammassages/${cyclisteId}/arrets`);

                // Supprimer les doublons en utilisant un Map
                const uniqueRammassagesMap = new Map();
                rammassages.forEach(ramassage => {
                    if (!uniqueRammassagesMap.has(ramassage.id)) {
                        uniqueRammassagesMap.set(ramassage.id, ramassage);
                    }
                });
                const uniqueRammassages = Array.from(uniqueRammassagesMap.values());

                // Effacer l'itinéraire précédent avant d'afficher le nouveau
                clearPreviousItinerary();

                // Afficher l'itinéraire spécifique
                afficherItineraire(map, rueArrets, arretsCoords, uniqueRammassages);
            });
        });

    } catch (error) {
        console.error('Erreur lors du chargement des données:', error);
    }

    // **Fonction pour effacer l'itinéraire précédent**
    function clearPreviousItinerary() {
        // **Suppression des marqueurs de l'itinéraire individuel**
        currentMarkers.forEach(marker => map.removeLayer(marker));
        currentMarkers = [];

        // **Suppression des polylignes de l'itinéraire individuel**
        currentPolylines.forEach(polyline => map.removeLayer(polyline));
        currentPolylines = [];

        // **Suppression du marqueur animé s'il existe**
        if (currentAnimatedMarker) {
            map.removeLayer(currentAnimatedMarker);
            currentAnimatedMarker = null;
        }

        // **Annulation de l'animation en cours**
        if (animationFrameId) {
            cancelAnimationFrame(animationFrameId);
            animationFrameId = null;
        }

        // **Ne pas supprimer les marqueurs et polylignes de la carte globale**
    }

    // **Fonction pour récupérer des données depuis le serveur**
    async function fetchData(url) {
        const response = await fetch(url);
        return await response.json();
    }

    // **Fonction pour afficher la carte globale avec tous les arrêts et les rues associées**
    function afficherCarteGlobale(map, rueArrets, arretsCoords) {
        const ligneColors = {};    // Objet pour stocker les couleurs des lignes (non utilisé actuellement)
        const groupedPoints = {};  // Objet pour regrouper les points par rue

        // **Regroupement des arrêts par rue**
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

        // **Création des polylignes pour chaque rue**
        for (const rue in groupedPoints) {
            const polylinePoints = groupedPoints[rue];
            const color = ligneColors[rue] || '#A9A9A9'; // Couleur par défaut : gris clair

            // **Ajout de la polyligne sur la carte**
            const polyline = L.polyline(polylinePoints, { color: color, pane: 'polylinesPane' })
                .addTo(map)
                .bindPopup(`<span style="color: ${color}">${rue}</span>`);
            globalPolylines.push(polyline); // Stocker la polyligne globale

            // **Ajout des marqueurs pour chaque arrêt**
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

                // **Recherche de l'ID de l'arrêt correspondant aux coordonnées**
                const arretId = Object.keys(arretsCoords).find(id =>
                    arretsCoords[id].lat === point[0] && arretsCoords[id].lon === point[1]);

                const arretLibelle = arretsCoords[arretId].libelle;

                // **Récupération des rues associées à l'arrêt**
                const ruesAssociees = rueArrets
                    .filter(rueArret => rueArret.arret.id === Number(arretId))
                    .map(rueArret => rueArret.rue.libelle)
                    .join(', ');

                // **Ajout d'une popup au marqueur avec les informations de l'arrêt**
                marker.bindPopup(`
                    <span style="color: #696969">${arretLibelle}</span><br>
                    <strong>Rues associées :</strong> ${ruesAssociees}
                `);

                globalMarkers.push(marker); // Stocker le marqueur global
            });
        }
    }

    // **Fonction pour afficher un itinéraire spécifique sur la carte**
    function afficherItineraire(map, rueArrets, arretsCoords, rammassages) {
        const groupedPoints = [];
        const allPoints = [];

        rammassages.forEach((ramassage, index) => {
            const arretId = ramassage.id;
            const arret = rueArrets.find(rueArret => rueArret.arret.id === arretId);
            const coords = arretsCoords[arretId];

            if (arret && coords) {
                groupedPoints.push({
                    coords: [coords.lat, coords.lon],
                    arretLibelle: arret.arret.libelle,
                    rueLibelle: arret.rue.libelle,
                    order: index + 1,
                    arretId: arretId,
                    ramasse: ramassage.ramasse
                });

                allPoints.push([coords.lat, coords.lon]);
            } else {
                console.warn(`Coordonnées non trouvées pour l'arrêt : ${arretId}`);
            }
        });

        // **Création de la polyligne pour l'itinéraire individuel**
        const points = groupedPoints.map(point => point.coords);
        const polyline = L.polyline(points, { color: '#00FF00', pane: 'itinerairePane' }).addTo(map);
        currentPolylines.push(polyline);

        // **Ajout des marqueurs avec des couleurs spécifiques**
        groupedPoints.forEach(pointData => {
            let color;
            let label;

            if (pointData.arretId === baseId) {
                color = '#FF0000'; // Rouge pour la base
                label = 'B';       // Étiquette pour la base
            } else {
                color = pointData.ramasse ? '#00FF00' : '#FFA500';
                label = pointData.order;
            }

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
                        font-size: 14px;">${label}</div>`,
                iconSize: [30, 30],
                iconAnchor: [15, 15]
            });

            const marker = L.marker(pointData.coords, { icon: numberIcon, pane: 'itinerairePane' }).addTo(map);
            currentMarkers.push(marker);
        });

        // **Identifier le dernier arrêt ramassé**
        let lastRamasseIndex = -1;
        for (let i = groupedPoints.length - 1; i >= 0; i--) {
            if (groupedPoints[i].ramasse) {
                lastRamasseIndex = i;
                break;
            }
        }

        if (lastRamasseIndex === -1) {
            // Aucun arrêt ramassé trouvé, commencer à l'arrêt 0
            console.warn('Aucun arrêt ramassé trouvé. L\'animation commencera au premier arrêt.');
            lastRamasseIndex = 0;
        }

        // **Déterminer l'arrêt suivant**
        let nextStopIndex = (lastRamasseIndex + 1) % groupedPoints.length;

        // **Extraire les points pour l'animation**
        let animationPoints;
        if (lastRamasseIndex !== nextStopIndex) {
            if (nextStopIndex > lastRamasseIndex) {
                // Cas normal
                animationPoints = groupedPoints.slice(lastRamasseIndex, nextStopIndex + 1);
            } else {
                // Cas de bouclage
                animationPoints = groupedPoints.slice(lastRamasseIndex).concat(groupedPoints.slice(0, nextStopIndex + 1));
            }
        } else {
            // Si le dernier ramassé est le seul point, on duplique pour l'animation
            animationPoints = [groupedPoints[lastRamasseIndex], groupedPoints[lastRamasseIndex]];
        }

        // **Extraire les coordonnées pour l'animation**
        let animationCoords = animationPoints.map(point => point.coords);

        // **Démarrer l'animation du cycliste**
        if (animationCoords.length >= 2) {
            animateCyclist(map, animationCoords);
        } else {
            console.warn('Pas assez de points pour l\'animation du cycliste.');
        }

        // **Ajuster la vue de la carte pour englober tous les points**
        if (allPoints.length > 0) {
            const bounds = L.latLngBounds(allPoints);
            map.fitBounds(bounds, { padding: [50, 50] });
        }
    }

    function animateCyclist(map, points) {
        if (points.length < 2) {
            console.warn('Pas assez de points pour animer le cycliste.');
            return;
        }

        // Supprimer tout marqueur animé existant
        if (currentAnimatedMarker) {
            map.removeLayer(currentAnimatedMarker);
            currentAnimatedMarker = null;
        }

        // Annuler toute animation en cours
        if (animationFrameId) {
            cancelAnimationFrame(animationFrameId);
            animationFrameId = null;
        }

        // Créer le marqueur du cycliste au point de départ
        let currentIndex = 0;
        let currentPoint = points[currentIndex];
        let nextPoint = points[currentIndex + 1];
        let progress = 0;
        let speed = 0.005; // Ajustez cette valeur pour changer la vitesse

        currentAnimatedMarker = L.circleMarker(currentPoint, {
            color: '#0000FF', // Couleur du marqueur animé
            radius: 7,
            pane: 'animatedPointPane'
        }).addTo(map);

        function animate() {
            if (!currentAnimatedMarker) return;

            progress += speed;

            if (progress >= 1) {
                progress = 0;
                currentIndex++;

                if (currentIndex >= points.length - 1) {
                    // Atteint la fin du chemin, on boucle
                    currentIndex = 0;
                }

                currentPoint = points[currentIndex];
                nextPoint = points[(currentIndex + 1) % points.length];
            }

            // Interpoler entre currentPoint et nextPoint
            let lat = currentPoint[0] + (nextPoint[0] - currentPoint[0]) * progress;
            let lon = currentPoint[1] + (nextPoint[1] - currentPoint[1]) * progress;
            currentAnimatedMarker.setLatLng([lat, lon]);

            // Planifier la prochaine image de l'animation
            animationFrameId = requestAnimationFrame(animate);
        }

        // Démarrer l'animation
        animate();
    }

});
