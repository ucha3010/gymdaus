package com.gymdaus.core.service;


import com.gymdaus.core.model.GymMoreRegistrationModel;
import com.gymdaus.core.model.ParticipatingEntityModel;

import java.util.List;

public interface GymMoreRegistrationParticipatingEntityService {

    List<ParticipatingEntityModel> findAllByGymMoreRegistration(Long gymMoreRegistrationId);

    void addParticipatingEntityList(GymMoreRegistrationModel gymMoreRegistrationModel, String username);

    void emptyByGymMoreRegistrationId(Long gymMoreRegistrationId);
}