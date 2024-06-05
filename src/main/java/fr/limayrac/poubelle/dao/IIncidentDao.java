package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.Incident;
import org.springframework.data.repository.CrudRepository;

public interface IIncidentDao extends CrudRepository<Incident, Long> {
}
