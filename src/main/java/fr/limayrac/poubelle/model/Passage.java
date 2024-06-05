package fr.limayrac.poubelle.model;

import java.time.LocalDateTime;

public class Passage {
    private Long id;
    private LocalDateTime localDateTime;
    private Utilisateur ramasseur;
    private Arret arret;
}
