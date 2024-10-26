package com.gymdaus.core.service;


import com.gymdaus.core.model.GymMoreRegistrationModel;

import java.util.List;

public interface GymMoreRegistrationService {

    List<GymMoreRegistrationModel> findAll();

    GymMoreRegistrationModel findById(Long id);

    void add(GymMoreRegistrationModel model);

    void update(GymMoreRegistrationModel model);

    void delete(Long id);

}