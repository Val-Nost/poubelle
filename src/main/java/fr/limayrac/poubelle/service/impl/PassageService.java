package fr.limayrac.poubelle.service.impl;

import fr.limayrac.poubelle.dao.IPassageDao;
import fr.limayrac.poubelle.model.ramassage.Passage;
import fr.limayrac.poubelle.service.IPassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassageService implements IPassageService {
    @Autowired
    private IPassageDao ramassageArretDao;
    @Override
    public List<Passage> saveAll(List<Passage> passages) {
        return (List<Passage>) ramassageArretDao.saveAll(passages);
    }
}
