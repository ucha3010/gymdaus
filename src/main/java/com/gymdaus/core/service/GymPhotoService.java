package com.gymdaus.core.service;


import com.gymdaus.core.model.GymPhotoModel;

import java.util.List;

public interface GymPhotoService {

    List<GymPhotoModel> findAll();

    GymPhotoModel findById(Long id);

    void add(GymPhotoModel model);

    void update(GymPhotoModel model);

    void delete(Long id);

}