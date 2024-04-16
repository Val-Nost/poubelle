package fr.limayrac.poubelle.security;

import fr.limayrac.poubelle.model.Role;
import fr.limayrac.poubelle.model.Utilisateur;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Remplacer par la recherche en bdd
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setLogin(username);
        utilisateur.setPassword(new BCryptPasswordEncoder().encode("root"));
        utilisateur.setActif(true);
        utilisateur.setRole(Role.Admin);

        if (utilisateur == null) {
            throw new UsernameNotFoundException("L'utilisateur " + username + " est introuvable");
        }

        // Construction de l'objet userDetails

        return new UserSpringSecurity(utilisateur);
    }
}
