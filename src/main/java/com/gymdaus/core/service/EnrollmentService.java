package com.gymdaus.core.service;


import com.gymdaus.core.model.EnrollmentModel;

import java.util.List;

public interface EnrollmentService {

    List<EnrollmentModel> findAll();

    List<EnrollmentModel> findByUsername(String username);

    List<EnrollmentModel> findByGymId(Long gymId);

    EnrollmentModel findById(Long id);

    void add(EnrollmentModel model);

    void update(EnrollmentModel model);

    void delete(Long id);

}