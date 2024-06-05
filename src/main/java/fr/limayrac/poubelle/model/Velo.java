package fr.limayrac.poubelle.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Velo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer autonomie;
    private Integer autonomieMax;
    private Integer charge;
    private Integer chargeMax;
    private StatutVelo statutVelo;
    private Boolean modeRamassage;
}
