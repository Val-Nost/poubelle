package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.RueArret;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRueArretDao extends CrudRepository<RueArret, Long> {
    List<RueArret> findByOrderById();
}
