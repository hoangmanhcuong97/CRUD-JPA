package com.listcrush.service;

import com.listcrush.model.Crush;

import java.util.List;

public interface ICrushService {
    List<Crush> showAll();
    void save(Crush crush);
    void delete(int id);
    Crush findById(int id);
}
