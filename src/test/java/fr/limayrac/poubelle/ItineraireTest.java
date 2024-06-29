package fr.limayrac.poubelle;

import fr.limayrac.poubelle.dto.CheminPossibleDto;
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
                                passage = arretRecursif(arretCourantParUtilisateur.get(ramassageCyclisteVelo.getCycliste()).getArretVoisinsSuivant());
                            } else {
                                passage = arretRecursif(arretDepart.getArretVoisinsSuivant());
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
                                for (ArretVoisin arretVoisin : mapOriginal.get(ramassageCyclisteVelo.getCycliste()).get(i).getArret().getArretVoisinsSuivant()) {
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
                for (ArretVoisin arretN1 : arretN.getArretSuivant().getArretVoisinsSuivant()) {
                    // On regarde si tous les arrets voisins de 1 niveaux sont ramasse
                    return arretRecursif(arretN1.getArretSuivant().getArretVoisinsSuivant());
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

    @Test
    public void chercheChemin() {
        List<Arret> terminus = arretService.findFeuille();
        Map<Arret, List<CheminPossibleDto>> cheminsPossiblesParTerminus = new HashMap<>();
        List<CheminPossibleDto> cheminPossibleDtosSuivant = new ArrayList<>();
        CheminPossibleDto cheminPossibleDto = new CheminPossibleDto();
        cheminPossibleDto.addArret(arretDepart);
        cheminPossibleDtosSuivant.add(cheminPossibleDto);
        chercheCheminPrecedent(arretDepart/*, arret*/, 0, cheminPossibleDtosSuivant);
//        cheminsPossiblesParTerminus.put(arret, cheminPossibleDtosSuivant);
//        for (Arret arret : terminus) {
//            if (!cheminsPossiblesParTerminus.containsKey(arret)) {
//                List<CheminPossibleDto> cheminPossibleDtosSuivant = new ArrayList<>();
//                CheminPossibleDto cheminPossibleDto = new CheminPossibleDto();
//                cheminPossibleDto.addArret(arretDepart);
//                cheminPossibleDtosSuivant.add(cheminPossibleDto);
//                chercheCheminPrecedentRecursif(arretDepart, arret, 0, cheminPossibleDtosSuivant);
//                cheminsPossiblesParTerminus.put(arret, cheminPossibleDtosSuivant);
//            }
//        }
        List<String> messages = new ArrayList<>();
        for (CheminPossibleDto cheminPossibleDto1 : cheminPossibleDtosSuivant) {
            StringBuilder message = new StringBuilder();
            for (Arret arret : cheminPossibleDto1.getArrets()) {
                message.append(arret.getLibelle()).append(" ");
            }
            messages.add(message.toString());
            System.out.print(message + "\n");
//            logger.info(message.toString());
        }
        cheminsPossiblesParTerminus = cheminsPossiblesParTerminus;
    }

    public void chercheCheminSuivantRecursif(Arret arret/*, Arret arretDestination*/, Integer numeroChemin, List<CheminPossibleDto> cheminPossibleDtos) {
//        if (!arret.equals(arretDestination)) {
            if (arret.getArretVoisinsSuivant().size() == 1) {
                // S'il s'agit d'un arrêt simple, on l'ajoute juste au chemin possible
                cheminPossibleDtos.get(numeroChemin).addArret(arret.getArretVoisinsSuivant().get(0).getArret());
                chercheCheminSuivantRecursif(arret.getArretVoisinsSuivant().get(0).getArretSuivant()/*, arretDestination*/, numeroChemin, cheminPossibleDtos);
            } else {
                // Sinon on dupplique le chemin actuel par le nomnbre de voisins -1 (comme on garde le premier chemin)
                for (int i = 0; i < arret.getArretVoisinsSuivant().size()-1; i++) {
                    cheminPossibleDtos.add(new CheminPossibleDto(cheminPossibleDtos.get(numeroChemin)));
                }
                for (ArretVoisin arretVoisin : arret.getArretVoisinsSuivant()) {
                    cheminPossibleDtos.get(numeroChemin).addArret(arretVoisin.getArretSuivant());
                    chercheCheminSuivantRecursif(arretVoisin.getArretSuivant()/*, arretDestination*/, numeroChemin, cheminPossibleDtos);
                    // On incrémente le nombre de chemin possible pour chaque voisins
                    numeroChemin++;
                }
            }
//        }
    }
    public void chercheCheminPrecedentRecursif(Arret arret/*, Arret arretDestination*/, Integer numeroChemin, List<CheminPossibleDto> cheminPossibleDtos) {
        // TODO hash et equals de Arret
        if (!arret.getArretVoisinsPrecedent().isEmpty()) {
            if (arret.getArretVoisinsPrecedent().size() == 1) {
                // S'il s'agit d'un arrêt simple, on l'ajoute juste au chemin possible
                cheminPossibleDtos.get(numeroChemin).addArret(arret.getArretVoisinsPrecedent().get(0).getArret());
                chercheCheminPrecedentRecursif(arret.getArretVoisinsPrecedent().get(0).getArret()/*, arretDestination*/, numeroChemin, cheminPossibleDtos);
            } else {
                // Sinon on dupplique le chemin actuel par le nomnbre de voisins -1 (comme on garde le premier chemin)
                for (int i = 0; i < arret.getArretVoisinsPrecedent().size()-1; i++) {
                    cheminPossibleDtos.add(new CheminPossibleDto(cheminPossibleDtos.get(numeroChemin)));
                }
                for (ArretVoisin arretVoisin : arret.getArretVoisinsPrecedent()) {
                    cheminPossibleDtos.get(numeroChemin).addArret(arretVoisin.getArret());
                    chercheCheminPrecedentRecursif(arretVoisin.getArret()/*, arretDestination*/, numeroChemin, cheminPossibleDtos);
                    // On incrémente le nombre de chemin possible pour chaque voisins
                    numeroChemin++;
                }
            }
        }
    }

    public void chercheCheminPrecedent(Arret arret, Integer numeroChemin, List<CheminPossibleDto> cheminPossibleDtos) {
        if (!arret.getArretVoisinsPrecedent().isEmpty()) {
            if (arret.getArretVoisinsPrecedent().size() == 1 || arret.arretVoisinsPrecedentIdentique()) {
                // S'il s'agit d'un arrêt simple, on l'ajoute juste au chemin possible
                cheminPossibleDtos.get(numeroChemin).addArret(arret.getArretVoisinsPrecedent().get(0).getArret());
                chercheCheminPrecedent(arret.getArretVoisinsPrecedent().get(0).getArret(), numeroChemin, cheminPossibleDtos);
            } else {
                // TODO Revoir tout

                for (int i = 0; i < arret.getArretVoisinsPrecedent().size()-1; i++) {
                    cheminPossibleDtos.add(new CheminPossibleDto(cheminPossibleDtos.get(numeroChemin)));
                }
                for (ArretVoisin arretVoisin : arret.getArretVoisinsPrecedent()) {
                    cheminPossibleDtos.get(numeroChemin).addArret(arretVoisin.getArret());
                    chercheCheminPrecedent(arretVoisin.getArret(), numeroChemin, cheminPossibleDtos);
                    // On incrémente le numéro du chemin possible pour chaque voisins
                    // TODO, associer le numero de chemin au chemin ou trouver une autre alternative
                    numeroChemin = cheminPossibleDtos.size()-1;
                }
            }
        }
    }
}
