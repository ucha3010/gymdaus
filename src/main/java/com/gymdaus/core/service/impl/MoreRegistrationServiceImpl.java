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
}
