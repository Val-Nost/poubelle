package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.Itineraire;
import fr.limayrac.poubelle.model.ramassage.Ramassage;

import java.util.List;

public interface IItineraireService {
    List<Itineraire> findByRamassage(Ramassage ramassage);
}
