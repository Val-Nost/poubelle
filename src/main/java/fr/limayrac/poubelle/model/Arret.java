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
    private List<ArretVoisin> arretVoisins;
    private Boolean ramasse;

    public Boolean isCarrefour() {
        if (arretVoisins == null) {
            return null;
        }
        return arretVoisins.size() > 1;
    }



}
