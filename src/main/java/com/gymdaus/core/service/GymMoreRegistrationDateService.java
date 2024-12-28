package com.gymdaus.core.service;


import com.gymdaus.core.model.GymMoreRegistrationDateModel;

import java.util.List;

public interface GymMoreRegistrationDateService {

    List<GymMoreRegistrationDateModel> findAllByGymMoreRegistration(Long gymMoreRegistrationId);

    GymMoreRegistrationDateModel findById(Long id);

    GymMoreRegistrationDateModel add(GymMoreRegistrationDateModel model);

    void delete(Long id);

    void emptyByGymMoreRegistrationId(Long gymMoreRegistrationId);

}