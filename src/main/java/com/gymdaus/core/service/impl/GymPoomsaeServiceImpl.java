package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymCategory;
import com.gymdaus.core.entity.GymPoomsae;
import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.mapper.MapperGymPoomsae;
import com.gymdaus.core.model.GymPoomsaeModel;
import com.gymdaus.core.repository.GymCategoryRepository;
import com.gymdaus.core.repository.GymPoomsaeRepository;
import com.gymdaus.core.service.GymPoomsaeService;
import com.gymdaus.core.util.Constants;
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
    @Autowired
    private GymCategoryRepository gymCategoryRepository;

    @Override
    public List<GymPoomsaeModel> findAllByGymId(Long gymId) {
        List<GymPoomsaeModel> gymPoomsaeModelList = new ArrayList<>();
        for (GymPoomsae gymPoomsae : gymPoomsaeRepository.findAllByGymIdOrderByPositionAsc(gymId)) {
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
    public void delete(Long id) throws RemoveException {
        GymPoomsae gymPoomsae = gymPoomsaeRepository.findById(id).orElse(null);
        if (gymPoomsae != null) {
            List<GymCategory> gymCategoryList = gymCategoryRepository.findAllByGymIdOrderByPositionAsc(gymPoomsae.getGymId());
            for (GymCategory gymCategory : gymCategoryList) {
                if (id.equals(gymCategory.getPoomsaeId())) {
                    throw new RemoveException(Constants.DELETE_ADVICE, "error.deleting.item.in.use");
                }
            }
            try {
                gymPoomsaeRepository.deleteById(id);
                List<GymPoomsae> gymPoomsaeList = gymPoomsaeRepository.findAllByGymIdOrderByPositionAsc(gymPoomsae.getGymId());
                for (int i = 0; i < gymPoomsaeList.size(); i++) {
                    if (gymPoomsaeList.get(i).getPosition() != i) {
                        gymPoomsaeList.get(i).setPosition(i);
                        gymPoomsaeRepository.save(gymPoomsaeList.get(i));
                    }
                }
            } catch (Exception e) {
                throw new RemoveException("1000", e.getMessage());
            }
        }
    }

    @Override
    public void dragOfPosition(Long gymId, int initialPosition, int finalPosition) {
        GymPoomsae gymPoomsae = gymPoomsaeRepository.findByGymIdAndPosition(gymId, initialPosition);
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
        gymPoomsae.setPosition(finalPosition);
        gymPoomsaeRepository.save(gymPoomsae);
    }

    @Override
    public int findMaxPosition(Long gymId) {
        GymPoomsae gymPoomsae = gymPoomsaeRepository.findTopByGymIdOrderByPositionDesc(gymId);
        if (gymPoomsae != null) {
            return gymPoomsae.getPosition();
        } else {
            return -1;
        }
    }

    private void moveItem(Long gymId, int position, boolean moveUp) {
        GymPoomsae gymPoomsae = gymPoomsaeRepository.findByGymIdAndPosition(gymId, position);
        gymPoomsae.setPosition(position + (moveUp ? 1 : -1));
        gymPoomsaeRepository.save(gymPoomsae);
    }
}
