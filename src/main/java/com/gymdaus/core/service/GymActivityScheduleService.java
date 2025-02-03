package com.gymdaus.core.service;


import com.gymdaus.core.model.GymActivityScheduleModel;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

public interface GymActivityScheduleService {

    List<GymActivityScheduleModel> findAll();

    List<GymActivityScheduleModel> findAllByGymAddressIdAndActivityId(Long gymAddressId, Long activityId);

    GymActivityScheduleModel findById(Long id);

    void add(GymActivityScheduleModel model);

    void update(GymActivityScheduleModel model);

    void delete(Long id);

    void dragOfPosition(Long gymAddressId, Long activityId, int initialPosition, int finalPosition);

    int findMaxPosition(Long gymAddressId, Long activityId);

    void fillDescription(List<GymActivityScheduleModel> gymActivityScheduleModelList, MessageSource messageSource, Locale locale);
}