let etapeCourante = 0;
let suivant = document.querySelector("#suivant");
let precedent = document.querySelector("#precedent");
let etapes = document.querySelectorAll(".etape");
let availableBikes = parseInt(document.querySelector("input[name='nbvelos']").value); // Assurez-vous que c'est un entier
let cyclistesSelected = 0;

function toggleSelection() {
    const checkboxes = document.querySelectorAll('input[name="cyclistes"]');
    const toggleIcon = document.getElementById('toggleIcon');
    const toggleText = document.getElementById('toggleText');
    const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);
    const anyChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);

    if (allChecked) {
        // Si toutes les cases sont cochées, décocher toutes les cases
        checkboxes.forEach(checkbox => checkbox.checked = false);
        cyclistesSelected = 0; // Remettre le compteur à zéro
        toggleIcon.className = 'fa fa-plus'; // Changer l'icône
        toggleText.textContent = 'Tout cocher'; // Mettre à jour le texte
    } else {
        // Cocher les cases restantes jusqu'à la limite des vélos disponibles
        let count = 0;
        checkboxes.forEach(checkbox => {
            // Ne pas toucher aux cases déjà cochées
            if (!checkbox.checked && count < availableBikes) {
                checkbox.checked = true;
                count++;
            }
        });
        cyclistesSelected = Array.from(checkboxes).filter(checkbox => checkbox.checked).length; // Mettre à jour le compteur
        toggleIcon.className = 'fa fa-minus'; // Changer l'icône
        toggleText.textContent = 'Tout décocher'; // Mettre à jour le texte
    }


    updateToggleButton(); // Mettre à jour le bouton de basculement
}

// Fonction pour mettre à jour le compteur de cyclistes sélectionnés
function onSelect(checkbox) {
    if (checkbox.checked) {
        cyclistesSelected++;
    } else {
        cyclistesSelected--;
    }

    updateToggleButton();
}

// Vérifiez si le nombre de cyclistes sélectionnés dépasse la limite de vélos
function validateSelection() {
    const checkboxes = document.querySelectorAll('input[name="cyclistes"]');
    const selectedCheckboxes = Array.from(checkboxes).filter(checkbox => checkbox.checked);

    if (selectedCheckboxes.length > availableBikes) {
        alert(`Vous ne pouvez pas sélectionner plus de ${availableBikes} cyclistes.`);
        selectedCheckboxes[selectedCheckboxes.length - 1].checked = false; // Décocher le dernier coché
        cyclistesSelected--;
    }
}

function updateToggleButton() {
    const checkboxes = document.querySelectorAll('input[name="cyclistes"]');
    const toggleIcon = document.getElementById('toggleIcon');
    const toggleText = document.getElementById('toggleText');

    const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);
    const anyChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);

    if (allChecked) {
        toggleIcon.className = 'fa fa-minus'; // Si toutes les cases sont cochées
        toggleText.textContent = 'Tout décocher'; // Mettre à jour le texte
    } else if (anyChecked) {
        toggleIcon.className = 'fa fa-plus'; // Si au moins une case est cochée
        toggleText.textContent = 'Tout cocher'; // Mettre à jour le texte
    } else {
        toggleIcon.className = 'fa fa-plus';
        toggleText.textContent = 'Tout cocher';
    }
}

// Fonction pour obtenir des indices aléatoires
function getRandomIndexes(total, count) {
    const indexes = new Set();
    while (indexes.size < Math.min(count, total)) {
        indexes.add(Math.floor(Math.random() * total));
    }
    return Array.from(indexes);
}

// Ajout d'un écouteur d'événements sur les cases à cocher pour valider la sélection
document.querySelectorAll('input[name="cyclistes"]').forEach(checkbox => {
    checkbox.addEventListener('change', () => {
        onSelect(checkbox);
        validateSelection();
    });
});


const form = document.getElementById('myForm');
const spinner = document.getElementById('spinner');

form.addEventListener('submit', function (e) {
    e.preventDefault();
    spinner.style.display = 'block';
    form.classList.add('loading');

    const formData = new FormData(form);

    fetch(form.action, {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            console.log('Succès:', data);
            spinner.style.display = 'none';
            form.classList.remove('loading');
        })
        .catch(error => {
            console.error('Erreur:', error);
            spinner.style.display = 'none';
            form.classList.remove('loading');
        });
});