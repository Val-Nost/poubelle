package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IItineraireArretDao;
import fr.limayrac.poubelle.model.ItineraireArret;
import fr.limayrac.poubelle.service.IItineraireArretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItineraireArretService implements IItineraireArretService {
    @Autowired
    private IItineraireArretDao itineraireArretDao;
    @Override
    public ItineraireArret save(ItineraireArret itineraireArret) {
        return itineraireArretDao.save(itineraireArret);
    }
}
