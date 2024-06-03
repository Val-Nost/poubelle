package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.ramassage.RamassageArret;

import java.util.List;

public interface IRamassageArretService {
    List<RamassageArret> saveAll(List<RamassageArret> ramassageArrets);
}
