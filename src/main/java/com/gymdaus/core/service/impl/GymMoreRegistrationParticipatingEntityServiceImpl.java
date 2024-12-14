package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymMoreRegistrationParticipatingEntity;
import com.gymdaus.core.mapper.MapperGymMoreRegistrationParticipatingEntity;
import com.gymdaus.core.model.GymMoreRegistrationParticipatingEntityModel;
import com.gymdaus.core.repository.GymMoreRegistrationParticipatingEntityRepository;
import com.gymdaus.core.service.GymMoreRegistrationParticipatingEntityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class GymMoreRegistrationParticipatingEntityServiceImpl implements GymMoreRegistrationParticipatingEntityService {

    @Autowired
    private GymMoreRegistrationParticipatingEntityRepository gymMoreRegistrationParticipatingEntityRepository;

    @Autowired
    private MapperGymMoreRegistrationParticipatingEntity mapperGymMoreRegistrationParticipatingEntity;

    @Override
    public List<GymMoreRegistrationParticipatingEntityModel> findAll() {
        List<GymMoreRegistrationParticipatingEntityModel> gymMoreRegistrationParticipatingEntityModelList = new ArrayList<>();
        for (GymMoreRegistrationParticipatingEntity gymMoreRegistrationParticipatingEntity : gymMoreRegistrationParticipatingEntityRepository.findAllByOrderByPositionAsc()) {
            gymMoreRegistrationParticipatingEntityModelList.add(mapperGymMoreRegistrationParticipatingEntity.entity2Model(gymMoreRegistrationParticipatingEntity));
        }
        return gymMoreRegistrationParticipatingEntityModelList;
    }

    @Override
    public List<GymMoreRegistrationParticipatingEntityModel> findAllByGymMoreRegistration(Long gymMoreRegistrationId) {
        List<GymMoreRegistrationParticipatingEntityModel> gymMoreRegistrationParticipatingEntityModelList = new ArrayList<>();
        for (GymMoreRegistrationParticipatingEntity gymMoreRegistrationParticipatingEntity : gymMoreRegistrationParticipatingEntityRepository.findAllByGymMoreRegistrationIdOrderByRegistrationDateDesc(gymMoreRegistrationId)) {
            gymMoreRegistrationParticipatingEntityModelList.add(mapperGymMoreRegistrationParticipatingEntity.entity2Model(gymMoreRegistrationParticipatingEntity));
        }
        return gymMoreRegistrationParticipatingEntityModelList;
    }

    @Override
    public GymMoreRegistrationParticipatingEntityModel findById(Long id) {
        try {
            return mapperGymMoreRegistrationParticipatingEntity.entity2Model(gymMoreRegistrationParticipatingEntityRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymMoreRegistrationParticipatingEntityModel();
        }
    }

    @Override
    public void add(GymMoreRegistrationParticipatingEntityModel gymMoreRegistrationParticipatingEntityModel) {
        gymMoreRegistrationParticipatingEntityRepository.save(mapperGymMoreRegistrationParticipatingEntity.model2Entity(gymMoreRegistrationParticipatingEntityModel));
    }

    @Override
    public void update(GymMoreRegistrationParticipatingEntityModel gymMoreRegistrationParticipatingEntityModel) {
        gymMoreRegistrationParticipatingEntityRepository.save(mapperGymMoreRegistrationParticipatingEntity.model2Entity(gymMoreRegistrationParticipatingEntityModel));
    }

    @Override
    public void delete(Long id) {
        gymMoreRegistrationParticipatingEntityRepository.deleteById(id);
        List<GymMoreRegistrationParticipatingEntity> gymMoreRegistrationParticipatingEntityList = gymMoreRegistrationParticipatingEntityRepository.findAllByOrderByPositionAsc();
        for (int i = 0; i < gymMoreRegistrationParticipatingEntityList.size(); i++) {
            if (gymMoreRegistrationParticipatingEntityList.get(i).getPosition() != i) {
                gymMoreRegistrationParticipatingEntityList.get(i).setPosition(i);
                gymMoreRegistrationParticipatingEntityRepository.save(gymMoreRegistrationParticipatingEntityList.get(i));
            }
        }
    }

    @Override
    public void dragOfPosition(int initialPosition, int finalPosition) {
        GymMoreRegistrationParticipatingEntity gymMoreRegistrationParticipatingEntity = gymMoreRegistrationParticipatingEntityRepository.findByPosition(initialPosition);
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
        gymMoreRegistrationParticipatingEntity.setPosition(finalPosition);
        gymMoreRegistrationParticipatingEntityRepository.save(gymMoreRegistrationParticipatingEntity);
    }

    @Override
    public int findMaxPosition() {
        GymMoreRegistrationParticipatingEntity gymMoreRegistrationParticipatingEntity = gymMoreRegistrationParticipatingEntityRepository.findTopByOrderByPositionDesc();
        if (gymMoreRegistrationParticipatingEntity != null) {
            return gymMoreRegistrationParticipatingEntity.getPosition();
        } else {
            return -1;
        }
    }

    private void moveItem(int position, boolean moveUp) {
        GymMoreRegistrationParticipatingEntity gymMoreRegistrationParticipatingEntity = gymMoreRegistrationParticipatingEntityRepository.findByPosition(position);
        gymMoreRegistrationParticipatingEntity.setPosition(position + (moveUp ? 1 : -1));
        gymMoreRegistrationParticipatingEntityRepository.save(gymMoreRegistrationParticipatingEntity);
    }
}
