package fr.limayrac.poubelle.controller;

import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.service.IArretService;
import fr.limayrac.poubelle.model.ArretRue;
import fr.limayrac.poubelle.service.IArretRueService;
import fr.limayrac.poubelle.model.Rue;
import fr.limayrac.poubelle.service.IRueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArretControllerWS {
    @Autowired
    private IArretService arretService;
    @Autowired
    private IArretRueService arretRueService;
    @Autowired
    private IRueService rueService;

    @GetMapping(value = "/arrets", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Arret> getArrets() {
        return arretService.findAll();
    }

    @GetMapping(value = "/arret-rue", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArretRue> getAllArretRues() {
        return arretRueService.findAll();
    }

    @GetMapping(value = "/rue", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Rue> getAllRues() {
        return rueService.findAll();
    }

}
