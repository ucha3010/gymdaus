package com.gymdaus.core.service;


import com.gymdaus.core.model.GymDocumentManagerModel;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.UserModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GymDocumentManagerService {

    List<GymDocumentManagerModel> findAll();

    GymDocumentManagerModel findById(Long id);

    void add(GymDocumentManagerModel model);

    void update(GymDocumentManagerModel model);

    boolean delete(Long id);

    List<GymDocumentManagerModel> findByGymId(Long gymId);

    boolean addDocument(UserModel user, MultipartFile file, String section, GymModel gymModel);
}