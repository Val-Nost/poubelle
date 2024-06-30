package fr.limayrac.poubelle;

import fr.limayrac.poubelle.dto.CheminPossibleDto;
import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.model.ArretVoisin;
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
    public void chercheChemin() {
        List<Arret> terminus = arretService.findFeuille();
        Map<Arret, List<CheminPossibleDto>> cheminsPossiblesParTerminus = new HashMap<>();
        List<CheminPossibleDto> cheminPossibleDtosSuivant = new ArrayList<>();
        CheminPossibleDto cheminPossibleDto = new CheminPossibleDto();
        cheminPossibleDto.addArret(arretDepart);
        cheminPossibleDtosSuivant.add(cheminPossibleDto);
        chercheCheminPrecedent(arretDepart, 0, cheminPossibleDtosSuivant);

        // On trie les chemins possibles par terminus
        Map<Arret, List<CheminPossibleDto>> mapCheminPossibleParArret = new HashMap<>();
        CheminPossibleDto cheminPossibleDtoPlusCourt = null;
        Map<Arret, CheminPossibleDto> cheminPlusCourtParArret = new HashMap<>();
        for (CheminPossibleDto cheminPossibleDto1 : cheminPossibleDtosSuivant) {
            if (!cheminPlusCourtParArret.containsKey(cheminPossibleDto1.dernierArret())) {
                cheminPlusCourtParArret.put(cheminPossibleDto1.dernierArret(), cheminPossibleDto1);
            }
            if (cheminPossibleDto1.calculDistance() < cheminPlusCourtParArret.get(cheminPossibleDto1.dernierArret()).calculDistance()) {
                cheminPlusCourtParArret.put(cheminPossibleDto1.dernierArret(), cheminPossibleDto1);
            }


            if (cheminPossibleDtoPlusCourt == null || cheminPossibleDto1.calculDistance() < cheminPossibleDtoPlusCourt.calculDistance()) {
                cheminPossibleDtoPlusCourt = cheminPossibleDto1;
            }

            if (!mapCheminPossibleParArret.containsKey(cheminPossibleDto1.dernierArret())) {
                mapCheminPossibleParArret.put(cheminPossibleDto1.dernierArret(), new ArrayList<>());
            }
            mapCheminPossibleParArret.get(cheminPossibleDto1.dernierArret()).add(cheminPossibleDto1);

//            StringBuilder message = new StringBuilder();
//            for (Arret arret : cheminPossibleDto1.getArrets()) {
//                message.append(arret.getLibelle()).append(" ");
//            }
//            System.out.print(message + "\n");
        }
        for (RamassageCyclisteVelo ramassageCyclisteVelo : ramassage.getRamassageCyclisteVelos()) {
            // TODO Chercher chemin adéquat

        }
    }

    public CheminPossibleDto chercheCheminPlusCourtParArret(Map<Arret, CheminPossibleDto> arretCheminPossibleDtoMap) {
        return null;
    }


    public void chercheCheminPrecedent(Arret arret, Integer numeroChemin, List<CheminPossibleDto> cheminPossibleDtos) {
        if (!arret.getArretVoisinsPrecedent().isEmpty()) {
            if (arret.getArretVoisinsPrecedent().size() == 1 || arret.arretVoisinsPrecedentIdentique()) {
                // S'il s'agit d'un arrêt simple, on l'ajoute juste au chemin possible
                cheminPossibleDtos.get(numeroChemin).addArret(arret.getArretVoisinsPrecedent().get(0).getArret());
                chercheCheminPrecedent(arret.getArretVoisinsPrecedent().get(0).getArret(), numeroChemin, cheminPossibleDtos);
            } else {
                for (int i = 0; i < arret.getArretVoisinsPrecedent().size()-1; i++) {
                    cheminPossibleDtos.add(new CheminPossibleDto(cheminPossibleDtos.get(numeroChemin)));
                }
                for (ArretVoisin arretVoisin : arret.getArretVoisinsPrecedent()) {
                    cheminPossibleDtos.get(numeroChemin).addArret(arretVoisin.getArret());
                    chercheCheminPrecedent(arretVoisin.getArret(), numeroChemin, cheminPossibleDtos);
                    // On cherche le chemin duppliqué qui était
                    for (CheminPossibleDto cheminPossibleDto : cheminPossibleDtos) {
                        if (cheminPossibleDto.dernierArret().equals(arretVoisin.getArretSuivant())) {
                            numeroChemin = cheminPossibleDtos.indexOf(cheminPossibleDto);
                        }
                    }
                }
            }
        }
    }


}
