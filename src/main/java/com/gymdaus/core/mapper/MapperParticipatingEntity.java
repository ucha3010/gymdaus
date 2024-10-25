package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.ParticipatingEntity;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.ParticipatingEntityModel;
import org.springframework.stereotype.Component;

@Component
public class MapperParticipatingEntity {

    public ParticipatingEntityModel entity2Model(ParticipatingEntity externObject) {
        ParticipatingEntityModel localObject = new ParticipatingEntityModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setNotes(externObject.getNotes());
            localObject.setPosition(externObject.getPosition());
            if (externObject.getGymId() != 0) {
                GymModel gymModel = new GymModel();
                gymModel.setId(externObject.getGymId());
                localObject.setGymModel(gymModel);
            }
        }
        return localObject;
    }

    public ParticipatingEntity model2Entity(ParticipatingEntityModel externObject) {
        ParticipatingEntity localObject = new ParticipatingEntity();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setNotes(externObject.getNotes());
            localObject.setPosition(externObject.getPosition());
            if (externObject.getGymModel() != null) {
                localObject.setGymId(externObject.getGymModel().getId());
            } else {
                localObject.setGymId(0L);
            }
        }
        return localObject;
    }
}
