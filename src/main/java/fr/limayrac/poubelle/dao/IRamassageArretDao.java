package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.ramassage.RamassageArret;
import org.springframework.data.repository.CrudRepository;

public interface IRamassageArretDao extends CrudRepository<RamassageArret, Long> {
}
