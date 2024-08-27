package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.Itineraire;
import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.model.ramassage.Ramassage;

import java.util.Collection;
import java.util.List;

public interface IItineraireService {
    List<Itineraire> findByRamassage(Ramassage ramassage);

    List<Itineraire> saveAll(Collection<Itineraire> itineraire);

    Itineraire findById(Long idItineraire);
    Itineraire findItineraireByCycliste(Ramassage ramassage, Utilisateur utilisateur);
}
