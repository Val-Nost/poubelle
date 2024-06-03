package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.StatutVelo;
import fr.limayrac.poubelle.model.Velo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IVeloDao extends CrudRepository<Velo, Long> {
    List<Velo> findByStatutVelo(StatutVelo statutVelo);
}
