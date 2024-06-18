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
        while (!arrets.stream().allMatch(arret -> arret.getRamasse().equals(true))) {
            for (RamassageCyclisteVelo ramassageCyclisteVelo : ramassage.getRamassageCyclisteVelos()) {
                if (!ramassageCyclisteVelo.getVelo().chargeMaxAtteint()) {
                    if (arretCourant.getRamasse()) {
                        Passage passage = arretRecursif(arretCourant.getArretVoisins());
                        passage.setDatePassage(LocalDateTime.now());
                        passage.setRamasseur(ramassageCyclisteVelo.getCycliste());
                        passage.setRamasse(true);
                        if (!map.containsKey(ramassageCyclisteVelo.getCycliste())) {
                            map.put(ramassageCyclisteVelo.getCycliste(), new ArrayList<>());
                        }
                        map.get(ramassageCyclisteVelo.getCycliste()).add(passage);
                        // TODO enregistrer passage

                        // TODO Reduire l'autonomie
                        // TODO Augmenter la charge
                    } else {
                        arretCourant.setRamasse(true);
                        Passage passage = new Passage();
                        passage.setArret(arretDepart);
                        passage.setRamassage(ramassage);
                        passage.setRamasse(true);
                        passage.setDatePassage(LocalDateTime.now());
                        passage.setRamasseur(ramassageCyclisteVelo.getCycliste());
                        if (!map.containsKey(ramassageCyclisteVelo.getCycliste())) {
                            map.put(ramassageCyclisteVelo.getCycliste(), new ArrayList<>());
                        }
                        map.get(ramassageCyclisteVelo.getCycliste()).add(passage);
                        // TODO enregistrer passage
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
                                if (arretVoisin.getArret().equals(arretDepart)) {
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
    }

    public Passage arretRecursif(List<ArretVoisin> arretVoisins) {
        // Si tous les arretsVoisins sont parcourus
        if (allRamasse(arretVoisins)) {
            for (ArretVoisin arretN : arretVoisins) {
                // On parours les arrêts voisins afin de trouver
                for (ArretVoisin arretN1 : arretN.getArret().getArretVoisins()) {
                    // On regarde si tous les arrets voisins de 1 niveaux sont ramasse
                    if (!allRamasse(arretN1.getArret().getArretVoisins())) {
                        return arretRecursif(arretN1.getArret().getArretVoisins());
                    }
                }
                // Si aucun arrêt voisin de niveax 1 n'est trouvé
                for (ArretVoisin arretN1 : arretN.getArret().getArretVoisins()) {
                    // On regarde si tous les arrets voisins de 1 niveaux sont ramasse
                    return arretRecursif(arretN1.getArret().getArretVoisins());
                }
            }
        } else {
            for (ArretVoisin arretVoisin : arretVoisins) {
                if (!arretVoisin.getArret().getRamasse()) {
                    Passage passage = new Passage();
                    passage.setArret(arretVoisin.getArret());
                    passage.setRamasse(true);
                    passage.setDatePassage(LocalDateTime.now());
                    return passage;
                }
            }
        }
        return null;
    }

    public Boolean allRamasse(List<ArretVoisin> arretVoisins) {
        return arretVoisins.stream().allMatch(arretVoisin -> arretVoisin.getArret().getRamasse().equals(true));
    }
}
