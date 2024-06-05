package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.StatutVelo;
import fr.limayrac.poubelle.model.Velo;

import java.util.List;

public interface IVeloService {
    List<Velo> findByStatut(StatutVelo statutVelo);
}
