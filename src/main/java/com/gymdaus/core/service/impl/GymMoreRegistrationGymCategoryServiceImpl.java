package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymMoreRegistrationGymCategory;
import com.gymdaus.core.mapper.MapperGymMoreRegistrationGymCategory;
import com.gymdaus.core.model.GymMoreRegistrationGymCategoryModel;
import com.gymdaus.core.repository.GymMoreRegistrationGymCategoryRepository;
import com.gymdaus.core.service.GymMoreRegistrationGymCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class GymMoreRegistrationGymCategoryServiceImpl implements GymMoreRegistrationGymCategoryService {

    @Autowired
    private GymMoreRegistrationGymCategoryRepository gymMoreRegistrationGymCategoryRepository;

    @Autowired
    private MapperGymMoreRegistrationGymCategory mapperGymMoreRegistrationGymCategory;

    @Override
    public List<GymMoreRegistrationGymCategoryModel> findAll() {
        List<GymMoreRegistrationGymCategoryModel> gymMoreRegistrationGymCategoryModelList = new ArrayList<>();
        for (GymMoreRegistrationGymCategory gymMoreRegistrationGymCategory : gymMoreRegistrationGymCategoryRepository.findAll()) {
            gymMoreRegistrationGymCategoryModelList.add(mapperGymMoreRegistrationGymCategory.entity2Model(gymMoreRegistrationGymCategory));
        }
        return gymMoreRegistrationGymCategoryModelList;
    }

    @Override
    public GymMoreRegistrationGymCategoryModel findById(Long id) {
        return mapperGymMoreRegistrationGymCategory.entity2Model(gymMoreRegistrationGymCategoryRepository.findById(id).orElse(null));
    }

    @Override
    public void add(GymMoreRegistrationGymCategoryModel gymMoreRegistrationGymCategoryModel) {
        gymMoreRegistrationGymCategoryRepository.save(mapperGymMoreRegistrationGymCategory.model2Entity(gymMoreRegistrationGymCategoryModel));
    }

    @Override
    public void update(GymMoreRegistrationGymCategoryModel gymMoreRegistrationGymCategoryModel) {
        gymMoreRegistrationGymCategoryRepository.save(mapperGymMoreRegistrationGymCategory.model2Entity(gymMoreRegistrationGymCategoryModel));
    }

    @Override
    public void delete(Long id) {
        gymMoreRegistrationGymCategoryRepository.deleteById(id);
    }

    @Override
    public List<GymMoreRegistrationGymCategoryModel> findByGymMoreRegistration(Long gymMoreRegistrationId) {
        List<GymMoreRegistrationGymCategoryModel> gymMoreRegistrationGymCategoryModelList = new ArrayList<>();
        for (GymMoreRegistrationGymCategory gymMoreRegistrationGymCategory : gymMoreRegistrationGymCategoryRepository.findByGymMoreRegistrationId(gymMoreRegistrationId)) {
            gymMoreRegistrationGymCategoryModelList.add(mapperGymMoreRegistrationGymCategory.entity2Model(gymMoreRegistrationGymCategory));
        }
        return gymMoreRegistrationGymCategoryModelList;
    }

    @Override
    public List<GymMoreRegistrationGymCategoryModel> findByGymCategory(Long gymCategoryId) {
        List<GymMoreRegistrationGymCategoryModel> gymMoreRegistrationGymCategoryModelList = new ArrayList<>();
        for (GymMoreRegistrationGymCategory gymMoreRegistrationGymCategory : gymMoreRegistrationGymCategoryRepository.findByGymCategoryId(gymCategoryId)) {
            gymMoreRegistrationGymCategoryModelList.add(mapperGymMoreRegistrationGymCategory.entity2Model(gymMoreRegistrationGymCategory));
        }
        return gymMoreRegistrationGymCategoryModelList;
    }

    @Override
    public GymMoreRegistrationGymCategoryModel findByGymMoreRegistrationAndGymCategory(Long gymMoreRegistrationId, Long gymCategoryId) {
        return mapperGymMoreRegistrationGymCategory.entity2Model(gymMoreRegistrationGymCategoryRepository.findByGymMoreRegistrationIdAndGymCategoryId(gymMoreRegistrationId, gymCategoryId));
    }
}
