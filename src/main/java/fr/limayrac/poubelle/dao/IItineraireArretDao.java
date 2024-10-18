package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.Itineraire;
import fr.limayrac.poubelle.model.ItineraireArret;
import org.springframework.data.repository.CrudRepository;

public interface IItineraireArretDao extends CrudRepository<ItineraireArret, Long> {

//    @Modifying
//    @Query("DELETE from ItineraireArret  where itineraire = :itineraire")
    void deleteAllByItineraire(Itineraire itineraire);
}
