package fr.limayrac.poubelle.utils;

import fr.limayrac.poubelle.dto.CheminPossibleDto;
import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.model.ArretAdjacent;
import fr.limayrac.poubelle.model.Itineraire;
import fr.limayrac.poubelle.model.ItineraireArret;
import fr.limayrac.poubelle.model.ramassage.RamassageCyclisteVelo;

import java.util.*;

public class ItineraireUtils {
    /**
     * Effectue la recherche des chemins possibles à partir de l'arrêt passé en paramètre
     * Lorsque l'arrêt possède plus de deux arrêts adjacents, il s'agit d'un carrefour, il ajoute donc un autre chemin possible
     * à la liste des chemins sur lequel il effectura un appel récursif.
     * La condition de sortie de cette fonction est lorsque l'arrêt est un terminus ou lorsque le chemin est une boucle sur lui-même
     * @param arret l'arrêt courant de la recherche
     * @param numeroChemin l'indice du chemin actuel dans la liste des chemins
     * @param cheminPossibleDtos la liste des chemins possibles
     */
    public static void chercheChemin(Arret arret, int numeroChemin, List<CheminPossibleDto> cheminPossibleDtos) {
        if (arret.getArretAdjacents().size() > 1) {
            // On est simplement sur un arrêt de "liaison" (O-O), il est donc inutile de duppliquer un nouveau chemin
            if (arret.getArretAdjacents().size() == 2) {
                for (ArretAdjacent arretAdjacent : arret.getArretAdjacents()) {
                    // On passe donc à l'arrêt adjacent suivant par lequel on n'est pas passé
                    // Exemple pour le cas : Marcadet-Poissonniers -> Simplon -> Porte de Clignancourt, on se positionne sur l'arrêt simplon
                    // Les arrêt adjacents sont Marcadet-Poissonniers et Porte de Clignancourt, et nous sommes passés par Marcadet-Poissonniers
                    // Cela permet donc en bouclant sur les arrêts adjacents de ne pas ajouter l'arrêt par lequel on est déjà passé
                    if (!cheminPossibleDtos.get(numeroChemin).getArrets().contains(arretAdjacent.getArretAdjacent())) {
                        cheminPossibleDtos.get(numeroChemin).addArret(arretAdjacent.getArretAdjacent());
                        chercheChemin(arretAdjacent.getArretAdjacent(), numeroChemin, cheminPossibleDtos);
                    }
                }
            } else {
                Set<Arret> arrets = new HashSet<>();
                for (ArretAdjacent arretAdjacent : arret.getArretAdjacents()) {
                    if (!cheminPossibleDtos.get(numeroChemin).getArrets().contains(arretAdjacent.getArretAdjacent())) {
                        arrets.add(arretAdjacent.getArretAdjacent());
                    }
                }
                if(!arrets.isEmpty()) {
                    CheminPossibleDto sauvegarde = new CheminPossibleDto(cheminPossibleDtos.get(numeroChemin));
                    cheminPossibleDtos.remove(sauvegarde);
                    for (Arret arret1 : arrets) {
                        CheminPossibleDto nouveauChemin = new CheminPossibleDto(sauvegarde);
                        nouveauChemin.addArret(arret1);
                        cheminPossibleDtos.add(nouveauChemin);
                        chercheChemin(arret1, cheminPossibleDtos.indexOf(nouveauChemin), cheminPossibleDtos);
                    }
                }
            }
        }
    }

