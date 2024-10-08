package fr.limayrac.poubelle.controller;

import fr.limayrac.poubelle.model.*;
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
    private IRueService rueService;
    @Autowired
    private IRueArretService rueArretService;
    @Autowired
    private IIncidentService incidentService;
    @Autowired
    private IItineraireService itineraireService;

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

        for (Long id : cyclistes) {
            cyclistesObj.add(utilisateurService.findById(id));
        }

        Ramassage ramassage = new Ramassage();
        ramassage.setEnCours(true);

        itineraireService.calculItineraire(ramassage, cyclistesObj);


        return "redirect:/ramassage/liste";
    }

    @GetMapping("/{idRamassage}")
    public String cyclistesRamassage(@PathVariable Long idRamassage, Model model) {
        Ramassage ramassage = ramassageService.findById(idRamassage);
        model.addAttribute("ramassage", ramassage);
        model.addAttribute("rues", rueService.findAllOrderById());

        // Itinéraire
        model.addAttribute("itineraires", itineraireService.findByRamassage(ramassage));

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

        RamassageCyclisteVelo ramassageCyclisteVeloR = null;
        if (cyclisteRemplacant != null && veloRemplacant != null) {
            Utilisateur cyclisteR =  utilisateurService.findById(cyclisteRemplacant);
            Velo veloR = veloService.findById(veloRemplacant);
            RamassageCyclisteVelo ramassageCyclisteVelo = new RamassageCyclisteVelo();
            ramassageCyclisteVelo.setVelo(veloR);
            ramassageCyclisteVelo.setCycliste(cyclisteR);
            ramassageCyclisteVelo.setRamassage(ramassage);
            ramassageCyclisteVeloR = ramassageCyclisteVeloService.save(ramassageCyclisteVelo);
        }

        Utilisateur cyclisteC = null;
        if (cyclisteConcerne != null) {
            cyclisteC = utilisateurService.findById(cyclisteConcerne);
            RamassageCyclisteVelo ramassageCyclisteVelo = ramassageCyclisteVeloService.findByRamassageAndCycliste(ramassage, cyclisteC);
            Itineraire itineraire = itineraireService.findItineraireByCycliste(ramassage, cyclisteC);
            if (ramassageCyclisteVeloR != null) {
                itineraire.setRamassageCyclisteVelo(ramassageCyclisteVeloR);
                itineraireService.save(itineraire);
            }
            ramassage = ramassageService.findById(idRamassage);
            ramassage.getRamassageCyclisteVelos().remove(ramassageCyclisteVelo);
            ramassageService.save(ramassage);
        }


        Velo veloC = null;
        if (veloConcerne != null) {
            veloC = veloService.findById(veloConcerne);
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

    @GetMapping("/{idRamassage}/itineraire/{idItineraire}")
    public String infoItineraire(Model model, @PathVariable Long idRamassage, @PathVariable Long idItineraire) {
        model.addAttribute(itineraireService.findById(idItineraire));
        return "infoItineraire";
    }

    @GetMapping("/ramassages")
    public String getAllRuesArrets(Model model) {
        List<Rue> rues = rueService.findAll();
        List<Arret> arrets = arretService.findAll();
        List<RueArret> rueArrets = rueArretService.findAll();
        List<Utilisateur> cyclistes = utilisateurService.findByRole(Role.Cycliste);
        Ramassage ramassageEnCours = ramassageService.findByEnCours(true).get(0);

        model.addAttribute("rues", rues);
        model.addAttribute("arrets", arrets);
        model.addAttribute("rueArrets", rueArrets);
        model.addAttribute("ramassagesCyclistesVelos", ramassageEnCours.getRamassageCyclisteVelos());

        return "ramassageGlobal";
    }

    @GetMapping("/ramassageByUser/{idUser}")
    public String getRamasseByUser(@PathVariable Long idUser, Model model) {
        List<Rue> rues = rueService.findAll();
        List<Arret> arrets = arretService.findAll();
        List<RueArret> rueArrets = rueArretService.findAll();
        Utilisateur utilisateur = utilisateurService.findById(idUser);

        List<Ramassage> ramassages = ramassageService.findByEnCours(true);
        Ramassage ramassage = ramassages.get(0);

        Itineraire itineraire = itineraireService.findItineraireByCycliste(ramassage, utilisateur);
        for (ItineraireArret itineraireArret : itineraire.getItineraireArrets()) {

        }

        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("rues", rues);
        model.addAttribute("arrets", arrets);
        model.addAttribute("rueArrets", rueArrets);

        return "ramassageByUser";
    }

    @GetMapping("/{idRamassage}/recalculerItineraire")
    public String recalculItineraire(@PathVariable Long idRamassage) {
        Ramassage ramassage = ramassageService.findById(idRamassage);
        itineraireService.recalculItineraire(ramassage);
        return "redirect:/ramassages/" + idRamassage;
    }
}
