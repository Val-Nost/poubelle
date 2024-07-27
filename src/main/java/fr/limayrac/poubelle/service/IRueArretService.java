package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.RueArret;

import java.util.List;

public interface IRueArretService {
    List<RueArret> findAll();

    RueArret findById(Long id);
}
