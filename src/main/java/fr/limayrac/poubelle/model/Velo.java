package fr.limayrac.poubelle.model;

import fr.limayrac.poubelle.utils.DateUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Setter
public class Velo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    private Double autonomie;
    private Double autonomieMax;
    @Getter
    private Integer charge;
    @Getter
    private Integer chargeMax;
    @Getter
    private StatutVelo statutVelo;
    @Getter
    private Boolean modeRamassage;
    private Integer compteurCarrefour;

    public Double getAutonomie() {
        return autonomie > getAutonomieMax() ? autonomieMax : autonomie;
    }

    public Double getAutonomieMax() {
        // En hiver, le vélo perd 10% d'autonomie
        return DateUtils.isWinter() ? autonomieMax * 0.9 : autonomieMax;
    }

    public void raz() {
        compteurCarrefour = 0;
        charge = 0;
        autonomie = getAutonomieMax();
    }

    public void passageArret(Arret arret) {
        autonomie -= 0.5;
        if (arret.isCarrefour()) {
            if (compteurCarrefour == null) {
                compteurCarrefour = 0;
            }
            compteurCarrefour++;
        }
        // Tous les 20 carrefours, on perd un km d'autonomie
        if (compteurCarrefour % 20 == 0) {
            autonomie -= 1;
        }
    }

    public Boolean chargeMaxAtteint() {
        return Objects.equals(charge, chargeMax);
    }

    public Double autonomieRestante() {
        return autonomieMax - autonomie;
    }



}
