package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.Menu1;
import com.gymdaus.core.model.Menu1Model;
import org.springframework.stereotype.Component;

@Component
public class MapperMenu1 {

    public Menu1Model entity2Model(Menu1 externObject) {
        Menu1Model localObject = new Menu1Model();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setEnabled(externObject.isEnabled());
            localObject.setName(externObject.getName());
            localObject.setPosition(externObject.getPosition());
        }
        return localObject;
    }
    public Menu1 model2Entity(Menu1Model externObject) {
        Menu1 localObject = new Menu1();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setEnabled(externObject.isEnabled());
            localObject.setName(externObject.getName());
            localObject.setPosition(externObject.getPosition());
        }
        return localObject;
    }
}
