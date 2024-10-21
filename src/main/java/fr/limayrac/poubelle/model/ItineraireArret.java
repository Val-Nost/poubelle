package fr.limayrac.poubelle.model;

import fr.limayrac.poubelle.utils.DateUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private Integer ordrePassage;
    private Integer ordreRamassage;
    private LocalDateTime datePassage;
    public String getDatePassageFormatted() {
        if (datePassage != null) {
            return DateUtils.formatDateTime("dd/MM/yyyy hh:mm:ss", datePassage);
        } else {
            return null;
        }
    }
}
