package com.gymdaus.core.controller;

import com.gymdaus.core.configuration.SessionData;
import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.model.GymBeltModel;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.GymBeltService;
import com.gymdaus.core.service.GymService;
import com.gymdaus.core.service.SecurityService;
import com.gymdaus.core.service.UtilService;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/gymBelt")
public class GymBeltController {

    @Autowired
    private GymBeltService gymBeltService;
    @Autowired
    private GymService gymService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private SessionData sessionData;

    @GetMapping("/belts/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView belts(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gymBelt/belts/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, false, "/gymBelt/belts/");
        modelAndView.setViewName("gym/admin-belt");
        modelAndView.addObject("gymBeltModel", new GymBeltModel());
        modelAndView.addObject("gymBeltModelList", gymBeltService.findAllByGymId(gymId));
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/changeBelt/{gymId}/{oldIndex}/{newIndex}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView changeBelt(ModelAndView modelAndView, @PathVariable Long gymId, @PathVariable int oldIndex, @PathVariable int newIndex) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gymBelt/changeBelt/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, false, "/gymBelt/changeBelt/");
        gymBeltService.dragOfPosition(gymId, oldIndex, newIndex);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return belts(modelAndView, gymId);
    }

    @PostMapping("/addBelt/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addBelt(ModelAndView modelAndView, @PathVariable Long gymId, @ModelAttribute("gymBeltModel") GymBeltModel gymBeltModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gymBelt/addBelt/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, false, "/gymBelt/addBelt/");
        GymModel gymModel = new GymModel();
        gymModel.setId(gymId);
        gymBeltModel.setGymModel(gymModel);
        gymBeltModel.setPosition(gymBeltService.findMaxPosition(gymId) + 1);
        gymBeltService.add(gymBeltModel);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return belts(modelAndView, gymId);
    }

    @GetMapping("/removeBelt/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView removeBelt(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/gymBelt/removeBelt/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymBeltModel gymBeltModel = gymBeltService.findById(id);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymBeltModel.getGymModel().getId(), false, "/gymBelt/removeBelt/");
        try {
            gymBeltService.delete(id);
        } catch (RemoveException re) {
            modelAndView.addObject("removeProblem", "removeProblem");
            LoggerMapper.log(Level.ERROR, Utils.getMethodName(), re.getMessage(), this.getClass());
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return belts(modelAndView, gymBeltModel.getGymModel().getId());
    }

}
