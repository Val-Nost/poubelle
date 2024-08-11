package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.ArretRue;

import java.util.List;

public interface IArretRueService {
    List<ArretRue> findAll();
}