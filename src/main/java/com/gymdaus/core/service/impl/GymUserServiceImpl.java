package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymUser;
import com.gymdaus.core.mapper.MapperGymUser;
import com.gymdaus.core.model.GymUserModel;
import com.gymdaus.core.repository.GymUserRepository;
import com.gymdaus.core.service.GymUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class GymUserServiceImpl implements GymUserService {

    @Autowired
    private GymUserRepository gymUserRepository;

    @Autowired
    private MapperGymUser mapperGymUser;

    @Override
    public List<GymUserModel> findAll() {
        List<GymUserModel> gymUserModelList = new ArrayList<>();
        for (GymUser gymUser : gymUserRepository.findAll()) {
            gymUserModelList.add(mapperGymUser.entity2Model(gymUser));
        }
        return gymUserModelList;
    }

    @Override
    public GymUserModel findById(Long id) {
        try {
            return mapperGymUser.entity2Model(gymUserRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymUserModel();
        }
    }

    @Override
    public void add(GymUserModel gymUserModel) {
        gymUserRepository.save(mapperGymUser.model2Entity(gymUserModel));
    }

    @Override
    public void update(GymUserModel gymUserModel) {
        gymUserRepository.save(mapperGymUser.model2Entity(gymUserModel));
    }

    @Override
    public void delete(Long id) {
        gymUserRepository.deleteById(id);
    }
}
