package com.gymdaus.core.service;



import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.model.Menu1Model;

import java.util.List;

public interface Menu1Service {

    List<Menu1Model> findAll();
    Menu1Model findById(Long id);
    void add(Menu1Model menu1Model);
    void update(Menu1Model menu1Model);
    void delete(Long id) throws RemoveException;
    void dragOfPosition(int initialPosition, int finalPosition);
    int findMaxPosition();
}
