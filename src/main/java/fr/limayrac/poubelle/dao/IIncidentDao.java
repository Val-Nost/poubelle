package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.Incident;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IIncidentDao extends CrudRepository<Incident, Long> {
    List<Incident> findByRamassage(Ramassage ramassage);
}
