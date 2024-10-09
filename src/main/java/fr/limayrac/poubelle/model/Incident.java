package fr.limayrac.poubelle.model;

import fr.limayrac.poubelle.model.ramassage.Ramassage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TypeIncident typeIncident;
    @ManyToOne
    @JoinColumn(name = "cycliste")
    private Utilisateur cycliste;
    @ManyToOne
    @JoinColumn(name = "velo")
    private Velo velo;
    @ManyToOne
    @JoinColumn(name = "arret")
    private Arret arret;
    @ManyToOne
    @JoinColumn(name = "ramassage")
    private Ramassage ramassage;
}
