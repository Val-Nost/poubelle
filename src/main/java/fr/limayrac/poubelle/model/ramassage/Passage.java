package fr.limayrac.poubelle.model.ramassage;

import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.model.Utilisateur;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Passage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ramassage")
    private Ramassage ramassage;
    @ManyToOne
    @JoinColumn(name = "arret")
    private Arret arret;
    private Boolean ramasse;
    private LocalDateTime datePassage;
    @ManyToOne
    @JoinColumn(name = "ramasseur")
    private Utilisateur ramasseur;
}
