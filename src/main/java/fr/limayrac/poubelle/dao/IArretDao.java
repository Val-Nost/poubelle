package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.model.Rue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IArretDao extends CrudRepository<Arret, Long> {
    @Query("select aa.arret from ArretAdjacent aa group by aa.arret having count(aa.arretAdjacent) = 1")
    List<Arret> findTerminusByRue();

    @Query("select a from Arret a join ArretRue av on av.arret = a where av.rue = :rue")
    List<Arret> findArretsByRue(Rue rue);

    @Query("select a from Arret a join ArretRue av on av.arret = a where av.rue = :rue and a.isAccessible = :isAccessible and a.ramasse = :ramasse")
    List<Arret> findByRueAndAccessibleAndRamasse(Rue rue, Boolean isAccessible, Boolean ramasse);

    List<Arret> findByIsAccessible(boolean accessible);
}