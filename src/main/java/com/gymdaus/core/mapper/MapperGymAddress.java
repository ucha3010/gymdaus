package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymAddress;
import com.gymdaus.core.model.GymAddressModel;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.repository.GymRepository;
import com.gymdaus.core.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperGymAddress {

    @Autowired
    private CountryService countryService;
    @Autowired
    private GymRepository gymRepository;

    public GymAddressModel entity2Model(GymAddress externObject) {
        GymAddressModel localObject = new GymAddressModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            GymModel gymModel = new GymModel();
            gymModel.setId(externObject.getGymId());
            gymModel.setName(gymRepository.getNameById(externObject.getGymId()));
            localObject.setGymModel(gymModel);
            localObject.setName(externObject.getName());
            localObject.setEnabled(externObject.isEnabled());
            localObject.setEmail(externObject.getEmail());
            localObject.setPhone(externObject.getPhone());
            localObject.setAddressStreet(externObject.getAddressStreet());
            localObject.setAddressNumber(externObject.getAddressNumber());
            localObject.setAddressOther(externObject.getAddressOther());
            localObject.setAddressCity(externObject.getAddressCity());
            localObject.setAddressZip(externObject.getAddressZip());
            localObject.setCountryModel(countryService.findById(externObject.getCountryId()));
            localObject.setSepaDirectDebitAvailable(externObject.isSepaDirectDebitAvailable());
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
            localObject.setSepaDirectDebitAvailable(externObject.isSepaDirectDebitAvailable());
            if (externObject.getGymModel() != null) {
                localObject.setGymId(externObject.getGymModel().getId());
            } else {
                localObject.setGymId(0L);
            }
        }
        return localObject;
    }
}
