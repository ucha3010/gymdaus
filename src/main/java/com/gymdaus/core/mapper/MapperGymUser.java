package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymUser;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.GymUserModel;
import com.gymdaus.core.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class MapperGymUser {

    public GymUserModel entity2Model(GymUser externObject) {
        GymUserModel localObject = null;
        if (externObject != null) {
            localObject = new GymUserModel();
            localObject.setId(externObject.getId());
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setRegistrationUser(externObject.getRegistrationUser());
            UserModel userModel = new UserModel();
            userModel.setUsername(externObject.getUsername());
            localObject.setUserModel(userModel);
            localObject.setGymRole(externObject.getGymRole());
            if (externObject.getGymId() != 0) {
                GymModel gymModel = new GymModel();
                gymModel.setId(externObject.getGymId());
                localObject.setGymModel(gymModel);
            }
        }
        return localObject;
    }

    public GymUser model2Entity(GymUserModel externObject) {
        GymUser localObject = new GymUser();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setRegistrationUser(externObject.getRegistrationUser());
            localObject.setGymRole(externObject.getGymRole());
            if (externObject.getGymModel() != null) {
                localObject.setGymId(externObject.getGymModel().getId());
            } else {
                localObject.setGymId(0L);
            }
            if (externObject.getUserModel() != null) {
                localObject.setUsername(externObject.getUserModel().getUsername());
            }
        }
        return localObject;
    }
}
