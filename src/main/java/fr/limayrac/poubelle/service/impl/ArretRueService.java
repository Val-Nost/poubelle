package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IArretRueDao;
import fr.limayrac.poubelle.model.ArretRue;
import fr.limayrac.poubelle.service.IArretRueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArretRueService implements IArretRueService {
    @Autowired
    private IArretRueDao arretRueDao;

    @Override
    public List<ArretRue> findAll() {
        return arretRueDao.findAll();
    }
}