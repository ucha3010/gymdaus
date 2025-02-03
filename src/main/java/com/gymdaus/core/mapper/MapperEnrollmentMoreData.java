package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.EnrollmentMoreData;
import com.gymdaus.core.model.EnrollmentMoreDataModel;
import org.springframework.stereotype.Component;

@Component
public class MapperEnrollmentMoreData {

    public EnrollmentMoreDataModel entity2Model(EnrollmentMoreData externObject) {
        EnrollmentMoreDataModel localObject = new EnrollmentMoreDataModel();
        if (externObject != null) {
            localObject.setEnrollmentId(externObject.getEnrollmentId());
            localObject.setCategory(externObject.getCategory());
            localObject.setBelt(externObject.getBelt());
            localObject.setPoomsae(externObject.getPoomsae());
            localObject.setNotes(externObject.getNotes());
        }
        return localObject;
    }

    public EnrollmentMoreData model2Entity(EnrollmentMoreDataModel externObject) {
        EnrollmentMoreData localObject = new EnrollmentMoreData();
        if (externObject != null) {
            localObject.setEnrollmentId(externObject.getEnrollmentId());
            localObject.setCategory(externObject.getCategory());
            localObject.setBelt(externObject.getBelt());
            localObject.setPoomsae(externObject.getPoomsae());
            localObject.setNotes(externObject.getNotes());
        }
        return localObject;
    }
}
