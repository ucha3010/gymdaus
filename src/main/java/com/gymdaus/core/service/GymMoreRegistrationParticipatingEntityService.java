package com.gymdaus.core.service;


import com.gymdaus.core.model.GymMoreRegistrationParticipatingEntityModel;

import java.util.List;

public interface GymMoreRegistrationParticipatingEntityService {

    List<GymMoreRegistrationParticipatingEntityModel> findAll();

    GymMoreRegistrationParticipatingEntityModel findById(Long id);

    void add(GymMoreRegistrationParticipatingEntityModel model);

    void update(GymMoreRegistrationParticipatingEntityModel model);

    void delete(Long id);

    void dragOfPosition(int initialPosition, int finalPosition);

    int findMaxPosition();

}