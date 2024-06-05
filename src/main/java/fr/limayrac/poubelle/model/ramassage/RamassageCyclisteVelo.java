package fr.limayrac.poubelle.model.ramassage;

import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.model.Velo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class RamassageCyclisteVelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ramassage")
    private Ramassage ramassage;
    @ManyToOne
    @JoinColumn(name = "cycliste")
    private Utilisateur cycliste;
    @ManyToOne
    @JoinColumn(name = "velo")
    private Velo velo;
}
