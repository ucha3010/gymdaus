package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymPoomsae;
import com.gymdaus.core.mapper.MapperGymPoomsae;
import com.gymdaus.core.model.GymPoomsaeModel;
import com.gymdaus.core.repository.GymPoomsaeRepository;
import com.gymdaus.core.service.GymPoomsaeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class GymPoomsaeServiceImpl implements GymPoomsaeService {

    @Autowired
    private GymPoomsaeRepository gymPoomsaeRepository;

    @Autowired
    private MapperGymPoomsae mapperGymPoomsae;

    @Override
    public List<GymPoomsaeModel> findAll() {
        List<GymPoomsaeModel> gymPoomsaeModelList = new ArrayList<>();
        for (GymPoomsae gymPoomsae : gymPoomsaeRepository.findAllByOrderByPositionAsc()) {
            gymPoomsaeModelList.add(mapperGymPoomsae.entity2Model(gymPoomsae));
        }
        return gymPoomsaeModelList;
    }

    @Override
    public GymPoomsaeModel findById(Long id) {
        try {
            return mapperGymPoomsae.entity2Model(gymPoomsaeRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymPoomsaeModel();
        }
    }

    @Override
    public void add(GymPoomsaeModel gymPoomsaeModel) {
        gymPoomsaeRepository.save(mapperGymPoomsae.model2Entity(gymPoomsaeModel));
    }

    @Override
    public void update(GymPoomsaeModel gymPoomsaeModel) {
        gymPoomsaeRepository.save(mapperGymPoomsae.model2Entity(gymPoomsaeModel));
    }

    @Override
    public void delete(Long id) {
        gymPoomsaeRepository.deleteById(id);
        List<GymPoomsae> gymPoomsaeList = gymPoomsaeRepository.findAllByOrderByPositionAsc();
        for (int i = 0; i < gymPoomsaeList.size(); i++) {
            if (gymPoomsaeList.get(i).getPosition() != i) {
                gymPoomsaeList.get(i).setPosition(i);
                gymPoomsaeRepository.save(gymPoomsaeList.get(i));
            }
        }
    }

    @Override
    public void dragOfPosition(int initialPosition, int finalPosition) {
        GymPoomsae gymPoomsae = gymPoomsaeRepository.findByPosition(initialPosition);
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
        gymPoomsae.setPosition(finalPosition);
        gymPoomsaeRepository.save(gymPoomsae);
    }

    @Override
    public int findMaxPosition() {
        GymPoomsae gymPoomsae = gymPoomsaeRepository.findTopByOrderByPositionDesc();
        if (gymPoomsae != null) {
            return gymPoomsae.getPosition();
        } else {
            return -1;
        }
    }

    private void moveItem(int position, boolean moveUp) {
        GymPoomsae gymPoomsae = gymPoomsaeRepository.findByPosition(position);
        gymPoomsae.setPosition(position + (moveUp ? 1 : -1));
        gymPoomsaeRepository.save(gymPoomsae);
    }
}
