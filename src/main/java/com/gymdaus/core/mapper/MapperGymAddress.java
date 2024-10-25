package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymAddress;
import com.gymdaus.core.model.CountryModel;
import com.gymdaus.core.model.GymAddressModel;
import com.gymdaus.core.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperGymAddress {

    @Autowired
    private CountryService countryService;

    public GymAddressModel entity2Model(GymAddress externObject) {
        GymAddressModel localObject = new GymAddressModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setEnabled(externObject.isEnabled());
            localObject.setEmail(externObject.getEmail());
            localObject.setEmailHost(externObject.getEmailHost());
            localObject.setEmailPassword(externObject.getEmailPassword());
            localObject.setEmailPort(externObject.getEmailPort());
            localObject.setPhone(externObject.getPhone());
            localObject.setAddressStreet(externObject.getAddressStreet());
            localObject.setAddressNumber(externObject.getAddressNumber());
            localObject.setAddressOther(externObject.getAddressOther());
            localObject.setAddressCity(externObject.getAddressCity());
            localObject.setAddressZip(externObject.getAddressZip());
            localObject.setCountryModel(countryService.findById(externObject.getCountryId()));
        }
        return localObject;
    }

    public GymAddress model2Entity(GymAddressModel externObject) {
        GymAddress localObject = new GymAddress();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setEnabled(externObject.isEnabled());
            localObject.setEmail(externObject.getEmail());
            localObject.setEmailHost(externObject.getEmailHost());
            localObject.setEmailPassword(externObject.getEmailPassword());
            localObject.setEmailPort(externObject.getEmailPort());
            localObject.setPhone(externObject.getPhone());
            localObject.setAddressStreet(externObject.getAddressStreet());
            localObject.setAddressNumber(externObject.getAddressNumber());
            localObject.setAddressOther(externObject.getAddressOther());
            localObject.setAddressCity(externObject.getAddressCity());
            localObject.setAddressZip(externObject.getAddressZip());
            if (externObject.getCountryModel() != null) {
                localObject.setCountryId(externObject.getCountryModel().getId());
            } else {
                localObject.setCountryId(0L);
            }
        }
        return localObject;
    }
}
