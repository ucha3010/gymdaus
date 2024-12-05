package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymActivity;
import com.gymdaus.core.entity.GymActivitySchedule;
import com.gymdaus.core.mapper.MapperGymActivity;
import com.gymdaus.core.model.GymActivityModel;
import com.gymdaus.core.repository.GymActivityRepository;
import com.gymdaus.core.repository.GymActivityScheduleRepository;
import com.gymdaus.core.service.GymActivityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        for (GymActivity gymActivity : gymActivityRepository.findAllByGymIdOrderByRegistrationDateAsc(gymId)) {
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
        gymActivityRepository.deleteById(id);
        for (GymActivitySchedule gymActivitySchedule : gymActivityScheduleRepository.findAllByGymAddressIdAndActivityIdOrderByPositionAsc(gymActivityModel.getGymAddressModel().getId(), gymActivityModel.getActivityModel().getId())) {
            gymActivityScheduleRepository.delete(gymActivitySchedule);
        }
    }
}
