package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IArretDao;
import fr.limayrac.poubelle.dao.IRueDao;
import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.model.Rue;
import fr.limayrac.poubelle.service.IArretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArretService implements IArretService {
    @Autowired
    private IArretDao arretDao;
    @Autowired
    private IRueDao rueDao;
    @Override
    public List<Arret> findAll() {
        return (List<Arret>) arretDao.findAll();
    }

    @Override
    public Arret findById(Long id) {
        return arretDao.findById(id).orElse(null);
    }

    @Override
    public List<Arret> findFeuille() {
        return arretDao.findTerminusByRue();
    }

    @Override
    public List<Arret> findByRue(Rue rue) {
        return arretDao.findArretsByRue(rue);
    }
}
