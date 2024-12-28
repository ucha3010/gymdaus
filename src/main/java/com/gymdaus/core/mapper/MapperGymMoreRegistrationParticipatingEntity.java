package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymMoreRegistrationParticipatingEntity;
import com.gymdaus.core.model.GymMoreRegistrationModel;
import com.gymdaus.core.model.GymMoreRegistrationParticipatingEntityModel;
import com.gymdaus.core.model.ParticipatingEntityModel;
import org.springframework.stereotype.Component;

@Component
public class MapperGymMoreRegistrationParticipatingEntity {

    public GymMoreRegistrationParticipatingEntityModel entity2Model(GymMoreRegistrationParticipatingEntity externObject) {
        GymMoreRegistrationParticipatingEntityModel localObject = new GymMoreRegistrationParticipatingEntityModel();
        if (externObject != null) {
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setRegistrationUser(externObject.getRegistrationUser());
            localObject.setPosition(externObject.getPosition());
            if (externObject.getGymMoreRegistrationId() != 0) {
                GymMoreRegistrationModel gymMoreRegistrationModel = new GymMoreRegistrationModel();
                gymMoreRegistrationModel.setId(externObject.getGymMoreRegistrationId());
                localObject.setGymMoreRegistrationModel(gymMoreRegistrationModel);
            }
            if (externObject.getParticipatingEntityId() != 0) {
                ParticipatingEntityModel participatingEntityModel = new ParticipatingEntityModel();
                participatingEntityModel.setId(externObject.getParticipatingEntityId());
                localObject.setParticipatingEntityModel(participatingEntityModel);
            }
        }
        return localObject;
    }

    public GymMoreRegistrationParticipatingEntity model2Entity(GymMoreRegistrationParticipatingEntityModel externObject) {
        GymMoreRegistrationParticipatingEntity localObject = new GymMoreRegistrationParticipatingEntity();
        if (externObject != null) {
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setRegistrationUser(externObject.getRegistrationUser());
            localObject.setPosition(externObject.getPosition());
            if (externObject.getGymMoreRegistrationModel() != null) {
                localObject.setGymMoreRegistrationId(externObject.getGymMoreRegistrationModel().getId());
            } else {
                localObject.setGymMoreRegistrationId(0L);
            }
            if (externObject.getParticipatingEntityModel() != null) {
                localObject.setParticipatingEntityId(externObject.getParticipatingEntityModel().getId());
            } else {
                localObject.setParticipatingEntityId(0L);
            }
        }
        return localObject;
    }
}
