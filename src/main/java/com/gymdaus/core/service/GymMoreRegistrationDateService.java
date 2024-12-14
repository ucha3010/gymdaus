package com.gymdaus.core.service;


import com.gymdaus.core.model.GymMoreRegistrationDateModel;

import java.util.List;

public interface GymMoreRegistrationDateService {

    List<GymMoreRegistrationDateModel> findAll();

    List<GymMoreRegistrationDateModel> findAllByGymMoreRegistration(Long gymMoreRegistrationId);

    GymMoreRegistrationDateModel findById(Long id);

    void add(GymMoreRegistrationDateModel model);

    void update(GymMoreRegistrationDateModel model);

    void delete(Long id);

}