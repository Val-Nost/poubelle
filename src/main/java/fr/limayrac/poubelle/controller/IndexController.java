package fr.limayrac.poubelle.controller;

import fr.limayrac.poubelle.dao.IUtilisateurDao;
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
    private IUtilisateurDao utilisateurDao;

    @GetMapping("/accueil")
    public String accueil(Model model) {
        UserSpringSecurity userSpringSecurity = (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur utilisateurConnecte = userSpringSecurity.getUtilisateur();
        switch (utilisateurConnecte.getRole()) {
            case Cycliste -> {
                return "redirect:/itineraire/derniersArrets";
            }
            case RH, Admin -> {
                model.addAttribute("utilisateurs", utilisateurDao.findAll());
                model.addAttribute("utilisateurConnecte", utilisateurConnecte);
                return "listeUtilisateur";
            }
            case Gestionnaire -> {
                return "redirect:/ramassage/ramassages";
            }
            default -> {
                return "accueil";
            }
        }
    }
}
