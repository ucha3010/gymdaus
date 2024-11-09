package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymParameter;
import com.gymdaus.core.mapper.MapperGymParameter;
import com.gymdaus.core.model.GymParameterModel;
import com.gymdaus.core.repository.GymParameterRepository;
import com.gymdaus.core.service.GymParameterService;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, String> getStartWith(Long gymId, String keyDataStart) {
        Map<String, String> keyValueMap = new HashMap<>();
        for (GymParameter gymParameter : gymParameterRepository.findAllByGymId(gymId)) {
            if (gymParameter.getKeyData().startsWith(keyDataStart)) {
                keyValueMap.put(gymParameter.getKeyData(),gymParameter.getValue());
            }
        }
        return keyValueMap;
    }

    @Override
    public GymParameterModel get(Long gymId, String keyData) {
        return mapperGymParameter.entity2Model(gymParameterRepository.findByGymIdAndKeyData(gymId, keyData));
    }

    @Override
    public GymParameterModel update(GymParameterModel gymParameterModel) {
        return mapperGymParameter.entity2Model(gymParameterRepository.save(mapperGymParameter.model2Entity(gymParameterModel)));
    }

    @Override
    public boolean comparePassword(Long gymId, String oldPassword) {
        GymParameter gymParameter = gymParameterRepository.findByGymIdAndKeyData(gymId, Constants.EMAIL_PASSWORD);
        return gymParameter != null && !Utils.isNullOrEmpty(oldPassword) && oldPassword.equals(gymParameter.getValue());
    }
}
