package fr.limayrac.poubelle.controller;

import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.model.Rue;
import fr.limayrac.poubelle.model.RueArret;
import fr.limayrac.poubelle.service.IArretService;
import fr.limayrac.poubelle.service.IRueArretService;
import fr.limayrac.poubelle.service.IRueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RueController {

    @Autowired
    private IRueService rueService;

    @Autowired
    private IArretService arretService;

    @Autowired
    private IRueArretService rueArretService;

    @GetMapping("/rues-arrets")
    public String getAllRuesArrets(Model model) {
        List<Rue> rues = rueService.findAll();
        List<Arret> arrets = arretService.findAll();
        List<RueArret> rueArrets = rueArretService.findAll();

        model.addAttribute("rues", rues);
        model.addAttribute("arrets", arrets);
        model.addAttribute("rueArrets", rueArrets);

        return "ramassageGlobal";
    }
}
