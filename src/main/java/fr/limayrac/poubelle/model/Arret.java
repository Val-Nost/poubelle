package fr.limayrac.poubelle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
public class Arret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;

    @OneToMany(mappedBy = "arret", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ArretRue> arretVoisinsSuivant;

    @OneToMany(mappedBy = "arret", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ArretAdjacent> arretAdjacents;
    private Double latitude;
    private Double longitude;

    private Boolean ramasse;
    private Boolean isAccessible;

    @JsonIgnore
    public Boolean isCarrefour() {
        // Terminaus = 1 arrêt adjacent
        // Liaison = 2 arrets adjacents
        // Carrefour = >2 arrets adjacents
        return  arretAdjacents.size() > 2;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Arret) {
            return id.equals(((Arret) object).id);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.id == null ? 0 : this.id.intValue());
        return result;
    }

    // TODO à supprimer, simplifie le debug
    @Override
    public String toString() {
        return libelle;
    }

}
