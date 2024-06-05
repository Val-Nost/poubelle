package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IUtilisateurDao;
import fr.limayrac.poubelle.model.Role;
import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.service.IUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService implements IUtilisateurService {
    @Autowired
    private IUtilisateurDao utilisateurDao;

    @Override
    public Utilisateur findById(Long id) {
        return utilisateurDao.findById(id).orElse(null);
    }

    @Override
    public List<Utilisateur> findByRole(Role role) {
        return utilisateurDao.findByRole(role);
    }
}
