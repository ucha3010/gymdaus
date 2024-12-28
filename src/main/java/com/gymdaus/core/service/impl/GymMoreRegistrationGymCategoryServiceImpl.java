package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymMoreRegistrationGymCategory;
import com.gymdaus.core.model.GymCategoryModel;
import com.gymdaus.core.model.GymMoreRegistrationModel;
import com.gymdaus.core.repository.GymMoreRegistrationGymCategoryRepository;
import com.gymdaus.core.service.GymCategoryService;
import com.gymdaus.core.service.GymMoreRegistrationGymBeltService;
import com.gymdaus.core.service.GymMoreRegistrationGymCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service()
public class GymMoreRegistrationGymCategoryServiceImpl implements GymMoreRegistrationGymCategoryService {

    @Autowired
    private GymMoreRegistrationGymCategoryRepository gymMoreRegistrationGymCategoryRepository;
    @Autowired
    private GymCategoryService gymCategoryService;
    @Autowired
    private GymMoreRegistrationGymBeltService gymMoreRegistrationGymBeltService;

    @Override
    public List<GymCategoryModel> findAllByGymMoreRegistration(Long gymMoreRegistrationId) {
        List<GymCategoryModel> gymCategoryModelList = new ArrayList<>();
        for (GymMoreRegistrationGymCategory gymMoreRegistrationGymCategory : gymMoreRegistrationGymCategoryRepository.findByGymMoreRegistrationId(gymMoreRegistrationId)) {
            gymCategoryModelList.add(gymCategoryService.findById(gymMoreRegistrationGymCategory.getGymCategoryId()));
        }
        return gymCategoryModelList;
    }

    @Override
    public void addGymCategoryList(GymMoreRegistrationModel gymMoreRegistrationModel, String username) {
        List<Long> gymCategoryIdList = gymMoreRegistrationModel.getGymCategoryIdList();
        emptyByGymMoreRegistrationId(gymMoreRegistrationModel.getId());
        if (gymCategoryIdList != null) {
            GymMoreRegistrationGymCategory gymMoreRegistrationGymCategory = new GymMoreRegistrationGymCategory();
            gymMoreRegistrationGymCategory.setRegistrationUser(username);
            gymMoreRegistrationGymCategory.setRegistrationDate(new Date());
            gymMoreRegistrationGymCategory.setGymMoreRegistrationId(gymMoreRegistrationModel.getId());
            for (Long id : gymCategoryIdList) {
                gymMoreRegistrationGymCategory.setGymCategoryId(id);
                gymMoreRegistrationGymCategoryRepository.save(gymMoreRegistrationGymCategory);
                gymMoreRegistrationGymBeltService.addFromCategory(gymMoreRegistrationModel.getId(), id);
            }
        }
    }

    @Override
    public void emptyByGymMoreRegistrationId(Long gymMoreRegistrationId) {
        for (GymMoreRegistrationGymCategory gymMoreRegistrationGymCategory : gymMoreRegistrationGymCategoryRepository.findByGymMoreRegistrationId(gymMoreRegistrationId)) {
            gymMoreRegistrationGymCategoryRepository.delete(gymMoreRegistrationGymCategory);
        }
        gymMoreRegistrationGymBeltService.emptyByGymMoreRegistrationId(gymMoreRegistrationId);
    }
}
