package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymCategory;
import com.gymdaus.core.model.*;
import org.springframework.stereotype.Component;

@Component
public class MapperGymCategory {

    public GymCategoryModel entity2Model(GymCategory externObject) {
        GymCategoryModel localObject = new GymCategoryModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setStartAge(externObject.getStartAge());
            localObject.setEndAge(externObject.getEndAge());
            localObject.setPosition(externObject.getPosition());
            if (externObject.getGymMoreRegistrationId() != 0) {
                GymMoreRegistrationModel gymMoreRegistrationModel = new GymMoreRegistrationModel();
                gymMoreRegistrationModel.setId(externObject.getGymMoreRegistrationId());
                localObject.setGymMoreRegistrationModel(gymMoreRegistrationModel);
            }
            if (externObject.getAgeCategoryId() != 0) {
                AgeCategoryModel ageCategoryModel = new AgeCategoryModel();
                ageCategoryModel.setId(externObject.getAgeCategoryId());
                localObject.setAgeCategoryModel(ageCategoryModel);
            }
            if (externObject.getStartBeltId() != 0) {
                GymMoreRegistrationBeltModel gymMoreRegistrationBeltModel = new GymMoreRegistrationBeltModel();
                gymMoreRegistrationBeltModel.setId(externObject.getStartBeltId());
                localObject.setStartBeltModel(gymMoreRegistrationBeltModel);
            }
            if (externObject.getEndBeltId() != 0) {
                GymMoreRegistrationBeltModel gymMoreRegistrationBeltModel = new GymMoreRegistrationBeltModel();
                gymMoreRegistrationBeltModel.setId(externObject.getEndBeltId());
                localObject.setEndBeltModel(gymMoreRegistrationBeltModel);
            }
            if (externObject.getPoomsaeId() != 0) {
                GymPoomsaeModel gymPoomsaeModel = new GymPoomsaeModel();
                gymPoomsaeModel.setId(externObject.getPoomsaeId());
                localObject.setGymPoomsaeModel(gymPoomsaeModel);
            }
        }
        return localObject;
    }

    public GymCategory model2Entity(GymCategoryModel externObject) {
        GymCategory localObject = new GymCategory();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setStartAge(externObject.getStartAge());
            localObject.setEndAge(externObject.getEndAge());
            localObject.setPosition(externObject.getPosition());
            if (externObject.getGymMoreRegistrationModel() != null) {
                localObject.setGymMoreRegistrationId(externObject.getGymMoreRegistrationModel().getId());
            } else {
                localObject.setGymMoreRegistrationId(0L);
            }
            if (externObject.getAgeCategoryModel() != null) {
                localObject.setAgeCategoryId(externObject.getAgeCategoryModel().getId());
            } else {
                localObject.setAgeCategoryId(0L);
            }
            if (externObject.getStartBeltModel() != null) {
                localObject.setStartBeltId(externObject.getStartBeltModel().getId());
            } else {
                localObject.setStartBeltId(0L);
            }
            if (externObject.getEndBeltModel() != null) {
                localObject.setEndBeltId(externObject.getEndBeltModel().getId());
            } else {
                localObject.setEndBeltId(0L);
            }
            if (externObject.getGymPoomsaeModel() != null) {
                localObject.setPoomsaeId(externObject.getGymPoomsaeModel().getId());
            } else {
                localObject.setPoomsaeId(0L);
            }
        }
        return localObject;
    }
}
