package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.Gym;
import com.gymdaus.core.mapper.MapperGym;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.repository.GymRepository;
import com.gymdaus.core.service.GymService;
import com.gymdaus.core.util.LoggerMapper;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service()
public class GymServiceImpl implements GymService {

    @Autowired
    private GymRepository gymRepository;
    @Autowired
    private MapperGym mapperGym;
    @Autowired
    private UserService userService;

    @Override
    public List<GymModel> findAll() {
        List<GymModel> gymModelList = new ArrayList<>();
        for (Gym gym : gymRepository.findAllByOrderByPositionAsc()) {
            gymModelList.add(mapperGym.entity2Model(gym));
        }
        return gymModelList;
    }

    @Override
    public List<GymModel> findAllEnabled() {
        List<GymModel> gymModelList = new ArrayList<>();
        for (Gym gym : gymRepository.findAllByEnabledTrueOrderByPositionAsc()) {
            gymModelList.add(mapperGym.entity2Model(gym));
        }
        return gymModelList;
    }

    @Override
    public GymModel findById(Long id) {
        try {
            return mapperGym.entity2Model(gymRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymModel();
        }
    }

    @Override
    public void add(GymModel gymModel) {
        gymModel.setEnabled(Boolean.TRUE);
        gymModel.setRegistrationDate(new Date());
        gymModel.setModificationUsername(userService.getLoggedUser().getUsername());
        gymModel.setPosition(findMaxPosition() + 1);
        gymRepository.save(mapperGym.model2Entity(gymModel));
    }

    @Override
    public void update(GymModel gymModel) {
        gymModel.setModificationDate(new Date());
        gymModel.setModificationUsername(userService.getLoggedUser().getUsername());
        gymRepository.save(mapperGym.model2Entity(gymModel));
    }

    @Override
    public void delete(Long id) {
        gymRepository.deleteById(id);
    }

    @Override
    public void enableDisable(Long gymIdModel) {
        GymModel gymModel = findById(gymIdModel);
        gymModel.setEnabled(!gymModel.isEnabled());
        try {
            update(gymModel);
        } catch (Exception e) {
            LoggerMapper.log(Level.ERROR, "enableDisable", e.getMessage(), this.getClass());
        }
    }

    @Override
    public GymModel findByIdEnabled(Long id) {
        return mapperGym.entity2Model(gymRepository.findByIdAndEnabledTrue(id).orElse(null));
    }

    @Override
    public List<Long> getGymIdEnabled() {
        return gymRepository.findIdsByEnabledTrue();
    }

    @Override
    public void dragOfPosition(int initialPosition, int finalPosition) {
        Gym gym = gymRepository.findByPosition(initialPosition);
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
        gym.setPosition(finalPosition);
        gymRepository.save(gym);
    }

    @Override
    public int findMaxPosition() {
        Gym gym = gymRepository.findTopByOrderByPositionDesc();
        if (gym != null) {
            return gym.getPosition();
        } else {
            return -1;
        }
    }

    private void moveItem(int position, boolean moveUp) {
        Gym gym = gymRepository.findByPosition(position);
        gym.setPosition(position + (moveUp ? 1 : -1));
        gymRepository.save(gym);
    }
}
