package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.Menu2;
import com.gymdaus.core.model.Menu2Model;
import org.springframework.stereotype.Component;

@Component
public class MapperMenu2 {

    public Menu2Model entity2Model(Menu2 externObject) {
        Menu2Model localObject = new Menu2Model();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setIdMenu1(externObject.getMenu1Id());
            localObject.setEnabled(externObject.isEnabled());
            localObject.setName(externObject.getName());
            localObject.setPosition(externObject.getPosition());
            localObject.setUrl(externObject.getUrl());
            localObject.setAdvise(externObject.getAdvise());
        }
        return localObject;
    }
    public Menu2 model2Entity(Menu2Model externObject) {
        Menu2 localObject = new Menu2();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setMenu1Id(externObject.getIdMenu1());
            localObject.setEnabled(externObject.isEnabled());
            localObject.setName(externObject.getName());
            localObject.setPosition(externObject.getPosition());
            localObject.setUrl(externObject.getUrl());
            localObject.setAdvise(externObject.getAdvise());
        }
        return localObject;
    }
}
