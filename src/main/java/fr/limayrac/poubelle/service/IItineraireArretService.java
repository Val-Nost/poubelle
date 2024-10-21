package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.Itineraire;
import fr.limayrac.poubelle.model.ItineraireArret;
import fr.limayrac.poubelle.model.ramassage.Ramassage;

import java.util.List;

public interface IItineraireArretService {
    ItineraireArret save(ItineraireArret itineraireArret);

    void deleteByItineraire(Itineraire itineraire);

    List<ItineraireArret> findItineraireArretByDatePassageNotNullAndOrdreRamassageNotNullAndRamassage(Ramassage ramassage);
}
