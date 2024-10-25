package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.EnrollmentAs;
import com.gymdaus.core.model.EnrollmentAsModel;
import org.springframework.stereotype.Component;

@Component
public class MapperEnrollmentAs {

    public EnrollmentAsModel entity2Model(EnrollmentAs externObject) {
        EnrollmentAsModel localObject = new EnrollmentAsModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setPosition(externObject.getPosition());
        }
        return localObject;
    }

    public EnrollmentAs model2Entity(EnrollmentAsModel externObject) {
        EnrollmentAs localObject = new EnrollmentAs();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setPosition(externObject.getPosition());
        }
        return localObject;
    }
}
