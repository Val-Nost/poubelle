package fr.limayrac.poubelle.model;

import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Itineraire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ramassageCyclisteVelo")
    private RamassageCyclisteVelo ramassageCyclisteVelo;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="itineraire_arret",
            joinColumns = @JoinColumn( name="itineraire"),
            inverseJoinColumns = @JoinColumn( name="arret")
    )
    private List<Arret> arrets;

    public Itineraire() {
        arrets = new ArrayList<>();
    }
}
