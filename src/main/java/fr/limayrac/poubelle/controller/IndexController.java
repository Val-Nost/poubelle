package fr.limayrac.poubelle.controller;

import fr.limayrac.poubelle.UtilisateurDao;
import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired
    private UtilisateurDao utilisateurDao;

    @GetMapping("/accueil")
    public String accueil(Model model) {
        UserSpringSecurity userSpringSecurity = (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur utilisateurConnecte = userSpringSecurity.getUtilisateur();
        model.addAttribute("utilisateurs", utilisateurDao.findAll());
        model.addAttribute("utilisateurConnecte", utilisateurConnecte);
        return "accueil";
    }
}
