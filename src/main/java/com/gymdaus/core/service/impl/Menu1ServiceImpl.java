package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.Menu1;
import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.mapper.MapperMenu1;
import com.gymdaus.core.model.Menu1Model;
import com.gymdaus.core.model.Menu2Model;
import com.gymdaus.core.repository.Menu1Repository;
import com.gymdaus.core.service.Menu1Service;
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
public class Menu1ServiceImpl implements Menu1Service {

    @Autowired
    private Menu1Repository menu1Repository;
    @Autowired
    private MapperMenu1 mapperMenu1;
    @Autowired
    private Menu2Service menu2Service;

    @Override
    public List<Menu1Model> findAll() {
        List<Menu1Model> menu1ModelList = new ArrayList<>();
        Menu1Model menu1Model;
        for (Menu1 menu1: menu1Repository.findAllByOrderByPositionAsc()) {
            menu1Model = mapperMenu1.entity2Model(menu1);
            menu1Model.setMenu2ModelList(menu2Service.findAll(menu1.getId()));
            menu1ModelList.add(menu1Model);
        }
        return menu1ModelList;
    }

    @Override
    public Menu1Model findById(Long id) {
        try {
            return mapperMenu1.entity2Model(menu1Repository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new Menu1Model();
        }
    }

    @Override
    public void add(Menu1Model menu1Model) {
        menu1Repository.save(mapperMenu1.model2Entity(menu1Model));
    }

    @Override
    public void update(Menu1Model menu1Model) {
        menu1Repository.save(mapperMenu1.model2Entity(menu1Model));
    }

    @Override
    public void delete(Long id) throws RemoveException {
        List<Menu2Model> menu2List = menu2Service.findAll(id);
        if (menu2List == null || menu2List.isEmpty()) {
            menu1Repository.deleteById(id);
            List<Menu1> menu1List = menu1Repository.findAllByOrderByPositionAsc();
            for (int i = 0; i < menu1List.size(); i++) {
                if (menu1List.get(i).getPosition() != i) {
                    menu1List.get(i).setPosition(i);
                    menu1Repository.save(menu1List.get(i));
                }
            }
        } else {
            LoggerMapper.log(Level.ERROR, "delete", "idMenu1: " + id + " tiene submenú asociado", getClass());
            throw new RemoveException(Constants.ERROR_BORRAR_MENU_CON_SUBMENU, "Error al borrar el menú principal");
        }
    }

    @Override
    public void dragOfPosition(int initialPosition, int finalPosition) {
        Menu1 menu1 = menu1Repository.findByPosition(initialPosition);
        if (initialPosition > finalPosition) {
            for (int i = initialPosition - 1; i >= finalPosition; i--) {
                moveItem(i, true);
            }
        }
        if (initialPosition < finalPosition) {
            for (int i = initialPosition + 1; i <= finalPosition; i++) {
                moveItem(i, false);
            }
        }
        menu1.setPosition(finalPosition);
        menu1Repository.save(menu1);
    }

    @Override
    public int findMaxPosition() {
        Menu1 menu1 = menu1Repository.findTopByOrderByPositionDesc();
        if (menu1 != null) {
            return menu1.getPosition();
        } else {
            return -1;
        }
    }

    private void moveItem(int position, boolean moveUp) {
        Menu1 menu1 = menu1Repository.findByPosition(position);
        menu1.setPosition(position + (moveUp ? 1 : -1));
        menu1Repository.save(menu1);
    }
}
