package com.gymdaus.core.service;


import com.gymdaus.core.model.GymCategoryGymBeltModel;

import java.util.List;

public interface GymCategoryGymBeltService {

    List<GymCategoryGymBeltModel> findAll();

    GymCategoryGymBeltModel findById(Long id);

    void add(GymCategoryGymBeltModel model);

    void update(GymCategoryGymBeltModel model);

    void delete(Long id);

    void deleteByGymCategoryId(Long gymCategoryId);

    List<GymCategoryGymBeltModel> findByGymCategory(Long gymCategoryId);

    List<GymCategoryGymBeltModel> findByGymBelt(Long gymBeltId);


}