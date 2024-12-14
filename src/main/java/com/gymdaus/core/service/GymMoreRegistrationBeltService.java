package com.gymdaus.core.service;


import com.gymdaus.core.model.GymMoreRegistrationBeltModel;

import java.util.List;

public interface GymMoreRegistrationBeltService {

    List<GymMoreRegistrationBeltModel> findAll();

    List<GymMoreRegistrationBeltModel> findAllByGymMoreRegistration(Long gymMoreRegistrationId);

    GymMoreRegistrationBeltModel findById(Long id);

    void add(GymMoreRegistrationBeltModel model);

    void update(GymMoreRegistrationBeltModel model);

    void delete(Long id);

    void dragOfPosition(int initialPosition, int finalPosition);

    int findMaxPosition();

}