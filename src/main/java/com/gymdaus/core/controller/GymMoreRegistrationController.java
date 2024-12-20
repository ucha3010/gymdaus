package com.gymdaus.core.controller;

import com.gymdaus.core.model.GymMoreRegistrationModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.*;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/gymMoreRegistration")
public class GymMoreRegistrationController {

    @Autowired
    private GymMoreRegistrationService gymMoreRegistrationService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private MoreRegistrationService moreRegistrationService;
    @Autowired
    private GymMoreRegistrationBeltService gymMoreRegistrationBeltService;
    @Autowired
    private GymMoreRegistrationDateService gymMoreRegistrationDateService;
    @Autowired
    private GymMoreRegistrationGymCategoryService gymMoreRegistrationGymCategoryService;
    @Autowired
    private GymMoreRegistrationParticipatingEntityService gymMoreRegistrationParticipatingEntityService;
    @Autowired
    private GymService gymService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UtilService utilService;

    @GetMapping("/more-registrations/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView moreRegistrations(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/activities/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, true, "/gymMoreRegistration/more-registrations/" + gymId);
        modelAndView.setViewName("gym/more-registrations");
        modelAndView.addObject("gymMoreRegistrationModel", new GymMoreRegistrationModel());
        modelAndView.addObject("gymMoreRegistrationModelList", gymMoreRegistrationService.findAllByGymId(gymId));
        modelAndView.addObject("moreRegistrationModelList", moreRegistrationService.findAllEnabled());
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/more-registration")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addMoreRegistrationSchedule(ModelAndView modelAndView, @ModelAttribute("gymMoreRegistrationModel") GymMoreRegistrationModel gymMoreRegistrationModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymMoreRegistrationModel, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/more-registration");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationModel.getGymModel().getId(), true, "/gymMoreRegistration/more-registration");
        gymMoreRegistrationModel.setRegistrationUser(user.getUsername());
        gymMoreRegistrationModel = gymMoreRegistrationService.add(gymMoreRegistrationModel);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return moreRegistration(modelAndView, gymMoreRegistrationModel.getId());
    }

    @GetMapping("/more-registration/{gymMoreRegistrationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView moreRegistration(ModelAndView modelAndView, @PathVariable Long gymMoreRegistrationId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymMoreRegistrationId, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/more-registration/" + gymMoreRegistrationId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymMoreRegistrationModel gymMoreRegistrationModel = gymMoreRegistrationService.findById(gymMoreRegistrationId);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationModel.getGymModel().getId(), true, "/gymMoreRegistration/more-registration/" + gymMoreRegistrationId);
        modelAndView.setViewName("gym/more-registration");
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymMoreRegistrationModel.getGymModel().getId()));
        modelAndView.addObject("gymMoreRegistrationModel", gymMoreRegistrationModel);
        modelAndView.addObject("gymMoreRegistrationBeltModelList", gymMoreRegistrationBeltService.findAllByGymMoreRegistration(gymMoreRegistrationId));
        modelAndView.addObject("gymMoreRegistrationParticipatingEntityModelList", gymMoreRegistrationParticipatingEntityService.findAllByGymMoreRegistration(gymMoreRegistrationId));
        modelAndView.addObject("gymMoreRegistrationGymCategoryModelList", gymMoreRegistrationGymCategoryService.findByGymMoreRegistration(gymMoreRegistrationId));
        modelAndView.addObject("gymMoreRegistrationDateModelList", gymMoreRegistrationDateService.findAllByGymMoreRegistration(gymMoreRegistrationId));
        modelAndView.addObject("countryModelList", countryService.findAll());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }
/*
    @GetMapping("/remove-moreRegistration/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView removeMoreRegistration(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/remove-moreRegistration/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymMoreRegistrationModel gymMoreRegistrationModel = gymMoreRegistrationService.findById(id);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationModel.getGymAddressModel().getGymModel().getId(), true, "/gymMoreRegistration/remove-moreRegistration/" + id);
        gymMoreRegistrationService.delete(id);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return moreRegistrations(modelAndView, gymMoreRegistrationModel.getGymAddressModel().getGymModel().getId());
    }

    @GetMapping("/changeMoreRegistrationSchedule/{gymMoreRegistrationId}/{oldIndex}/{newIndex}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView changeMoreRegistrationSchedule(ModelAndView modelAndView, @PathVariable Long gymMoreRegistrationId, @PathVariable int oldIndex, @PathVariable int newIndex) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymMoreRegistrationId, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/changeMoreRegistrationSchedule/" + gymMoreRegistrationId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymMoreRegistrationModel gymMoreRegistrationModel = gymMoreRegistrationService.findById(gymMoreRegistrationId);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationModel.getGymModel().getId(), false, "/gymMoreRegistration/changeMoreRegistrationSchedule/");
        gymMoreRegistrationScheduleService.dragOfPosition(gymMoreRegistrationModel.getGymAddressModel().getId(), gymMoreRegistrationModel.getMoreRegistrationModel().getId(), oldIndex, newIndex);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return moreRegistration(modelAndView, gymMoreRegistrationId);
    }

    @GetMapping("/moreRegistrationSchedule/{gymMoreRegistrationScheduleId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView moreRegistrationSchedule(ModelAndView modelAndView, @PathVariable Long gymMoreRegistrationScheduleId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymMoreRegistrationScheduleId, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/moreRegistrationSchedule/" + gymMoreRegistrationScheduleId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymMoreRegistrationScheduleModel gymMoreRegistrationScheduleModel = gymMoreRegistrationScheduleService.findById(gymMoreRegistrationScheduleId);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationScheduleModel.getGymAddressModel().getGymModel().getId(), true, "/gymMoreRegistration/moreRegistrationSchedule/" + gymMoreRegistrationScheduleId);
        modelAndView.setViewName("gym/moreRegistrationSchedule");
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymMoreRegistrationScheduleModel.getGymAddressModel().getGymModel().getId()));
        modelAndView.addObject("gymMoreRegistrationScheduleModel", gymMoreRegistrationScheduleModel);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/update-moreRegistrationSchedule")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView updateMoreRegistrationSchedule(ModelAndView modelAndView, @ModelAttribute("gymMoreRegistrationScheduleModel") GymMoreRegistrationScheduleModel gymMoreRegistrationScheduleModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymMoreRegistrationScheduleModel, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/update-moreRegistrationSchedule");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationScheduleModel.getGymAddressModel().getGymModel().getId(), false, "/gymMoreRegistration/update-moreRegistrationSchedule");
        gymMoreRegistrationScheduleService.update(gymMoreRegistrationScheduleModel);
        modelAndView.addObject("updateOK", "updateOK");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return moreRegistrationSchedule(modelAndView, gymMoreRegistrationScheduleModel.getId());
    }

    @GetMapping("/remove-moreRegistrationSchedule/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView removeMoreRegistrationSchedule(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/remove-moreRegistrationSchedule/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymMoreRegistrationScheduleModel gymMoreRegistrationScheduleModel = gymMoreRegistrationScheduleService.findById(id);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationScheduleModel.getGymAddressModel().getGymModel().getId(), true, "/gymMoreRegistration/remove-moreRegistrationSchedule/" + id);
        gymMoreRegistrationScheduleService.delete(id);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return moreRegistration(modelAndView, gymMoreRegistrationScheduleModel.getGymMoreRegistrationModel().getId());
    }
*/
}
