package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymMoreRegistrationGymBelt;
import com.gymdaus.core.model.GymMoreRegistrationGymBeltModel;
import com.gymdaus.core.model.GymMoreRegistrationModel;
import com.gymdaus.core.service.GymBeltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperGymMoreRegistrationBelt {

    @Autowired
    private GymBeltService gymBeltService;

    public GymMoreRegistrationGymBeltModel entity2Model(GymMoreRegistrationGymBelt externObject) {
        GymMoreRegistrationGymBeltModel localObject = new GymMoreRegistrationGymBeltModel();
        if (externObject != null) {
            localObject.setPosition(externObject.getPosition());
            if (externObject.getGymMoreRegistrationId() != 0) {
                GymMoreRegistrationModel gymMoreRegistrationModel = new GymMoreRegistrationModel();
                gymMoreRegistrationModel.setId(externObject.getGymMoreRegistrationId());
                localObject.setGymMoreRegistrationModel(gymMoreRegistrationModel);
            }
            if (externObject.getGymBeltId() != null) {
                localObject.setGymBeltModel(gymBeltService.findById(externObject.getGymBeltId()));
            }
        }
        return localObject;
    }

    public GymMoreRegistrationGymBelt model2Entity(GymMoreRegistrationGymBeltModel externObject) {
        GymMoreRegistrationGymBelt localObject = new GymMoreRegistrationGymBelt();
        if (externObject != null) {
            localObject.setPosition(externObject.getPosition());
            if (externObject.getGymMoreRegistrationModel() != null) {
                localObject.setGymMoreRegistrationId(externObject.getGymMoreRegistrationModel().getId());
            } else {
                localObject.setGymMoreRegistrationId(0L);
            }
            if (externObject.getGymBeltModel() != null) {
                localObject.setGymBeltId(externObject.getGymBeltModel().getId());
            } else {
                localObject.setGymBeltId(0L);
            }
        }
        return localObject;
    }
}
