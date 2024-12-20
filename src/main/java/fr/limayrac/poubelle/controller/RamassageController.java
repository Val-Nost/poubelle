package fr.limayrac.poubelle.controller;

import fr.limayrac.poubelle.model.*;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;
import fr.limayrac.poubelle.service.*;
import fr.limayrac.poubelle.service.impl.ItineraireArretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ItineraireArretService itineraireArretService;

    @GetMapping
    public String ramassage(Model model) {
        model.addAttribute("cyclistes", utilisateurService.findByRole(Role.Cycliste));
        model.addAttribute("velos", veloService.findByStatut(StatutVelo.UTILISABLE));
        model.addAttribute("ramassagesEnCours", ramassageService.findByEnCours(true));
        return "lancementRamassage";
    }

    @PostMapping("/choixCycliste")
    public String choixCyclistes(@RequestParam List<Long> cyclistes) {
        // On remet tous les arrêts à leur états par défaut
        List<Arret> arrets = arretService.findAll();
        for (Arret arret : arrets) {
            arret.setRamasse(false);
            arret.setIsAccessible(true);
        }
        arrets = arretService.saveAll(arrets);

        // On remet tous les vélos à leur état par défaut
        List<Velo> velos = veloService.findAll();
        for (Velo velo : velos) {
            velo.setAutonomie(50.0);
            velo.setCompteurCarrefour(0);
            velo.setCharge(0);
            velo.setModeRamassage(true);
        }
        velos = veloService.saveAll(velos);

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

        // Rue et arrêts
        Map<Arret, ItineraireArret> arretMap = new HashMap<>();
        List<ItineraireArret> itineraireArrets = itineraireArretService.findItineraireArretByDatePassageNotNullAndOrdreRamassageNotNullAndRamassage(ramassage);
        for (ItineraireArret itineraireArret : itineraireArrets) {
            arretMap.put(itineraireArret.getArret(), itineraireArret);
        }
        model.addAttribute("arretMap", arretMap);

        // Incident
        model.addAttribute("incidents", incidentService.findByRamassage(ramassage));
        model.addAttribute("typesIncidents", TypeIncident.values());
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
    public String ajoutIncident(@PathVariable Long idRamassage, @RequestParam("typeIncident") Integer typeIncidentId,
                                @RequestParam(required = false) Long cyclisteConcerne, @RequestParam(required = false) Long cyclisteRemplacant,
                                @RequestParam(required = false) Long veloConcerne, @RequestParam(required = false) Long veloRemplacant,
                                @RequestParam(required = false) Long arret) {
        Ramassage ramassage = ramassageService.findById(idRamassage);

        RamassageCyclisteVelo ramassageCyclisteVeloR;
        TypeIncident typeIncident = TypeIncident.values()[typeIncidentId];

        Incident incident = new Incident();
        incident.setTypeIncident(typeIncident);
        incident.setRamassage(ramassage);

        switch (typeIncident) {
            case ACCIDENT_CORPOREL -> {
                Utilisateur cyclisteC =  utilisateurService.findById(cyclisteConcerne);
                Utilisateur cyclisteR =  null;
                if (cyclisteRemplacant != null) {
                    cyclisteR =  utilisateurService.findById(cyclisteRemplacant);
                }

                ramassageCyclisteVeloR = ramassageCyclisteVeloService.findByRamassageAndCycliste(ramassage, cyclisteC);
                incident.setCycliste(cyclisteC);
                if (cyclisteR == null) {
                    Itineraire itineraire = itineraireService.findByRamassageCyclisteVelo(ramassageCyclisteVeloR);
                    itineraireArretService.deleteByItineraire(itineraire);
                    itineraireService.delete(itineraire);

                    ramassageCyclisteVeloR = ramassageCyclisteVeloService.findById(ramassageCyclisteVeloR.getId());
                    ramassage.getRamassageCyclisteVelos().remove(ramassageCyclisteVeloR);
                    ramassage.setARecalculer(true);
                    ramassage = ramassageService.save(ramassage);
                    ramassageCyclisteVeloService.delete(ramassageCyclisteVeloR);
                } else {
                    ramassageCyclisteVeloR.setCycliste(cyclisteR);
                    ramassageCyclisteVeloService.save(ramassageCyclisteVeloR);
                }
            }
            case CASSE_VELO -> {
                Velo veloC =  veloService.findById(veloConcerne);
                Velo veloR =  null;
                if (veloRemplacant != null) {
                    veloR =  veloService.findById(veloRemplacant);
                }
                ramassageCyclisteVeloR = ramassageCyclisteVeloService.findByRamassageAndVelo(ramassage, veloC);
                incident.setVelo(veloC);

                if (veloR == null) {
                    Itineraire itineraire = itineraireService.findByRamassageCyclisteVelo(ramassageCyclisteVeloR);
                    itineraireArretService.deleteByItineraire(itineraire);
                    itineraireService.delete(itineraire);

                    ramassageCyclisteVeloR = ramassageCyclisteVeloService.findById(ramassageCyclisteVeloR.getId());
                    ramassage.getRamassageCyclisteVelos().remove(ramassageCyclisteVeloR);
                    ramassage.setARecalculer(true);
                    ramassage = ramassageService.save(ramassage);
                    ramassageCyclisteVeloService.delete(ramassageCyclisteVeloR);
                    ramassageCyclisteVeloService.delete(ramassageCyclisteVeloR);
                } else {
                    ramassageCyclisteVeloR.setVelo(veloR);
                    ramassageCyclisteVeloService.save(ramassageCyclisteVeloR);
                }

            }

            case ARRET_INACCESSIBLE -> incident.setArret(arretService.findById(arret));
        }

        incidentService.save(incident);

        return "redirect:/ramassage/{idRamassage}?tab=Incidents";
    }

    @GetMapping("/{idRamassage}/itineraire/{idItineraire}")
    public String infoItineraire(Model model, @PathVariable Long idItineraire) {
        model.addAttribute(itineraireService.findById(idItineraire));
        return "infoItineraire";
    }

    @GetMapping("/ramassages")
    public String getAllRuesArrets(Model model) {
        List<Rue> rues = rueService.findAll();
        List<Arret> arrets = arretService.findAll();
        List<RueArret> rueArrets = rueArretService.findAll();
        List<Ramassage> ramassages =  ramassageService.findByEnCours(true);
        List<RamassageCyclisteVelo> ramassageCyclisteVelos = new ArrayList<>();
        if (ramassages != null && !ramassages.isEmpty()) {
            ramassageCyclisteVelos = ramassages.get(0).getRamassageCyclisteVelos();
        }

        model.addAttribute("rues", rues);
        model.addAttribute("arrets", arrets);
        model.addAttribute("rueArrets", rueArrets);
        model.addAttribute("ramassagesCyclistesVelos", ramassageCyclisteVelos);

        return "ramassageGlobal";
    }

    @GetMapping("/ramassageByUser/{idUser}")
    public String getRamasseByUser(@PathVariable Long idUser, Model model, RedirectAttributes redirectAttributes) {
            List<Rue> rues = rueService.findAll();
            List<Arret> arrets = arretService.findAll();
            List<RueArret> rueArrets = rueArretService.findAll();
            Utilisateur utilisateur = utilisateurService.findById(idUser);

            if (utilisateur == null) {
                redirectAttributes.addFlashAttribute("error", "Utilisateur non trouvé.");
                return "redirect:/accueil";
            }

            List<Ramassage> ramassages = ramassageService.findByEnCours(true);
            if (ramassages.isEmpty()) {
                String errorMessage = String.format("Aucun ramassage en cours pour l'utilisateur %s %s.", utilisateur.getNom(), utilisateur.getPrenom());
                redirectAttributes.addFlashAttribute("error", errorMessage);
                return "redirect:/accueil";
            }

            Ramassage ramassage = ramassages.get(0);
            Itineraire itineraire = itineraireService.findItineraireByCycliste(ramassage, utilisateur);

            if (itineraire == null) {
                String errorMessage = String.format("Itinéraire non trouvé pour l'utilisateur %s %s.", utilisateur.getNom(), utilisateur.getPrenom());
                redirectAttributes.addFlashAttribute("error", errorMessage);
                return "redirect:/accueil";
            }

            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("rues", rues);
            model.addAttribute("arrets", arrets);
            model.addAttribute("rueArrets", rueArrets);
            model.addAttribute("itineraire", itineraire);

            return "ramassageByUser";
    }

    @GetMapping("/{idRamassage}/recalculerItineraire")
    public String recalculItineraire(@PathVariable Long idRamassage) {
        Ramassage ramassage = ramassageService.findById(idRamassage);
        itineraireService.recalculItineraire(ramassage);
        return "redirect:/ramassage/" + idRamassage;
    }
}
