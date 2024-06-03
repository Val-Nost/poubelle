package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;

import java.util.List;

public interface IRamassageCyclisteVeloService {
    RamassageCyclisteVelo save(RamassageCyclisteVelo ramassageCyclisteVelo);

    List<RamassageCyclisteVelo> saveAll(List<RamassageCyclisteVelo> ramassageCyclisteVelos);
}
