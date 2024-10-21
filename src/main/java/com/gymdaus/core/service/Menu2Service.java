package com.gymdaus.core.service;



import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.model.Menu2Model;

import java.util.List;

public interface Menu2Service {

    List<Menu2Model> findAll(Long idMenu1);
    Menu2Model findById(Long id);
    void add(Menu2Model menu2Model);
    void update(Menu2Model menu2Model);
    void delete(Long id) throws RemoveException;
    void dragOfPosition(Long idMenu1, int initialPosition, int finalPosition);
    int findMaxPosition(Long idMenu1);
    Menu2Model findByUrl(String url);
}
