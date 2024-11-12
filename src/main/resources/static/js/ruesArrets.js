// Toggle the display of the list associated with a button
function toggleList(button) {
    const listContainer = button.parentElement;
    $(button).find('i').toggleClass('fa-arrow-down fa-arrow-up');
    listContainer.classList.toggle('expanded');
}

document.addEventListener("DOMContentLoaded", async function() {
    const map = L.map('mynetwork').setView([48.8566, 2.3522], 12);

    ['polylinesPane', 'markersPane', 'itinerairePane', 'animatedPointPane'].forEach((pane, index) => {
        map.createPane(pane).style.zIndex = 400 + index * 50;
    });

    try {
        const arrets = await fetchData('/arrets');
        const rueArrets = await fetchData('/arret-rue');
        const arretsCoords = arrets.reduce((acc, arret) => {
            acc[arret.id] = { lat: arret.latitude, lon: arret.longitude, libelle: arret.libelle };
            return acc;
        }, {});

        const pathname = window.location.pathname;
        if (pathname.includes('/ramassage/ramassageByUser/')) {
            const userId = pathname.split('/').pop();
            const rammassages = await fetchData(`/rammassages/${userId}/arrets`);
            const uniqueStops = Array.from(new Set(rammassages.map(r => r.id))).map(id => {
                return rammassages.find(r => r.id === id);
            });

            afficherItineraire(map, rueArrets, arretsCoords, uniqueStops);
            animateCyclist(map, uniqueStops, arretsCoords); // Add cyclist animation after rendering
        }
    } catch (error) {
        console.error('Erreur lors du chargement des données:', error);
    }
});

async function fetchData(url) {
    const response = await fetch(url);
    return await response.json();
}

function afficherItineraire(map, rueArrets, arretsCoords, uniqueStops) {
    const baseId = 161;

    const groupedPoints = uniqueStops.map((stop, index) => {
        const coords = arretsCoords[stop.id];
        const rueArret = rueArrets.find(r => r.arret.id === stop.id);

        if (rueArret && coords) {
            return {
                coords: [coords.lat, coords.lon],
                arretLibelle: rueArret.arret.libelle,
                rueLibelle: rueArret.rue.libelle,
                order: index + 1,
                arretId: stop.id,
                isRammasse: stop.ramasse || false
            };
        } else {
            console.warn(`Coordinates not found for stop: ${stop.id}`);
            return null;
        }
    }).filter(Boolean);

    const points = groupedPoints.map(p => p.coords);
    createPolyline(map, points, '#00FF00');

    groupedPoints.forEach(point => {
        const isBase = point.arretId === baseId;
        const iconColor = isBase ? '#FF0000' : (point.isRammasse ? '#00FF00' : '#FFA500');
        createMarker(map, point.coords, point.order, iconColor, 'itinerairePane');
    });

    displayStopsList(groupedPoints);
    fitMapBounds(map, points);
}

function createPolyline(map, points, color) {
    L.polyline(points, { color, pane: 'polylinesPane' }).addTo(map);
}

function createMarker(map, coords, order, color, pane) {
    const icon = L.divIcon({
        className: 'number-icon',
        html: `<div style="width: 30px; height: 30px; background-color: ${color};
                color: black; text-align: center; line-height: 30px;
                border-radius: 50%; border: 2px solid #000;
                font-weight: bold; font-size: 14px;">${order}</div>`,
        iconSize: [30, 30],
        iconAnchor: [15, 15]
    });
    L.marker(coords, { icon, pane }).addTo(map);
}

function displayStopsList(points) {
    const arretsList = document.getElementById('arrets-list');
    arretsList.innerHTML = '';
    let currentRue = null;

    points.forEach(point => {
        if (point.rueLibelle !== currentRue) {
            const rueListItem = document.createElement('li');
            rueListItem.textContent = point.rueLibelle;
            rueListItem.classList.add('changement-de-rue');
            arretsList.appendChild(rueListItem);
            currentRue = point.rueLibelle;
        }

        const arretListItem = document.createElement('li');
        arretListItem.textContent = point.arretLibelle;
        if (point.isRammasse) arretListItem.classList.add('rammasse-stop');
        arretsList.appendChild(arretListItem);
    });
}

function fitMapBounds(map, points) {
    if (points.length > 0) {
        const bounds = L.latLngBounds(points);
        map.fitBounds(bounds, { padding: [50, 50] });
    }
}

// Function to animate the cyclist moving between the last collected stop and the next uncollected stop
function animateCyclist(map, uniqueStops, arretsCoords) {
    // Find the last collected stop and the first uncollected stop
    const lastCollectedIndex = uniqueStops.findIndex((stop, i) => stop.ramasse && (!uniqueStops[i + 1] || !uniqueStops[i + 1].ramasse));
    const nextUncollectedIndex = lastCollectedIndex + 1;

    // Ensure we have both a collected and an uncollected stop for the animation
    if (lastCollectedIndex === -1 || nextUncollectedIndex >= uniqueStops.length) {
        console.warn('No valid start and end points for the animation.');
        return;
    }

    const startCoords = [
        arretsCoords[uniqueStops[lastCollectedIndex].id].lat,
        arretsCoords[uniqueStops[lastCollectedIndex].id].lon
    ];
    const endCoords = [
        arretsCoords[uniqueStops[nextUncollectedIndex].id].lat,
        arretsCoords[uniqueStops[nextUncollectedIndex].id].lon
    ];

    // Create a red marker for the cyclist
    const cyclistMarker = L.circleMarker(startCoords, {
        color: '#FF0000',
        radius: 7,
        pane: 'animatedPointPane'
    }).addTo(map);

    // Function to animate the marker position between two points
    function moveToNextStop() {
        let progress = 0;
        const steps = 100; // Number of animation steps
        const interval = 20; // Milliseconds per step

        const animationInterval = setInterval(() => {
            progress += 1 / steps;
            if (progress >= 1) {
                progress = 1;
                clearInterval(animationInterval);
                // Reset marker to start after reaching the next stop
                cyclistMarker.setLatLng(startCoords);
                moveToNextStop(); // Repeat the animation
            }

            // Interpolate latitude and longitude between start and end points
            const lat = startCoords[0] + (endCoords[0] - startCoords[0]) * progress;
            const lon = startCoords[1] + (endCoords[1] - startCoords[1]) * progress;
            cyclistMarker.setLatLng([lat, lon]);
        }, interval);
    }

    moveToNextStop(); // Start the animation
}
