package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IItineraireDao;
import fr.limayrac.poubelle.dto.CheminPossibleDto;
import fr.limayrac.poubelle.model.*;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;
import fr.limayrac.poubelle.service.*;
import fr.limayrac.poubelle.utils.ItineraireUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
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
        ramassage = ramassageService.save(ramassage);

        // On calcule les itinéraires de chaque cycliste
        Arret arretDepart = arretService.findById(161L); // Porte d'Ivry
        List<Arret> terminus = arretService.findFeuille();
        // TODO Voir pour inclure un booléen si l'arrêt est inaccessible
        // FIXME Modifier cette ligne une fois les tests finis
        Set<Arret> arretsARamasser = new HashSet<>();
//        List<Arret> arretsARamasser = arretService.findByAccessible(true);
        arretsARamasser.addAll(arretService.findByRue(rueService.findById(5L)));
        arretsARamasser.addAll(arretService.findByRue(rueService.findById(8L)));
        arretsARamasser.addAll(arretService.findByRue(rueService.findById(19L)));

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
        logger.info("Fin de l'attribution des arrêts aux cyclistes");
        saveAll(itineraireMap.values());
        logger.info("Fin du calcul des itinéraires");
    }

    @Override
    public void recalculItineraire(Ramassage ramassage) {
        deleteAll(findByRamassage(ramassage));
        ramassage = ramassageService.findById(ramassage.getId());

        // On calcule les itinéraires de chaque cycliste
        Arret arretDepart = arretService.findById(161L); // Porte d'Ivry
        List<Arret> terminus = arretService.findFeuille();
        // TODO Voir pour inclure un booléen si l'arrêt est inaccessible
        // FIXME Modifier cette ligne une fois les tests finis
        Set<Arret> arretsARamasser = new HashSet<>();
//        List<Arret> arretsARamasser = arretService.findByAccessible(true);
        arretsARamasser.addAll(arretService.findByRue(rueService.findById(5L)));
        arretsARamasser.addAll(arretService.findByRue(rueService.findById(8L)));
        arretsARamasser.addAll(arretService.findByRue(rueService.findById(19L)));

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
        logger.info("Fin de l'attribution des arrêts aux cyclistes");
        saveAll(itineraireMap.values());
        logger.info("Fin du calcul des itinéraires");
    }

    @Override
    public void deleteAll(List<Itineraire> itineraires) {
        itineraireDao.deleteAll(itineraires);
    }

    @Override
    public Itineraire save(Itineraire itineraire) {
        return itineraireDao.save(itineraire);
    }
}
