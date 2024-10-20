package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.Itineraire;
import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;

import java.util.Collection;
import java.util.List;

public interface IItineraireService {
    List<Itineraire> findByRamassage(Ramassage ramassage);

    List<Itineraire> saveAll(Collection<Itineraire> itineraire);

    Itineraire findById(Long idItineraire);
    Itineraire findItineraireByCycliste(Ramassage ramassage, Utilisateur utilisateur);

    Itineraire findByRamassageCyclisteVelo(RamassageCyclisteVelo ramassageCyclisteVelo);

    void calculItineraire(Ramassage ramassage, List<Utilisateur> utilisateurs);

    void recalculItineraire(Ramassage ramassage);

    void deleteAll(List<Itineraire> itineraires);

    Itineraire save(Itineraire itineraire);

    void deleteByRamassageCyclisteVelo(RamassageCyclisteVelo ramassageCyclisteVelo);

    void delete(Itineraire itineraire);
}
