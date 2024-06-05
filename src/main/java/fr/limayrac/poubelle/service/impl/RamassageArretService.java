package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IRamassageArretDao;
import fr.limayrac.poubelle.model.Arret;
import fr.limayrac.poubelle.model.ramassage.RamassageArret;
import fr.limayrac.poubelle.service.IArretService;
import fr.limayrac.poubelle.service.IRamassageArretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RamassageArretService implements IRamassageArretService {
    @Autowired
    private IRamassageArretDao ramassageArretDao;
    @Override
    public List<RamassageArret> saveAll(List<RamassageArret> ramassageArrets) {
        return (List<RamassageArret>) ramassageArretDao.saveAll(ramassageArrets);
    }
}
