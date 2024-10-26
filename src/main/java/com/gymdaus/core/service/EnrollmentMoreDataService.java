package com.gymdaus.core.service;


import com.gymdaus.core.model.EnrollmentMoreDataModel;

import java.util.List;

public interface EnrollmentMoreDataService {

    List<EnrollmentMoreDataModel> findAll();

    EnrollmentMoreDataModel findById(Long id);

    void add(EnrollmentMoreDataModel model);

    void update(EnrollmentMoreDataModel model);

    void delete(Long id);

}