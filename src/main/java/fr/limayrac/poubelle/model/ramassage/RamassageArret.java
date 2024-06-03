package fr.limayrac.poubelle.model.ramassage;

import fr.limayrac.poubelle.model.Arret;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class RamassageArret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Ramassage ramassage;
    @ManyToOne
    private Arret arret;
    private Boolean ramasse;
}
