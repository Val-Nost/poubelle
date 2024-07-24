package fr.limayrac.poubelle.utils;

import fr.limayrac.poubelle.dto.CheminPossibleDto;
import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.model.ArretAdjacent;
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

    public static List<Arret> ramasseCharge(RamassageCyclisteVelo ramassageCyclisteVelo, List<CheminPossibleDto> allCheminsPossibles, Set<Arret> arretsRamasse) {
        if (!allCheminsPossibles.isEmpty()) {
            // On trie la liste de tous les chemin pour récupérer la liste des chemins les plus court par arrêt
            List<CheminPossibleDto> cheminPlusCourtParArret = cheminPlusCourtParArret(allCheminsPossibles);

            // On récupère le chemin le plus court par arrêt
            // TODO à vérifier, il est sans doute possible de n'utiliser que cette fonction
            CheminPossibleDto cheminPossibleLePlusCourt = chercheCheminPlusCourt(cheminPlusCourtParArret);

            // Le velo a assez d'autonomie pour faire l'allez-retour
            if (ramassageCyclisteVelo.getVelo().autonomieRestante() >= cheminPossibleLePlusCourt.calculDistance()) {
                // TODO mettre le code en dessous ici
            }

            // Début code à déplacer

            // On calcule l'autonomie
            ramassageCyclisteVelo.getVelo().setAutonomie(ramassageCyclisteVelo.getVelo().getAutonomie() + cheminPossibleLePlusCourt.calculDistance());

            // La liste des arrêts parcourus
            List<Arret> arrets = new ArrayList<>(cheminPossibleLePlusCourt.getArrets());

            // On retire le dernier vu qu'il est ajouté en dessous
            arrets.remove(arrets.size()-1);
            for (int i = cheminPossibleLePlusCourt.getArrets().size()-1; i >= 0; i--) {
                arrets.add(cheminPossibleLePlusCourt.getArrets().get(i));
                // Si la charge max n'est pas atteinte on ramasse l'arrêt
                if (!ramassageCyclisteVelo.getVelo().chargeMaxAtteint()) {
                    arretsRamasse.add(cheminPossibleLePlusCourt.getArrets().get(i));
                    // On supprime l'arrêt du chemin
                    ramassageCyclisteVelo.getVelo().setCharge(ramassageCyclisteVelo.getVelo().getCharge() + 50);
                    // On supprime l'arrêt pour tout les chemins dont il est la dernier arrêt
                    removeArrets(allCheminsPossibles, cheminPossibleLePlusCourt.getArrets().get(i));
                }
            }
            // Fin code à déplacer

            // TODO c'est ici qu'il faut remettre l'autonomie à 0 en fonction de la réponse du prof
//            ramassageCyclisteVelo.getVelo().setAutonomie(0.0);

            // Si tous les arrêts restant du chemins sont ramassés, inutile d'y repasser
            if (cheminPossibleLePlusCourt.getArrets().isEmpty() || arretsRamasse.containsAll(cheminPossibleLePlusCourt.getArrets())) {
                allCheminsPossibles.remove(cheminPossibleLePlusCourt);
            }
            // Si la charge max n'est pas atteinte on recommence sur un autre chemin
            if (!ramassageCyclisteVelo.getVelo().chargeMaxAtteint()) {
                // Il faut retirer le point de départ sinon il apparaît deux fois aussi
                arrets.remove(arrets.size()-1);
                // On retire le chemin que l'on vient de parcourir de la liste
                List<Arret> arretsRecursif = ramasseCharge(ramassageCyclisteVelo, allCheminsPossibles, arretsRamasse);
                if (arretsRecursif != null) {
                    arrets.addAll(arretsRecursif);
                }
            } else {
//            cheminPlusCourtParArret.remove(cheminPossibleLePlusCourt);
                // On vide la charge
                ramassageCyclisteVelo.getVelo().setCharge(0);
                // TODO, demander si à chaque fois que l'on passe par l'arrêt de départ on change la batterie
            }
            return arrets;
        }
        return null;
    }

    private static void removeArrets(List<CheminPossibleDto> cheminPossibleDtos, Arret arrets) {
        for (CheminPossibleDto cheminPossibleDto : cheminPossibleDtos) {
            if (arrets.equals(cheminPossibleDto.dernierArret())) {
                cheminPossibleDto.removeArret();
            }
        }
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
