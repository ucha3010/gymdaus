package com.gymdaus.core.controller;

import com.gymdaus.core.configuration.SessionData;
import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.ParticipatingEntityModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.GymService;
import com.gymdaus.core.service.ParticipatingEntityService;
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
@RequestMapping("/participatingEntity")
public class ParticipatingEntityController {

    @Autowired
    private ParticipatingEntityService participatingEntityService;
    @Autowired
    private GymService gymService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private SessionData sessionData;

    @GetMapping("/participants/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView participants(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/participatingEntity/participants/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, false, "/participatingEntity/participants/" + gymId);
        modelAndView.setViewName("gym/admin-participants");
        modelAndView.addObject("participatingEntityModel", new ParticipatingEntityModel());
        modelAndView.addObject("participatingEntityModelList", participatingEntityService.findAllByGymId(gymId));
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/changeParticipant/{gymId}/{oldIndex}/{newIndex}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView changeParticipant(ModelAndView modelAndView, @PathVariable Long gymId, @PathVariable int oldIndex, @PathVariable int newIndex) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/participatingEntity/changeParticipant/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, false, "/participatingEntity/changeParticipant/" + gymId);
        participatingEntityService.dragOfPosition(gymId, oldIndex, newIndex);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return participants(modelAndView, gymId);
    }

    @PostMapping("/addParticipant/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addParticipant(ModelAndView modelAndView, @PathVariable Long gymId, @ModelAttribute("participatingEntityModel") ParticipatingEntityModel participatingEntityModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/participatingEntity/addParticipant/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, false, "/participatingEntity/addParticipant/" + gymId);
        GymModel gymModel = new GymModel();
        gymModel.setId(gymId);
        participatingEntityModel.setGymModel(gymModel);
        participatingEntityModel.setPosition(participatingEntityService.findMaxPosition(gymId) + 1);
        participatingEntityService.add(participatingEntityModel);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return participants(modelAndView, gymId);
    }

    @GetMapping("/removeParticipant/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView removeParticipant(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/participatingEntity/removeParticipant/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        ParticipatingEntityModel participatingEntityModel = participatingEntityService.findById(id);
        securityService.enabledAdministrationGymUser(user.getUsername(), participatingEntityModel.getGymModel().getId(), false, "/participatingEntity/removeParticipant/" + id);
        try {
            participatingEntityService.delete(id);
        } catch (RemoveException re) {
            modelAndView.addObject("removeProblem", "removeProblem");
            LoggerMapper.log(Level.ERROR, Utils.getMethodName(), re.getMessage(), this.getClass());
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return participants(modelAndView, participatingEntityModel.getGymModel().getId());
    }

}
