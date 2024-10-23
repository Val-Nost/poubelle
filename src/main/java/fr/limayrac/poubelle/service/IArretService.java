package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.model.Rue;

import java.util.List;

public interface IArretService {
    List<Arret> findAll();

    Arret findById(Long id);

    List<Arret> findFeuille();
    List<Arret> findByRue(Rue rue);

    List<Arret> findByRueAndIsAccessibleAndRamasse(Rue rue, Boolean accessible, Boolean ramasse);

    Arret save(Arret arret);

    List<Arret> findByAccessible(boolean accessible);

    List<Arret> saveAll(List<Arret> arrets);
}
