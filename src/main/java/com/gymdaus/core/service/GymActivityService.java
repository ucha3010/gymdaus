package com.gymdaus.core.service;


import com.gymdaus.core.model.GymActivityModel;

import java.util.List;

public interface GymActivityService {

    List<GymActivityModel> findAll();

    List<GymActivityModel> findAllByGymId(Long gymId);

    List<GymActivityModel> findAllByActivityId(Long activityId);

    GymActivityModel findById(Long id);

    GymActivityModel add(GymActivityModel model);

    void update(GymActivityModel model);

    void delete(Long id);

    List<GymActivityModel> sortByZipCode(List<GymActivityModel> gymActivityModelList);
}