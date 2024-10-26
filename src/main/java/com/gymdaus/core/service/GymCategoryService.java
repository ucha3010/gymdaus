package com.gymdaus.core.service;


import com.gymdaus.core.model.GymCategoryModel;

import java.util.List;

public interface GymCategoryService {

    List<GymCategoryModel> findAll();

    GymCategoryModel findById(Long id);

    void add(GymCategoryModel model);

    void update(GymCategoryModel model);

    void delete(Long id);

    void dragOfPosition(int initialPosition, int finalPosition);

    int findMaxPosition();

}