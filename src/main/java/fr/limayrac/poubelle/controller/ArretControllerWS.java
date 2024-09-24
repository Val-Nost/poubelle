package fr.limayrac.poubelle.controller;

import fr.limayrac.poubelle.model.*;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;
import fr.limayrac.poubelle.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ArretControllerWS {
    @Autowired
    private IArretService arretService;
    @Autowired
    private IArretRueService arretRueService;
    @Autowired
    private IRueService rueService;
    @Autowired
    private IRamassageService ramassageService;
    @Autowired
    private IUtilisateurService utilisateurService;
    @Autowired
    private IItineraireService itineraireService;
    @Autowired
    private IItineraireArretService itineraireArretService;

    @GetMapping(value = "/arrets", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Arret> getArrets() {
        return arretService.findAll();
    }

    @GetMapping(value = "/arret-rue", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArretRue> getAllArretRues() {
        return arretRueService.findAll();
    }

    @GetMapping(value = "/rue", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Rue> getAllRues() {
        return rueService.findAll();
    }

    @GetMapping(value = "/rammassages/{idUser}/arrets", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Arret> getArretsByUser(@PathVariable Long idUser) {
        // Récupérer le premier ramassage en cours
        List<Ramassage> ramassages = ramassageService.findByEnCours(true);
        if (ramassages.isEmpty()) {
            return new ArrayList<>(); // Retourne une liste vide si aucun ramassage en cours
        }

        Ramassage ramassage = ramassages.get(0);

        // Trouver l'itinéraire par utilisateur
        Utilisateur utilisateur = utilisateurService.findById(idUser);
        Itineraire itineraire = itineraireService.findItineraireByCycliste(ramassage, utilisateur);

        // Extraire les arrêts de l'itinéraire
        List<Arret> arrets = new ArrayList<>();
        if (itineraire != null) {
            for (ItineraireArret itineraireArret : itineraire.getItineraireArrets()) {
                arrets.add(itineraireArret.getArret());
            }
        }
        // Retourner la liste des arrêts en JSON
        return arrets;
    }

    @PostMapping("/ramassageDerniersArrets")
    public Boolean ramassageDerniersArrets() {
        Ramassage ramassage = ramassageService.findByEnCours(true).get(0);
        ItineraireArret toUpdate = null;
        for (RamassageCyclisteVelo ramassageCyclisteVelo : ramassage.getRamassageCyclisteVelos()) {
            Itineraire itineraire = itineraireService.findByRamassageCyclisteVelo(ramassageCyclisteVelo);
            for (ItineraireArret itineraireArret : itineraire.getItineraireArrets()) {
                if (!itineraireArret.getArret().getRamasse()) {
                    toUpdate = itineraireArret;
                    Arret arret = arretService.findById(itineraireArret.getArret().getId());
                    arret.setRamasse(true);
                    arretService.save(arret);
                    break;
                }
            }
            // On enregistre la date de passage
            if (toUpdate != null) {
                toUpdate.setDatePassage(LocalDateTime.now());
                itineraireArretService.save(toUpdate);
            }
        }
        // TODO, rediriger vers affichage carte individuelle
        return true;
    }
}
