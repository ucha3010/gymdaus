package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymActivity;
import com.gymdaus.core.entity.GymActivitySchedule;
import com.gymdaus.core.mapper.MapperGymActivity;
import com.gymdaus.core.model.GymActivityModel;
import com.gymdaus.core.model.GymAddressModel;
import com.gymdaus.core.repository.GymActivityRepository;
import com.gymdaus.core.repository.GymActivityScheduleRepository;
import com.gymdaus.core.service.GymActivityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service()
public class GymActivityServiceImpl implements GymActivityService {

    @Autowired
    private GymActivityRepository gymActivityRepository;

    @Autowired
    private MapperGymActivity mapperGymActivity;
    @Autowired
    private GymActivityScheduleRepository gymActivityScheduleRepository;

    @Override
    public List<GymActivityModel> findAll() {
        List<GymActivityModel> gymActivityModelList = new ArrayList<>();
        for (GymActivity gymActivity : gymActivityRepository.findAll()) {
            gymActivityModelList.add(mapperGymActivity.entity2Model(gymActivity));
        }
        return gymActivityModelList;
    }

    @Override
    public List<GymActivityModel> findAllByGymId(Long gymId) {
        List<GymActivityModel> gymActivityModelList = new ArrayList<>();
        for (GymActivity gymActivity : gymActivityRepository.findAllByGymIdAndEnabledTrueOrderByRegistrationDateAsc(gymId)) {
            gymActivityModelList.add(mapperGymActivity.entity2Model(gymActivity));
        }
        return gymActivityModelList;
    }

    @Override
    public List<GymActivityModel> findAllByActivityId(Long activityId) {
        List<GymActivityModel> gymActivityModelList = new ArrayList<>();
        for (GymActivity gymActivity : gymActivityRepository.findAllByActivityIdAndEnabledTrue(activityId)) {
            gymActivityModelList.add(mapperGymActivity.entity2Model(gymActivity));
        }
        return gymActivityModelList;
    }

    @Override
    public GymActivityModel findById(Long id) {
        try {
            return mapperGymActivity.entity2Model(gymActivityRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymActivityModel();
        }
    }

    @Override
    public GymActivityModel add(GymActivityModel gymActivityModel) {
        gymActivityModel.setEnabled(Boolean.TRUE);
        gymActivityModel.setRegistrationDate(new Date());
        return mapperGymActivity.entity2Model(gymActivityRepository.save(mapperGymActivity.model2Entity(gymActivityModel)));
    }

    @Override
    public void update(GymActivityModel gymActivityModel) {
        gymActivityRepository.save(mapperGymActivity.model2Entity(gymActivityModel));
    }

    @Override
    public void delete(Long id) {
        GymActivityModel gymActivityModel = findById(id);
        gymActivityModel.setEnabled(Boolean.FALSE);
        update(gymActivityModel);
        for (GymActivitySchedule gymActivitySchedule : gymActivityScheduleRepository.findAllByGymAddressIdAndActivityIdAndEnabledTrueOrderByPositionAsc(gymActivityModel.getGymAddressModel().getId(), gymActivityModel.getActivityModel().getId())) {
            gymActivitySchedule.setEnabled(Boolean.FALSE);
            gymActivityScheduleRepository.save(gymActivitySchedule);
        }
    }

    @Override
    public List<GymActivityModel> sortByZipCode(List<GymActivityModel> gymActivityModelList) {
        return gymActivityModelList.stream()
                .sorted(Comparator.comparing(gymActivityModel ->
                        Optional.ofNullable(gymActivityModel.getGymAddressModel())
                                .map(GymAddressModel::getAddressZip)
                                .orElse("")
                ))
                .collect(Collectors.toList());
    }
}
