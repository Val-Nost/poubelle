package fr.limayrac.poubelle.controller;

import fr.limayrac.poubelle.UtilisateurDao;
import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/modifier/{id}")
    public String modifierUtilisateurForm(@PathVariable Long id, Model model) {
        Utilisateur utilisateur = utilisateurDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("roles", Role.values());
        return "modifierUtilisateur";
    }
    @PostMapping("/update/{id}")
    public String modifierUtilisateur(@PathVariable Long id, @ModelAttribute Utilisateur utilisateurForm) {
        Utilisateur utilisateur = utilisateurDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        utilisateur.setNom(utilisateurForm.getNom());
        utilisateur.setPrenom(utilisateurForm.getPrenom());
        utilisateur.setLogin(utilisateurForm.getLogin());
        utilisateur.setPassword(passwordEncoder.encode(utilisateurForm.getPassword()));
        utilisateur.setRole(utilisateurForm.getRole());
        utilisateur.setActif(utilisateurForm.getActif());
        utilisateurDao.save(utilisateur);
        return "redirect:/accueil";
    }
    @PostMapping("/supprimer/{id}")
    public String supprimerUtilisateur(@PathVariable Long id) {
        utilisateurDao.deleteById(id);
        return "redirect:/accueil";
    }
}