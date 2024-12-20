package com.gymdaus.core.controller;

import com.gymdaus.core.model.EnrollmentModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.EnrollmentService;
import com.gymdaus.core.service.GymService;
import com.gymdaus.core.service.SecurityService;
import com.gymdaus.core.service.UtilService;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/enrollment")
public class EnrollmentController {

    @Autowired
    private GymService gymService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UtilService utilService;

    @GetMapping("/enrollments/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView gymActivities(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/enrollment/gym-activities/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, false, "/enrollment/gym-activities/" + gymId);
        modelAndView.setViewName("gym/enrollments");
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymId));
        List<EnrollmentModel> enrollmentModelList = enrollmentService.findByGymId(gymId);
        modelAndView.addObject("enrollmentModelList", enrollmentModelList);
        Set<String> sectionList = enrollmentModelList.stream()
                .map(EnrollmentModel::getGymActivityModel)
                .map(obj -> obj.getGymAddressModel().getName())
                .collect(Collectors.toSet());
        modelAndView.addObject("sectionList", sectionList);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/enrollment/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView gymEnrollment(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/enrollment/enrollment/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        EnrollmentModel enrollmentModel = enrollmentService.findById(id);
        securityService.enabledAdministrationGymUser(user.getUsername(), enrollmentModel.getGymModel().getId(), false, "/enrollment/enrollment/" + id);
        modelAndView.setViewName("gym/enrollment-detail");
        modelAndView.addObject("enrollment", enrollmentModel);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

}
