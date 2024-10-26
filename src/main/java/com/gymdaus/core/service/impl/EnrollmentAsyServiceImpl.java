package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.EnrollmentAs;
import com.gymdaus.core.mapper.MapperEnrollmentAs;
import com.gymdaus.core.model.EnrollmentAsModel;
import com.gymdaus.core.repository.EnrollmentAsRepository;
import com.gymdaus.core.service.EnrollmentAsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class EnrollmentAsyServiceImpl implements EnrollmentAsService {

    @Autowired
    private EnrollmentAsRepository enrollmentAsRepository;

    @Autowired
    private MapperEnrollmentAs mapperEnrollmentAs;

    @Override
    public List<EnrollmentAsModel> findAll() {
        List<EnrollmentAsModel> enrollmentAsModelList = new ArrayList<>();
        for (EnrollmentAs enrollmentAs : enrollmentAsRepository.findAllByOrderByPositionAsc()) {
            enrollmentAsModelList.add(mapperEnrollmentAs.entity2Model(enrollmentAs));
        }
        return enrollmentAsModelList;
    }

    @Override
    public EnrollmentAsModel findById(Long id) {
        try {
            return mapperEnrollmentAs.entity2Model(enrollmentAsRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new EnrollmentAsModel();
        }
    }

    @Override
    public void add(EnrollmentAsModel enrollmentAsModel) {
        enrollmentAsRepository.save(mapperEnrollmentAs.model2Entity(enrollmentAsModel));
    }

    @Override
    public void update(EnrollmentAsModel enrollmentAsModel) {
        enrollmentAsRepository.save(mapperEnrollmentAs.model2Entity(enrollmentAsModel));
    }

    @Override
    public void delete(Long id) {
        enrollmentAsRepository.deleteById(id);
        List<EnrollmentAs> enrollmentAsList = enrollmentAsRepository.findAllByOrderByPositionAsc();
        for (int i = 0; i < enrollmentAsList.size(); i++) {
            if (enrollmentAsList.get(i).getPosition() != i) {
                enrollmentAsList.get(i).setPosition(i);
                enrollmentAsRepository.save(enrollmentAsList.get(i));
            }
        }
    }

    @Override
    public void dragOfPosition(int initialPosition, int finalPosition) {
        EnrollmentAs enrollmentAs = enrollmentAsRepository.findByPosition(initialPosition);
        if (initialPosition > finalPosition) {
            for (int i = initialPosition - 1; i >= finalPosition; i--) {
                moveItem(i, true);
            }
        }
        if (initialPosition < finalPosition) {
            for (int i = initialPosition + 1; i <= finalPosition; i++) {
                moveItem(i, false);
            }
        }
        enrollmentAs.setPosition(finalPosition);
        enrollmentAsRepository.save(enrollmentAs);
    }

    @Override
    public int findMaxPosition() {
        EnrollmentAs enrollmentAs = enrollmentAsRepository.findTopByOrderByPositionDesc();
        if (enrollmentAs != null) {
            return enrollmentAs.getPosition();
        } else {
            return -1;
        }
    }

    private void moveItem(int position, boolean moveUp) {
        EnrollmentAs enrollmentAs = enrollmentAsRepository.findByPosition(position);
        enrollmentAs.setPosition(position + (moveUp ? 1 : -1));
        enrollmentAsRepository.save(enrollmentAs);
    }
}
