package fr.limayrac.poubelle.controller;

import fr.limayrac.poubelle.dto.CheminPossibleDto;
import fr.limayrac.poubelle.model.*;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;
import fr.limayrac.poubelle.service.*;
import fr.limayrac.poubelle.utils.ItineraireUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        List<Velo> velos = veloService.findByStatut(StatutVelo.UTILISABLE);

        for (Long id : cyclistes) {
            cyclistesObj.add(utilisateurService.findById(id));
        }

        Ramassage ramassage = new Ramassage();
        ramassage.setEnCours(true);

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
        // On sauvegarde en cascades
        ramassage.setRamassageCyclisteVelos(ramassageCyclisteVelos);
        ramassage = ramassageService.save(ramassage);


        // On calcule les itinéraires de chaque cycliste
        Arret arretDepart = arretService.findById(161L); // Porte d'Ivry
        List<Arret> terminus = arretService.findFeuille();
        // TODO Voir pour inclure un booléen si l'arrêt est inaccessible
        // FIXME Modifier cette ligne une fois les tests finis
        Set<Arret> arretsARamasser = new HashSet<>();
        arretsARamasser.addAll(arretService.findByRue(rueService.findById(5L)));
        arretsARamasser.addAll(arretService.findByRue(rueService.findById(8L)));
        arretsARamasser.addAll(arretService.findByRue(rueService.findById(19L)));

        // La liste complète des chemins possibles
        List<CheminPossibleDto> cheminPossibleDtos = new ArrayList<>();
        // On crée un premier chemin auquel on ajoute l'arrêt de départ, pour ensuite l'ajouter à la liste de chemins possibles
        CheminPossibleDto cheminPossibleDto = new CheminPossibleDto();
        cheminPossibleDto.addArret(arretDepart);
        cheminPossibleDtos.add(cheminPossibleDto);

        // Fonction récursive qui cherche les chemins possibles à partir d'un arrêt donné et les affecte à la liste passé en argument
        ItineraireUtils.chercheChemin(arretDepart, 0, cheminPossibleDtos);

        cheminPossibleDtos.removeIf(cheminPossibleDto1 -> !terminus.contains(cheminPossibleDto1.dernierArret()));

        Map<RamassageCyclisteVelo, Itineraire> itineraireMap = new HashMap<>();
        Set<Arret> arretsRamasses = new HashSet<>();

        while (!arretsRamasses.containsAll(arretsARamasser)) {
            for (RamassageCyclisteVelo ramassageCyclisteVelo : ramassage.getRamassageCyclisteVelos()) {
                int ordreRamassage = 0;
                if (!itineraireMap.containsKey(ramassageCyclisteVelo)) {
                    itineraireMap.put(ramassageCyclisteVelo, new Itineraire());
                    itineraireMap.get(ramassageCyclisteVelo).setRamassageCyclisteVelo(ramassageCyclisteVelo);
                }
                List<Arret> arrets = ItineraireUtils.ramasseCharge(ramassageCyclisteVelo, cheminPossibleDtos, arretsRamasses);
                if (arrets != null) {
                    for (Arret arret : arrets) {
                        ItineraireArret itineraireArret = new ItineraireArret();
                        itineraireArret.setItineraire(itineraireMap.get(ramassageCyclisteVelo));
                        itineraireArret.setArret(arret);
                        itineraireArret.setOrdre(ordreRamassage);
                        ordreRamassage++;
                        itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets().add(itineraireArret);
                    }
                }
            }
        }

        itineraireService.saveAll(itineraireMap.values());

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

        model.addAttribute("rues", rues);
        model.addAttribute("arrets", arrets);
        model.addAttribute("rueArrets", rueArrets);
        model.addAttribute("cyclistes", cyclistes);

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

    @PostMapping("/ramassageDerniersArrets")
    public String ramassageDerniersArrets() {
        Ramassage ramassage = ramassageService.findByEnCours(true).get(0);
        for (RamassageCyclisteVelo ramassageCyclisteVelo : ramassage.getRamassageCyclisteVelos()) {
            Itineraire itineraire = itineraireService.findByRamassageCyclisteVelo(ramassageCyclisteVelo);
            for (ItineraireArret itineraireArret : itineraire.getItineraireArrets()) {
                if (!itineraireArret.getArret().getRamasse()) {
                    Arret arret = arretService.findById(itineraireArret.getArret().getId());
                    arret.setRamasse(true);
                    arretService.save(arret);
                    break;
                }
            }
        }
        // TODO, rediriger vers affichage carte individuelle
        return "";
    }
}
