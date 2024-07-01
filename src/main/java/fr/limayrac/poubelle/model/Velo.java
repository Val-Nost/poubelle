package fr.limayrac.poubelle.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter @Setter
public class Velo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double autonomie;
    private Double autonomieMax;
    private Integer charge;
    private Integer chargeMax;
    private StatutVelo statutVelo;
    private Boolean modeRamassage;

    public Boolean chargeMaxAtteint() {
        return Objects.equals(charge, chargeMax);
    }

    public void reduireAutonomie(Double distance) {

    }
}
