package fr.limayrac.poubelle.controller;

import fr.limayrac.poubelle.UtilisateurDao;
import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/ajouter")
    public String ajouterUtilisateurForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("roles", Role.values());
        return "ajouterUtilisateur";
    }

    @PostMapping("/creer")
    public String creerUtilisateur(@ModelAttribute Utilisateur utilisateur) {
        String encodedPassword = passwordEncoder.encode(utilisateur.getPassword());
        utilisateur.setPassword(encodedPassword);
        utilisateurDao.save(utilisateur);
        return "redirect:/accueil";
    }
}