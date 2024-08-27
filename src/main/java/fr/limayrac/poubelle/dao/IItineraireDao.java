package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.Itineraire;
import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IItineraireDao extends CrudRepository<Itineraire, Long> {
    @Query("select i from Itineraire i where i.ramassageCyclisteVelo.ramassage = :ramassage")
    List<Itineraire> findByRamassage(Ramassage ramassage);

    @Query("select i from Itineraire i join RamassageCyclisteVelo rcv on i.ramassageCyclisteVelo = rcv where rcv.ramassage = :ramassage and rcv.cycliste = :cycliste")
    Itineraire findByRamassageAndCycliste(Ramassage ramassage, Utilisateur cycliste);
}
