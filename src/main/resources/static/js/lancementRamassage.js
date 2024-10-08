let etapeCourante = 0
let suivant = document.querySelector("#suivant")
let precedent = document.querySelector("#precedent")
let etapes = document.querySelectorAll(".etape")
let nbVelos = document.querySelector("input[name='nbvelos']").value
let cyclistesSelected = 0;
let cyclistes = []

function onSelect(element) {
    if (element.checked) {
        if (cyclistesSelected < nbVelos) {
            cyclistesSelected++;
            cyclistes.push(element)
        } else {
            event.preventDefault()
            alert("Vous ne pouvez plus sélectionner de cycliste, il n'y a pas assez de vélos")
        }
    } else {
        if (cyclistesSelected > 0) {
            cyclistesSelected--;
            cyclistes.splice(cyclistes.indexOf(element))
        }
    }
}

// suivant.addEventListener('click', (event) => {
//     switch (etapeCourante) {
//         case 0:
//             // On active precedent
//             precedent.disabled = false
//             etapeCourante++;
//
//             let etape2 = document.querySelector("#Etape2");
//             cyclistes.forEach((element) => {
//
//             })
//             break;
//         case 1:
//
//             break;
//         case etapes.length-1:
//             // On atteint la dernière étape
//             suivant.disabled = true
//             break;
//     }
//
//     // On montre l'étape suivante
//     if (etapeCourante < etapes.length) {
//         // On cache l'étape courante
//         etapes[etapeCourante].classList.add("cache");
//         etapeCourante++;
//         etapes[etapeCourante].classList.remove("cache");
//     }
// })
//
// precedent.addEventListener('click', () => {
//     // Si on est à la dernière étape on réactive suivant
//     suivant.disabled = false
//
//     if (etapeCourante > 0) {
//         etapes[etapeCourante].classList.add("cache");
//         etapeCourante--;
//         etapes[etapeCourante].classList.remove("cache");
//     }
//
//     // On atteint la première étape
//     if (etapeCourante === 0) {
//         precedent.disabled = true
//     }
// })