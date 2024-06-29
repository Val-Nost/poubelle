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

    public Boolean arretVoisinsPrecedentIdentique() {
        Arret arret = null;
        for (ArretVoisin arretVoisin : arretVoisinsPrecedent) {
            if (arret == null) {
                // On affecte le premier arrêt qu'on trouve
                arret = arretVoisin.getArret();
            } else {
                // On compare le premier arrêt trouvé avec les suivants
                // Si un seul arrêt est différent, alors les arrêts voisins ne sont pas identiques
                if (!arret.equals(arretVoisin.getArret())) {
                    return false;
                }
            }
        }
        // Si la boucle est fini, alors les arrêts voisins sont tous identiques
        // Attention, si la liste d'arretvoisin est vide, alors true est renvoyé
        return true;
    }

    public Boolean arretVoisinsSuivantIdentique() {
        Arret arret = null;
        for (ArretVoisin arretVoisin : arretVoisinsSuivant) {
            if (arret == null) {
                // On affecte le premier arrêt qu'on trouve
                arret = arretVoisin.getArretSuivant();
            } else {
                // On compare le premier arrêt trouvé avec les suivants
                // Si un seul arrêt est différent, alors les arrêts voisins ne sont pas identiques
                if (!arret.equals(arretVoisin.getArretSuivant())) {
                    return false;
                }
            }
        }
        // Si la boucle est fini, alors les arrêts voisins sont tous identiques
        // Attention, si la liste d'arretvoisin est vide, alors true est renvoyé
        return true;
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

    // TODO à supprimer
    @Override
    public String toString() {
        return libelle;
    }

}
