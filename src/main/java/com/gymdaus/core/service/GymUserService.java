package com.gymdaus.core.service;


import com.gymdaus.core.model.GymUserModel;
import com.gymdaus.core.model.TokenModel;

import java.util.List;

public interface GymUserService {

    List<GymUserModel> findAll();

    GymUserModel findById(Long id);

    void add(GymUserModel model);

    void update(GymUserModel model);

    void delete(Long id);

    List<GymUserModel> findByUsername(String username);

    List<GymUserModel> findByGymId(Long id);

    List<GymUserModel> findByUsernameAndGymId(String username, Long id);

    void addNewManager(TokenModel tokenModel);
}