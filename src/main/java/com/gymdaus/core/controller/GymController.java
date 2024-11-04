package com.gymdaus.core.controller;

import com.gymdaus.core.configuration.SessionData;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.GymUserModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.GymService;
import com.gymdaus.core.service.GymUserService;
import com.gymdaus.core.service.SecurityService;
import com.gymdaus.core.service.UtilService;
import com.gymdaus.core.service.impl.UserService;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/gym")
public class GymController {

    @Autowired
    private GymService gymService;
    @Autowired
    private GymUserService gymUserService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionData sessionData;

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView gymMainPage(ModelAndView modelAndView) {
        securityService.userAccessValidation("/gym/");
        UserModel user = utilService.basicDataCharge(modelAndView);
        List<GymUserModel> gymUserModelList = gymUserService.findByUsername(user.getUsername());
        if (gymUserModelList.isEmpty()) {
            modelAndView.setViewName("gym/gym-admin");
            modelAndView.addObject("backButton", "/");
            modelAndView.addObject("gymModel", null);
        } else if (gymUserModelList.size() == 1) {
            modelAndView.setViewName("gym/gym-admin");
            modelAndView.addObject("backButton", "/");
            modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymUserModelList.get(0).getGymModel().getId()));
        } else {
            List<GymModel> gymModelList = new ArrayList<>();
            GymModel gymModel;
            for (GymUserModel gymUserModel : gymUserModelList) {
                gymModel = gymService.findByIdEnabled(gymUserModel.getGymModel().getId());
                if (gymModel != null) {
                    gymModelList.add(gymModel);
                }
            }
            if (gymModelList.isEmpty()) {
                modelAndView.setViewName("gym/gym-admin");
                modelAndView.addObject("backButton", "/");
                modelAndView.addObject("gymModel", null);
            } else if (gymModelList.size() == 1) {
                modelAndView.setViewName("gym/gym-admin");
                modelAndView.addObject("backButton", "/");
                modelAndView.addObject("gymModel", gymModelList.get(0));
            } else {
                modelAndView.setViewName("gym/select-gym");
                modelAndView.addObject("gymModelList", gymModelList);
            }
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/gym-admin/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView gymAdmin(ModelAndView modelAndView, @PathVariable Long id) {
        securityService.userAccessValidation("/gym/gym-admin/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        List<GymUserModel> gymUserModelList = gymUserService.findByGymId(id);
        boolean validAccess = false;
        for (GymUserModel gymUserModel : gymUserModelList) {
            if(gymUserModel.getGymModel().getId().equals(id)) {
                validAccess = true;
                break;
            }
        }
        if (!validAccess) {
            throw new AccessDeniedException("/gym/gym-admin/" + id);
        } else {
            modelAndView.setViewName("gym/gym-admin");
            modelAndView.addObject("backButton", "/gym/");
            modelAndView.addObject("gymModel", gymService.findByIdEnabled(id));
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

}
