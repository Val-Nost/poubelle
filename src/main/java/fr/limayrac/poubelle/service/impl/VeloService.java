package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IVeloDao;
import fr.limayrac.poubelle.model.StatutVelo;
import fr.limayrac.poubelle.model.Velo;
import fr.limayrac.poubelle.service.IVeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeloService implements IVeloService {
    @Autowired
    private IVeloDao veloDao;
    @Override
    public List<Velo> findByStatut(StatutVelo statutVelo) {
        return veloDao.findByStatutVelo(statutVelo);
    }
}