package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.ArretRue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
public interface IArretRueDao extends CrudRepository<ArretRue, Long> {
    List<ArretRue> findAll();
}
