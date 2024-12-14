package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymMoreRegistrationDate;
import com.gymdaus.core.mapper.MapperGymMoreRegistrationDate;
import com.gymdaus.core.model.GymMoreRegistrationDateModel;
import com.gymdaus.core.repository.GymMoreRegistrationDateRepository;
import com.gymdaus.core.service.GymMoreRegistrationDateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class GymMoreRegistrationDateServiceImpl implements GymMoreRegistrationDateService {

    @Autowired
    private GymMoreRegistrationDateRepository gymMoreRegistrationDateRepository;

    @Autowired
    private MapperGymMoreRegistrationDate mapperGymMoreRegistrationDate;

    @Override
    public List<GymMoreRegistrationDateModel> findAll() {
        List<GymMoreRegistrationDateModel> gymMoreRegistrationDateModelList = new ArrayList<>();
        for (GymMoreRegistrationDate gymMoreRegistrationDate : gymMoreRegistrationDateRepository.findAll()) {
            gymMoreRegistrationDateModelList.add(mapperGymMoreRegistrationDate.entity2Model(gymMoreRegistrationDate));
        }
        return gymMoreRegistrationDateModelList;
    }

    @Override
    public List<GymMoreRegistrationDateModel> findAllByGymMoreRegistration(Long gymMoreRegistrationId) {
        List<GymMoreRegistrationDateModel> gymMoreRegistrationDateModelList = new ArrayList<>();
        for (GymMoreRegistrationDate gymMoreRegistrationDate : gymMoreRegistrationDateRepository.findAllByGymMoreRegistrationId(gymMoreRegistrationId)) {
            gymMoreRegistrationDateModelList.add(mapperGymMoreRegistrationDate.entity2Model(gymMoreRegistrationDate));
        }
        return gymMoreRegistrationDateModelList;
    }

    @Override
    public GymMoreRegistrationDateModel findById(Long id) {
        try {
            return mapperGymMoreRegistrationDate.entity2Model(gymMoreRegistrationDateRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymMoreRegistrationDateModel();
        }
    }

    @Override
    public void add(GymMoreRegistrationDateModel gymMoreRegistrationDateModel) {
        gymMoreRegistrationDateRepository.save(mapperGymMoreRegistrationDate.model2Entity(gymMoreRegistrationDateModel));
    }

    @Override
    public void update(GymMoreRegistrationDateModel gymMoreRegistrationDateModel) {
        gymMoreRegistrationDateRepository.save(mapperGymMoreRegistrationDate.model2Entity(gymMoreRegistrationDateModel));
    }

    @Override
    public void delete(Long id) {
        gymMoreRegistrationDateRepository.deleteById(id);
    }
}
