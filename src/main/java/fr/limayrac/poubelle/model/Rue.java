package fr.limayrac.poubelle.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Rue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    @OneToMany(mappedBy = "rue")
    private List<ArretVoisin> arretVoisins;

    public boolean estImpasse() {
        if (arretVoisins == null || arretVoisins.isEmpty()) {
            return false;
        } else {
            return arretVoisins.size() == 1;
        }
    }

    public boolean estPassage() {
        if (arretVoisins == null || arretVoisins.isEmpty()) {
            return false;
        } else {
            return arretVoisins.size() == 2;
        }
    }

    public boolean estCarrefour() {
        return !estImpasse() && !estPassage();
    }


}
