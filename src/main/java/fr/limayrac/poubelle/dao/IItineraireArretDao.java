package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.Itineraire;
import fr.limayrac.poubelle.model.ItineraireArret;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IItineraireArretDao extends CrudRepository<ItineraireArret, Long> {

//    @Modifying
//    @Query("DELETE from ItineraireArret  where itineraire = :itineraire")
    void deleteAllByItineraire(Itineraire itineraire);
    @Query("SELECT ia from ItineraireArret ia join Itineraire i on ia.itineraire = i join RamassageCyclisteVelo rcv on i.ramassageCyclisteVelo = rcv where ia.datePassage is not null and ia.ordreRamassage is not null and rcv.ramassage = :ramassage")
    List<ItineraireArret> findItineraireArretByDatePassageNotNullAndOrdreRamassageNotNullAndRamassage(Ramassage ramassage);
}
