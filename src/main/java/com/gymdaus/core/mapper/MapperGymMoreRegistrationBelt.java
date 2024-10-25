package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymMoreRegistrationBelt;
import com.gymdaus.core.model.GymBeltModel;
import com.gymdaus.core.model.GymMoreRegistrationBeltModel;
import com.gymdaus.core.model.GymMoreRegistrationModel;
import org.springframework.stereotype.Component;

@Component
public class MapperGymMoreRegistrationBelt {

    public GymMoreRegistrationBeltModel entity2Model(GymMoreRegistrationBelt externObject) {
        GymMoreRegistrationBeltModel localObject = new GymMoreRegistrationBeltModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setPosition(externObject.getPosition());
            if (externObject.getGymMoreRegistrationId() != 0) {
                GymMoreRegistrationModel gymMoreRegistrationModel = new GymMoreRegistrationModel();
                gymMoreRegistrationModel.setId(externObject.getGymMoreRegistrationId());
                localObject.setGymMoreRegistrationModel(gymMoreRegistrationModel);
            }
            if (externObject.getBeltId() != null) {
                GymBeltModel gymBeltModel = new GymBeltModel();
                gymBeltModel.setId(externObject.getBeltId());
                localObject.setGymBeltModel(gymBeltModel);
            }
        }
        return localObject;
    }

    public GymMoreRegistrationBelt model2Entity(GymMoreRegistrationBeltModel externObject) {
        GymMoreRegistrationBelt localObject = new GymMoreRegistrationBelt();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setPosition(externObject.getPosition());
            if (externObject.getGymMoreRegistrationModel() != null) {
                localObject.setGymMoreRegistrationId(externObject.getGymMoreRegistrationModel().getId());
            } else {
                localObject.setGymMoreRegistrationId(0L);
            }
            if (externObject.getGymBeltModel() != null) {
                localObject.setBeltId(externObject.getGymBeltModel().getId());
            } else {
                localObject.setBeltId(0L);
            }
        }
        return localObject;
    }
}
