package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymMoreRegistrationBelt;
import com.gymdaus.core.mapper.MapperGymMoreRegistrationBelt;
import com.gymdaus.core.model.GymMoreRegistrationBeltModel;
import com.gymdaus.core.repository.GymMoreRegistrationBeltRepository;
import com.gymdaus.core.service.GymMoreRegistrationBeltService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class GymMoreRegistrationBeltServiceImpl implements GymMoreRegistrationBeltService {

    @Autowired
    private GymMoreRegistrationBeltRepository gymMoreRegistrationBeltRepository;

    @Autowired
    private MapperGymMoreRegistrationBelt mapperGymMoreRegistrationBelt;

    @Override
    public List<GymMoreRegistrationBeltModel> findAll() {
        List<GymMoreRegistrationBeltModel> gymMoreRegistrationBeltModelList = new ArrayList<>();
        for (GymMoreRegistrationBelt gymMoreRegistrationBelt : gymMoreRegistrationBeltRepository.findAllByOrderByPositionAsc()) {
            gymMoreRegistrationBeltModelList.add(mapperGymMoreRegistrationBelt.entity2Model(gymMoreRegistrationBelt));
        }
        return gymMoreRegistrationBeltModelList;
    }

    @Override
    public GymMoreRegistrationBeltModel findById(Long id) {
        try {
            return mapperGymMoreRegistrationBelt.entity2Model(gymMoreRegistrationBeltRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymMoreRegistrationBeltModel();
        }
    }

    @Override
    public void add(GymMoreRegistrationBeltModel gymMoreRegistrationBeltModel) {
        gymMoreRegistrationBeltRepository.save(mapperGymMoreRegistrationBelt.model2Entity(gymMoreRegistrationBeltModel));
    }

    @Override
    public void update(GymMoreRegistrationBeltModel gymMoreRegistrationBeltModel) {
        gymMoreRegistrationBeltRepository.save(mapperGymMoreRegistrationBelt.model2Entity(gymMoreRegistrationBeltModel));
    }

    @Override
    public void delete(Long id) {
        gymMoreRegistrationBeltRepository.deleteById(id);
        List<GymMoreRegistrationBelt> gymMoreRegistrationBeltList = gymMoreRegistrationBeltRepository.findAllByOrderByPositionAsc();
        for (int i = 0; i < gymMoreRegistrationBeltList.size(); i++) {
            if (gymMoreRegistrationBeltList.get(i).getPosition() != i) {
                gymMoreRegistrationBeltList.get(i).setPosition(i);
                gymMoreRegistrationBeltRepository.save(gymMoreRegistrationBeltList.get(i));
            }
        }
    }

    @Override
    public void dragOfPosition(int initialPosition, int finalPosition) {
        GymMoreRegistrationBelt gymMoreRegistrationBelt = gymMoreRegistrationBeltRepository.findByPosition(initialPosition);
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
        gymMoreRegistrationBelt.setPosition(finalPosition);
        gymMoreRegistrationBeltRepository.save(gymMoreRegistrationBelt);
    }

    @Override
    public int findMaxPosition() {
        GymMoreRegistrationBelt gymMoreRegistrationBelt = gymMoreRegistrationBeltRepository.findTopByOrderByPositionDesc();
        if (gymMoreRegistrationBelt != null) {
            return gymMoreRegistrationBelt.getPosition();
        } else {
            return -1;
        }
    }

    private void moveItem(int position, boolean moveUp) {
        GymMoreRegistrationBelt gymMoreRegistrationBelt = gymMoreRegistrationBeltRepository.findByPosition(position);
        gymMoreRegistrationBelt.setPosition(position + (moveUp ? 1 : -1));
        gymMoreRegistrationBeltRepository.save(gymMoreRegistrationBelt);
    }
}
