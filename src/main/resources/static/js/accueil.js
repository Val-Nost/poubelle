document.addEventListener("DOMContentLoaded", function () {
    const table = document.getElementById('myTable');
    const headers = table.querySelectorAll('th');

    const popup = document.getElementById('popup');
    const confirmDeleteBtn = document.getElementById('confirm-delete');
    const cancelDeleteBtn = document.getElementById('cancel-delete');
    const closePopupBtn = document.getElementById('close-popup');
    let formToDelete = null;

    // Récupérer le message d'erreur
    var errorMessage = document.getElementById('error-message');
    if (errorMessage) {
        var message = errorMessage.textContent.trim();
        if (message) {
            Swal.fire({
                icon: "info",
                title: "Information ramassage d'un utilisateur",
                text: message,
                confirmButtonText: 'OK', // Texte du bouton de confirmation
                confirmButtonColor: '#f57c00'
            });
        }
    }

    document.querySelectorAll('.open-popup').forEach(button => {
        button.addEventListener('click', (event) => {
            const userId = event.target.closest('.open-popup').getAttribute('data-id');
            formToDelete = document.getElementById(`delete-form-${userId}`);
            popup.classList.add('is-visible');
        });
    });

    // Confirmer la suppression
    confirmDeleteBtn.addEventListener('click', (event) => {
        event.preventDefault();
        if (formToDelete) {
            formToDelete.submit();
        }
    });

    const errorMessageElement = document.querySelector('.error-message p');
    if (errorMessageElement && errorMessageElement.textContent.trim() !== '') {
        Swal.fire({
            icon: 'info',
            title: 'Problème de suppression d\'un utilisateur',
            text: errorMessageElement.textContent.trim(),
            timer: 3000,
            timerProgressBar: true,
            didOpen: () => {
                Swal.showLoading();
            },
            willClose: () => {
                Swal.hideLoading();
            }
        });
    }

    const closePopup = () => {
        popup.classList.remove('is-visible');
        formToDelete = null;
    };

    closePopupBtn.addEventListener('click', (event) => {
        event.preventDefault();
        closePopup();
    });

    cancelDeleteBtn.addEventListener('click', (event) => {
        event.preventDefault();
        closePopup();
    });

    window.addEventListener('click', (event) => {
        if (event.target === popup) {
            closePopup();
        }
    });

    headers.forEach(header => {
        header.addEventListener('click', () => {
            const column = header.cellIndex;
            let order = header.dataset.order;

            const sortedRows = Array.from(table.rows).slice(1);
            const sortMethod = (a, b) => {
                const aValue = a.cells[column].textContent.trim();
                const bValue = b.cells[column].textContent.trim();
                return order === 'asc' ? aValue.localeCompare(bValue) : bValue.localeCompare(aValue);
            };

            sortedRows.sort(sortMethod);

            header.dataset.order = order === 'asc' ? 'desc' : 'asc';

            table.tBodies[0].innerHTML = '';
            table.tBodies[0].append(...sortedRows);

            headers.forEach(h => {
                h.classList.remove('bg-gray-200');
            });

            header.classList.add('bg-gray-200');
        });
    });
});