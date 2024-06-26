package fr.limayrac.poubelle.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Arret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;

    @OneToMany(mappedBy = "arret", fetch = FetchType.EAGER)
    private List<ArretVoisin> arretVoisinsSuivant;
    @OneToMany(mappedBy = "arretSuivant", fetch = FetchType.EAGER)
    private List<ArretVoisin> arretVoisinsPrecedent;
    private Boolean ramasse;

    public Boolean isCarrefour() {
        // TODO demander si un arrêt terminus (qui n'a pas d'arrêt suivant) mais qui à deux arrêts précédents, est-il considéré comme un carrefour ?
        return arretVoisinsSuivant.size() <= 1 && arretVoisinsPrecedent.size() <= 1;
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


}
