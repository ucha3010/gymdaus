package com.gymdaus.core.service;


import com.gymdaus.core.model.GymBeltModel;
import com.gymdaus.core.model.GymMoreRegistrationGymBeltModel;

import java.util.List;

public interface GymMoreRegistrationGymBeltService {

    void add(GymMoreRegistrationGymBeltModel model);

    List<GymBeltModel> findAllByGymMoreRegistration(Long gymMoreRegistrationId);

    void emptyByGymMoreRegistrationId(Long gymMoreRegistrationId);

    void addFromCategory(Long gymMoreRegistrationId, Long gymCategoryId);
}