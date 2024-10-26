package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymDocumentManager;
import com.gymdaus.core.mapper.MapperGymDocumentManager;
import com.gymdaus.core.model.GymDocumentManagerModel;
import com.gymdaus.core.repository.GymDocumentManagerRepository;
import com.gymdaus.core.service.GymDocumentManagerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class GymDocumentManagerServiceImpl implements GymDocumentManagerService {

    @Autowired
    private GymDocumentManagerRepository gymDocumentManagerRepository;

    @Autowired
    private MapperGymDocumentManager mapperGymDocumentManager;

    @Override
    public List<GymDocumentManagerModel> findAll() {
        List<GymDocumentManagerModel> gymDocumentManagerModelList = new ArrayList<>();
        for (GymDocumentManager gymDocumentManager : gymDocumentManagerRepository.findAll()) {
            gymDocumentManagerModelList.add(mapperGymDocumentManager.entity2Model(gymDocumentManager));
        }
        return gymDocumentManagerModelList;
    }

    @Override
    public GymDocumentManagerModel findById(Long id) {
        try {
            return mapperGymDocumentManager.entity2Model(gymDocumentManagerRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new GymDocumentManagerModel();
        }
    }

    @Override
    public void add(GymDocumentManagerModel gymDocumentManagerModel) {
        gymDocumentManagerRepository.save(mapperGymDocumentManager.model2Entity(gymDocumentManagerModel));
    }

    @Override
    public void update(GymDocumentManagerModel gymDocumentManagerModel) {
        gymDocumentManagerRepository.save(mapperGymDocumentManager.model2Entity(gymDocumentManagerModel));
    }

    @Override
    public void delete(Long id) {
        gymDocumentManagerRepository.deleteById(id);
    }
}
