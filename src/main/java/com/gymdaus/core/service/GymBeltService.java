package com.gymdaus.core.service;


import com.gymdaus.core.model.GymBeltModel;

import java.util.List;

public interface GymBeltService {

    List<GymBeltModel> findAll();

    GymBeltModel findById(Long id);

    void add(GymBeltModel model);

    void update(GymBeltModel model);

    void delete(Long id);

    void dragOfPosition(int initialPosition, int finalPosition);

    int findMaxPosition();

}