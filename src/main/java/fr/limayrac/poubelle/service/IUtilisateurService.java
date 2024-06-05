package fr.limayrac.poubelle.service;

import fr.limayrac.poubelle.model.Role;
import fr.limayrac.poubelle.model.Utilisateur;

import java.util.List;

public interface IUtilisateurService {
    Utilisateur findById(Long id);
    List<Utilisateur> findByRole(Role role);
}
