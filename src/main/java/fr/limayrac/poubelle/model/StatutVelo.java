package fr.limayrac.poubelle.model;

import lombok.Getter;

@Getter
public enum StatutVelo {
    UTILISABLE(0, "Utilisable"),
    EN_COURS_UTILISATION(1, "En cours d'utilisation"),
    EN_MAINTENANCE(2, "En maintenance"),;

    private final Integer id;
    private final String libelle;

    StatutVelo(Integer id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }
}