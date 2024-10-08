package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;
import org.springframework.data.repository.CrudRepository;

public interface IRamassageCyclisteVeloDao extends CrudRepository<RamassageCyclisteVelo, Long> {
    RamassageCyclisteVelo findByRamassageAndCycliste(Ramassage ramassage, Utilisateur utilisateur);
}
