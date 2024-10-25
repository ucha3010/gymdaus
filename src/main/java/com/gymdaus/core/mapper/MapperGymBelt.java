package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymBelt;
import com.gymdaus.core.model.GymBeltModel;
import com.gymdaus.core.model.GymModel;
import org.springframework.stereotype.Component;

@Component
public class MapperGymBelt {

    public GymBeltModel entity2Model(GymBelt externObject) {
        GymBeltModel localObject = new GymBeltModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setColour(externObject.getColour());
            localObject.setPosition(externObject.getPosition());
            if (externObject.getGymId() != 0) {
                GymModel gymModel = new GymModel();
                gymModel.setId(externObject.getGymId());
                localObject.setGymModel(gymModel);
            }
        }
        return localObject;
    }

    public GymBelt model2Entity(GymBeltModel externObject) {
        GymBelt localObject = new GymBelt();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setColour(externObject.getColour());
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