    // Map<RamassageCyclisteVelo, List<ItineraireArret>>
    // Map<RamassageCyclisteVelo, Itineraire>
    public static List<Arret> ramasseCharge(RamassageCyclisteVelo ramassageCyclisteVelo,
                                            List<CheminPossibleDto> allCheminsPossibles,
                                            Set<Arret> arretsRamasse,
                                            Map<RamassageCyclisteVelo, Itineraire> itineraireMap) {
        if (!allCheminsPossibles.isEmpty()) {
            // On trie la liste de tous les chemin pour récupérer la liste des chemins les plus court par arrêt
            List<CheminPossibleDto> cheminPlusCourtParArret = cheminPlusCourtParArret(allCheminsPossibles);

            // On récupère le chemin le plus court par arrêt
            // TODO à vérifier, il est sans doute possible de n'utiliser que cette fonction
            CheminPossibleDto cheminPossibleLePlusCourt = chercheCheminPlusCourt(cheminPlusCourtParArret);

            // La liste des arrêts parcourus
            List<Arret> arrets = new ArrayList<>(cheminPossibleLePlusCourt.getArrets());

            // Aller
            for (Arret arret : cheminPossibleLePlusCourt.getArrets()) {
                if (itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets().isEmpty() || !arret.equals(itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets().get(itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets().size()-1).getArret())) {
                    ItineraireArret itineraireArret = new ItineraireArret();
                    itineraireArret.setItineraire(itineraireMap.get(ramassageCyclisteVelo));
                    itineraireArret.setArret(arret);
                    itineraireArret.setOrdrePassage(getDernierOrdrePassage(itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets())+1);
                    itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets().add(itineraireArret);
                }
            }

            // Le velo a assez d'autonomie pour faire l'allez-retour
            if (ramassageCyclisteVelo.getVelo().getAutonomie() >= cheminPossibleLePlusCourt.calculDistance()) {
                // On calcule l'autonomie
                ramassageCyclisteVelo.getVelo().setAutonomie(ramassageCyclisteVelo.getVelo().getAutonomie() - cheminPossibleLePlusCourt.calculDistance());

                // On retire le dernier vu qu'il est ajouté en dessous
                arrets.remove(arrets.size()-1);
                itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets().remove(itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets().size()-1);
                // Retour
                for (int i = cheminPossibleLePlusCourt.getArrets().size()-1; i >= 0; i--) {
                    arrets.add(cheminPossibleLePlusCourt.getArrets().get(i));
                    ItineraireArret itineraireArret = new ItineraireArret();
                    itineraireArret.setItineraire(itineraireMap.get(ramassageCyclisteVelo));
                    itineraireArret.setArret(cheminPossibleLePlusCourt.getArrets().get(i));
                    itineraireArret.setOrdrePassage(getDernierOrdrePassage(itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets())+1);
                    // Si la charge max n'est pas atteinte on ramasse l'arrêt
                    if (!ramassageCyclisteVelo.getVelo().chargeMaxAtteint() && !cheminPossibleLePlusCourt.getArrets().get(i).getRamasse()) {
                        itineraireArret.setOrdreRamassage(getDernierOrdreRamassage(itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets())+1);

                        arretsRamasse.add(cheminPossibleLePlusCourt.getArrets().get(i));
                        // On supprime l'arrêt du chemin
                        ramassageCyclisteVelo.getVelo().setCharge(ramassageCyclisteVelo.getVelo().getCharge() + 50);
                        // On supprime l'arrêt pour tout les chemins dont il est la dernier arrêt
                        removeArrets(allCheminsPossibles, cheminPossibleLePlusCourt.getArrets().get(i));
                    }
                    itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets().add(itineraireArret);
                }
                ramassageCyclisteVelo.getVelo().setAutonomie(50.0);
            }

            // Si tous les arrêts restant du chemins sont ramassés, inutile d'y repasser
            if (cheminPossibleLePlusCourt.getArrets().isEmpty() || arretsRamasse.containsAll(cheminPossibleLePlusCourt.getArrets())) {
                allCheminsPossibles.remove(cheminPossibleLePlusCourt);
            }
            // Si la charge max n'est pas atteinte on recommence sur un autre chemin
            arrets.remove(arrets.size()-1);

//            itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets().remove(itineraireMap.get(ramassageCyclisteVelo).getItineraireArrets().size()-1);
            if (!ramassageCyclisteVelo.getVelo().chargeMaxAtteint()) {
                // Il faut retirer le point de départ sinon il apparaît deux fois aussi
                // On retire le chemin que l'on vient de parcourir de la liste
                List<Arret> arretsRecursif = ramasseCharge(ramassageCyclisteVelo, allCheminsPossibles, arretsRamasse, itineraireMap);
                if (arretsRecursif != null) {
                    arrets.addAll(arretsRecursif);
                }
            } else {
                // On vide la charge
                ramassageCyclisteVelo.getVelo().setCharge(0);
                ramassageCyclisteVelo.getVelo().setAutonomie(50.0);
            }
            return arrets;
        }
        return null;
    }

