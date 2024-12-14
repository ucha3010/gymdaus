package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymCategoryGymBelt;
import com.gymdaus.core.mapper.MapperGymCategoryGymBelt;
import com.gymdaus.core.model.GymCategoryGymBeltModel;
import com.gymdaus.core.repository.GymCategoryGymBeltRepository;
import com.gymdaus.core.service.GymCategoryGymBeltService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service()
public class GymCategoryGymBeltServiceImpl implements GymCategoryGymBeltService {

    @Autowired
    private GymCategoryGymBeltRepository gymCategoryGymBeltRepository;

    @Autowired
    private MapperGymCategoryGymBelt mapperGymmapperGymCategoryGymBelt;
    @Autowired
    private UserService userService;

    @Override
    public List<GymCategoryGymBeltModel> findAll() {
        List<GymCategoryGymBeltModel> gymCategoryGymBeltModelList = new ArrayList<>();
        for (GymCategoryGymBelt gymCategoryGymBelt : gymCategoryGymBeltRepository.findAll()) {
            gymCategoryGymBeltModelList.add(mapperGymmapperGymCategoryGymBelt.entity2Model(gymCategoryGymBelt));
        }
        return gymCategoryGymBeltModelList;
    }

    @Override
    public GymCategoryGymBeltModel findById(Long id) {
        try {
            return mapperGymmapperGymCategoryGymBelt.entity2Model(gymCategoryGymBeltRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymCategoryGymBeltModel();
        }
    }

    @Override
    public void add(GymCategoryGymBeltModel gymCategoryGymBeltModel) {
        gymCategoryGymBeltModel.setRegistrationDate(new Date());
        gymCategoryGymBeltModel.setRegistrationUser(userService.getLoggedUser().getUsername());
        gymCategoryGymBeltRepository.save(mapperGymmapperGymCategoryGymBelt.model2Entity(gymCategoryGymBeltModel));
    }

    @Override
    public void update(GymCategoryGymBeltModel gymCategoryGymBeltModel) {
        gymCategoryGymBeltModel.setRegistrationDate(new Date());
        gymCategoryGymBeltModel.setRegistrationUser(userService.getLoggedUser().getUsername());
        gymCategoryGymBeltRepository.save(mapperGymmapperGymCategoryGymBelt.model2Entity(gymCategoryGymBeltModel));
    }

    @Override
    public void delete(Long id) {
        gymCategoryGymBeltRepository.deleteById(id);
    }

    @Override
    public void deleteByGymCategoryId(Long gymCategoryId) {
        List<GymCategoryGymBelt> gymCategoryGymBeltList = gymCategoryGymBeltRepository.findByGymCategoryId(gymCategoryId);
        if (gymCategoryGymBeltList != null) {
            for (GymCategoryGymBelt gymCategoryGymBelt : gymCategoryGymBeltList) {
                gymCategoryGymBeltRepository.deleteById(gymCategoryGymBelt.getId());
            }
        }
    }

    @Override
    public List<GymCategoryGymBeltModel> findByGymCategory(Long gymCategoryId) {
        List<GymCategoryGymBeltModel> gymCategoryGymBeltModelList = new ArrayList<>();
        for (GymCategoryGymBelt gymCategoryGymBelt : gymCategoryGymBeltRepository.findByGymCategoryId(gymCategoryId)) {
            gymCategoryGymBeltModelList.add(mapperGymmapperGymCategoryGymBelt.entity2Model(gymCategoryGymBelt));
        }
        return gymCategoryGymBeltModelList;
    }

    @Override
    public List<GymCategoryGymBeltModel> findByGymBelt(Long gymBeltId) {
        List<GymCategoryGymBeltModel> gymCategoryGymBeltModelList = new ArrayList<>();
        for (GymCategoryGymBelt gymCategoryGymBelt : gymCategoryGymBeltRepository.findByGymBeltId(gymBeltId)) {
            gymCategoryGymBeltModelList.add(mapperGymmapperGymCategoryGymBelt.entity2Model(gymCategoryGymBelt));
        }
        return gymCategoryGymBeltModelList;
    }
}
