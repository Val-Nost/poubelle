package fr.limayrac.poubelle.dto;

import fr.limayrac.poubelle.model.Arret;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CheminPossibleDto {
    private Integer compteurCarrefour;
    private Integer compteurArret;
    private List<Arret> arrets;

    public Integer calculDistance() {
        // 500 mètres séparent chaque arrêt
        // On perd 1 kilomètre tous les 20 carrefours
        // On multiplie par deux pour compter la distance du retour en plus
        return ((500*compteurArret) + (compteurCarrefour%20))*2;
    }

    public void removeArret(Arret arret) {
        if (arret.is)
        arrets.remove(arret);
    }
}
