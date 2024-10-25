package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymMoreRegistrationDate;
import com.gymdaus.core.model.GymMoreRegistrationDateModel;
import com.gymdaus.core.model.GymMoreRegistrationModel;
import org.springframework.stereotype.Component;

@Component
public class MapperGymMoreRegistrationDate {

    public GymMoreRegistrationDateModel entity2Model(GymMoreRegistrationDate externObject) {
        GymMoreRegistrationDateModel localObject = new GymMoreRegistrationDateModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setStartDateTime(externObject.getStartDateTime());
            localObject.setEndDateTime(externObject.getEndDateTime());
            if (externObject.getGymMoreRegistrationId() != 0) {
                GymMoreRegistrationModel gymMoreRegistrationModel = new GymMoreRegistrationModel();
                gymMoreRegistrationModel.setId(externObject.getGymMoreRegistrationId());
                localObject.setGymMoreRegistrationModel(gymMoreRegistrationModel);
            }
        }
        return localObject;
    }

    public GymMoreRegistrationDate model2Entity(GymMoreRegistrationDateModel externObject) {
        GymMoreRegistrationDate localObject = new GymMoreRegistrationDate();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setStartDateTime(externObject.getStartDateTime());
            localObject.setEndDateTime(externObject.getEndDateTime());
            if (externObject.getGymMoreRegistrationModel() != null) {
                localObject.setGymMoreRegistrationId(externObject.getGymMoreRegistrationModel().getId());
            } else {
                localObject.setGymMoreRegistrationId(0L);
            }
        }
        return localObject;
    }
}
