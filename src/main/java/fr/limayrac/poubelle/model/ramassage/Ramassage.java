package fr.limayrac.poubelle.model.ramassage;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Ramassage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "ramassage")
    private List<RamassageCyclisteVelo> ramassageCyclisteVelos;
    private Boolean enCours;
    @OneToMany(mappedBy = "ramassage")
    private List<RamassageArret> ramassageArrets;
}
