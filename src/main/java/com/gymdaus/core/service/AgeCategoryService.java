package com.gymdaus.core.service;


import com.gymdaus.core.model.AgeCategoryModel;

import java.util.List;

public interface AgeCategoryService {

    List<AgeCategoryModel> findAll();

    AgeCategoryModel findById(Long id);

    void add(AgeCategoryModel model);

    void update(AgeCategoryModel model);

    void delete(Long id);

    void dragOfPosition(int initialPosition, int finalPosition);

    int findMaxPosition();

}