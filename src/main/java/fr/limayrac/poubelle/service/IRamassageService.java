package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.ramassage.Ramassage;

import java.util.List;

public interface IRamassageService {
    Ramassage findById(Long id);
    Ramassage save(Ramassage ramassage);
    List<Ramassage> findByEnCours(Boolean enCours);
}
