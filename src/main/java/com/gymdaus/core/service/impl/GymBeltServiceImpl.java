package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymBelt;
import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.mapper.MapperGymBelt;
import com.gymdaus.core.model.GymBeltModel;
import com.gymdaus.core.repository.GymBeltRepository;
import com.gymdaus.core.service.GymBeltService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class GymBeltServiceImpl implements GymBeltService {

    @Autowired
    private GymBeltRepository gymBeltRepository;

    @Autowired
    private MapperGymBelt mapperGymBelt;

    @Override
    public List<GymBeltModel> findAllByGymId(Long gymId) {
        List<GymBeltModel> gymBeltModelList = new ArrayList<>();
        for (GymBelt gymBelt : gymBeltRepository.findAllByGymIdOrderByPositionAsc(gymId)) {
            gymBeltModelList.add(mapperGymBelt.entity2Model(gymBelt));
        }
        return gymBeltModelList;
    }

    @Override
    public GymBeltModel findById(Long id) {
        try {
            return mapperGymBelt.entity2Model(gymBeltRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymBeltModel();
        }
    }

    @Override
    public void add(GymBeltModel gymBeltModel) {
        gymBeltRepository.save(mapperGymBelt.model2Entity(gymBeltModel));
    }

    @Override
    public void update(GymBeltModel gymBeltModel) {
        gymBeltRepository.save(mapperGymBelt.model2Entity(gymBeltModel));
    }

    @Override
    public void delete(Long id) throws RemoveException {
        GymBelt gymBelt = gymBeltRepository.findById(id).orElse(null);
        if (gymBelt != null) {
            try {
                gymBeltRepository.deleteById(id);
                List<GymBelt> gymBeltList = gymBeltRepository.findAllByGymIdOrderByPositionAsc(gymBelt.getGymId());
                for (int i = 0; i < gymBeltList.size(); i++) {
                    if (gymBeltList.get(i).getPosition() != i) {
                        gymBeltList.get(i).setPosition(i);
                        gymBeltRepository.save(gymBeltList.get(i));
                    }
                }
            } catch (Exception e) {
                throw new RemoveException("1000", e.getMessage());
            }
        }
    }

    @Override
    public void dragOfPosition(Long gymId, int initialPosition, int finalPosition) {
        GymBelt gymBelt = gymBeltRepository.findByGymIdAndPosition(gymId, initialPosition);
        if (initialPosition > finalPosition) {
            for (int i = initialPosition - 1; i >= finalPosition; i--) {
                moveItem(gymId, i, true);
            }
        }
        if (initialPosition < finalPosition) {
            for (int i = initialPosition + 1; i <= finalPosition; i++) {
                moveItem(gymId, i, false);
            }
        }
        gymBelt.setPosition(finalPosition);
        gymBeltRepository.save(gymBelt);
    }

    @Override
    public int findMaxPosition(Long gymId) {
        GymBelt gymBelt = gymBeltRepository.findTopByGymIdOrderByPositionDesc(gymId);
        if (gymBelt != null) {
            return gymBelt.getPosition();
        } else {
            return -1;
        }
    }

    private void moveItem(Long gymId, int position, boolean moveUp) {
        GymBelt gymBelt = gymBeltRepository.findByGymIdAndPosition(gymId, position);
        gymBelt.setPosition(position + (moveUp ? 1 : -1));
        gymBeltRepository.save(gymBelt);
    }
}
