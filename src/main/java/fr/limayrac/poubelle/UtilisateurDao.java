package fr.limayrac.poubelle;

import fr.limayrac.poubelle.model.Utilisateur;
import org.springframework.data.repository.CrudRepository;

public interface UtilisateurDao extends CrudRepository<Utilisateur, Long> {
    Utilisateur findByLogin(String login);
}
