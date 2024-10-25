package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymPoomsae;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.GymPoomsaeModel;
import org.springframework.stereotype.Component;

@Component
public class MapperGymPoomsae {

    public GymPoomsaeModel entity2Model(GymPoomsae externObject) {
        GymPoomsaeModel localObject = new GymPoomsaeModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setPosition(externObject.getPosition());
            if (externObject.getGymId() != 0) {
                GymModel gymModel = new GymModel();
                gymModel.setId(externObject.getGymId());
                localObject.setGymModel(gymModel);
            }
        }
        return localObject;
    }

    public GymPoomsae model2Entity(GymPoomsaeModel externObject) {
        GymPoomsae localObject = new GymPoomsae();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
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
