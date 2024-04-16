package fr.limayrac.poubelle.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {
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

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
