package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.Token;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.TokenModel;
import org.springframework.stereotype.Component;

@Component
public class MapperToken {

    public TokenModel entity2Model(Token externObject) {
        TokenModel localObject = null;
        if (externObject != null) {
            localObject = new TokenModel();
            localObject.setId(externObject.getId());
            localObject.setUsername(externObject.getUsername());
            localObject.setExpiration(externObject.getExpiration());
            localObject.setAttempts(externObject.getAttempts());
            GymModel gymModel = new GymModel();
            gymModel.setId(externObject.getGymId());
            localObject.setGymModel(gymModel);
            localObject.setMethodToBeUse(externObject.getMethodToBeUse());
            localObject.setUsernameSendChange(externObject.getUsernameSendChange());
            localObject.setDateUsedOk(externObject.getDateUsedOk());
        }
        return localObject;
    }

    public Token model2Entity(TokenModel externObject) {
        Token localObject = new Token();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setUsername(externObject.getUsername());
            localObject.setExpiration(externObject.getExpiration());
            localObject.setAttempts(externObject.getAttempts());
            localObject.setMethodToBeUse(externObject.getMethodToBeUse());
            localObject.setUsernameSendChange(externObject.getUsernameSendChange());
            localObject.setDateUsedOk(externObject.getDateUsedOk());
            if (externObject.getGymModel() != null) {
                localObject.setGymId(externObject.getGymModel().getId());
            }
        }
        return localObject;
    }
}
