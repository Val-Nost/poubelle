function checkPasswordMatch() {
    var password = document.getElementById('password').value;
    var confirmPasswordField = document.getElementById('confirmPassword');

    if (password !== confirmPasswordField.value) {
        confirmPasswordField.style.borderColor = 'red';
    } else {
        confirmPasswordField.style.borderColor = 'green';
    }
}

function onSubmit(event) {
    var password = document.getElementById('password');
    var confirmPasswordField = document.getElementById('confirmPassword');
    if (password.value !== confirmPasswordField.value) {
        event.preventDefault()
    }
}