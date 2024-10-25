package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.AgeCategory;
import com.gymdaus.core.model.AgeCategoryModel;
import com.gymdaus.core.model.GymMoreRegistrationModel;
import org.springframework.stereotype.Component;

@Component
public class MapperAgeCategory {

    public AgeCategoryModel entity2Model(AgeCategory externObject) {
        AgeCategoryModel localObject = new AgeCategoryModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setPosition(externObject.getPosition());
            localObject.setStartAge(externObject.getStartAge());
            localObject.setEndAge(externObject.getEndAge());
            if (externObject.getGymMoreRegistrationId() != 0) {
                GymMoreRegistrationModel gymMoreRegistrationModel = new GymMoreRegistrationModel();
                gymMoreRegistrationModel.setId(externObject.getGymMoreRegistrationId());
                localObject.setGymMoreRegistrationModel(gymMoreRegistrationModel);
            }
        }
        return localObject;
    }

    public AgeCategory model2Entity(AgeCategoryModel externObject) {
        AgeCategory localObject = new AgeCategory();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setPosition(externObject.getPosition());
            localObject.setStartAge(externObject.getStartAge());
            localObject.setEndAge(externObject.getEndAge());
            if (externObject.getGymMoreRegistrationModel() != null) {
                localObject.setGymMoreRegistrationId(externObject.getGymMoreRegistrationModel().getId());
            } else {
                localObject.setGymMoreRegistrationId(0L);
            }
        }
        return localObject;
    }
}
