package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymParameter;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.GymParameterModel;
import org.springframework.stereotype.Component;

@Component
public class MapperGymParameter {

    public GymParameterModel entity2Model(GymParameter externObject) {
        GymParameterModel localObject = new GymParameterModel();
        if (externObject != null) {
            localObject.setKeyData(externObject.getKeyData());
            localObject.setValue(externObject.getValue());
            localObject.setModificationDate(externObject.getModificationDate());
            localObject.setModificationUsername(externObject.getModificationUsername());
            if (externObject.getGymId() != 0) {
                GymModel gymModel = new GymModel();
                gymModel.setId(externObject.getGymId());
                localObject.setGymModel(gymModel);
            }
        }
        return localObject;
    }

    public GymParameter model2Entity(GymParameterModel externObject) {
        GymParameter localObject = new GymParameter();
        if (externObject != null) {
            localObject.setKeyData(externObject.getKeyData());
            localObject.setValue(externObject.getValue());
            localObject.setModificationDate(externObject.getModificationDate());
            localObject.setModificationUsername(externObject.getModificationUsername());
            if (externObject.getGymModel() != null) {
                localObject.setGymId(externObject.getGymModel().getId());
            } else {
                localObject.setGymId(0L);
            }
        }
        return localObject;
    }
}
