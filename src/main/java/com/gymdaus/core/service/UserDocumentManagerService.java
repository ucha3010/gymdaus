package com.gymdaus.core.service;


import com.gymdaus.core.model.UserDocumentManagerModel;

import java.util.List;

public interface UserDocumentManagerService {

    List<UserDocumentManagerModel> findAll();

    UserDocumentManagerModel findById(Long id);

    void add(UserDocumentManagerModel model);

    void update(UserDocumentManagerModel model);

    void delete(Long id);

}