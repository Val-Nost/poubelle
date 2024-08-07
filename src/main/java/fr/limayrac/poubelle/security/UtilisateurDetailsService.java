package fr.limayrac.poubelle.security;

import fr.limayrac.poubelle.dao.IUtilisateurDao;
import fr.limayrac.poubelle.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurDetailsService implements UserDetailsService {
    @Autowired
    private IUtilisateurDao utilisateurDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurDao.findByLogin(username);

        if (utilisateur == null) {
            throw new UsernameNotFoundException("L'utilisateur " + username + " est introuvable");
        }

        // Construction de l'objet userDetails

        return new UserSpringSecurity(utilisateur);
    }
}
