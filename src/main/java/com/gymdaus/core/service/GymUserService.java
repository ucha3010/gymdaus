package com.gymdaus.core.service;


import com.gymdaus.core.model.GymUserModel;

import java.util.List;

public interface GymUserService {

    List<GymUserModel> findAll();

    GymUserModel findById(Long id);

    void add(GymUserModel model);

    void update(GymUserModel model);

    void delete(Long id);

    List<GymUserModel> findByUsername(String username);

    List<GymUserModel> findByGymId(Long id);
}