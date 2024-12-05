package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymActivitySchedule;
import com.gymdaus.core.mapper.MapperGymActivitySchedule;
import com.gymdaus.core.model.GymActivityScheduleModel;
import com.gymdaus.core.repository.GymActivityScheduleRepository;
import com.gymdaus.core.service.GymActivityScheduleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class GymActivityScheduleServiceImpl implements GymActivityScheduleService {

    @Autowired
    private GymActivityScheduleRepository gymActivityScheduleRepository;

    @Autowired
    private MapperGymActivitySchedule mapperGymActivitySchedule;

    @Override
    public List<GymActivityScheduleModel> findAll() {
        List<GymActivityScheduleModel> gymActivityScheduleModelList = new ArrayList<>();
        for (GymActivitySchedule gymActivitySchedule : gymActivityScheduleRepository.findAllByOrderByPositionAsc()) {
            gymActivityScheduleModelList.add(mapperGymActivitySchedule.entity2Model(gymActivitySchedule));
        }
        return gymActivityScheduleModelList;
    }

    @Override
    public List<GymActivityScheduleModel> findAllByGymAddressIdAndActivityId(Long gymAddressId, Long activityId) {
        List<GymActivityScheduleModel> gymActivityScheduleModelList = new ArrayList<>();
        for (GymActivitySchedule gymActivitySchedule : gymActivityScheduleRepository.findAllByGymAddressIdAndActivityIdOrderByPositionAsc(gymAddressId, activityId)) {
            gymActivityScheduleModelList.add(mapperGymActivitySchedule.entity2Model(gymActivitySchedule));
        }
        return gymActivityScheduleModelList;
    }

    @Override
    public GymActivityScheduleModel findById(Long id) {
        try {
            return mapperGymActivitySchedule.entity2Model(gymActivityScheduleRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymActivityScheduleModel();
        }
    }

    @Override
    public void add(GymActivityScheduleModel gymActivityScheduleModel) {
        gymActivityScheduleRepository.save(mapperGymActivitySchedule.model2Entity(gymActivityScheduleModel));
    }

    @Override
    public void update(GymActivityScheduleModel gymActivityScheduleModel) {
        gymActivityScheduleRepository.save(mapperGymActivitySchedule.model2Entity(gymActivityScheduleModel));
    }

    @Override
    public void delete(Long id) {
        gymActivityScheduleRepository.deleteById(id);
        List<GymActivitySchedule> gymActivityScheduleList = gymActivityScheduleRepository.findAllByOrderByPositionAsc();
        for (int i = 0; i < gymActivityScheduleList.size(); i++) {
            if (gymActivityScheduleList.get(i).getPosition() != i) {
                gymActivityScheduleList.get(i).setPosition(i);
                gymActivityScheduleRepository.save(gymActivityScheduleList.get(i));
            }
        }
    }

    @Override
    public void dragOfPosition(Long gymAddressId, Long activityId, int initialPosition, int finalPosition) {
        GymActivitySchedule gymActivitySchedule = gymActivityScheduleRepository.findByGymAddressIdAndActivityIdAndPosition(gymAddressId, activityId, initialPosition);
        if (initialPosition > finalPosition) {
            for (int i = initialPosition - 1; i >= finalPosition; i--) {
                moveItem(gymAddressId, activityId, i, true);
            }
        }
        if (initialPosition < finalPosition) {
            for (int i = initialPosition + 1; i <= finalPosition; i++) {
                moveItem(gymAddressId, activityId, i, false);
            }
        }
        gymActivitySchedule.setPosition(finalPosition);
        gymActivityScheduleRepository.save(gymActivitySchedule);
    }

    @Override
    public int findMaxPosition(Long gymAddressId, Long activityId) {
        GymActivitySchedule gymActivitySchedule = gymActivityScheduleRepository.findTopByGymAddressIdAndActivityIdOrderByPositionDesc(gymAddressId, activityId);
        if (gymActivitySchedule != null) {
            return gymActivitySchedule.getPosition();
        } else {
            return -1;
        }
    }

    private void moveItem(Long gymAddressId, Long activityId, int position, boolean moveUp) {
        GymActivitySchedule gymActivitySchedule = gymActivityScheduleRepository.findByGymAddressIdAndActivityIdAndPosition(gymAddressId, activityId, position);
        gymActivitySchedule.setPosition(position + (moveUp ? 1 : -1));
        gymActivityScheduleRepository.save(gymActivitySchedule);
    }
}
