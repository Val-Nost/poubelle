package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IPassageDao;
import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.model.ramassage.Passage;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.service.IPassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassageService implements IPassageService {
    @Autowired
    private IPassageDao passageDao;
    @Override
    public List<Passage> saveAll(List<Passage> passages) {
        return (List<Passage>) passageDao.saveAll(passages);
    }

    @Override
    public Passage finfByRamassageAndArret(Ramassage ramassage, Arret arret) {
        return passageDao.findByRamassageAndArret(ramassage, arret);
    }
}
