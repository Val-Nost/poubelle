package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IArretDao;
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

    @Override
    public List<Arret> findByRueAndIsAccessibleAndRamasse(Rue rue, Boolean accessible, Boolean ramasse) {
        return arretDao.findByRueAndAccessibleAndRamasse(rue, accessible, ramasse);
    }

    @Override
    public Arret save(Arret arret) {
        return arretDao.save(arret);
    }

    @Override
    public List<Arret> findByAccessible(boolean accessible) {
        return arretDao.findByIsAccessible(accessible);
    }

    @Override
    public List<Arret> saveAll(List<Arret> arrets) {
        return (List<Arret>) arretDao.saveAll(arrets);
    }
}
