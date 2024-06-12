package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.ramassage.Passage;

import java.util.List;

public interface IPassageService {
    List<Passage> saveAll(List<Passage> passages);
}
