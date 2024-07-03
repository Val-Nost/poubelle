package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.Rue;

import java.util.List;

public interface IRueService {

    List<Rue> findAllOrderById();

    List<Rue> findAll();

    Rue findById(Long id);
}
