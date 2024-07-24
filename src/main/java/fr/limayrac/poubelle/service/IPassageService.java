package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.model.ramassage.Passage;
import fr.limayrac.poubelle.model.ramassage.Ramassage;

import java.util.List;

public interface IPassageService {
    List<Passage> saveAll(List<Passage> passages);
    Passage finfByRamassageAndArret(Ramassage ramassage, Arret arret);
}
