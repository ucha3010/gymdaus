package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymMoreRegistration;
import com.gymdaus.core.model.GymMoreRegistrationModel;
import com.gymdaus.core.service.CountryService;
import com.gymdaus.core.service.GymService;
import com.gymdaus.core.service.MoreRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperGymMoreRegistration {

    @Autowired
    private CountryService countryService;
    @Autowired
    private GymService gymService;
    @Autowired
    private MoreRegistrationService moreRegistrationService;

    public GymMoreRegistrationModel entity2Model(GymMoreRegistration externObject) {
        GymMoreRegistrationModel localObject = null;
        if (externObject != null) {
            localObject = new GymMoreRegistrationModel();
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setRegistrationUser(externObject.getRegistrationUser());
            localObject.setEnabled(externObject.isEnabled());
            localObject.setModificationDate(externObject.getModificationDate());
            localObject.setModificationUser(externObject.getModificationUser());
            localObject.setStartRegistrationAvailable(externObject.getStartRegistrationAvailable());
            localObject.setEndRegistrationAvailable(externObject.getEndRegistrationAvailable());
            localObject.setAddressStreet(externObject.getAddressStreet());
            localObject.setAddressNumber(externObject.getAddressNumber());
            localObject.setAddressOther(externObject.getAddressOther());
            localObject.setAddressCity(externObject.getAddressCity());
            localObject.setAddressZip(externObject.getAddressZip());
            localObject.setCountryModel(countryService.findById(externObject.getCountryId()));
            localObject.setNeedsPaid(externObject.isNeedsPaid());
            localObject.setSepa(externObject.isSepa());
            if (externObject.getGymId() != 0) {
                localObject.setGymModel(gymService.findById(externObject.getGymId()));
            }
            if (externObject.getMoreRegistrationId() != 0) {
                localObject.setMoreRegistrationModel(moreRegistrationService.findById(externObject.getMoreRegistrationId()));
            }
        }
        return localObject;
    }

    public GymMoreRegistration model2Entity(GymMoreRegistrationModel externObject) {
        GymMoreRegistration localObject = new GymMoreRegistration();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setRegistrationUser(externObject.getRegistrationUser());
            localObject.setEnabled(externObject.isEnabled());
            localObject.setModificationDate(externObject.getModificationDate());
            localObject.setModificationUser(externObject.getModificationUser());
            localObject.setStartRegistrationAvailable(externObject.getStartRegistrationAvailable());
            localObject.setEndRegistrationAvailable(externObject.getEndRegistrationAvailable());
            localObject.setAddressStreet(externObject.getAddressStreet());
            localObject.setAddressNumber(externObject.getAddressNumber());
            localObject.setAddressOther(externObject.getAddressOther());
            localObject.setAddressCity(externObject.getAddressCity());
            localObject.setAddressZip(externObject.getAddressZip());
            localObject.setNeedsPaid(externObject.isNeedsPaid());
            localObject.setSepa(externObject.isSepa());
            if (externObject.getGymModel() != null) {
                localObject.setGymId(externObject.getGymModel().getId());
            } else {
                localObject.setGymId(0L);
            }
            if (externObject.getMoreRegistrationModel() != null) {
                localObject.setMoreRegistrationId(externObject.getMoreRegistrationModel().getId());
            } else {
                localObject.setMoreRegistrationId(0L);
            }
            if (externObject.getCountryModel() != null) {
                localObject.setCountryId(externObject.getCountryModel().getId());
            } else {
                localObject.setCountryId(0L);
            }
        }
        return localObject;
    }
}
