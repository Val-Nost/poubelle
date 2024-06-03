package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IRamassageDao;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.service.IRamassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RamassageService implements IRamassageService {
    @Autowired
    private IRamassageDao ramassageDao;

    @Override
    public List<Ramassage> findByEnCours(Boolean enCours) {
        return ramassageDao.findByEnCours(enCours);
    }

    @Override
    public Ramassage findById(Long id) {
        return ramassageDao.findById(id).orElse(null);
    }

    @Override
    public Ramassage save(Ramassage ramassage) {
        return ramassageDao.save(ramassage);
    }
}
