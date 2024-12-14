package com.gymdaus.core.service;


import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.model.GymCategoryModel;

import java.util.List;

public interface GymCategoryService {

    List<GymCategoryModel> findAllByGymId(Long gymId);

    GymCategoryModel findById(Long id);

    GymCategoryModel add(GymCategoryModel model);

    void update(GymCategoryModel model);

    void delete(Long id) throws RemoveException;

    void dragOfPosition(Long gymId, int initialPosition, int finalPosition);

    int findMaxPosition(Long gymId);

}