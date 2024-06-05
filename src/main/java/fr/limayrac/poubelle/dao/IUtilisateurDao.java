package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.Role;
import fr.limayrac.poubelle.model.Utilisateur;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUtilisateurDao extends CrudRepository<Utilisateur, Long> {
    List<Utilisateur> findByRole(Role role);
}
