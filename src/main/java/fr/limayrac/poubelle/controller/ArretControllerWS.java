package fr.limayrac.poubelle.controller;

import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.service.IArretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArretControllerWS {
    @Autowired
    private IArretService arretService;

    @GetMapping(value = "/arrets", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Arret> getArrets() {
        return arretService.findAll();
    }
}
