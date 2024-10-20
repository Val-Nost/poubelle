package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.Itineraire;
import fr.limayrac.poubelle.model.ItineraireArret;

public interface IItineraireArretService {
    ItineraireArret save(ItineraireArret itineraireArret);

    void deleteByItineraire(Itineraire itineraire);
}
