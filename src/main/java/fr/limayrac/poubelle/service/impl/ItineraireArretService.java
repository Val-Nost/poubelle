package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IItineraireArretDao;
import fr.limayrac.poubelle.model.Itineraire;
import fr.limayrac.poubelle.model.ItineraireArret;
import fr.limayrac.poubelle.service.IItineraireArretService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ItineraireArretService implements IItineraireArretService {
    @Autowired
    private IItineraireArretDao itineraireArretDao;
    @Override
    public ItineraireArret save(ItineraireArret itineraireArret) {
        return itineraireArretDao.save(itineraireArret);
    }
    @Override
    public void deleteByItineraire(Itineraire itineraire) {
        itineraireArretDao.deleteAllByItineraire(itineraire);
    }
}
