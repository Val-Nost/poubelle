package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IRueArretDao;
import fr.limayrac.poubelle.model.RueArret;
import fr.limayrac.poubelle.service.IRueArretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RueArretService implements IRueArretService {
    @Autowired
    private IRueArretDao rueArretDao;

    @Override
    public List<RueArret> findAll() {
        return (List<RueArret>) rueArretDao.findAll();
    }

    @Override
    public RueArret findById(Long id) {
        return rueArretDao.findById(id).orElse(null);
    }
}