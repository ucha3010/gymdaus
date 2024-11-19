package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.Gym;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.service.GymAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperGym {

    @Autowired
    private GymAddressService gymAddressService;

    public GymModel entity2Model(Gym externObject) {
        GymModel localObject = null;
        if (externObject != null) {
            localObject = new GymModel();
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setEnabled(externObject.isEnabled());
            localObject.setTaxIdCode(externObject.getTaxIdCode());
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setModificationDate(externObject.getModificationDate());
            localObject.setModificationUsername(externObject.getModificationUsername());
            localObject.setContractedRecords(externObject.getContractedRecords());
            localObject.setContractedVisibility(externObject.getContractedVisibility());
            localObject.setPosition(externObject.getPosition());
            localObject.setGymAddressModelList(gymAddressService.findByGymId(externObject.getId()));
        }
        return localObject;
    }

    public Gym model2Entity(GymModel externObject) {
        Gym localObject = new Gym();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setEnabled(externObject.isEnabled());
            localObject.setTaxIdCode(externObject.getTaxIdCode());
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setModificationDate(externObject.getModificationDate());
            localObject.setModificationUsername(externObject.getModificationUsername());
            localObject.setContractedRecords(externObject.getContractedRecords());
            localObject.setContractedVisibility(externObject.getContractedVisibility());
            localObject.setPosition(externObject.getPosition());
        }
        return localObject;
    }
}
