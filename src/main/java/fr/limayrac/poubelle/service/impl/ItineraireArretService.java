package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IItineraireArretDao;
import fr.limayrac.poubelle.model.Itineraire;
import fr.limayrac.poubelle.model.ItineraireArret;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.service.IItineraireArretService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<ItineraireArret> findItineraireArretByDatePassageNotNullAndOrdreRamassageNotNullAndRamassage(Ramassage ramassage) {
        return itineraireArretDao.findItineraireArretByDatePassageNotNullAndOrdreRamassageNotNullAndRamassage(ramassage);
    }
}
