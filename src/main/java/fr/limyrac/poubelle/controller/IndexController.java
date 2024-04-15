package fr.limyrac.poubelle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/accueil")
    public String accueil() {
        return "accueil";
    }
}
