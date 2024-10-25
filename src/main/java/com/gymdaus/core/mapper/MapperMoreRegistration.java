package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.MoreRegistration;
import com.gymdaus.core.model.MoreRegistrationModel;
import org.springframework.stereotype.Component;

@Component
public class MapperMoreRegistration {

    public MoreRegistrationModel entity2Model(MoreRegistration externObject) {
        MoreRegistrationModel localObject = new MoreRegistrationModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setEnabled(externObject.isEnabled());
        }
        return localObject;
    }

    public MoreRegistration model2Entity(MoreRegistrationModel externObject) {
        MoreRegistration localObject = new MoreRegistration();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setEnabled(externObject.isEnabled());
        }
        return localObject;
    }
}
