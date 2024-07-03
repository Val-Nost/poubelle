package fr.limayrac.poubelle.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ArretAdjacent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "arret")
    private Arret arret;
    @ManyToOne
    @JoinColumn(name = "rue")
    private Rue rue;
    @ManyToOne
    @JoinColumn(name = "arretAdjacent")
    private Arret arretAdjacent;
}
