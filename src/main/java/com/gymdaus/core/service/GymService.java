package com.gymdaus.core.service;


import com.gymdaus.core.model.GymModel;

import java.util.List;

public interface GymService {

    List<GymModel> findAll();

    List<GymModel> findAllEnabled();

    GymModel findById(Long id);

    void add(GymModel model);

    void update(GymModel model);

    void delete(Long id);

    void enableDisable(Long gymIdModel);

    GymModel findByIdEnabled(Long id);

    List<Long> getGymIdEnabled();

    void dragOfPosition(int initialPosition, int finalPosition);

    int findMaxPosition();
}