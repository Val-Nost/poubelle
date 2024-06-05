package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IRamassageCyclisteVeloDao;
import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;
import fr.limayrac.poubelle.service.IRamassageCyclisteVeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RamassageCyclisteVeloService implements IRamassageCyclisteVeloService {
    @Autowired
    private IRamassageCyclisteVeloDao ramassageCyclisteVeloDao;
    @Override
    public RamassageCyclisteVelo save(RamassageCyclisteVelo ramassageCyclisteVelo) {
        return ramassageCyclisteVeloDao.save(ramassageCyclisteVelo);
    }

    @Override
    public List<RamassageCyclisteVelo> saveAll(List<RamassageCyclisteVelo> ramassageCyclisteVelos) {
        return (List<RamassageCyclisteVelo>) ramassageCyclisteVeloDao.saveAll(ramassageCyclisteVelos);
    }
}
