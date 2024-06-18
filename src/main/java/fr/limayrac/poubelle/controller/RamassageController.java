package fr.limayrac.poubelle.controller;

import fr.limayrac.poubelle.model.*;
import fr.limayrac.poubelle.model.ramassage.Passage;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;
import fr.limayrac.poubelle.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ramassage")
public class RamassageController {
    @Autowired
    private IUtilisateurService utilisateurService;
    @Autowired
    private IVeloService veloService;
    @Autowired
    private IRamassageService ramassageService;
    @Autowired
    private IRamassageCyclisteVeloService ramassageCyclisteVeloService;
    @Autowired
    private IArretService arretService;
    @Autowired
    private IPassageService ramassageArretService;
    @Autowired
    private IRueService rueService;
    @Autowired
    private IIncidentService incidentService;

    @GetMapping
    public String ramassage(Model model) {
        model.addAttribute("cyclistes", utilisateurService.findByRole(Role.Cycliste));
        model.addAttribute("velos", veloService.findByStatut(StatutVelo.UTILISABLE));
        model.addAttribute("ramassagesEnCours", ramassageService.findByEnCours(true));
        return "lancementRamassage";
    }

    @PostMapping("/choixCycliste")
    public String choixCyclistes(Model model, @RequestParam List<Long> cyclistes) {
        List<Utilisateur> cyclistesObj = new ArrayList<>();
        List<Velo> velos = veloService.findByStatut(StatutVelo.UTILISABLE);

        for (Long id : cyclistes) {
            cyclistesObj.add(utilisateurService.findById(id));
        }

        Ramassage ramassage = new Ramassage();
        ramassage.setEnCours(true);
        ramassage = ramassageService.save(ramassage);

        // On attribue chaque velos à chaque cycliste choisie
        /*
        Remarque on crée une liste pour éviter les appels multiples vers la BDD
        Il est peut recommandé de faire appel à un DAO dans une boucle
        */
        List<RamassageCyclisteVelo> ramassageCyclisteVelos = new ArrayList<>();
        for (int i = 0; i < cyclistesObj.size(); i++) {
            RamassageCyclisteVelo ramassageCyclisteVelo = new RamassageCyclisteVelo();
            ramassageCyclisteVelo.setRamassage(ramassage);
            ramassageCyclisteVelo.setCycliste(cyclistesObj.get(i));
            ramassageCyclisteVelo.setVelo(velos.get(i));
            ramassageCyclisteVelos.add(ramassageCyclisteVelo);
        }
        ramassageCyclisteVeloService.saveAll(ramassageCyclisteVelos);

        // On ajoute les arrets du ramassage
        /*
        Remarque on crée une liste pour éviter les appels multiples vers la BDD
        Il est peut recommandé de faire appel à un DAO dans une boucle
        */
        List<Passage> passages = new ArrayList<>();
        for (Arret arret : arretService.findAll()) {
            Passage passage = new Passage();
            passage.setArret(arret);
            passage.setRamassage(ramassage);
            passage.setRamasse(false);
        }
        ramassageArretService.saveAll(passages);

        return "redirect:/ramassage/liste";
    }

    @GetMapping("/{idRamassage}")
    public String cyclistesRamassage(@PathVariable Long idRamassage, Model model) {
        Ramassage ramassage = ramassageService.findById(idRamassage);
        model.addAttribute("ramassage", ramassage);
        model.addAttribute("rues", rueService.findAllOrderById());

        // Cyclistes
        model.addAttribute("velosRestants", veloService.findVeloNotAffectedToRamassage(ramassage));
        model.addAttribute("cyclistesRestants", utilisateurService.findUtilisateurNotAffectedToRamassageByRole(ramassage, Role.Cycliste));

        // Incident
        model.addAttribute("cyclistes", utilisateurService.findByRole(Role.Cycliste));
        model.addAttribute("velos", veloService.findByStatut(StatutVelo.UTILISABLE));
        model.addAttribute("arrets", arretService.findAll());
        return "infoRamassage";
    }

    @GetMapping("/liste")
    public String listeRamassage(Model model) {
        model.addAttribute("ramassagesEnCours", ramassageService.findByEnCours(true));
        model.addAttribute("ramassagesTermine", ramassageService.findByEnCours(false));
        return "ramassage";
    }

    @PostMapping("/{idRamassage}/ajoutCyclisteVelo")
    public String ajoutCyclisteVelo(@PathVariable Long idRamassage, @RequestParam Long cyclisteRestant, @RequestParam Long veloRestant) {
        Ramassage ramassage = ramassageService.findById(idRamassage);
        Utilisateur cycliste = utilisateurService.findById(cyclisteRestant);
        Velo velo = veloService.findById(veloRestant);

        RamassageCyclisteVelo ramassageCyclisteVelo = new RamassageCyclisteVelo();
        ramassageCyclisteVelo.setCycliste(cycliste);
        ramassageCyclisteVelo.setRamassage(ramassage);
        ramassageCyclisteVelo.setVelo(velo);

        ramassageCyclisteVeloService.save(ramassageCyclisteVelo);
        return "redirect:/ramassage/{idRamassage}?tab=Cyclistes";
    }

    @PostMapping("/{idRamassage}/ajoutIncident")
    public String ajoutIncident(Model model, @PathVariable Long idRamassage, @RequestParam String libelle,
                                @RequestParam(required = false) Long cyclisteConcerne, @RequestParam(required = false) Long cyclisteRemplacant,
                                @RequestParam(required = false) Long veloConcerne, @RequestParam(required = false) Long veloRemplacant,
                                @RequestParam(required = false) Long arret, @RequestParam(required = false) Boolean pratiquable) {
        Ramassage ramassage = ramassageService.findById(idRamassage);

        Utilisateur cyclisteC = null;
        if (cyclisteConcerne != null) {
            cyclisteC = utilisateurService.findById(cyclisteConcerne);
        }

        Utilisateur cyclisteR = null;
        if (cyclisteRemplacant != null) {
            cyclisteR =  utilisateurService.findById(cyclisteRemplacant);
        }

        Velo veloC = null;
        if (veloConcerne != null) {
            veloC = veloService.findById(veloConcerne);
        }

        Velo veloR = null;
        if (veloRemplacant != null) {
            veloR = veloService.findById(veloRemplacant);
        }

        Arret arretObj = null;
        if (arret != null) {
            arretObj = arretService.findById(arret);
        }

        Incident incident = new Incident();
        incident.setLibelle(libelle);
        incident.setCycliste(cyclisteC);
        incident.setVelo(veloC);
        incident.setArret(arretObj);
        incident.setRamassage(ramassage);

        incidentService.save(incident);

        return "redirect:/ramassage/{idRamassage}?tab=Incidents";
    }
}
