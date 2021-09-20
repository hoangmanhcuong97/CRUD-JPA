package com.listcrush.service;

import com.listcrush.model.Crush;
import com.listcrush.repository.crush.ICrushRepo;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

public class CrushService implements ICrushService{
    @Autowired
    private ICrushRepo iCrushRepo;
    @Override
    public List<Crush> showAll() {
        return iCrushRepo.showAll() ;
    }

    @Override
    public void save(Crush crush) {
        iCrushRepo.save(crush);
    }

    @Override
    public void delete(int id) {
        iCrushRepo.delete(id);
    }

    @Override
    public Crush findById(int id) {
        return iCrushRepo.findById(id);
    }
}
