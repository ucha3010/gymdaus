package com.gymdaus.core.service;


import com.gymdaus.core.model.EnrollmentAsModel;

import java.util.List;

public interface EnrollmentAsService {

    List<EnrollmentAsModel> findAll();

    EnrollmentAsModel findById(Long id);

    void add(EnrollmentAsModel model);

    void update(EnrollmentAsModel model);

    void delete(Long id);

    void dragOfPosition(int initialPosition, int finalPosition);

    int findMaxPosition();

}