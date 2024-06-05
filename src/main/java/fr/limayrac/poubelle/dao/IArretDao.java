package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.Arret;
import org.springframework.data.repository.CrudRepository;

public interface IArretDao extends CrudRepository<Arret, Long> {
}