    public static int getDernierOrdrePassage(List<ItineraireArret> itineraireArrets) {
        if (itineraireArrets.isEmpty()) {
            return 0;
        } else {
            int max = itineraireArrets.get(0).getOrdrePassage();
            for (ItineraireArret itineraireArret : itineraireArrets) {
                if (itineraireArret.getOrdrePassage() > max) {
                    max = itineraireArret.getOrdrePassage();
                }
            }
            return max;
        }
    }
    public static int getDernierOrdreRamassage(List<ItineraireArret> itineraireArrets) {
        if (itineraireArrets.isEmpty()) {
            return 0;
        } else {
            int max = itineraireArrets.get(0).getOrdreRamassage() != null ? itineraireArrets.get(0).getOrdreRamassage() : 0;
            for (ItineraireArret itineraireArret : itineraireArrets) {
                if (itineraireArret.getOrdreRamassage() != null && itineraireArret.getOrdreRamassage() > max) {
                    max = itineraireArret.getOrdreRamassage();
                }
            }
            return max;
        }
    }

    private static void removeArrets(List<CheminPossibleDto> cheminPossibleDtos, Arret arrets) {
        List<CheminPossibleDto> toRemove = new ArrayList<>();
        for (CheminPossibleDto cheminPossibleDto : cheminPossibleDtos) {
            if (arrets.equals(cheminPossibleDto.dernierArret())) {
                cheminPossibleDto.removeArret();
                if (cheminPossibleDto.getArrets().isEmpty()) {
                    toRemove.add(cheminPossibleDto);
                }
            }
        }
        cheminPossibleDtos.removeAll(toRemove);
    }

    /**
     * Retourne une liste des chemins les plus courts, chaque chemin mène à un arrêt (terminus) différents
     * @param cheminPossibleDtos la liste dont on filtre les chemins
     * @return la liste des chemins
     */
    private static List<CheminPossibleDto> cheminPlusCourtParArret(List<CheminPossibleDto> cheminPossibleDtos) {
        Map<Arret, CheminPossibleDto> arretCheminPossibleDtoMap = new HashMap<>();
        for (CheminPossibleDto cheminPossibleDto : cheminPossibleDtos) {
            if (!arretCheminPossibleDtoMap.containsKey(cheminPossibleDto.dernierArret()) ||
                    arretCheminPossibleDtoMap.get(cheminPossibleDto.dernierArret()).calculDistance() > cheminPossibleDto.calculDistance()) {
                arretCheminPossibleDtoMap.put(cheminPossibleDto.dernierArret(), cheminPossibleDto);
            }
        }
//        cheminPossibleDtos.removeIf(cheminPossibleDto1 -> !arretCheminPossibleDtoMap.containsValue(cheminPossibleDto1));
        return new ArrayList<>(arretCheminPossibleDtoMap.values());
    }

    /**
     * Retourne le chemin le plus court de la liste
     * @param cheminPossibleDtos la liste à filtrer
     * @return le chemin le plus court
     */
    private static CheminPossibleDto chercheCheminPlusCourt(List<CheminPossibleDto> cheminPossibleDtos) {
        CheminPossibleDto cheminPossibleLePlusCourt = null;
        for (CheminPossibleDto cheminPossibleDto : cheminPossibleDtos) {
            if (cheminPossibleLePlusCourt == null || cheminPossibleLePlusCourt.calculDistance() > cheminPossibleDto.calculDistance()) {
                cheminPossibleLePlusCourt = cheminPossibleDto;
            }
        }
        return cheminPossibleLePlusCourt;
    }

}
