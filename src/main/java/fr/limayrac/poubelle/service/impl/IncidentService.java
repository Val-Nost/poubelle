package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IIncidentDao;
import fr.limayrac.poubelle.model.Incident;
import fr.limayrac.poubelle.service.IIncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncidentService implements IIncidentService {
    @Autowired
    private IIncidentDao incidentDao;
    @Override
    public Incident save(Incident incident) {
        return incidentDao.save(incident);
    }
}
