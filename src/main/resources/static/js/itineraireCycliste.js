function updateItineraire(userId) {
    console.log("test");
    fetch(`/ramassageDerniersArrets/${userId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data) {
                alert('Itinéraire mis à jour avec succès');
                location.reload(); // Rafraîchir la page pour afficher les modifications
            } else {
                alert('Erreur lors de la mise à jour de l\'itinéraire');
            }
        })
        .catch(error => {
            console.error('Erreur:', error);
        });
}