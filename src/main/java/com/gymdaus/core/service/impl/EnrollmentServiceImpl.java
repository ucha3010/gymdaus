package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.Enrollment;
import com.gymdaus.core.mapper.MapperEnrollment;
import com.gymdaus.core.model.EnrollmentModel;
import com.gymdaus.core.repository.EnrollmentRepository;
import com.gymdaus.core.service.EnrollmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private MapperEnrollment mapperEnrollment;

    @Override
    public List<EnrollmentModel> findByUsername(String username) {
        List<EnrollmentModel> enrollmentModelList = new ArrayList<>();
        for (Enrollment enrollment : enrollmentRepository.findByUsername(username)) {
            enrollmentModelList.add(mapperEnrollment.entity2Model(enrollment));
        }
        return enrollmentModelList;
    }

    @Override
    public List<EnrollmentModel> findByGymId(Long gymId) {
        List<EnrollmentModel> enrollmentModelList = new ArrayList<>();
        for (Enrollment enrollment : enrollmentRepository.findByGymId(gymId)) {
            enrollmentModelList.add(mapperEnrollment.entity2Model(enrollment));
        }
        return enrollmentModelList;
    }

    @Override
    public EnrollmentModel findById(Long id) {
        try {
            return mapperEnrollment.entity2Model(enrollmentRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new EnrollmentModel();
        }
    }

    @Override
    public void add(EnrollmentModel enrollmentModel) {
        enrollmentRepository.save(mapperEnrollment.model2Entity(enrollmentModel));
    }

    @Override
    public void update(EnrollmentModel enrollmentModel) {
        enrollmentRepository.save(mapperEnrollment.model2Entity(enrollmentModel));
    }

    @Override
    public void delete(Long id) {
        enrollmentRepository.deleteById(id);
    }
}
