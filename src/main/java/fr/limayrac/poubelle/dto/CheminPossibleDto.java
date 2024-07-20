package fr.limayrac.poubelle.dto;

import fr.limayrac.poubelle.model.Arret;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CheminPossibleDto {
    private Integer compteurCarrefour;
    private Integer compteurArret;
    private List<Arret> arrets;

    public CheminPossibleDto() {
        compteurCarrefour = 0;
        compteurArret = 0;
        arrets = new ArrayList<>();
    }

    public CheminPossibleDto(CheminPossibleDto cheminPossibleDto) {
        compteurCarrefour = cheminPossibleDto.getCompteurCarrefour();
        compteurArret = cheminPossibleDto.getCompteurArret();
        arrets = new ArrayList<>(cheminPossibleDto.getArrets());
    }

    public Double calculDistance() {
        // 500 mètres séparent chaque arrêt
        // On perd 1 kilomètre tous les 20 carrefours
        // On multiplie par deux pour compter la distance du retour en plus
        return ((0.5*compteurArret) + (compteurCarrefour%20))*2;
    }

    public void addArret(Arret arret) {
        if (arret.isCarrefour()) {
            compteurCarrefour++;
        }
        compteurArret++;
        arrets.add(arret);
    }

    public void removeArret() {
        Arret arret = arrets.get(arrets.size()-1);
        if (arret.isCarrefour()) {
            compteurCarrefour--;
        }
        compteurArret--;
        arrets.remove(arret);

    }

    public Arret dernierArret() {
        if (!arrets.isEmpty()) {
            return arrets.get(arrets.size()-1);
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof CheminPossibleDto) {
            return arrets.equals(((CheminPossibleDto) object).arrets);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime;
        if (arrets != null) {
            for (Arret arret : arrets) {
                result += arret.hashCode();
            }
        }
        return result;
    }
}
