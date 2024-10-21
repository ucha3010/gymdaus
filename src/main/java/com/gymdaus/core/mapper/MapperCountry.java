package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.Country;
import com.gymdaus.core.model.CountryModel;
import org.springframework.stereotype.Component;

@Component
public class MapperCountry {

    public CountryModel entity2Model(Country externObject) {
        CountryModel localObject = new CountryModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setPosition(externObject.getPosition());
        }
        return localObject;
    }
    public Country model2Entity(CountryModel externObject) {
        Country localObject = new Country();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setPosition(externObject.getPosition());
        }
        return localObject;
    }
}
