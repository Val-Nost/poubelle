package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.Role;
import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.model.ramassage.Ramassage;

import java.util.List;

public interface IUtilisateurService {
    Utilisateur findById(Long id);
    List<Utilisateur> findByRole(Role role);
    List<Utilisateur> findUtilisateurNotAffectedToRamassageByRole(Ramassage ramassage, Role role);
}
