package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymCategoryGymBelt;
import com.gymdaus.core.model.GymBeltModel;
import com.gymdaus.core.model.GymCategoryGymBeltModel;
import com.gymdaus.core.model.GymCategoryModel;
import org.springframework.stereotype.Component;

@Component
public class MapperGymCategoryGymBelt {

    public GymCategoryGymBeltModel entity2Model(GymCategoryGymBelt externObject) {
        GymCategoryGymBeltModel localObject = new GymCategoryGymBeltModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setRegistrationUser(externObject.getRegistrationUser());
            if (externObject.getGymCategoryId() != 0) {
                GymCategoryModel gymCategoryModel = new GymCategoryModel();
                gymCategoryModel.setId(externObject.getGymCategoryId());
                localObject.setGymCategoryModel(gymCategoryModel);
            }
            if (externObject.getGymBeltId() != 0) {
                GymBeltModel gymBeltModel = new GymBeltModel();
                gymBeltModel.setId(externObject.getGymBeltId());
                localObject.setGymBeltModel(gymBeltModel);
            }
        }
        return localObject;
    }

    public GymCategoryGymBelt model2Entity(GymCategoryGymBeltModel externObject) {
        GymCategoryGymBelt localObject = new GymCategoryGymBelt();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setRegistrationUser(externObject.getRegistrationUser());
            if (externObject.getGymCategoryModel() != null) {
                localObject.setGymCategoryId(externObject.getGymCategoryModel().getId());
            } else {
                localObject.setGymCategoryId(0L);
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
