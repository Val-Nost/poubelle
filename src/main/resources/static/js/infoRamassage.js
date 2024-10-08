const urlParams = new URLSearchParams(window.location.search);
if (urlParams.has("tab")) {
    document.getElementById(urlParams.get("tab")).style.display = "flex"
} else {
    document.getElementById("Itinéraire").style.display = 'flex'
}

let ajoutVelo = document.querySelector('#ajoutCyclisteVelo')

let veloConcerne = document.querySelector('#veloConcerne');
let veloRemplacant = document.querySelector('#veloRemplacant');

let cyclisteConcerne = document.querySelector('#cyclisteConcerne');
let cyclisteRemplacant = document.querySelector('#cyclisteRemplacant');

let divVelo = document.querySelector("#veloRemplacantDiv")
let divCycliste = document.querySelector("#cyclisteRemplacantDiv")
let divArret = document.querySelector("#arretDiv")

function openTab(evt, tapName) {
    const urlParams = new URLSearchParams(window.location.search);
    urlParams.set("tab", tapName)
    window.location.search = urlParams;
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace("active", "");
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(tapName).style.display = "flex";
    evt.currentTarget.className += "active";
}

// Tab Cyclistes
function addVelo(button) {
    if (button.textContent === "Ajouter") {
        button.innerHTML = "Annuler"
        ajoutVelo.style.display = 'block'
    } else {
        button.innerHTML = "Ajouter"
        ajoutVelo.style.display = 'none'
    }
}
function addVeloValidate(evt) {
    let veloRestant = document.querySelectorAll('#veloRestant option')
    let cyclisteRestant = document.querySelectorAll('#cyclisteRestant option')

    let message = ""
    console.log(message)
    if (veloRestant.length === 0) {
        message = message.concat("Aucun vélo n'est actuellement disponible\n")
    }
    if (cyclisteRestant.length === 0) {
        message = message.concat("Aucun cycliste n'est actuellement disponible")
    }
    if (veloRestant.length === 0 || cyclisteRestant.length === 0) {
        evt.preventDefault()
        alert(message)
    }
}
// Tab Incidents
function selectVelo(element) {
    if (element.value === "") {
        divVelo.style.display = 'none';
        veloRemplacant.innerHTML = ""
    } else {
        divVelo.style.display = 'block';
        veloRemplacant.innerHTML = ""
        for (var i = 0; i < veloConcerne.length; i++){
            var option = veloConcerne.options[i];
            if (element.value !== veloConcerne.options[i].value) {
                veloRemplacant.appendChild(option.cloneNode(true))
            }
        }
    }
}
function selectCycliste(element) {
    if (element.value === "") {
        divCycliste.style.display = 'none';
        cyclisteRemplacant.innerHTML = ""
    } else {
        divCycliste.style.display = 'block';
        cyclisteRemplacant.innerHTML = ""
        for (var i = 0; i < cyclisteConcerne.length; i++){
            var option = cyclisteConcerne.options[i];
            if (element.value !== cyclisteConcerne.options[i].value) {
                cyclisteRemplacant.appendChild(option.cloneNode(true))
            }
        }
    }
}
function selectArret(element) {
    if (element.value === "") {
        divArret.style.display = 'none'
    } else {
        divArret.style.display = 'block'
    }
}