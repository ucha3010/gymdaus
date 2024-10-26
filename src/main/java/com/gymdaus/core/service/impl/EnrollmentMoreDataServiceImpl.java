package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.EnrollmentMoreData;
import com.gymdaus.core.mapper.MapperEnrollmentMoreData;
import com.gymdaus.core.model.EnrollmentMoreDataModel;
import com.gymdaus.core.repository.EnrollmentMoreDataRepository;
import com.gymdaus.core.service.EnrollmentMoreDataService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class EnrollmentMoreDataServiceImpl implements EnrollmentMoreDataService {

    @Autowired
    private EnrollmentMoreDataRepository enrollmentMoreDataRepository;

    @Autowired
    private MapperEnrollmentMoreData mapperEnrollmentMoreData;

    @Override
    public List<EnrollmentMoreDataModel> findAll() {
        List<EnrollmentMoreDataModel> enrollmentMoreDataModelList = new ArrayList<>();
        for (EnrollmentMoreData enrollmentMoreData : enrollmentMoreDataRepository.findAll()) {
            enrollmentMoreDataModelList.add(mapperEnrollmentMoreData.entity2Model(enrollmentMoreData));
        }
        return enrollmentMoreDataModelList;
    }

    @Override
    public EnrollmentMoreDataModel findById(Long id) {
        try {
            return mapperEnrollmentMoreData.entity2Model(enrollmentMoreDataRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new EnrollmentMoreDataModel();
        }
    }

    @Override
    public void add(EnrollmentMoreDataModel enrollmentMoreDataModel) {
        enrollmentMoreDataRepository.save(mapperEnrollmentMoreData.model2Entity(enrollmentMoreDataModel));
    }

    @Override
    public void update(EnrollmentMoreDataModel enrollmentMoreDataModel) {
        enrollmentMoreDataRepository.save(mapperEnrollmentMoreData.model2Entity(enrollmentMoreDataModel));
    }

    @Override
    public void delete(Long id) {
        enrollmentMoreDataRepository.deleteById(id);
    }
}
