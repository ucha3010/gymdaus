package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.ManagerParameter;
import com.gymdaus.core.mapper.MapperManagerParameter;
import com.gymdaus.core.model.ManagerParameterModel;
import com.gymdaus.core.repository.ManagerParameterRepository;
import com.gymdaus.core.service.ManagerParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class ManagerParameterServiceImpl implements ManagerParameterService {

    @Autowired
    private ManagerParameterRepository managerParameterRepository;

    @Autowired
    private MapperManagerParameter mapperManagerParameter;

    @Override
    public ManagerParameterModel get() {
        List<ManagerParameter> managerParameterList = managerParameterRepository.findAll();
        ManagerParameterModel managerParameterModel;
        if (managerParameterList.isEmpty()) {
            managerParameterModel = new ManagerParameterModel();
        } else {
            managerParameterModel = mapperManagerParameter.entity2Model(managerParameterList.get(0));
        }
        return managerParameterModel;
    }

    @Override
    public ManagerParameterModel update(ManagerParameterModel managerParameterModel) {
        return mapperManagerParameter.entity2Model(managerParameterRepository.save(mapperManagerParameter.model2Entity(managerParameterModel)));
    }
}
