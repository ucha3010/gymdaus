package com.gymdaus.core.service;


import com.gymdaus.core.model.GymDocumentManagerModel;

import java.util.List;

public interface GymDocumentManagerService {

    List<GymDocumentManagerModel> findAll();

    GymDocumentManagerModel findById(Long id);

    void add(GymDocumentManagerModel model);

    void update(GymDocumentManagerModel model);

    void delete(Long id);

}