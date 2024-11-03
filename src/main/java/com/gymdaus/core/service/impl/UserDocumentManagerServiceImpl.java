package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.UserDocumentManager;
import com.gymdaus.core.mapper.MapperUserDocumentManager;
import com.gymdaus.core.model.UserDocumentManagerModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.repository.UserDocumentManagerRepository;
import com.gymdaus.core.service.UserDocumentManagerService;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public UserDocumentManagerModel findByUsernameEnabled(String username) {
        return mapperUserDocumentManager.entity2Model(userDocumentManagerRepository.findByUsernameAndEnabledTrue(username));
    }

    @Override
    public boolean addPhoto(UserModel userModel, MultipartFile file) {
        boolean answer = true;
        try {
            UserDocumentManagerModel userDocumentManagerModel = findByUsernameEnabled(userModel.getUsername());
            if (userDocumentManagerModel != null) {
                userDocumentManagerModel.setEnabled(Boolean.FALSE);
                update(userDocumentManagerModel);
            }
            userDocumentManagerModel = fillObject(userModel, file, "userPhotos");
            if(answer) {
                answer = Utils.uploadFile(file, userDocumentManagerModel.getPath());
            }
            add(userDocumentManagerModel);
        } catch (Exception e) {
            answer = false;
        }
        return answer;
    }



    private UserDocumentManagerModel fillObject(UserModel userModel, MultipartFile file, @NotNull String section) {

        String ruta = "src" + File.separator + "main" + File.separator + "resources" + File.separator
                + "static" + File.separator + "files" + File.separator + section;
        File folder = new File(Utils.getAbsolutePath() + ruta);
        if (!folder.exists()) {
            if(!folder.mkdirs()) {
                LoggerMapper.methodIn(Level.ERROR, Utils.getMethodName(), "Problems making folder ".concat(folder.getName()), this.getClass());
            }
        }
        UserDocumentManagerModel userDocumentManagerModel = new UserDocumentManagerModel();
        userDocumentManagerModel.setEnabled(true);
        userDocumentManagerModel.setFilename(Utils.getClearFilename(file));
        userDocumentManagerModel.setPath(ruta);
        userDocumentManagerModel.setExtension(Utils.getFileExtension(file));
        userDocumentManagerModel.setCreationDate(new Date());
        userDocumentManagerModel.setUsername(userModel.getUsername());

        return userDocumentManagerModel;
    }

}