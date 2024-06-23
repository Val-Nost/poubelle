package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.Arret;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IArretDao extends CrudRepository<Arret, Long> {
//    @Query("select a from ArretVoisin a join ArretVoisin av ON a = av.arret where (av.arret not in (select arretSuivant from ArretVoisin where rue = :rue) or av.arretSuivant not in (select arret from ArretVoisin where rue = :rue)) and av.rue = :rue")
//    @Query("select arret from ArretVoisin where (arret not in (select arretSuivant from ArretVoisin where rue = :rue))and rue = :rue union select arretSuivant from ArretVoisin where (arretSuivant not in (select arret from ArretVoisin where rue = :rue)) and rue = :rue")
//    @Query("select a.arret from ArretVoisin a where a.arret not in (select a.arretSuivant from ArretVoisin group by rue.id) group by a.rue.id union select b.arretSuivant from ArretVoisin b where b.arretSuivant not in (select arret from ArretVoisin group by rue.id) group by b.rue.id")
    @Query("select ava.arret " +
            "from ArretVoisin ava " +
            "where ava.arret not in (select arretSuivant from ArretVoisin group by rue) " +
            "group by ava.rue " +
            "union " +
            "select avb.arretSuivant " +
            "from ArretVoisin avb " +
            "where avb.arretSuivant not in (select arret from ArretVoisin group by rue) " +
            "group by avb.rue")
    List<Arret> findTerminusByRue(/*@Param("rue") Rue rue*/);

}
