package com.gymdaus.core.service.impl;


import com.gymdaus.core.entity.Menu2;
import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.mapper.MapperMenu2;
import com.gymdaus.core.model.ActivityModel;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.Menu2Model;
import com.gymdaus.core.model.MoreRegistrationModel;
import com.gymdaus.core.repository.Menu2Repository;
import com.gymdaus.core.service.ActivityService;
import com.gymdaus.core.service.GymService;
import com.gymdaus.core.service.Menu2Service;
import com.gymdaus.core.service.MoreRegistrationService;
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
    @Autowired
    private ActivityService activityService;
    @Autowired
    private GymService gymService;
    @Autowired
    private MoreRegistrationService moreRegistrationService;

    @Override
    public List<Menu2Model> findAll(Long idMenu1) {
        List<Menu2Model> menu2ModelList = new ArrayList<>();
        for (Menu2 menu2: menu2Repository.findByMenu1IdOrderByPositionAsc(idMenu1)) {
            menu2ModelList.add(mapperMenu2.entity2Model(menu2));
        }
        return menu2ModelList;
    }

    @Override
    public List<Menu2Model> findAllEnabled(Long idMenu1) {
        List<Menu2Model> menu2ModelList = new ArrayList<>();
        for (Menu2 menu2: menu2Repository.findByMenu1IdAndEnabledTrueOrderByPositionAsc(idMenu1)) {
            menu2ModelList.add(mapperMenu2.entity2Model(menu2));
        }
        return menu2ModelList;
    }

    @Override
    public List<Menu2Model> findAllGymList() {
        List<GymModel> gymModelList = gymService.findAllEnabled();
        List<Menu2Model> menu2ModelList = new ArrayList<>();
        Menu2Model menu2Model;
        for (GymModel gymModel: gymModelList) {
            menu2Model = new Menu2Model();
            menu2Model.setName(gymModel.getName());
            menu2Model.setAdvise(Constants.GYM_ADVISE);
            menu2Model.setUrl(Constants.GYM_DETAIL + gymModel.getId());
            menu2ModelList.add(menu2Model);
        }
        return menu2ModelList;
    }

    @Override
    public List<Menu2Model> findAllActivityList() {
        List<ActivityModel> activityModelList = activityService.findAllEnabled();
        List<Menu2Model> menu2ModelList = new ArrayList<>();
        Menu2Model menu2Model;
        for (ActivityModel activityModel: activityModelList) {
            menu2Model = new Menu2Model();
            menu2Model.setName(activityModel.getName());
            menu2Model.setUrl(activityModel.getUrl());
            menu2ModelList.add(menu2Model);
        }
        return menu2ModelList;
    }

    @Override
    public List<Menu2Model> findAllMoreRegistrationList() {
        List<MoreRegistrationModel> moreRegistrationModelList = moreRegistrationService.findAllEnabled();
        List<Menu2Model> menu2ModelList = new ArrayList<>();
        Menu2Model menu2Model;
        for (MoreRegistrationModel registrationModel: moreRegistrationModelList) {
            menu2Model = new Menu2Model();
            menu2Model.setName(registrationModel.getName());
            menu2Model.setUrl(registrationModel.getUrl());
            menu2ModelList.add(menu2Model);
        }
        return menu2ModelList;
    }

    @Override
    public Menu2Model findById(Long id) {
        try {
            return mapperMenu2.entity2Model(menu2Repository.findById(id).orElse(null));
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
        } catch (IllegalArgumentException e){
            LoggerMapper.log(Level.ERROR, "delete", e.getMessage(), getClass());
            throw new RemoveException(Constants.ERROR_BORRAR_MENU, "Error deleting submenu id " + id);
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
