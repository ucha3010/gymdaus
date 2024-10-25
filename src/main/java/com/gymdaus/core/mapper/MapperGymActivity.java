package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymActivity;
import com.gymdaus.core.model.ActivityModel;
import com.gymdaus.core.model.GymActivityModel;
import com.gymdaus.core.model.GymModel;
import org.springframework.stereotype.Component;

@Component
public class MapperGymActivity {

    public GymActivityModel entity2Model(GymActivity externObject) {
        GymActivityModel localObject = new GymActivityModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            if (externObject.getActivityId() != 0) {
                ActivityModel activityModel = new ActivityModel();
                activityModel.setId(externObject.getActivityId());
                localObject.setActivityModel(activityModel);
            }
            if (externObject.getGymId() != 0) {
                GymModel gymModel = new GymModel();
                gymModel.setId(externObject.getGymId());
                localObject.setGymModel(gymModel);
            }
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setRegistrationUser(externObject.getRegistrationUser());
        }
        return localObject;
    }

    public GymActivity model2Entity(GymActivityModel externObject) {
        GymActivity localObject = new GymActivity();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            if (externObject.getActivityModel() != null) {
                localObject.setActivityId(externObject.getActivityModel().getId());
            } else {
                localObject.setActivityId(0L);
            }
            if (externObject.getGymModel() != null) {
                localObject.setGymId(externObject.getGymModel().getId());
            } else {
                localObject.setGymId(0L);
            }
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setRegistrationUser(externObject.getRegistrationUser());
        }
        return localObject;
    }
}
