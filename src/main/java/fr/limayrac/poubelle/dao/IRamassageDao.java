package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.ramassage.Ramassage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRamassageDao extends CrudRepository<Ramassage, Long> {
    List<Ramassage> findByEnCours(Boolean enCours);
}
