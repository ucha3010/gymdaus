package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymMoreRegistrationGymCategory;
import com.gymdaus.core.model.GymCategoryModel;
import com.gymdaus.core.model.GymMoreRegistrationGymCategoryModel;
import com.gymdaus.core.model.GymMoreRegistrationModel;
import org.springframework.stereotype.Component;

@Component
public class MapperGymMoreRegistrationGymCategory {

    public GymMoreRegistrationGymCategoryModel entity2Model(GymMoreRegistrationGymCategory externObject) {
        GymMoreRegistrationGymCategoryModel localObject = null;
        if (externObject != null) {
            localObject = new GymMoreRegistrationGymCategoryModel();
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setRegistrationUser(externObject.getRegistrationUser());
            GymMoreRegistrationModel gymMoreRegistrationModel = new GymMoreRegistrationModel();
            gymMoreRegistrationModel.setId(externObject.getGymMoreRegistrationId());
            localObject.setGymMoreRegistrationModel(gymMoreRegistrationModel);
            GymCategoryModel gymCategoryModel = new GymCategoryModel();
            gymCategoryModel.setId(externObject.getGymCategoryId());
            localObject.setGymCategoryModel(gymCategoryModel);
        }
        return localObject;
    }

    public GymMoreRegistrationGymCategory model2Entity(GymMoreRegistrationGymCategoryModel externObject) {
        GymMoreRegistrationGymCategory localObject = new GymMoreRegistrationGymCategory();
        if (externObject != null) {
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setRegistrationUser(externObject.getRegistrationUser());
            if (externObject.getGymMoreRegistrationModel() != null) {
                localObject.setGymMoreRegistrationId(externObject.getGymMoreRegistrationModel().getId());
            } else {
                localObject.setGymMoreRegistrationId(0L);
            }
            if (externObject.getGymCategoryModel() != null) {
                localObject.setGymCategoryId(externObject.getGymCategoryModel().getId());
            } else {
                localObject.setGymCategoryId(0L);
            }
        }
        return localObject;
    }
}
