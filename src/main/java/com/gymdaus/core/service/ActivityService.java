package com.gymdaus.core.service;


import com.gymdaus.core.model.ActivityModel;

import java.util.List;

public interface ActivityService {

    List<ActivityModel> findAll();

    ActivityModel findById(Long id);

    void add(ActivityModel model);

    void update(ActivityModel model);

    void delete(Long id);

    void dragOfPosition(int initialPosition, int finalPosition);

    int findMaxPosition();

    List<ActivityModel> findAllEnabled();
}