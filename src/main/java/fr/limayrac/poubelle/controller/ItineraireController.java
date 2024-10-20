package fr.limayrac.poubelle.controller;

import fr.limayrac.poubelle.model.Itineraire;
import fr.limayrac.poubelle.model.ItineraireArret;
import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.security.UserSpringSecurity;
import fr.limayrac.poubelle.service.IItineraireService;
import fr.limayrac.poubelle.service.IRamassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/itineraire")
public class ItineraireController {
    @Autowired
    private IItineraireService itineraireService;
    @Autowired
    private IRamassageService ramassageService;

    @GetMapping("/derniersArrets")
    public String dernierArrets(Model model) {
        List<Ramassage> ramassages = ramassageService.findByEnCours(true);
        if (ramassages != null && !ramassages.isEmpty()) {
            Ramassage ramassage = ramassages.get(0);
            UserSpringSecurity userSpringSecurity = (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Utilisateur utilisateur = userSpringSecurity.getUtilisateur();
            Itineraire itineraire = itineraireService.findItineraireByCycliste(ramassage, utilisateur);
            if (itineraire != null) {
                ItineraireArret arretPrecedent = null;
                ItineraireArret arretCourant = null;
                ItineraireArret arretSuivant = null;
                for (ItineraireArret itineraireArret : itineraire.getItineraireArrets()) {
                    if (!itineraireArret.getArret().getRamasse()) {
                        arretCourant = itineraireArret;
                        break;
                    }
                }

                // Si l'on trouve l'arrêt courant
                if (arretCourant != null) {
                    // L'arrêt courant n'est le premier de la liste
                    if (itineraire.getItineraireArrets().indexOf(arretCourant) != 0) {
                        arretPrecedent = itineraire.getItineraireArrets().get(itineraire.getItineraireArrets().indexOf(arretCourant)-1);
                    }
                    // L'arrêt courant n'est pas le dernier
                    if (itineraire.getItineraireArrets().indexOf(arretCourant) != (itineraire.getItineraireArrets().size()-1)) {
                        arretSuivant = itineraire.getItineraireArrets().get(itineraire.getItineraireArrets().indexOf(arretCourant)+1);
                    }
                    // Tout a été ramassé
                } else {
                    model.addAttribute("message", "Tout les arrêts ont été ramassés");
                }

                model.addAttribute("arretPrecedent", arretPrecedent);
                model.addAttribute("arretCourant", arretCourant);
                model.addAttribute("arretSuivant", arretSuivant);
                model.addAttribute("itineraire", itineraire);
                model.addAttribute("utilisateur", utilisateur);
            } else {
                model.addAttribute("message", "Aucun itinéraire ne vous est attribué");
            }
        } else {
            model.addAttribute("message", "Aucun ramassage n'est en cours");
        }

        return "itineraireCycliste";
    }
}
