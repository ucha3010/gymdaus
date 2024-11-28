package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymDocumentManager;
import com.gymdaus.core.mapper.MapperGymDocumentManager;
import com.gymdaus.core.model.GymDocumentManagerModel;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.repository.GymDocumentManagerRepository;
import com.gymdaus.core.service.GymDocumentManagerService;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
    public boolean delete(Long id) {
        GymDocumentManagerModel gymDocumentManagerModel = findById(id);
        gymDocumentManagerRepository.deleteById(id);
        String ruta = gymDocumentManagerModel.getPath() + File.separator + gymDocumentManagerModel.getFilename();
        File file = new File(Utils.getAbsolutePath() + ruta);
        return file.delete();
    }

    @Override
    public List<GymDocumentManagerModel> findByGymId(Long gymId) {
        List<GymDocumentManagerModel> gymDocumentManagerModelList = new ArrayList<>();
        for (GymDocumentManager gymDocumentManager : gymDocumentManagerRepository.findAllByGymIdOrderByCreationDateDesc(gymId)) {
            gymDocumentManagerModelList.add(mapperGymDocumentManager.entity2Model(gymDocumentManager));
        }
        return gymDocumentManagerModelList;
    }

    @Override
    public boolean addDocument(UserModel user, MultipartFile file, String section, GymModel gymModel) {
        boolean answer;
        try {
            GymDocumentManagerModel gymDocumentManagerModel = fillObject(user, file, section, gymModel);
            answer = Utils.uploadFile(file, gymDocumentManagerModel.getPath());
            add(gymDocumentManagerModel);
        } catch (Exception e) {
            answer = false;
        }
        return answer;
    }


    private GymDocumentManagerModel fillObject(UserModel userModel, MultipartFile file, String section, GymModel gymModel) {

        String ruta = "files" + File.separator + "gyms" + File.separator + gymModel.getId();
        File folder = new File(Utils.getAbsolutePath() + ruta);
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                LoggerMapper.methodIn(Level.ERROR, Utils.getMethodName(), "Problems making folder ".concat(folder.getName()), this.getClass());
            }
        }
        GymDocumentManagerModel gymDocumentManagerModel = new GymDocumentManagerModel();
        gymDocumentManagerModel.setCreationDate(new Date());
        gymDocumentManagerModel.setExtension(Utils.getFileExtension(file));
        gymDocumentManagerModel.setFilename(Utils.getClearFilename(file));
        gymDocumentManagerModel.setPath(ruta);
        gymDocumentManagerModel.setGymName(gymModel.getName());
        gymDocumentManagerModel.setSigned(Boolean.FALSE);
        gymDocumentManagerModel.setSection(section);
        gymDocumentManagerModel.setGymModel(gymModel);

        return gymDocumentManagerModel;
    }
}
