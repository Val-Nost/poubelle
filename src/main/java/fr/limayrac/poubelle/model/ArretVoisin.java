package fr.limayrac.poubelle.model;

import jakarta.persistence.*;

@Entity
public class ArretVoisin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "arret")
    private Arret arret;
    @ManyToOne
    @JoinColumn(name = "rue")
    private Rue rue;
    private Integer ordre;
}
