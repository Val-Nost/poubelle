package fr.limayrac.poubelle.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ItineraireArret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "itineraire")
    private Itineraire itineraire;
    @ManyToOne
    @JoinColumn(name = "arret")
    private Arret arret;
}
