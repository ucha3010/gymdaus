package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymActivity;
import com.gymdaus.core.model.GymActivityModel;
import com.gymdaus.core.model.GymAddressModel;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.service.ActivityService;
import com.gymdaus.core.service.GymAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperGymActivity {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private GymAddressService gymAddressService;

    public GymActivityModel entity2Model(GymActivity externObject) {
        GymActivityModel localObject = new GymActivityModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            if (externObject.getActivityId() != 0) {
                localObject.setActivityModel(activityService.findById(externObject.getActivityId()));
            }
            if (externObject.getGymId() != 0) {
                GymModel gymModel = new GymModel();
                gymModel.setId(externObject.getGymId());
                localObject.setGymModel(gymModel);
            }
            if (externObject.getGymAddressId() != 0) {
                GymAddressModel gymAddressModel = gymAddressService.findById(externObject.getGymAddressId());
                localObject.setGymAddressModel(gymAddressModel);
            }
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setRegistrationUser(externObject.getRegistrationUser());
            localObject.setEnabled(externObject.isEnabled());
        }
        return localObject;
    }

    public GymActivity model2Entity(GymActivityModel externObject) {
        GymActivity localObject = new GymActivity();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            if (externObject.getActivityModel() != null) {
                localObject.setActivityId(externObject.getActivityModel().getId());
            } else {
                localObject.setActivityId(0L);
            }
            if (externObject.getGymModel() != null) {
                localObject.setGymId(externObject.getGymModel().getId());
            } else {
                localObject.setGymId(0L);
            }
            if (externObject.getGymAddressModel() != null) {
                localObject.setGymAddressId(externObject.getGymAddressModel().getId());
            } else {
                localObject.setGymAddressId(0L);
            }
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setRegistrationUser(externObject.getRegistrationUser());
            localObject.setEnabled(externObject.isEnabled());
        }
        return localObject;
    }
}
