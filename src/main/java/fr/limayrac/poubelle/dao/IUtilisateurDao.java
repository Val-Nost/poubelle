package fr.limayrac.poubelle.dao;

import fr.limayrac.poubelle.model.Role;
import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUtilisateurDao extends CrudRepository<Utilisateur, Long> {
    List<Utilisateur> findByRole(Role role);
    Utilisateur findByLogin(String login);
    @Query("select u from Utilisateur u where u.id not in (select rcv.cycliste.id from RamassageCyclisteVelo rcv where rcv.ramassage = :ramassage) and u.role = :role")
    List<Utilisateur> findUtilisateurNotAffectedToRamassageByRole(@Param("ramassage") Ramassage ramassage, @Param("role") Role role);
}
