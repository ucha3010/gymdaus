package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.UserDocumentManager;
import com.gymdaus.core.mapper.MapperUserDocumentManager;
import com.gymdaus.core.model.UserDocumentManagerModel;
import com.gymdaus.core.repository.UserDocumentManagerRepository;
import com.gymdaus.core.service.UserDocumentManagerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class UserDocumentManagerServiceImpl implements UserDocumentManagerService {

    @Autowired
    private UserDocumentManagerRepository userDocumentManagerRepository;

    @Autowired
    private MapperUserDocumentManager mapperUserDocumentManager;

    @Override
    public List<UserDocumentManagerModel> findAll() {
        List<UserDocumentManagerModel> userDocumentManagerModelList = new ArrayList<>();
        for (UserDocumentManager userDocumentManager : userDocumentManagerRepository.findAll()) {
            userDocumentManagerModelList.add(mapperUserDocumentManager.entity2Model(userDocumentManager));
        }
        return userDocumentManagerModelList;
    }

    @Override
    public UserDocumentManagerModel findById(Long id) {
        try {
            return mapperUserDocumentManager.entity2Model(userDocumentManagerRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new UserDocumentManagerModel();
        }
    }

    @Override
    public void add(UserDocumentManagerModel userDocumentManagerModel) {
        userDocumentManagerRepository.save(mapperUserDocumentManager.model2Entity(userDocumentManagerModel));
    }

    @Override
    public void update(UserDocumentManagerModel userDocumentManagerModel) {
        userDocumentManagerRepository.save(mapperUserDocumentManager.model2Entity(userDocumentManagerModel));
    }

    @Override
    public void delete(Long id) {
        userDocumentManagerRepository.deleteById(id);
    }
}
