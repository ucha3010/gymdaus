package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.ManagerParameter;
import com.gymdaus.core.model.ManagerParameterModel;
import org.springframework.stereotype.Component;

@Component
public class MapperManagerParameter {

    public ManagerParameterModel entity2Model(ManagerParameter externObject) {
        ManagerParameterModel localObject = new ManagerParameterModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setHostPageName(externObject.getHostPageName());
            localObject.setEmail(externObject.getEmail());
            localObject.setPassword(externObject.getPassword());
            localObject.setEmailHost(externObject.getEmailHost());
            localObject.setEmailPort(externObject.getEmailPort());
        }
        return localObject;
    }
    public ManagerParameter model2Entity(ManagerParameterModel externObject) {
        ManagerParameter localObject = new ManagerParameter();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setHostPageName(externObject.getHostPageName());
            localObject.setEmail(externObject.getEmail());
            localObject.setPassword(externObject.getPassword());
            localObject.setEmailHost(externObject.getEmailHost());
            localObject.setEmailPort(externObject.getEmailPort());
        }
        return localObject;
    }
}
