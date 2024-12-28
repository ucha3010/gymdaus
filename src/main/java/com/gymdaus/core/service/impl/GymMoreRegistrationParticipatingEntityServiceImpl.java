package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymMoreRegistrationParticipatingEntity;
import com.gymdaus.core.model.GymMoreRegistrationModel;
import com.gymdaus.core.model.ParticipatingEntityModel;
import com.gymdaus.core.repository.GymMoreRegistrationParticipatingEntityRepository;
import com.gymdaus.core.service.GymMoreRegistrationParticipatingEntityService;
import com.gymdaus.core.service.ParticipatingEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service()
public class GymMoreRegistrationParticipatingEntityServiceImpl implements GymMoreRegistrationParticipatingEntityService {

    @Autowired
    private GymMoreRegistrationParticipatingEntityRepository gymMoreRegistrationParticipatingEntityRepository;
    @Autowired
    private ParticipatingEntityService participatingEntityService;

    @Override
    public List<ParticipatingEntityModel> findAllByGymMoreRegistration(Long gymMoreRegistrationId) {
        List<ParticipatingEntityModel> participatingEntityModelList = new ArrayList<>();
        for (GymMoreRegistrationParticipatingEntity gymMoreRegistrationParticipatingEntity : gymMoreRegistrationParticipatingEntityRepository.findAllByGymMoreRegistrationIdOrderByPositionAsc(gymMoreRegistrationId)) {
            participatingEntityModelList.add(participatingEntityService.findById(gymMoreRegistrationParticipatingEntity.getParticipatingEntityId()));
        }
        return participatingEntityModelList;
    }

    @Override
    public void addParticipatingEntityList(GymMoreRegistrationModel gymMoreRegistrationModel, String username) {
        List<Long> participatingEntityIdList = gymMoreRegistrationModel.getParticipatingEntityIdList();
        emptyByGymMoreRegistrationId(gymMoreRegistrationModel.getId());
        if (participatingEntityIdList != null) {
            GymMoreRegistrationParticipatingEntity gymMoreRegistrationParticipatingEntity = new GymMoreRegistrationParticipatingEntity();
            gymMoreRegistrationParticipatingEntity.setRegistrationUser(username);
            gymMoreRegistrationParticipatingEntity.setRegistrationDate(new Date());
            gymMoreRegistrationParticipatingEntity.setGymMoreRegistrationId(gymMoreRegistrationModel.getId());
            for (Long id : participatingEntityIdList) {
                gymMoreRegistrationParticipatingEntity.setParticipatingEntityId(id);
                gymMoreRegistrationParticipatingEntity.setPosition(participatingEntityService.findById(id).getPosition());
                gymMoreRegistrationParticipatingEntityRepository.save(gymMoreRegistrationParticipatingEntity);
            }
        }
    }

    @Override
    public void emptyByGymMoreRegistrationId(Long gymMoreRegistrationId) {
        for (GymMoreRegistrationParticipatingEntity gymMoreRegistrationParticipatingEntity : gymMoreRegistrationParticipatingEntityRepository.findAllByGymMoreRegistrationIdOrderByPositionAsc(gymMoreRegistrationId)) {
            gymMoreRegistrationParticipatingEntityRepository.delete(gymMoreRegistrationParticipatingEntity);
        }
    }
}
