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
    private String libelle;
    @ManyToOne
    private Utilisateur cycliste;
    @ManyToOne
    private Velo velo;
    @ManyToOne
    private Arret arret;
    @ManyToOne
    private Ramassage ramassage;
}
