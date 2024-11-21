package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.MoreRegistration;
import com.gymdaus.core.mapper.MapperMoreRegistration;
import com.gymdaus.core.model.MoreRegistrationModel;
import com.gymdaus.core.repository.MoreRegistrationRepository;
import com.gymdaus.core.service.MoreRegistrationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class MoreRegistrationServiceImpl implements MoreRegistrationService {

    @Autowired
    private MoreRegistrationRepository moreRegistrationRepository;

    @Autowired
    private MapperMoreRegistration mapperMoreRegistration;

    @Override
    public List<MoreRegistrationModel> findAll() {
        List<MoreRegistrationModel> moreRegistrationModelList = new ArrayList<>();
        for (MoreRegistration moreRegistration : moreRegistrationRepository.findAll()) {
            moreRegistrationModelList.add(mapperMoreRegistration.entity2Model(moreRegistration));
        }
        return moreRegistrationModelList;
    }

    @Override
    public MoreRegistrationModel findById(Long id) {
        try {
            return mapperMoreRegistration.entity2Model(moreRegistrationRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new MoreRegistrationModel();
        }
    }

    @Override
    public void add(MoreRegistrationModel moreRegistrationModel) {
        moreRegistrationRepository.save(mapperMoreRegistration.model2Entity(moreRegistrationModel));
    }

    @Override
    public void update(MoreRegistrationModel moreRegistrationModel) {
        moreRegistrationRepository.save(mapperMoreRegistration.model2Entity(moreRegistrationModel));
    }

    @Override
    public void delete(Long id) {
        moreRegistrationRepository.deleteById(id);
    }

    @Override
    public void dragOfPosition(int initialPosition, int finalPosition) {
        MoreRegistration moreRegistration = moreRegistrationRepository.findByPosition(initialPosition);
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
        moreRegistration.setPosition(finalPosition);
        moreRegistrationRepository.save(moreRegistration);
    }

    @Override
    public int findMaxPosition() {
        MoreRegistration moreRegistration = moreRegistrationRepository.findTopByOrderByPositionDesc();
        if (moreRegistration != null) {
            return moreRegistration.getPosition();
        } else {
            return -1;
        }
    }

    @Override
    public List<MoreRegistrationModel> findAllEnabled() {
        List<MoreRegistrationModel> moreRegistrationModelList = new ArrayList<>();
        for (MoreRegistration moreRegistration : moreRegistrationRepository.findAllByEnabledTrueOrderByPositionAsc()) {
            moreRegistrationModelList.add(mapperMoreRegistration.entity2Model(moreRegistration));
        }
        return moreRegistrationModelList;
    }

    private void moveItem(int position, boolean moveUp) {
        MoreRegistration moreRegistration = moreRegistrationRepository.findByPosition(position);
        moreRegistration.setPosition(position + (moveUp ? 1 : -1));
        moreRegistrationRepository.save(moreRegistration);
    }
}
