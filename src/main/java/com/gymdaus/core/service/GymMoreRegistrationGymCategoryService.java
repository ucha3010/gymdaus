package com.gymdaus.core.service;


import com.gymdaus.core.model.GymCategoryModel;
import com.gymdaus.core.model.GymMoreRegistrationGymCategoryModel;
import com.gymdaus.core.model.GymMoreRegistrationModel;

import java.util.List;

public interface GymMoreRegistrationGymCategoryService {

    List<GymCategoryModel> findAllByGymMoreRegistration(Long gymMoreRegistrationId);

    void addGymCategoryList(GymMoreRegistrationModel gymMoreRegistrationModel, String username);

    void emptyByGymMoreRegistrationId(Long gymMoreRegistrationId);
}