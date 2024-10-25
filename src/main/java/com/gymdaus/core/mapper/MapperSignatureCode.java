package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.SignatureCode;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.SignatureCodeModel;
import org.springframework.stereotype.Component;

@Component
public class MapperSignatureCode {

    public SignatureCodeModel entity2Model(SignatureCode externObject) {
        SignatureCodeModel localObject = new SignatureCodeModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setCode(externObject.getCode());
            localObject.setUsername(externObject.getUsername());
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setExpirationDate(externObject.getExpirationDate());
            localObject.setOperationId(externObject.getOperationId());
            localObject.setOperationName(externObject.getOperationName());
            localObject.setSignedOkPage(externObject.getSignedOkPage());
            if (externObject.getGymId() != 0) {
                GymModel gymModel = new GymModel();
                gymModel.setId(externObject.getGymId());
                localObject.setGymModel(gymModel);
            }
        }
        return localObject;
    }

    public SignatureCode model2Entity(SignatureCodeModel externObject) {
        SignatureCode localObject = new SignatureCode();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setCode(externObject.getCode());
            localObject.setUsername(externObject.getUsername());
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setExpirationDate(externObject.getExpirationDate());
            localObject.setOperationId(externObject.getOperationId());
            localObject.setOperationName(externObject.getOperationName());
            localObject.setSignedOkPage(externObject.getSignedOkPage());
            if (externObject.getGymModel() != null) {
                localObject.setGymId(externObject.getGymModel().getId());
            } else {
                localObject.setGymId(0L);
            }
        }
        return localObject;
    }
}
