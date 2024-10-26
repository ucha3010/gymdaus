package com.gymdaus.core.service;


import com.gymdaus.core.model.GymActivityModel;

import java.util.List;

public interface GymActivityService {

    List<GymActivityModel> findAll();

    GymActivityModel findById(Long id);

    void add(GymActivityModel model);

    void update(GymActivityModel model);

    void delete(Long id);

}