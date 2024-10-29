package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IItineraireDao;
import fr.limayrac.poubelle.dto.CheminPossibleDto;
import fr.limayrac.poubelle.model.*;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;
import fr.limayrac.poubelle.service.*;
import fr.limayrac.poubelle.utils.ItineraireUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItineraireService implements IItineraireService {
    private static final Logger logger = LoggerFactory.getLogger(ItineraireService.class);
    @Autowired
    private IVeloService veloService;
    @Autowired
    private IRamassageService ramassageService;
    @Autowired
    private IItineraireDao itineraireDao;
    @Autowired
    private IArretService arretService;
    @Autowired
    private IRueService rueService;
    @Autowired
    private IncidentService incidentService;

    @Override
    public List<Itineraire> findByRamassage(Ramassage ramassage) {
        return itineraireDao.findByRamassage(ramassage);
    }

    @Override
    public List<Itineraire> saveAll(Collection<Itineraire> itineraire) {
        return (List<Itineraire>) itineraireDao.saveAll(itineraire);
    }

    @Override
    public Itineraire findById(Long idItineraire) {
        return itineraireDao.findById(idItineraire).orElse(null);
    }

    @Override
    public Itineraire findItineraireByCycliste(Ramassage ramassage, Utilisateur utilisateur) {
        return itineraireDao.findByRamassageAndCycliste(ramassage, utilisateur);
    }
    @Override
    public Itineraire findByRamassageCyclisteVelo(RamassageCyclisteVelo ramassageCyclisteVelo) {
        return itineraireDao.findByRamassageCyclisteVelo(ramassageCyclisteVelo);
    }

    @Override
    public void calculItineraire(Ramassage ramassage, List<Utilisateur> utilisateurs) {
        logger.info("Début du calcul des itinéraires");
        List<Velo> velos = veloService.findByStatut(StatutVelo.UTILISABLE);

        // On attribue chaque velos à chaque cycliste choisie
        /*
        Remarque on crée une liste pour éviter les appels multiples vers la BDD
        Il est peut recommandé de faire appel à un DAO dans une boucle
        */
        List<RamassageCyclisteVelo> ramassageCyclisteVelos = new ArrayList<>();
        for (int i = 0; i < utilisateurs.size(); i++) {
            RamassageCyclisteVelo ramassageCyclisteVelo = new RamassageCyclisteVelo();
            ramassageCyclisteVelo.setRamassage(ramassage);
            ramassageCyclisteVelo.setCycliste(utilisateurs.get(i));
            ramassageCyclisteVelo.setVelo(velos.get(i));
            ramassageCyclisteVelos.add(ramassageCyclisteVelo);
        }

        // On sauvegarde en cascades
        ramassage.setRamassageCyclisteVelos(ramassageCyclisteVelos);
        ramassage.setARecalculer(false);
        ramassage = ramassageService.save(ramassage);

        // On calcule les itinéraires de chaque cycliste
        Arret arretDepart = arretService.findById(161L); // Porte d'Ivry
        List<Arret> terminus = arretService.findFeuille();
        Set<Arret> arretsARamasser = new HashSet<>();
//        List<Arret> arretsARamasser = arretService.findByAccessible(true);
        arretsARamasser.addAll(arretService.findByRueAndIsAccessibleAndRamasse(rueService.findById(5L), true, false));
        arretsARamasser.addAll(arretService.findByRueAndIsAccessibleAndRamasse(rueService.findById(8L), true, false));
        arretsARamasser.addAll(arretService.findByRueAndIsAccessibleAndRamasse(rueService.findById(19L), true, false));

//        arretsARamasser.addAll(arretService.findByRue(rueService.findById(7L)));
//        arretsARamasser.addAll(arretService.findByRue(rueService.findById(10L)));
//        arretsARamasser.addAll(arretService.findByRue(rueService.findById(16L)));
//        arretsARamasser.addAll(arretService.findByRue(rueService.findById(22L)));

        // La liste complète des chemins possibles
        List<CheminPossibleDto> cheminPossibleDtos = new ArrayList<>();
        // On crée un premier chemin auquel on ajoute l'arrêt de départ, pour ensuite l'ajouter à la liste de chemins possibles
        CheminPossibleDto cheminPossibleDto = new CheminPossibleDto();
        cheminPossibleDto.addArret(arretDepart);
        cheminPossibleDtos.add(cheminPossibleDto);

        logger.info("Début de la recherche des chemins possibles");
        // Fonction récursive qui cherche les chemins possibles à partir d'un arrêt donné et les affecte à la liste passé en argument
        ItineraireUtils.chercheChemin(arretDepart, 0, cheminPossibleDtos);
        logger.info("Fin de la recherche des chemins possibles");


        cheminPossibleDtos.removeIf(cheminPossibleDto1 -> !terminus.contains(cheminPossibleDto1.dernierArret()));

        Map<RamassageCyclisteVelo, Itineraire> itineraireMap = new HashMap<>();
        Set<Arret> arretsRamasses = new HashSet<>();

        logger.info("Début de l'attribution des arrêts aux cyclistes");
        while (!arretsRamasses.containsAll(arretsARamasser)) {
            for (RamassageCyclisteVelo ramassageCyclisteVelo : ramassage.getRamassageCyclisteVelos()) {
                int ordreRamassage = 0;
                if (!itineraireMap.containsKey(ramassageCyclisteVelo)) {
                    itineraireMap.put(ramassageCyclisteVelo, new Itineraire());
                    itineraireMap.get(ramassageCyclisteVelo).setRamassageCyclisteVelo(ramassageCyclisteVelo);
                }
                List<Arret> arrets = ItineraireUtils.ramasseCharge(ramassageCyclisteVelo, cheminPossibleDtos, arretsRamasses, itineraireMap);
                if (arrets != null) {
//                    for (Arret arret : arrets) {
//                        ItineraireArret itineraireArret = new ItineraireArret();
//                        itineraireArret.setItineraire(itineraireMap.get(ramassageCyclisteVelo));
//                        itineraireArret.setArret(arret);
//                        itineraireArret.setOrdre(ordreRamassage);
//                        ordreRamassage++;
//                        itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets().add(itineraireArret);
//                    }
                }
            }
        }
        logger.info("Fin de l'attribution des arrêts aux cyclistes");
        saveAll(itineraireMap.values());
        logger.info("Fin du calcul des itinéraires");
    }

    @Override
    public void recalculItineraire(Ramassage ramassage) {
        deleteAll(findByRamassage(ramassage));
        ramassage = ramassageService.findById(ramassage.getId());

        // Calculer les itinéraires pour chaque cycliste
        Arret arretDepart = arretService.findById(161L); // Porte d'Ivry
        List<Arret> terminus = arretService.findFeuille();
        Set<Arret> arretsARamasser = new HashSet<>();
        arretsARamasser.addAll(arretService.findByRue(rueService.findById(5L)));
        arretsARamasser.addAll(arretService.findByRue(rueService.findById(8L)));
        arretsARamasser.addAll(arretService.findByRue(rueService.findById(19L)));

        List<CheminPossibleDto> cheminPossibleDtos = new ArrayList<>();
        CheminPossibleDto cheminPossibleDto = new CheminPossibleDto();
        cheminPossibleDto.addArret(arretDepart);
        cheminPossibleDtos.add(cheminPossibleDto);

        logger.info("Début de la recherche des chemins possibles");
        ItineraireUtils.chercheChemin(arretDepart, 0, cheminPossibleDtos);
        logger.info("Fin de la recherche des chemins possibles");

        cheminPossibleDtos.removeIf(cheminPossibleDto1 -> !terminus.contains(cheminPossibleDto1.dernierArret()));

        Map<RamassageCyclisteVelo, Itineraire> itineraireMap = new HashMap<>();
        Set<Arret> arretsRamasses = new HashSet<>();

        logger.info("Début de l'attribution des arrêts aux cyclistes");
        while (!arretsRamasses.containsAll(arretsARamasser)) {
            for (RamassageCyclisteVelo ramassageCyclisteVelo : ramassage.getRamassageCyclisteVelos()) {
                int ordreRamassage = 0;
                List<Incident> incidents = incidentService.findByRamassage(ramassage);

                if (!itineraireMap.containsKey(ramassageCyclisteVelo)) {
                    itineraireMap.put(ramassageCyclisteVelo, new Itineraire());
                    itineraireMap.get(ramassageCyclisteVelo).setRamassageCyclisteVelo(ramassageCyclisteVelo);
                }

                List<Arret> arrets = ItineraireUtils.ramasseCharge(ramassageCyclisteVelo, cheminPossibleDtos, arretsRamasses, itineraireMap);

                if (arrets != null) {
                    for (Arret arret : arrets) {
                        if (incidents.stream().anyMatch(incident -> incident.getTypeIncident() == TypeIncident.ARRET_INACCESSIBLE && incident.getArret().equals(arret))) {
                            logger.warn("Arrêt inaccessible: " + arret.getLibelle());
                            continue; // Passer cet arrêt et chercher un autre chemin
                        }

                        if (incidents.stream().anyMatch(incident -> incident.getTypeIncident() == TypeIncident.ACCIDENT_CORPOREL)) {
                            logger.warn("Accident corporel détecté, recalcul en cours...");
                            //recalculerCheminAlternatif(ramassageCyclisteVelo, cheminPossibleDtos, arretsRamasses, itineraireMap);
                            return;
                        }

                        if (incidents.stream().anyMatch(incident -> incident.getTypeIncident() == TypeIncident.CASSE_VELO)) {
                            logger.warn("Casse de vélo, recalcul de l'itinéraire...");
                            // Gérer le changement de vélo ou assigner un itinéraire à un autre cycliste
                            affecterCyclisteAlternatif(ramassageCyclisteVelo);
                            return;
                        }

                        ItineraireArret itineraireArret = new ItineraireArret();
                        itineraireArret.setItineraire(itineraireMap.get(ramassageCyclisteVelo));
                        itineraireArret.setArret(arret);
                        itineraireArret.setOrdrePassage(ordreRamassage++);
                        itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets().add(itineraireArret);
                    }
                }
            }
        }
        logger.info("Fin de l'attribution des arrêts aux cyclistes");

        saveAll(itineraireMap.values());
        logger.info("Fin du calcul des itinéraires");

        ramassage = ramassageService.findById(ramassage.getId());
        ramassage.setARecalculer(false);
        ramassageService.save(ramassage);
    }

    private void recalculerCheminAlternatif(RamassageCyclisteVelo ramassageCyclisteVelo, List<CheminPossibleDto> cheminPossibleDtos,
                                            Set<Arret> arretsRamasses, Map<RamassageCyclisteVelo, Itineraire> itineraireMap) {
        List<ItineraireArret> itineraireArrets = itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets();
        Arret arretDepart = itineraireArrets.isEmpty() ? arretService.findById(161L) : itineraireArrets.get(itineraireArrets.size() - 1).getArret();
        List<CheminPossibleDto> cheminsValides = cheminPossibleDtos.stream()
                .filter(chemin -> {
                    // Retrieve the list of incidents associated with the collection
                    List<Incident> incidents = incidentService.findByRamassage(ramassageCyclisteVelo.getRamassage());

                    // Check that none of the stops correspond to a bodily injury incident
                    return chemin.getArrets().stream()
                            .filter(Objects::nonNull) // Filter out null stops
                            .noneMatch(arret -> {
                                // Ensure the arret is not null before comparing
                                return incidents.stream()
                                        .anyMatch(incident ->
                                                incident.getTypeIncident() == TypeIncident.ACCIDENT_CORPOREL &&
                                                        arret.equals(incident.getArret())); // Safely compare stops
                            });
                })
                .collect(Collectors.toList());

        ItineraireUtils.chercheChemin(arretDepart,0, cheminsValides);
    }

    private void affecterCyclisteAlternatif(RamassageCyclisteVelo ramassageCyclisteVelo) {
        // Récupérer les incidents associés au ramassage actuel
        List<Incident> incidents = incidentService.findByRamassage(ramassageCyclisteVelo.getRamassage());

        boolean veloCasse = incidents.stream().anyMatch(incident -> incident.getTypeIncident() == TypeIncident.CASSE_VELO);

        if (veloCasse) {
            logger.warn("Le vélo est cassé. Recherche d'un nouveau cycliste et d'un vélo disponible...");

            // Rechercher un vélo disponible
            List<Velo> velosDisponibles = veloService.findByStatut(StatutVelo.UTILISABLE);
            if (velosDisponibles.isEmpty()) {
                logger.warn("Aucun vélo disponible pour réaffectation.");
                return; // Sortir si aucun vélo n'est disponible
            }

            // Récupérer le prochain utilisateur à affecter (cela peut être une logique personnalisée)
            Utilisateur nouvelUtilisateur = ramassageCyclisteVelo.getCycliste();

            // Assigner le nouvel utilisateur à un vélo disponible
            Velo nouveauVelo = velosDisponibles.get(0); // Choisir le premier vélo disponible, vous pouvez ajouter une logique de sélection plus avancée
            ramassageCyclisteVelo.setCycliste(nouvelUtilisateur);
            ramassageCyclisteVelo.setVelo(nouveauVelo);

            logger.info("Le cycliste " + nouvelUtilisateur.getNom() + " a été affecté au vélo " + nouveauVelo.getId());
        }
    }


    @Override
    public void deleteAll(List<Itineraire> itineraires) {
        itineraireDao.deleteAll(itineraires);
    }

    @Override
    public Itineraire save(Itineraire itineraire) {
        return itineraireDao.save(itineraire);
    }
    @Override
    public void deleteByRamassageCyclisteVelo(RamassageCyclisteVelo ramassageCyclisteVelo) {
        itineraireDao.deleteAllByRamassageCyclisteVelo(ramassageCyclisteVelo);
    }

    @Override
    public void delete(Itineraire itineraire) {
        itineraireDao.delete(itineraire);
    }
}
