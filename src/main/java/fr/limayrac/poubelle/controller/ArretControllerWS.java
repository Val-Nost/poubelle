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
    @Autowired
    private IVeloService veloService;

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

    @PostMapping("/ramassageDerniersArrets/{idUser}")
    public Boolean ramassageDerniersArrets(@PathVariable Long idUser) {
        // TODO Diminuer l'autonomie du vélo
        // TODO Afficher l'autonomie du vélo
        // Récupérer le ramassage en cours
        Ramassage ramassage = ramassageService.findByEnCours(true).get(0);
        ItineraireArret toUpdate = null;

        // Filtrer les itinéraires par cycliste (utilisateur spécifique)
        for (RamassageCyclisteVelo ramassageCyclisteVelo : ramassage.getRamassageCyclisteVelos()) {
            if (ramassageCyclisteVelo.getCycliste().getId().equals(idUser)) { // Mettre à jour uniquement pour l'utilisateur cible
                Itineraire itineraire = itineraireService.findByRamassageCyclisteVelo(ramassageCyclisteVelo);
                for (ItineraireArret itineraireArret : itineraire.getItineraireArrets()) {
                    if (itineraireArret.getDatePassage() == null) {
                        toUpdate = itineraireArret;
                        Velo velo = ramassageCyclisteVelo.getVelo();
                        velo.passageArret(toUpdate.getArret());
                        if (itineraireArret.getOrdreRamassage() != null) {
                            // On augmente la charge
                            velo.setCharge(velo.getCharge() + 50);
                            Arret arret = arretService.findById(itineraireArret.getArret().getId());
                            arret.setRamasse(true);
                            arretService.save(arret);
                        }
                        // On passe par le centre de tri donc on change la batterie et on vide le vélo
                        if (toUpdate.getArret().getId() == 161L) {
                            velo.raz();
                        }
                        velo = veloService.save(velo);
                        break;
                    }
                }
                // Enregistrer la date de passage
                if (toUpdate != null) {
                    toUpdate.setDatePassage(LocalDateTime.now());
                    itineraireArretService.save(toUpdate);
                }
                break; // Sortir de la boucle après la mise à jour de cet utilisateur
            }
        }
        return true;
    }

}
