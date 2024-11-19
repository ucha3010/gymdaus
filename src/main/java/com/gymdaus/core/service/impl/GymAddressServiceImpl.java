package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymAddress;
import com.gymdaus.core.mapper.MapperGymAddress;
import com.gymdaus.core.model.GymAddressModel;
import com.gymdaus.core.repository.GymAddressRepository;
import com.gymdaus.core.service.GymAddressService;
import com.gymdaus.core.util.EmailEnum;
import com.gymdaus.core.util.LoggerMapper;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class GymAddressServiceImpl implements GymAddressService {

    @Autowired
    private GymAddressRepository gymAddressRepository;

    @Autowired
    private MapperGymAddress mapperGymAddress;

    @Override
    public List<GymAddressModel> findAll() {
        List<GymAddressModel> gymAddressModelList = new ArrayList<>();
        for (GymAddress gymAddress : gymAddressRepository.findAll()) {
            gymAddressModelList.add(mapperGymAddress.entity2Model(gymAddress));
        }
        return gymAddressModelList;
    }

    @Override
    public GymAddressModel findById(Long id) {
        try {
            return mapperGymAddress.entity2Model(gymAddressRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymAddressModel();
        }
    }

    @Override
    public List<GymAddressModel> findByGymId(Long gymId) {
        List<GymAddressModel> gymAddressModelList = new ArrayList<>();
        for (GymAddress gymAddress : gymAddressRepository.findByGymId(gymId)) {
            gymAddressModelList.add(mapperGymAddress.entity2Model(gymAddress));
        }
        return gymAddressModelList;
    }

    @Override
    public void add(GymAddressModel gymAddressModel) {
        chargePort(gymAddressModel);
        gymAddressRepository.save(mapperGymAddress.model2Entity(gymAddressModel));
    }

    @Override
    public void update(GymAddressModel gymAddressModel) {
        chargePort(gymAddressModel);
        gymAddressRepository.save(mapperGymAddress.model2Entity(gymAddressModel));
    }

    @Override
    public void delete(Long id) {
        gymAddressRepository.deleteById(id);
    }

    @Override
    public GymAddressModel enableDisable(Long gymAddressId) {
        GymAddressModel gymAddressModel = findById(gymAddressId);
        gymAddressModel.setEnabled(!gymAddressModel.isEnabled());
        try {
            update(gymAddressModel);
        } catch (Exception e) {
            LoggerMapper.log(Level.ERROR, "enableDisable", e.getMessage(), this.getClass());
        }
        return gymAddressModel;
    }

    private void chargePort(GymAddressModel gymAddressModel) {
        for (EmailEnum emailEnum : EmailEnum.values()) {
            if (emailEnum.getHost().equals(gymAddressModel.getEmailHost())) {
                gymAddressModel.setEmailPort(emailEnum.getPort());
                break;
            }
        }
    }
}
