package fr.limayrac.poubelle.model;

import lombok.Getter;

@Getter
public enum TypeIncident {
    ACCIDENT_CORPOREL(0, "Accident corporel"),
    CASSE_VELO(1, "Casse vélo"),
    ARRET_INACCESSIBLE(2, "Arrêt inaccessible"),;

    private final Integer id;
    private final String libelle;

    TypeIncident(Integer id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }
}
