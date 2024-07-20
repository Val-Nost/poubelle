package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IItineraireDao;
import fr.limayrac.poubelle.model.Itineraire;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.service.IItineraireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItineraireService implements IItineraireService {
    @Autowired
    private IItineraireDao itineraireDao;

    @Override
    public List<Itineraire> findByRamassage(Ramassage ramassage) {
        return itineraireDao.findByRamassage(ramassage);
    }
}
