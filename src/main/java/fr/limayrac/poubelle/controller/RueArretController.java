package fr.limayrac.poubelle.controller;

import fr.limayrac.poubelle.service.IRueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RueArretController {
    @Autowired
    private IRueService rueService;
    @GetMapping("/ruesEtArrets")
    public String rueArret(Model model) {
        model.addAttribute("rues", rueService.findAllOrderById());
        return "ruesArrets";
    }
}
