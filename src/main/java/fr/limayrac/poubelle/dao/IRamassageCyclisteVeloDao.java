package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;
import org.springframework.data.repository.CrudRepository;

public interface IRamassageCyclisteVeloDao extends CrudRepository<RamassageCyclisteVelo, Long> {
}
