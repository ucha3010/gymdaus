package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymMoreRegistration;
import com.gymdaus.core.mapper.MapperGymMoreRegistration;
import com.gymdaus.core.model.GymCategoryModel;
import com.gymdaus.core.model.GymMoreRegistrationGymCategoryModel;
import com.gymdaus.core.model.GymMoreRegistrationModel;
import com.gymdaus.core.repository.GymMoreRegistrationRepository;
import com.gymdaus.core.service.GymMoreRegistrationGymCategoryService;
import com.gymdaus.core.service.GymMoreRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service()
public class GymMoreRegistrationServiceImpl implements GymMoreRegistrationService {

    @Autowired
    private GymMoreRegistrationRepository gymMoreRegistrationRepository;

    @Autowired
    private MapperGymMoreRegistration mapperGymMoreRegistration;
    @Autowired
    private GymMoreRegistrationGymCategoryService gymMoreRegistrationGymCategoryService;

    @Override
    public List<GymMoreRegistrationModel> findAll() {
        List<GymMoreRegistrationModel> gymMoreRegistrationModelList = new ArrayList<>();
        for (GymMoreRegistration gymMoreRegistration : gymMoreRegistrationRepository.findAll()) {
            gymMoreRegistrationModelList.add(mapperGymMoreRegistration.entity2Model(gymMoreRegistration));
        }
        return gymMoreRegistrationModelList;
    }

    @Override
    public List<GymMoreRegistrationModel> findAllByGymId(Long gymId) {
        List<GymMoreRegistrationModel> gymMoreRegistrationModelList = new ArrayList<>();
        for (GymMoreRegistration gymMoreRegistration : gymMoreRegistrationRepository.findAllByGymIdAndEnabledTrueOrderByRegistrationDateAsc(gymId)) {
            gymMoreRegistrationModelList.add(mapperGymMoreRegistration.entity2Model(gymMoreRegistration));
        }
        return gymMoreRegistrationModelList;
    }

    @Override
    public GymMoreRegistrationModel findById(Long id) {
        GymMoreRegistrationModel gymMoreRegistrationModel = mapperGymMoreRegistration.entity2Model(gymMoreRegistrationRepository.findById(id).orElse(null));
        if (gymMoreRegistrationModel != null) {
            List<GymMoreRegistrationGymCategoryModel> gymMoreRegistrationGymCategoryModelList =
                    gymMoreRegistrationGymCategoryService.findByGymMoreRegistration(id);
            if (gymMoreRegistrationGymCategoryModelList != null && !gymMoreRegistrationGymCategoryModelList.isEmpty()) {
                List<GymCategoryModel> gymCategoryModelList = new ArrayList<>();
                for (GymMoreRegistrationGymCategoryModel gymMoreRegistrationGymCategoryModel : gymMoreRegistrationGymCategoryModelList) {
                    gymCategoryModelList.add(gymMoreRegistrationGymCategoryModel.getGymCategoryModel());
                }
                gymMoreRegistrationModel.setGymCategoryModelList(gymCategoryModelList);
            }
        }
        return gymMoreRegistrationModel;

    }

    @Override
    public GymMoreRegistrationModel add(GymMoreRegistrationModel gymMoreRegistrationModel) {
        gymMoreRegistrationModel.setRegistrationDate(new Date());
        gymMoreRegistrationModel.setEnabled(Boolean.TRUE);
        return mapperGymMoreRegistration.entity2Model(gymMoreRegistrationRepository.save(mapperGymMoreRegistration.model2Entity(gymMoreRegistrationModel)));
    }

    @Override
    public void update(GymMoreRegistrationModel gymMoreRegistrationModel) {
        gymMoreRegistrationModel.setModificationDate(new Date());
        gymMoreRegistrationRepository.save(mapperGymMoreRegistration.model2Entity(gymMoreRegistrationModel));
    }

    @Override
    public void delete(Long id) {
        gymMoreRegistrationRepository.deleteById(id);
    }
}
