package com.gymdaus.core.service.impl;


import com.gymdaus.core.model.ActivityModel;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.Menu2Model;
import com.gymdaus.core.model.MoreRegistrationModel;
import com.gymdaus.core.service.ActivityService;
import com.gymdaus.core.service.GymService;
import com.gymdaus.core.service.Menu2Service;
import com.gymdaus.core.service.MoreRegistrationService;
import com.gymdaus.core.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class Menu2ServiceImpl implements Menu2Service {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private GymService gymService;
    @Autowired
    private MoreRegistrationService moreRegistrationService;

    @Override
    public List<Menu2Model> findAllGymList() {
        List<GymModel> gymModelList = gymService.findAllEnabled();
        List<Menu2Model> menu2ModelList = new ArrayList<>();
        Menu2Model menu2Model;
        for (GymModel gymModel : gymModelList) {
            menu2Model = new Menu2Model();
            menu2Model.setName(gymModel.getName());
            menu2Model.setAdvise(Constants.GYM_ADVICE);
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
        for (ActivityModel activityModel : activityModelList) {
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
        for (MoreRegistrationModel registrationModel : moreRegistrationModelList) {
            menu2Model = new Menu2Model();
            menu2Model.setName(registrationModel.getName());
            menu2Model.setUrl(registrationModel.getUrl());
            menu2ModelList.add(menu2Model);
        }
        return menu2ModelList;
    }
}
