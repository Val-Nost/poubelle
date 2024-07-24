package fr.limayrac.poubelle.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ArretAdjacent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @ManyToOne
    @JoinColumn(name = "arret")
    @JsonBackReference
    private Arret arret;
    @ManyToOne
    @JoinColumn(name = "rue")
    private Rue rue;
    @ManyToOne
    @JoinColumn(name = "arretAdjacent")
//    @JsonBackReference
    private Arret arretAdjacent;
}
