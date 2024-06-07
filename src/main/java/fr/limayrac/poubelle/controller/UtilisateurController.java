package fr.limayrac.poubelle.controller;

import fr.limayrac.poubelle.dao.IUtilisateurDao;
import fr.limayrac.poubelle.model.Role;
import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    @Autowired
    private IUtilisateurDao utilisateurDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/userListe")
    public String userListe(Model model) {
        UserSpringSecurity userSpringSecurity = (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur utilisateurConnecte = userSpringSecurity.getUtilisateur();
        model.addAttribute("utilisateurs", utilisateurDao.findAll());
        model.addAttribute("utilisateurConnecte", utilisateurConnecte);
        return "listeUtilisateur";
    }

    @GetMapping("/ajouter")
    public String ajouterUtilisateurForm(Model model) {
        UserSpringSecurity userSpringSecurity = (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur utilisateurConnecte = userSpringSecurity.getUtilisateur();
        model.addAttribute("utilisateurConnecte", utilisateurConnecte);
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
        UserSpringSecurity userSpringSecurity = (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur utilisateurConnecte = userSpringSecurity.getUtilisateur();
        model.addAttribute("utilisateurConnecte", utilisateurConnecte);
        Utilisateur utilisateur = utilisateurDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("role", Role.values());
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