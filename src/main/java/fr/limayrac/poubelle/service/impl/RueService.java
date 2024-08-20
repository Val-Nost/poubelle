package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IRueDao;
import fr.limayrac.poubelle.model.Rue;
import fr.limayrac.poubelle.service.IRueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RueService implements IRueService {
    @Autowired
    private IRueDao rueDao;

    @Override
    public List<Rue> findAllOrderById() {
        return rueDao.findByOrderById();
    }

    @Override
    public List<Rue> findAll() {
        return (List<Rue>) rueDao.findAll();
    }

    @Override
    public Rue findById(Long id) {
        return rueDao.findById(id).orElse(null);
    }
}
