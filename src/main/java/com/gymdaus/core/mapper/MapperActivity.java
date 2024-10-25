package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.Activity;
import com.gymdaus.core.model.ActivityModel;
import org.springframework.stereotype.Component;

@Component
public class MapperActivity {

    public ActivityModel entity2Model(Activity externObject) {
        ActivityModel localObject = new ActivityModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setEnabled(externObject.isEnabled());
        }
        return localObject;
    }

    public Activity model2Entity(ActivityModel externObject) {
        Activity localObject = new Activity();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setEnabled(externObject.isEnabled());
        }
        return localObject;
    }
}
