package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymPhoto;
import com.gymdaus.core.mapper.MapperGymPhoto;
import com.gymdaus.core.model.GymPhotoModel;
import com.gymdaus.core.repository.GymPhotoRepository;
import com.gymdaus.core.service.GymPhotoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class GymPhotoServiceImpl implements GymPhotoService {

    @Autowired
    private GymPhotoRepository gymPhotoRepository;

    @Autowired
    private MapperGymPhoto mapperGymPhoto;

    @Override
    public List<GymPhotoModel> findAll() {
        List<GymPhotoModel> gymPhotoModelList = new ArrayList<>();
        for (GymPhoto gymPhoto : gymPhotoRepository.findAll()) {
            gymPhotoModelList.add(mapperGymPhoto.entity2Model(gymPhoto));
        }
        return gymPhotoModelList;
    }

    @Override
    public GymPhotoModel findById(Long id) {
        try {
            return mapperGymPhoto.entity2Model(gymPhotoRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymPhotoModel();
        }
    }

    @Override
    public void add(GymPhotoModel gymPhotoModel) {
        gymPhotoRepository.save(mapperGymPhoto.model2Entity(gymPhotoModel));
    }

    @Override
    public void update(GymPhotoModel gymPhotoModel) {
        gymPhotoRepository.save(mapperGymPhoto.model2Entity(gymPhotoModel));
    }

    @Override
    public void delete(Long id) {
        gymPhotoRepository.deleteById(id);
    }
}
