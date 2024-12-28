package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymMoreRegistrationGymBelt;
import com.gymdaus.core.mapper.MapperGymMoreRegistrationBelt;
import com.gymdaus.core.model.GymBeltModel;
import com.gymdaus.core.model.GymCategoryGymBeltModel;
import com.gymdaus.core.model.GymMoreRegistrationGymBeltModel;
import com.gymdaus.core.repository.GymMoreRegistrationGymBeltRepository;
import com.gymdaus.core.service.GymBeltService;
import com.gymdaus.core.service.GymCategoryGymBeltService;
import com.gymdaus.core.service.GymMoreRegistrationGymBeltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class GymMoreRegistrationGymBeltServiceImpl implements GymMoreRegistrationGymBeltService {

    @Autowired
    private GymMoreRegistrationGymBeltRepository gymMoreRegistrationGymBeltRepository;

    @Autowired
    private MapperGymMoreRegistrationBelt mapperGymMoreRegistrationBelt;
    @Autowired
    private GymBeltService gymBeltService;
    @Autowired
    private GymCategoryGymBeltService gymCategoryGymBeltService;

    @Override
    public List<GymBeltModel> findAllByGymMoreRegistration(Long gymMoreRegistrationId) {
        List<GymBeltModel> gymBeltModelList = new ArrayList<>();
        for (GymMoreRegistrationGymBelt gymMoreRegistrationGymBelt : gymMoreRegistrationGymBeltRepository.findAllByGymMoreRegistrationIdOrderByPositionAsc(gymMoreRegistrationId)) {
            gymBeltModelList.add(gymBeltService.findById(gymMoreRegistrationGymBelt.getGymBeltId()));
        }
        return gymBeltModelList;
    }

    @Override
    public void add(GymMoreRegistrationGymBeltModel gymMoreRegistrationGymBeltModel) {
        gymMoreRegistrationGymBeltRepository.save(mapperGymMoreRegistrationBelt.model2Entity(gymMoreRegistrationGymBeltModel));
    }

    @Override
    public void emptyByGymMoreRegistrationId(Long gymMoreRegistrationId) {
        for (GymMoreRegistrationGymBelt gymMoreRegistrationGymBelt : gymMoreRegistrationGymBeltRepository.findAllByGymMoreRegistrationIdOrderByPositionAsc(gymMoreRegistrationId)) {
            gymMoreRegistrationGymBeltRepository.delete(gymMoreRegistrationGymBelt);
        }
    }

    @Override
    public void addFromCategory(Long gymMoreRegistrationId, Long gymCategoryId) {
        List<GymCategoryGymBeltModel> gymCategoryGymBeltModelList = gymCategoryGymBeltService.findByGymCategory(gymCategoryId);
        GymMoreRegistrationGymBelt gymMoreRegistrationGymBelt;
        GymBeltModel gymBeltModel;
        for (GymCategoryGymBeltModel gymCategoryGymBeltModel : gymCategoryGymBeltModelList) {
            gymBeltModel = gymBeltService.findById(gymCategoryGymBeltModel.getGymBeltModel().getId());
            gymMoreRegistrationGymBelt = new GymMoreRegistrationGymBelt(gymMoreRegistrationId, gymBeltModel.getId(), gymBeltModel.getPosition());
            gymMoreRegistrationGymBeltRepository.save(gymMoreRegistrationGymBelt);
        }
    }
}
