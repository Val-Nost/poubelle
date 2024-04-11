package fr.limyrac.poubelle.model;

import lombok.Getter;

@Getter
public enum Role {
    Cycliste(0, "Cycliste"),
    Gestionnaire(1, "Gestionnaire"),
    RH(2, "Responsable Resource Humaines"),
    Admin(3, "Administrateur");

    private final Integer id;
    private final String libelle;

    Role(Integer id, String libelle) {
        this.id= id;
        this.libelle = libelle;
    }

}
