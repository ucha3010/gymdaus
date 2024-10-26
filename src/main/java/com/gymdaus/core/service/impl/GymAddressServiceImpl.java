package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymAddress;
import com.gymdaus.core.mapper.MapperGymAddress;
import com.gymdaus.core.model.GymAddressModel;
import com.gymdaus.core.repository.GymAddressRepository;
import com.gymdaus.core.service.GymAddressService;
import jakarta.persistence.EntityNotFoundException;
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
    public void add(GymAddressModel gymAddressModel) {
        gymAddressRepository.save(mapperGymAddress.model2Entity(gymAddressModel));
    }

    @Override
    public void update(GymAddressModel gymAddressModel) {
        gymAddressRepository.save(mapperGymAddress.model2Entity(gymAddressModel));
    }

    @Override
    public void delete(Long id) {
        gymAddressRepository.deleteById(id);
    }
}
