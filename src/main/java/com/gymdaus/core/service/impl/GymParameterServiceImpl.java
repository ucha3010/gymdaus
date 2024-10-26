package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymParameter;
import com.gymdaus.core.mapper.MapperGymParameter;
import com.gymdaus.core.model.GymParameterModel;
import com.gymdaus.core.repository.GymParameterRepository;
import com.gymdaus.core.service.GymParameterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class GymParameterServiceImpl implements GymParameterService {

    @Autowired
    private GymParameterRepository gymParameterRepository;

    @Autowired
    private MapperGymParameter mapperGymParameter;

    @Override
    public List<GymParameterModel> get(Long gymId) {
        List<GymParameterModel> gymParameterModelList = new ArrayList<>();
        for (GymParameter gymParameter : gymParameterRepository.findAllByGymId(gymId)) {
            gymParameterModelList.add(mapperGymParameter.entity2Model(gymParameter));
        }
        return gymParameterModelList;
    }

    @Override
    public GymParameterModel get(Long gymId, String keyData) {
        try {
            return mapperGymParameter.entity2Model(gymParameterRepository.findByGymIdAndKeyData(gymId, keyData));
        } catch (EntityNotFoundException e) {
            return new GymParameterModel();
        }
    }

    @Override
    public GymParameterModel update(GymParameterModel gymParameterModel) {
        return mapperGymParameter.entity2Model(gymParameterRepository.save(mapperGymParameter.model2Entity(gymParameterModel)));
    }
}
