package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.ramassage.Passage;
import org.springframework.data.repository.CrudRepository;

public interface IPassageDao extends CrudRepository<Passage, Long> {
}
