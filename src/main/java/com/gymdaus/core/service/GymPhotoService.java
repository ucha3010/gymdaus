package com.gymdaus.core.service;


import com.gymdaus.core.model.GymPhotoModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GymPhotoService {

    List<GymPhotoModel> findAll();

    GymPhotoModel findById(Long id);

    void add(GymPhotoModel model);

    void update(GymPhotoModel model);

    boolean delete(Long id);

    List<GymPhotoModel> findByGymId(Long gymId);

    GymPhotoModel findByGymIdAndMainPhotoTrue(Long gymId);

    boolean addPhoto(Long gymId, MultipartFile file);

    void doMain(Long id);
}