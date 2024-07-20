package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.Itineraire;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IItineraireDao extends CrudRepository<Itineraire, Long> {
    @Query("select i from Itineraire i where i.ramassageCyclisteVelo.ramassage = :ramassage")
    List<Itineraire> findByRamassage(Ramassage ramassage);
}
