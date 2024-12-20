package com.gymdaus.core.controller;

import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.GymPoomsaeModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.GymPoomsaeService;
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
@RequestMapping("/gymPoomsae")
public class GymPoomsaeController {

    @Autowired
    private GymPoomsaeService gymPoomsaeService;
    @Autowired
    private GymService gymService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UtilService utilService;

    @GetMapping("/poomsaes/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView poomsaes(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gymPoomsae/poomsaes/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, false, "/gymPoomsae/poomsaes/");
        modelAndView.setViewName("gym/admin-poomsae");
        modelAndView.addObject("gymPoomsaeModel", new GymPoomsaeModel());
        modelAndView.addObject("gymPoomsaeModelList", gymPoomsaeService.findAllByGymId(gymId));
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/changePoomsae/{gymId}/{oldIndex}/{newIndex}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView changePoomsae(ModelAndView modelAndView, @PathVariable Long gymId, @PathVariable int oldIndex, @PathVariable int newIndex) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gymPoomsae/changePoomsae/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, false, "/gymPoomsae/changePoomsae/");
        gymPoomsaeService.dragOfPosition(gymId, oldIndex, newIndex);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return poomsaes(modelAndView, gymId);
    }

    @PostMapping("/addPoomsae/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addPoomsae(ModelAndView modelAndView, @PathVariable Long gymId, @ModelAttribute("gymPoomsaeModel") GymPoomsaeModel gymPoomsaeModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gymPoomsae/addPoomsae/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, false, "/gymPoomsae/addPoomsae/");
        GymModel gymModel = new GymModel();
        gymModel.setId(gymId);
        gymPoomsaeModel.setGymModel(gymModel);
        gymPoomsaeModel.setPosition(gymPoomsaeService.findMaxPosition(gymId) + 1);
        gymPoomsaeService.add(gymPoomsaeModel);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return poomsaes(modelAndView, gymId);
    }

    @GetMapping("/removePoomsae/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView removePoomsae(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/gymPoomsae/removePoomsae/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymPoomsaeModel gymPoomsaeModel = gymPoomsaeService.findById(id);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymPoomsaeModel.getGymModel().getId(), false, "/gymPoomsae/removePoomsae/");
        try {
            gymPoomsaeService.delete(id);
        } catch (RemoveException re) {
            modelAndView.addObject("removeProblem", re.getMessage());
            LoggerMapper.log(Level.ERROR, Utils.getMethodName(), re.getMessage(), this.getClass());
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return poomsaes(modelAndView, gymPoomsaeModel.getGymModel().getId());
    }

}
