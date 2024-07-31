//package fr.limayrac.poubelle;
//
//import fr.limayrac.poubelle.dto.CheminPossibleDto;
//import fr.limayrac.poubelle.model.Arret;
//import fr.limayrac.poubelle.model.ArretAdjacent;
//import fr.limayrac.poubelle.model.Itineraire;
//import fr.limayrac.poubelle.model.ramassage.Ramassage;
//import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;
//import fr.limayrac.poubelle.service.IArretService;
//import fr.limayrac.poubelle.service.IPassageService;
//import fr.limayrac.poubelle.service.IRamassageService;
//import fr.limayrac.poubelle.service.IRueService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.*;
//
//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//public class ItineraireTest {
//    private static final Logger logger = LoggerFactory.getLogger(ItineraireTest.class);
//    @Autowired
//    private IArretService arretService;
//    @Autowired
//    private IRamassageService ramassageService;
//    @Autowired
//    private IRueService rueService;
//    @Autowired
//    private IPassageService passageService;
//    private List<Arret> arrets;
//    private Ramassage ramassage;
//    private Arret arretDepart;
//    Set<Arret> arretsARamasser;
//    @Before
//    public void setUp() {
//        ramassage = ramassageService.findById(1L);
//        arrets = arretService.findAll();
//        arretDepart = arretService.findById(161L);
//        arretsARamasser = new HashSet<>();
//        arretsARamasser.addAll(arretService.findByRue(rueService.findById(5L)));
//        arretsARamasser.addAll(arretService.findByRue(rueService.findById(8L)));
//        arretsARamasser.addAll(arretService.findByRue(rueService.findById(19L)));
////        passageDepart = passageService.finfByRamassageAndArret(ramassage, arretDepart);
//    }
//
//    @Test
//    public void chercheChemin() {
//        List<Arret> terminus = arretService.findFeuille();
//
//        List<CheminPossibleDto> cheminPossibleDtos = new ArrayList<>();
//        CheminPossibleDto cheminPossibleDto = new CheminPossibleDto();
//        cheminPossibleDto.addArret(arretDepart);
//        cheminPossibleDtos.add(cheminPossibleDto);
//        chercheChemin(arretDepart, 0, cheminPossibleDtos);
//
//
//        // On le supprime car il s'agit d'une boucle
//        // Print pour les tests
//        //            StringBuilder message = new StringBuilder();
//        //            for (Arret arret : cheminPossibleDto1.getArrets()) {
//        //                message.append(arret.getLibelle()).append(" ");
//        //            }
//        //            System.out.print(message + "\n");
//        cheminPossibleDtos.removeIf(cheminPossibleDto1 -> !terminus.contains(cheminPossibleDto1.dernierArret()));
//
//        Map<RamassageCyclisteVelo, Itineraire> itineraireMap = new HashMap<>();
//        List<Arret> arretsRamasses = new ArrayList<>();
//
//        while (!arretsRamasses.containsAll(arretsARamasser)) {
//            for (RamassageCyclisteVelo ramassageCyclisteVelo : ramassage.getRamassageCyclisteVelos()) {
//                // TODO Chercher chemin adéquat
//
//                if (!itineraireMap.containsKey(ramassageCyclisteVelo)) {
//                    itineraireMap.put(ramassageCyclisteVelo, new Itineraire());
//                    itineraireMap.get(ramassageCyclisteVelo).setRamassageCyclisteVelo(ramassageCyclisteVelo);
//                }
//                List<Arret> arrets = ramasseCharge(ramassageCyclisteVelo, cheminPossibleDtos, arretsRamasses);
//                if (arrets != null) {
//                    itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets().addAll(arrets);
//                }
//            }
//        }
//
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("Début : \n");
//        for (Itineraire itineraire : itineraireMap.values()) {
//            stringBuilder.append(itineraire.getItineraireArrets().size()).append(" : ").append(itineraire.getArrets().toString()).append("\n");
//        }
//        System.out.println(stringBuilder);
//    }
//
//    public void removeArrets(List<CheminPossibleDto> cheminPossibleDtos, Arret arrets) {
//        for (CheminPossibleDto cheminPossibleDto : cheminPossibleDtos) {
//            if (arrets.equals(cheminPossibleDto.dernierArret())) {
//                cheminPossibleDto.removeArret();
//            }
//        }
//    }
//
//    public List<Arret> ramasseCharge(RamassageCyclisteVelo ramassageCyclisteVelo, List<CheminPossibleDto> allCheminsPossibles, List<Arret> arretsRamasse) {
//        if (!allCheminsPossibles.isEmpty()) {
//            // On trie la liste de tous les chemin pour récupérer la liste des chemins les plus court par arrêt
//            List<CheminPossibleDto> cheminPlusCourtParArret = cheminPlusCourtParArret(allCheminsPossibles);
//
//            // On récupère le chemin le plus court par arrêt
//            CheminPossibleDto cheminPossibleLePlusCourt = chercheCheminPlusCourt(cheminPlusCourtParArret);
//
//            // Le velo a assez d'autonomie pour faire l'allez-retour
//            if (ramassageCyclisteVelo.getVelo().autonomieRestante() >= cheminPossibleLePlusCourt.calculDistance()) {
//
//            }
//
//            // On calcule l'autonomie
//            // TODO vérifier si le vélo à assez de batterie pour faire un allez-retour
//            ramassageCyclisteVelo.getVelo().setAutonomie(ramassageCyclisteVelo.getVelo().getAutonomie() + cheminPossibleLePlusCourt.calculDistance());
//
//            // La liste des arrêts parcourus
//            List<Arret> arrets = new ArrayList<>(cheminPossibleLePlusCourt.getArrets());
//
//            // On retire le dernier vu qu'il est ajouté en dessous
//            arrets.remove(arrets.size()-1);
//            for (int i = cheminPossibleLePlusCourt.getArrets().size()-1; i >= 0; i--) {
//                arrets.add(cheminPossibleLePlusCourt.getArrets().get(i));
//                // Si la charge max n'est pas atteinte on ramasse l'arrêt
//                if (!ramassageCyclisteVelo.getVelo().chargeMaxAtteint()) {
//                    arretsRamasse.add(cheminPossibleLePlusCourt.getArrets().get(i));
//                    // On supprime l'arrêt du chemin
//                    ramassageCyclisteVelo.getVelo().setCharge(ramassageCyclisteVelo.getVelo().getCharge() + 50);
//                    // On supprime l'arrêt pour tout les chemins dont il est la dernier arrêt
//                    removeArrets(allCheminsPossibles, cheminPossibleLePlusCourt.getArrets().get(i));
//                }
//            }
//            if (cheminPossibleLePlusCourt.getArrets().isEmpty()) {
//                allCheminsPossibles.remove(cheminPossibleLePlusCourt);
//            }
//            // Si la charge max n'est pas atteinte on recommence sur un autre chemin
//            if (!ramassageCyclisteVelo.getVelo().chargeMaxAtteint()) {
//                // Il faut retirer le point de départ sinon il apparaît deux fois aussi
//                arrets.remove(arrets.size()-1);
//                // On retire le chemin que l'on vient de parcourir de la liste
//                List<Arret> arretsRecursif = ramasseCharge(ramassageCyclisteVelo, allCheminsPossibles, arretsRamasse);
//                if (arretsRecursif != null) {
//                    arrets.addAll(arretsRecursif);
//                }
//            } else {
////            cheminPlusCourtParArret.remove(cheminPossibleLePlusCourt);
//                // On vide la charge
//                ramassageCyclisteVelo.getVelo().setCharge(0);
//                // TODO, demander si à chaque fois que l'on passe par l'arrêt de départ on change la batterie
//            }
//            return arrets;
//        }
//        return null;
//    }
//
//    public List<CheminPossibleDto> cheminPlusCourtParArret(List<CheminPossibleDto> cheminPossibleDtos) {
//        Map<Arret, CheminPossibleDto> arretCheminPossibleDtoMap = new HashMap<>();
//        for (CheminPossibleDto cheminPossibleDto : cheminPossibleDtos) {
//            if (!arretCheminPossibleDtoMap.containsKey(cheminPossibleDto.dernierArret()) ||
//                    arretCheminPossibleDtoMap.get(cheminPossibleDto.dernierArret()).calculDistance() > cheminPossibleDto.calculDistance()) {
//                arretCheminPossibleDtoMap.put(cheminPossibleDto.dernierArret(), cheminPossibleDto);
//            }
//        }
////        cheminPossibleDtos.removeIf(cheminPossibleDto1 -> !arretCheminPossibleDtoMap.containsValue(cheminPossibleDto1));
//        return new ArrayList<>(arretCheminPossibleDtoMap.values());
//    }
//
//    public CheminPossibleDto chercheCheminPlusCourt(List<CheminPossibleDto> cheminPossibleDtos) {
//        CheminPossibleDto cheminPossibleLePlusCourt = null;
//        for (CheminPossibleDto cheminPossibleDto : cheminPossibleDtos) {
//            if (cheminPossibleLePlusCourt == null || cheminPossibleLePlusCourt.calculDistance() > cheminPossibleDto.calculDistance()) {
//                cheminPossibleLePlusCourt = cheminPossibleDto;
//            }
//        }
//        return cheminPossibleLePlusCourt;
//    }
//
//    public void chercheChemin(Arret arret, int numeroChemin, List<CheminPossibleDto> cheminPossibleDtos) {
//         if (arret.getArretAdjacents().size() > 1) {
//            // On est simplement sur un arrêt de "liaison" (O-O), il est donc inutile de duppliquer un nouveau chemin
//            if (arret.getArretAdjacents().size() == 2) {
//                for (ArretAdjacent arretAdjacent : arret.getArretAdjacents()) {
//                    // On passe donc à l'arrêt adjacent suivant par lequel on n'est pas passé
//                    // Exemple pour le cas : Marcadet-Poissonniers -> Simplon -> Porte de Clignancourt, on se positionne sur l'arrêt simplon
//                    // Les arrêt adjacents sont Marcadet-Poissonniers et Porte de Clignancourt, et nous sommes passés par Marcadet-Poissonniers
//                    // Cela permet donc en bouclant sur les arrêts adjacents de ne pas ajouter l'arrêt par lequel on est déjà passé
//                    if (!cheminPossibleDtos.get(numeroChemin).getArrets().contains(arretAdjacent.getArretAdjacent())) {
//                        cheminPossibleDtos.get(numeroChemin).addArret(arretAdjacent.getArretAdjacent());
//                        chercheChemin(arretAdjacent.getArretAdjacent(), numeroChemin, cheminPossibleDtos);
//                    }
//                }
//            } else {
//                Set<Arret> arrets = new HashSet<>();
//                for (ArretAdjacent arretAdjacent : arret.getArretAdjacents()) {
//                    if (!cheminPossibleDtos.get(numeroChemin).getArrets().contains(arretAdjacent.getArretAdjacent())) {
//                        arrets.add(arretAdjacent.getArretAdjacent());
//                    }
//                }
//                if(!arrets.isEmpty()) {
//                    CheminPossibleDto sauvegarde = new CheminPossibleDto(cheminPossibleDtos.get(numeroChemin));
//                    cheminPossibleDtos.remove(sauvegarde);
//                    for (Arret arret1 : arrets) {
//                        CheminPossibleDto nouveauChemin = new CheminPossibleDto(sauvegarde);
//                        nouveauChemin.addArret(arret1);
//                        cheminPossibleDtos.add(nouveauChemin);
//                        chercheChemin(arret1, cheminPossibleDtos.indexOf(nouveauChemin), cheminPossibleDtos);
//                    }
//                }
//            }
//        }
//    }
//
//
////    public void chercheCheminPrecedent(Arret arret, Integer numeroChemin, List<CheminPossibleDto> cheminPossibleDtos) {
////        if (!arret.getArretVoisinsPrecedent().isEmpty()) {
////            if (arret.getArretVoisinsPrecedent().size() == 1 || arret.arretVoisinsPrecedentIdentique()) {
////                // S'il s'agit d'un arrêt simple, on l'ajoute juste au chemin possible
////                cheminPossibleDtos.get(numeroChemin).addArret(arret.getArretVoisinsPrecedent().get(0).getArret());
////                chercheCheminPrecedent(arret.getArretVoisinsPrecedent().get(0).getArret(), numeroChemin, cheminPossibleDtos);
////            } else {
////                int nbAdjacentNonParcouru = 0;
////                for (ArretAdjacent arretAdjacent : arret.getArretAdjacents()) {
////                    if (!cheminPossibleDtos.get(numeroChemin).getArrets().contains(arretAdjacent.getArretAdjacent())) {
////                        nbAdjacentNonParcouru++;
////                    }
////                }
////                if(nbAdjacentNonParcouru > 0) {
////                    for (int i = 0; i < nbAdjacentNonParcouru; i++) {
////                        cheminPossibleDtos.add(new CheminPossibleDto(cheminPossibleDtos.get(numeroChemin)));
////                    }
////                    for (ArretVoisin arretVoisin : arret.getArretVoisinsPrecedent()) {
////                        cheminPossibleDtos.get(numeroChemin).addArret(arretVoisin.getArret());
////                        chercheCheminPrecedent(arretVoisin.getArret(), numeroChemin, cheminPossibleDtos);
////                        // On cherche le chemin duppliqué qui était
////                        for (CheminPossibleDto cheminPossibleDto : cheminPossibleDtos) {
////                            if (cheminPossibleDto.dernierArret().equals(arretVoisin.getArretSuivant())) {
////                                numeroChemin = cheminPossibleDtos.indexOf(cheminPossibleDto);
////                            }
////                        }
////                    }
////                } else {
////                    // TODO, on est sur une boucle, il faut supprimer le chemin
////                    cheminPossibleDtos.remove(numeroChemin);
////                }
////            }
////        }
////    }
//
//
//}
