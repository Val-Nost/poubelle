package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.Rue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRueDao extends CrudRepository<Rue, Long> {
    List<Rue> findByOrderById();
}
