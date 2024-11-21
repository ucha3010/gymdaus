package com.gymdaus.core.service;


import com.gymdaus.core.model.MoreRegistrationModel;

import java.util.List;

public interface MoreRegistrationService {

    List<MoreRegistrationModel> findAll();

    MoreRegistrationModel findById(Long id);

    void add(MoreRegistrationModel model);

    void update(MoreRegistrationModel model);

    void delete(Long id);

    void dragOfPosition(int initialPosition, int finalPosition);

    int findMaxPosition();

    List<MoreRegistrationModel> findAllEnabled();
}