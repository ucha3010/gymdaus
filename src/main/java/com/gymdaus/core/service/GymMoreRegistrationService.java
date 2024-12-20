package com.gymdaus.core.service;


import com.gymdaus.core.model.GymMoreRegistrationModel;

import java.util.List;

public interface GymMoreRegistrationService {

    List<GymMoreRegistrationModel> findAll();

    List<GymMoreRegistrationModel> findAllByGymId(Long gymId);

    GymMoreRegistrationModel findById(Long id);

    GymMoreRegistrationModel add(GymMoreRegistrationModel model);

    void update(GymMoreRegistrationModel model);

    void delete(Long id);

}