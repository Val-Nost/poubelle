package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IIncidentDao;
import fr.limayrac.poubelle.model.Incident;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.service.IIncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentService implements IIncidentService {
    @Autowired
    private IIncidentDao incidentDao;
    @Override
    public Incident save(Incident incident) {
        return incidentDao.save(incident);
    }
    @Override
    public List<Incident> findByRamassage(Ramassage ramassage) {
        return incidentDao.findByRamassage(ramassage);
    }
}
