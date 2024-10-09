package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.model.Velo;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;

import java.util.List;

public interface IRamassageCyclisteVeloService {
    RamassageCyclisteVelo save(RamassageCyclisteVelo ramassageCyclisteVelo);

    List<RamassageCyclisteVelo> saveAll(List<RamassageCyclisteVelo> ramassageCyclisteVelos);

    void delete(RamassageCyclisteVelo ramassageCyclisteVelo);

    RamassageCyclisteVelo findByRamassageAndCycliste(Ramassage ramassage, Utilisateur utilisateur);
    RamassageCyclisteVelo findByRamassageAndVelo(Ramassage ramassage, Velo velo);
}
