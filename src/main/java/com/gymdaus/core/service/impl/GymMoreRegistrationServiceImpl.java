package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymMoreRegistration;
import com.gymdaus.core.mapper.MapperGymMoreRegistration;
import com.gymdaus.core.model.GymMoreRegistrationModel;
import com.gymdaus.core.repository.GymMoreRegistrationRepository;
import com.gymdaus.core.service.GymMoreRegistrationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class GymMoreRegistrationServiceImpl implements GymMoreRegistrationService {

    @Autowired
    private GymMoreRegistrationRepository gymMoreRegistrationRepository;

    @Autowired
    private MapperGymMoreRegistration mapperGymMoreRegistration;

    @Override
    public List<GymMoreRegistrationModel> findAll() {
        List<GymMoreRegistrationModel> gymMoreRegistrationModelList = new ArrayList<>();
        for (GymMoreRegistration gymMoreRegistration : gymMoreRegistrationRepository.findAll()) {
            gymMoreRegistrationModelList.add(mapperGymMoreRegistration.entity2Model(gymMoreRegistration));
        }
        return gymMoreRegistrationModelList;
    }

    @Override
    public GymMoreRegistrationModel findById(Long id) {
        try {
            return mapperGymMoreRegistration.entity2Model(gymMoreRegistrationRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymMoreRegistrationModel();
        }
    }

    @Override
    public void add(GymMoreRegistrationModel gymMoreRegistrationModel) {
        gymMoreRegistrationRepository.save(mapperGymMoreRegistration.model2Entity(gymMoreRegistrationModel));
    }

    @Override
    public void update(GymMoreRegistrationModel gymMoreRegistrationModel) {
        gymMoreRegistrationRepository.save(mapperGymMoreRegistration.model2Entity(gymMoreRegistrationModel));
    }

    @Override
    public void delete(Long id) {
        gymMoreRegistrationRepository.deleteById(id);
    }
}
