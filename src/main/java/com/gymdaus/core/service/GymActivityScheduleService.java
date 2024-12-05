package com.gymdaus.core.service;


import com.gymdaus.core.model.GymActivityScheduleModel;

import java.util.List;

public interface GymActivityScheduleService {

    List<GymActivityScheduleModel> findAll();

    List<GymActivityScheduleModel> findAllByGymAddressIdAndActivityId(Long gymAddressId, Long activityId);

    GymActivityScheduleModel findById(Long id);

    void add(GymActivityScheduleModel model);

    void update(GymActivityScheduleModel model);

    void delete(Long id);

    void dragOfPosition(Long gymAddressId, Long activityId, int initialPosition, int finalPosition);

    int findMaxPosition(Long gymAddressId, Long activityId);

}