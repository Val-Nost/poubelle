package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.model.Rue;

import java.util.List;

public interface IArretService {
    List<Arret> findAll();

    Arret findById(Long id);

    List<Arret> findFeuille();
    List<Arret> findByRue(Rue rue);

    Arret save(Arret arret);

    List<Arret> findByAccessible(boolean accessible);
}
