package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.model.ramassage.Passage;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import org.springframework.data.repository.CrudRepository;

public interface IPassageDao extends CrudRepository<Passage, Long> {
    Passage findByRamassageAndArret(Ramassage ramassage, Arret arret);
}
