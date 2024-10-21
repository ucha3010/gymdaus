package com.gymdaus.core.service.impl;


import com.gymdaus.core.entity.Menu2;
import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.mapper.MapperMenu2;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.Menu2Model;
import com.gymdaus.core.repository.Menu2Repository;
import com.gymdaus.core.service.Menu2Service;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.LoggerMapper;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class Menu2ServiceImpl implements Menu2Service {

    @Autowired
    private Menu2Repository menu2Repository;
    @Autowired
    private MapperMenu2 mapperMenu2;

    @Override
    public List<Menu2Model> findAll(Long idMenu1) {
        List<Menu2Model> menu2ModelList = new ArrayList<>();
        for (Menu2 menu2: menu2Repository.findByMenu1IdOrderByPositionAsc(idMenu1)) {
            menu2ModelList.add(mapperMenu2.entity2Model(menu2));
        }
        return menu2ModelList;
    }

    @Override
    public Menu2Model findById(Long id) {
        try {
            Menu2Model menu2Model = mapperMenu2.entity2Model(menu2Repository.findById(id).orElse(null));
            /*List<GymModel> gymModelList = new ArrayList<>();
            for(GimnasioMenu2Model gimnasioMenu2: gimnasioMenu2Service.findByIdMenu2(id)) {
                gymModelList.add(gimnasioService.findById(gimnasioMenu2.getIdGimnasio()));
            }
            menu2Model.setGimnasioModelList(gymModelList);*/
            return menu2Model;
        } catch (EntityNotFoundException e) {
            return new Menu2Model();
        }
    }

    @Override
    public void add(Menu2Model menu2Model) {
        menu2Repository.save(mapperMenu2.model2Entity(menu2Model));
    }

    @Override
    public void update(Menu2Model menu2Model) {
        menu2Repository.save(mapperMenu2.model2Entity(menu2Model));
    }

    @Override
    public void delete(Long id) throws RemoveException {
        Menu2 menu2 = menu2Repository.findById(id).orElse(null);
        try {
            menu2Repository.deleteById(id);
            if (menu2 != null) {
                List<Menu2> menu2List = menu2Repository.findByMenu1IdOrderByPositionAsc(menu2.getMenu1Id());
                for (int i = 0; i < menu2List.size(); i++) {
                    if (menu2List.get(i).getPosition() != i) {
                        menu2List.get(i).setPosition(i);
                        menu2Repository.save(menu2List.get(i));
                    }
                }
            }
//            gimnasioMenu2Service.deleteByIdMenu2(id);
        } catch (IllegalArgumentException e){
            LoggerMapper.log(Level.ERROR, "delete", e.getMessage(), getClass());
            throw new RemoveException(Constants.ERROR_BORRAR_MENU, "Error al borrar el menú secundario");
        }
        LoggerMapper.methodOut(Level.INFO, "delete", menu2 != null ? menu2.getId() : 0, getClass());
    }

    @Override
    public void dragOfPosition(Long idMenu1, int initialPosition, int finalPosition) {
        Menu2 menu2 = menu2Repository.findByMenu1IdAndPosition(idMenu1, initialPosition);
        if (initialPosition > finalPosition) {
            for (int i = initialPosition - 1; i >= finalPosition; i--) {
                moveItem(idMenu1, i, true);
            }
        }
        if (initialPosition < finalPosition) {
            for (int i = initialPosition + 1; i <= finalPosition; i++) {
                moveItem(idMenu1, i, false);
            }
        }
        menu2.setPosition(finalPosition);
        menu2Repository.save(menu2);
    }

    @Override
    public int findMaxPosition(Long idMenu1) {
        Menu2 menu2 = menu2Repository.findTopByMenu1IdOrderByPositionDesc(idMenu1);
        if (menu2 != null) {
            return menu2.getPosition();
        } else {
            return -1;
        }
    }

    @Override
    public Menu2Model findByUrl(String url) {
        return mapperMenu2.entity2Model(menu2Repository.findByUrl(url));
    }

    private void moveItem(Long idMenu1, int position, boolean moveUp) {
        Menu2 menu2 = menu2Repository.findByMenu1IdAndPosition(idMenu1, position);
        menu2.setPosition(position + (moveUp ? 1 : -1));
        menu2Repository.save(menu2);
    }
}
