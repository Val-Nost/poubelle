package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.StatutVelo;
import fr.limayrac.poubelle.model.Velo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IVeloDao extends CrudRepository<Velo, Long> {
    List<Velo> findByStatutVelo(StatutVelo statutVelo);
    @Query("select v from Velo v where v.id not in (select rcv.velo.id from RamassageCyclisteVelo rcv where rcv.ramassage.id = :ramassage)")
    List<Velo> findVeloNotAffectedToRamassage(@Param("ramassage") Long ramassage);
}
