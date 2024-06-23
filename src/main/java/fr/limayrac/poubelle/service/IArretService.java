package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.Arret;

import java.util.List;

public interface IArretService {
    List<Arret> findAll();

    Arret findById(Long id);

    List<Arret> findFeuille();
}
