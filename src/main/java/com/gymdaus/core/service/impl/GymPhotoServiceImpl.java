package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.GymPhoto;
import com.gymdaus.core.mapper.MapperGymPhoto;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.GymPhotoModel;
import com.gymdaus.core.repository.GymPhotoRepository;
import com.gymdaus.core.service.GymPhotoService;
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
    public boolean delete(Long id) {
        GymPhotoModel gymPhotoModel = findById(id);
        gymPhotoRepository.deleteById(id);
        String ruta = "files" + File.separator + "photos" + File.separator + "gym" + File.separator + gymPhotoModel.getGymModel().getId()
                + File.separator + gymPhotoModel.getFilename();
        File file = new File(Utils.getAbsolutePath() + ruta);
        return file.delete();
    }

    @Override
    public List<GymPhotoModel> findByGymId(Long gymId) {
        List<GymPhotoModel> gymPhotoModelList = new ArrayList<>();
        for (GymPhoto gymPhoto : gymPhotoRepository.findByGymIdOrderByMainPhotoDesc(gymId)) {
            gymPhotoModelList.add(mapperGymPhoto.entity2Model(gymPhoto));
        }
        return gymPhotoModelList;
    }

    @Override
    public GymPhotoModel findByGymIdAndMainPhotoTrue(Long gymId) {
        return mapperGymPhoto.entity2Model(gymPhotoRepository.findByGymIdAndMainPhotoTrue(gymId));
    }

    @Override
    public boolean addPhoto(Long gymId, MultipartFile file) {
        boolean answer;
        try {
            if (Utils.isNullOrEmpty(Utils.getClearFilename(file))) {
                return false;
            }
            String ruta = "files" + File.separator + "photos" + File.separator + "gym" + File.separator + gymId;
            File folder = new File(Utils.getAbsolutePath() + ruta);
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    LoggerMapper.methodIn(Level.ERROR, Utils.getMethodName(), "Problems making folder ".concat(folder.getName()), this.getClass());
                }
            }
            GymPhotoModel gymPhotoModel = new GymPhotoModel();
            gymPhotoModel.setCreationDate(new Date());
            gymPhotoModel.setExtension(Utils.getFileExtension(file));
            gymPhotoModel.setFilename(Utils.getClearFilename(file));
            gymPhotoModel.setPath(ruta);
            GymModel gymModel = new GymModel();
            gymModel.setId(gymId);
            gymPhotoModel.setGymModel(gymModel);
            if (findByGymId(gymId).isEmpty()) {
                gymPhotoModel.setMainPhoto(Boolean.TRUE);
            } else {
                gymPhotoModel.setMainPhoto(Boolean.FALSE);
            }
            answer = Utils.uploadFile(file, gymPhotoModel.getPath());
            add(gymPhotoModel);
        } catch (Exception e) {
            answer = false;
        }
        return answer;
    }

    @Override
    public void doMain(Long id) {
        GymPhotoModel gymPhotoModel = findById(id);
        GymPhotoModel gymPhotoModelOld = mapperGymPhoto.entity2Model(
                gymPhotoRepository.findByGymIdAndMainPhotoTrue(gymPhotoModel.getGymModel().getId()));
        gymPhotoModel.setMainPhoto(Boolean.TRUE);
        update(gymPhotoModel);
        gymPhotoModelOld.setMainPhoto(Boolean.FALSE);
        update(gymPhotoModelOld);
    }
}
