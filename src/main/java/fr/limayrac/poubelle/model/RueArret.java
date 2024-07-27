package fr.limayrac.poubelle.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RueArret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rue_id")
    private Rue rue;

    @ManyToOne
    @JoinColumn(name = "arret_id")
    private Arret arret;
}
