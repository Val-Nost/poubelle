package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IItineraireDao;
import fr.limayrac.poubelle.model.Itineraire;
import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;
import fr.limayrac.poubelle.service.IItineraireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ItineraireService implements IItineraireService {
    @Autowired
    private IItineraireDao itineraireDao;

    @Override
    public List<Itineraire> findByRamassage(Ramassage ramassage) {
        return itineraireDao.findByRamassage(ramassage);
    }

    @Override
    public List<Itineraire> saveAll(Collection<Itineraire> itineraire) {
        return (List<Itineraire>) itineraireDao.saveAll(itineraire);
    }

    @Override
    public Itineraire findById(Long idItineraire) {
        return itineraireDao.findById(idItineraire).orElse(null);
    }

    @Override
    public Itineraire findItineraireByCycliste(Ramassage ramassage, Utilisateur utilisateur) {
        return itineraireDao.findByRamassageAndCycliste(ramassage, utilisateur);
    }
    @Override
    public Itineraire findByRamassageCyclisteVelo(RamassageCyclisteVelo ramassageCyclisteVelo) {
        return itineraireDao.findByRamassageCyclisteVelo(ramassageCyclisteVelo);
    }
}
