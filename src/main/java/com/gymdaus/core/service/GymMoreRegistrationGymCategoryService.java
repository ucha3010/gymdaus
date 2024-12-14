package com.gymdaus.core.service;


import com.gymdaus.core.model.GymMoreRegistrationGymCategoryModel;

import java.util.List;

public interface GymMoreRegistrationGymCategoryService {

    List<GymMoreRegistrationGymCategoryModel> findAll();

    GymMoreRegistrationGymCategoryModel findById(Long id);

    void add(GymMoreRegistrationGymCategoryModel model);

    void update(GymMoreRegistrationGymCategoryModel model);

    void delete(Long id);

    List<GymMoreRegistrationGymCategoryModel> findByGymMoreRegistration(Long gymMoreRegistrationId);

    List<GymMoreRegistrationGymCategoryModel> findByGymCategory(Long gymCategoryId);

    GymMoreRegistrationGymCategoryModel findByGymMoreRegistrationAndGymCategory(Long gymMoreRegistrationId, Long gymCategoryId);
}