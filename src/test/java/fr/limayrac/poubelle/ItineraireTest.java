package fr.limayrac.poubelle;

import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.model.ArretVoisin;
import fr.limayrac.poubelle.model.Utilisateur;
import fr.limayrac.poubelle.model.ramassage.Passage;
import fr.limayrac.poubelle.model.ramassage.Ramassage;
import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;
import fr.limayrac.poubelle.service.IArretService;
import fr.limayrac.poubelle.service.IPassageService;
import fr.limayrac.poubelle.service.IRamassageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ItineraireTest {
    private static final Logger logger = LoggerFactory.getLogger(ItineraireTest.class);
    @Autowired
    private IArretService arretService;
    @Autowired
    private IRamassageService ramassageService;
    @Autowired
    private IPassageService passageService;
    private List<Arret> arrets;
    private Ramassage ramassage;
    private Arret arretDepart;
    @Before
    public void setUp() {
        ramassage = ramassageService.findById(1L);
        arrets = arretService.findAll();
        arretDepart = arretService.findById(161L);
//        passageDepart = passageService.finfByRamassageAndArret(ramassage, arretDepart);
    }
    @Test
    public void testCalculItineraire() {
        Arret arretCourant = arretDepart;
        Map<Utilisateur, List<Passage>> map = new HashMap<>();
        Map<Utilisateur, Arret> arretCourantParUtilisateur = new HashMap<>();
        try {
            while (!arrets.stream().allMatch(arret -> arret.getRamasse().equals(true))) {
                // On cherche le passage pour chaque cycliste
                for (RamassageCyclisteVelo ramassageCyclisteVelo : ramassage.getRamassageCyclisteVelos()) {
                    // On vérifie que le vélo puisse encore ramasser
                    if (!ramassageCyclisteVelo.getVelo().chargeMaxAtteint()) {
                        // Si l'arrêt courant est rammassé, on regarde ses voisins
                        if (arretDepart.getRamasse()) {
                            Passage passage;
                            if (arretCourantParUtilisateur.containsKey(ramassageCyclisteVelo.getCycliste())) {
                                passage = arretRecursif(arretCourantParUtilisateur.get(ramassageCyclisteVelo.getCycliste()).getArretVoisins());
                            } else {
                                passage = arretRecursif(arretDepart.getArretVoisins());
                            }
                            passage.setDatePassage(LocalDateTime.now());
                            passage.setRamasseur(ramassageCyclisteVelo.getCycliste());
                            passage.setRamasse(true);
                            passage.setRamassage(ramassage);

                            // On change l'arrêt courant du cycliste
                            arretCourantParUtilisateur.put(ramassageCyclisteVelo.getCycliste(), passage.getArret());
                            if (!map.containsKey(ramassageCyclisteVelo.getCycliste())) {
                                map.put(ramassageCyclisteVelo.getCycliste(), new ArrayList<>());
                            }
                            map.get(ramassageCyclisteVelo.getCycliste()).add(passage);
                            // TODO enregistrer passage

                            // TODO Reduire l'autonomie
                            // TODO Augmenter la charge
                        } else {
                            // Si l'arrêt de départ n'a pas été ramassé, on le ramasse
                            arretDepart.setRamasse(true);
                            Passage passage = new Passage();
                            passage.setArret(arretDepart);
                            passage.setRamassage(ramassage);
                            passage.setRamasse(true);
                            passage.setDatePassage(LocalDateTime.now());
                            passage.setRamasseur(ramassageCyclisteVelo.getCycliste());
                            if (!arretCourantParUtilisateur.containsKey(ramassageCyclisteVelo.getCycliste())) {
                                arretCourantParUtilisateur.put(ramassageCyclisteVelo.getCycliste(), arretDepart);
                            }
                            if (!map.containsKey(ramassageCyclisteVelo.getCycliste())) {
                                map.put(ramassageCyclisteVelo.getCycliste(), new ArrayList<>());
                            }
                            map.get(ramassageCyclisteVelo.getCycliste()).add(passage);
                        }
                    } else {
                        // TODO retour au bercail
                        Map<Utilisateur, List<Passage>> mapOriginal = new HashMap<>(map);
                        for (int i = mapOriginal.get(ramassageCyclisteVelo.getCycliste()).size()-1; i != 0; i--) {
                            // Si on tombe sur l'arrêt de départ, on décharge
                            if (mapOriginal.get(ramassageCyclisteVelo.getCycliste()).get(i).getArret().equals(arretDepart)) {
                                // On est à l'usine
                                // TODO Vider la cargaison
                                ramassageCyclisteVelo.getVelo().setCharge(0);
                            } else {
                                // Sinon on regarde les arrêts voisins
                                Passage passageDepart = null;
                                for (ArretVoisin arretVoisin : mapOriginal.get(ramassageCyclisteVelo.getCycliste()).get(i).getArret().getArretVoisins()) {
                                    if (arretVoisin.getArretSuivant().equals(arretDepart)) {
                                        // On est tombé sur l'arrêt de départ
                                        passageDepart = new Passage();
                                        passageDepart.setArret(arretVoisin.getArret());
                                        passageDepart.setDatePassage(LocalDateTime.now());
                                        passageDepart.setRamasseur(ramassageCyclisteVelo.getCycliste());
                                        passageDepart.setRamassage(ramassage);
                                    }
                                }
                                // Sinon on enregistre juste le passage courant
                                Passage passage = new Passage();
                                passage.setArret(mapOriginal.get(ramassageCyclisteVelo.getCycliste()).get(i).getArret());
                                passage.setDatePassage(LocalDateTime.now());
                                passage.setRamasseur(ramassageCyclisteVelo.getCycliste());
                                passage.setRamassage(ramassage);
                                map.get(ramassageCyclisteVelo.getCycliste()).add(passage);
                                if (passageDepart != null) {
                                    map.get(ramassageCyclisteVelo.getCycliste()).add(passageDepart);
                                }
                            }
                        }
                    }
                }
            }
        } catch (OutOfMemoryError e) {
            for (Map.Entry<Utilisateur, List<Passage>> entry : map.entrySet()) {
                logger.info("{} : {}", entry.getKey().getLogin(), entry.getValue().size());
                for (Passage passage : entry.getValue()) {
                    logger.info(passage.getArret().getLibelle());
                }
            }
        }
    }

    public Passage arretRecursif(List<ArretVoisin> arretVoisins) {
        // TODO Attention aux arrêts suivants null, il faut retourner en arrière
        // Si tous les arrets suivants sont parcourus
        if (allRamasse(arretVoisins)) {
            // Alors on regarde les arrêts suivants des arrêts suivants
            for (ArretVoisin arretN : arretVoisins) {
//                // On parcours une première fois les arrets suivants immédiat
//                for (ArretVoisin arretN1 : arretN.getArretSuivant().getArretVoisins()) {
//                    if (!allRamasse(arretN1.getArretSuivant().getArretVoisins())) {
//                        return arretRecursif(arretN1.getArretSuivant().getArretVoisins());
//                    }
//                }
                // Si aucun arrêt suivant
                for (ArretVoisin arretN1 : arretN.getArretSuivant().getArretVoisins()) {
                    // On regarde si tous les arrets voisins de 1 niveaux sont ramasse
                    return arretRecursif(arretN1.getArretSuivant().getArretVoisins());
                }
            }
        } else {
            // Un arret voisin n'est pas ramassé, on le notifie
            for (ArretVoisin arretVoisin : arretVoisins) {
                if (!arretVoisin.getArretSuivant().getRamasse()) {
                    Passage passage = new Passage();
                    passage.setArret(arretVoisin.getArretSuivant());
                    passage.setRamasse(true);
                    passage.setDatePassage(LocalDateTime.now());
                    passage.setRamassage(ramassage);
                    return passage;
                }
            }
        }
        return null;
    }

    public Boolean allRamasse(List<ArretVoisin> arretVoisins) {
        // TODO attention au arrêts suivants null
        return arretVoisins.stream().allMatch(arretVoisin -> arretVoisin.getArretSuivant().getRamasse().equals(true));
    }
}
