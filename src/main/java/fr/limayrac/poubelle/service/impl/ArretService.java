package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IArretDao;
import fr.limayrac.poubelle.model.Arret;
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
}
