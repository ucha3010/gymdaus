package com.gymdaus.core.service;


import com.gymdaus.core.model.UserDocumentManagerModel;
import com.gymdaus.core.model.UserModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserDocumentManagerService {

    List<UserDocumentManagerModel> findAll();

    UserDocumentManagerModel findById(Long id);

    void add(UserDocumentManagerModel model);

    void update(UserDocumentManagerModel model);

    void delete(Long id);

    UserDocumentManagerModel findByUsernameEnabled(String username);
    boolean addPhoto(UserModel userModel, MultipartFile file);

    String getProfilePhotoPath(String username);

}