package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.StatutVelo;
import fr.limayrac.poubelle.model.Velo;
import fr.limayrac.poubelle.model.ramassage.Ramassage;

import java.util.List;

public interface IVeloService {
    List<Velo> findByStatut(StatutVelo statutVelo);

    Velo findById(Long id);
    List<Velo> findVeloNotAffectedToRamassage(Ramassage ramassage);

    List<Velo> findAll();

    Velo save(Velo velo);

    List<Velo> saveAll(List<Velo> velos);
}
