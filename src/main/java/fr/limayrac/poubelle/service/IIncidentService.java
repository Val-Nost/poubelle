package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.Incident;
import fr.limayrac.poubelle.model.ramassage.Ramassage;

import java.util.List;

public interface IIncidentService {
    Incident save(Incident incident);

    List<Incident> findByRamassage(Ramassage ramassage);
}
