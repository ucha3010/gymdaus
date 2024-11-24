package com.gymdaus.core.service.impl;


import com.gymdaus.core.model.EnrollmentModel;
import com.gymdaus.core.model.GymPhotoModel;
import com.gymdaus.core.model.MainUserModel;
import com.gymdaus.core.service.*;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class MainServiceImpl implements MainService {

    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private GymPhotoService gymPhotoService;
    @Autowired
    private GymService gymService;
    @Autowired
    private Menu1Service menu1Service;
    @Autowired
    private UserService userService;

    @Override
    public List<MainUserModel> findByUsername(String username) {

        MainUserModel mainUserModel;
        List<EnrollmentModel> enrollmentModelList = enrollmentService.findByUsername(username);
        List<MainUserModel> mainUserModelList = new ArrayList<>();
        if (!enrollmentModelList.isEmpty()) {
            for (EnrollmentModel enrollmentModel : enrollmentModelList) {
                mainUserModel = new MainUserModel();
                mainUserModel.setId(enrollmentModel.getId());
                mainUserModel.setName(enrollmentModel.getUserEnrollmentName());
                mainUserModel.setLastname(enrollmentModel.getUserEnrollmentLastname());
                mainUserModel.setSecondLastname(enrollmentModel.getUserEnrollmentSecondLastname());
                mainUserModel.setRegistrationDate(enrollmentModel.getEnrollmentDate());
                mainUserModel.setTournamentName(enrollmentModel.getName());
                mainUserModel.setTournamentDate(enrollmentModel.getTournamentDate());
                mainUserModel.setOwnRegistration(enrollmentModel.isOwn());
                mainUserModelList.add(mainUserModel);
            }
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), mainUserModelList, getClass());
        return mainUserModelList;
    }

    @Override
    public void deleteEnrollment(Long enrollmentId) {
        enrollmentService.delete(enrollmentId);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), enrollmentId, getClass());
    }

    @Override
    public List<String> getPhotoList() {
        List<String> fileNames = new ArrayList<>();
        for (Long id : gymService.getGymIdEnabled()) {
            GymPhotoModel gymPhotoModel = gymPhotoService.findByGymIdAndMainPhotoTrue(id);
            if (gymPhotoModel != null) {
                fileNames.add(id + "/" + gymPhotoModel.getFilename());
            }
        }
        return fileNames;
    }
}
