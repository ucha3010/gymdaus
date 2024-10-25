package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.Signature;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.SignatureModel;
import org.springframework.stereotype.Component;

@Component
public class MapperSignature {

    public SignatureModel entity2Model(Signature externObject) {
        SignatureModel localObject = new SignatureModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setOperationId(externObject.getOperationId());
            localObject.setOperationName(externObject.getOperationName());
            localObject.setAttempts(externObject.getAttempts());
            localObject.setSigned(externObject.isSigned());
            if (externObject.getGymId() != 0) {
                GymModel gymModel = new GymModel();
                gymModel.setId(externObject.getGymId());
                localObject.setGymModel(gymModel);
            }
        }
        return localObject;
    }

    public Signature model2Entity(SignatureModel externObject) {
        Signature localObject = new Signature();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setOperationId(externObject.getOperationId());
            localObject.setOperationName(externObject.getOperationName());
            localObject.setAttempts(externObject.getAttempts());
            localObject.setSigned(externObject.isSigned());
            if (externObject.getGymModel() != null) {
                localObject.setGymId(externObject.getGymModel().getId());
            } else {
                localObject.setGymId(0L);
            }
        }
        return localObject;
    }
}
