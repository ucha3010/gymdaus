package com.gymdaus.core.controller;

import com.gymdaus.core.model.GymActivityModel;
import com.gymdaus.core.model.GymActivityScheduleModel;
import com.gymdaus.core.model.GymAddressModel;
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
@RequestMapping("/gymActivity")
public class GymActivityController {

    @Autowired
    private GymActivityService gymActivityService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private GymActivityScheduleService gymActivityScheduleService;
    @Autowired
    private GymAddressService gymAddressService;
    @Autowired
    private GymService gymService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UtilService utilService;

    @GetMapping("/activities/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView activities(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gymActivity/activities/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, true, "/gymActivity/activities/");
        modelAndView.setViewName("gym/activities");
        modelAndView.addObject("gymActivityModel", new GymActivityModel());
        modelAndView.addObject("gymActivityModelList", gymActivityService.findAllByGymId(gymId));
        modelAndView.addObject("activityModelList", activityService.findAllEnabled());
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/new-activity/{gymAddressId}/{activityId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView newActivity(ModelAndView modelAndView, @PathVariable Long gymAddressId, @PathVariable Long activityId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), "gymAddressId=" + gymAddressId + ", activityId=" + activityId, getClass());
        securityService.userAccessValidation("/gymActivity/new-activity/" + gymAddressId + "/" + activityId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymAddressModel gymAddressModel = gymAddressService.findById(gymAddressId);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymAddressModel.getGymModel().getId(), true, "/gymActivity/new-activity/" + gymAddressId + "/" + activityId);
        GymActivityModel gymActivityModel = new GymActivityModel();
        gymActivityModel.setGymAddressModel(gymAddressModel);
        gymActivityModel.setGymModel(gymAddressModel.getGymModel());
        gymActivityModel.setRegistrationUser(user.getUsername());
        gymActivityModel.setActivityModel(activityService.findById(activityId));
        gymActivityModel = gymActivityService.add(gymActivityModel);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return activity(modelAndView, gymActivityModel.getId());
    }

    @GetMapping("/activity/{gymActivityId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView activity(ModelAndView modelAndView, @PathVariable Long gymActivityId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymActivityId, getClass());
        securityService.userAccessValidation("/gymActivity/activity/" + gymActivityId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymActivityModel gymActivityModel = gymActivityService.findById(gymActivityId);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymActivityModel.getGymAddressModel().getGymModel().getId(), true, "/gymActivity/activity/" + gymActivityId);
        modelAndView.setViewName("gym/activity");
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymActivityModel.getGymAddressModel().getGymModel().getId()));
        modelAndView.addObject("gymActivityModel", gymActivityModel);
        modelAndView.addObject("gymActivityScheduleModel", new GymActivityScheduleModel());
        modelAndView.addObject("gymActivityScheduleModelList", gymActivityScheduleService
                .findAllByGymAddressIdAndActivityId(gymActivityModel.getGymAddressModel().getId(), gymActivityModel.getActivityModel().getId()));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/remove-activity/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView removeActivity(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/gymActivity/remove-activity/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymActivityModel gymActivityModel = gymActivityService.findById(id);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymActivityModel.getGymAddressModel().getGymModel().getId(), true, "/gymActivity/remove-activity/" + id);
        gymActivityService.delete(id);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return activities(modelAndView, gymActivityModel.getGymAddressModel().getGymModel().getId());
    }

    @PostMapping("/activitySchedule")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addActivitySchedule(ModelAndView modelAndView, @ModelAttribute("gymActivityScheduleModel") GymActivityScheduleModel gymActivityScheduleModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymActivityScheduleModel, getClass());
        securityService.userAccessValidation("/gymActivity/activitySchedule");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymActivityScheduleModel.getGymActivityModel().getGymModel().getId(), false, "/gymActivity/activitySchedule");
        gymActivityScheduleModel.setPosition(gymActivityScheduleService.findMaxPosition(gymActivityScheduleModel.getGymAddressModel().getId(), gymActivityScheduleModel.getActivityModel().getId()) + 1);
        gymActivityScheduleService.add(gymActivityScheduleModel);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return activity(modelAndView, gymActivityScheduleModel.getGymActivityModel().getId());
    }

    @GetMapping("/changeActivitySchedule/{gymActivityId}/{oldIndex}/{newIndex}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView changeActivitySchedule(ModelAndView modelAndView, @PathVariable Long gymActivityId, @PathVariable int oldIndex, @PathVariable int newIndex) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymActivityId, getClass());
        securityService.userAccessValidation("/gymActivity/changeActivitySchedule/" + gymActivityId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymActivityModel gymActivityModel = gymActivityService.findById(gymActivityId);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymActivityModel.getGymModel().getId(), false, "/gymActivity/changeActivitySchedule/");
        gymActivityScheduleService.dragOfPosition(gymActivityModel.getGymAddressModel().getId(), gymActivityModel.getActivityModel().getId(), oldIndex, newIndex);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return activity(modelAndView, gymActivityId);
    }

    @GetMapping("/activitySchedule/{gymActivityScheduleId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView activitySchedule(ModelAndView modelAndView, @PathVariable Long gymActivityScheduleId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymActivityScheduleId, getClass());
        securityService.userAccessValidation("/gymActivity/activitySchedule/" + gymActivityScheduleId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymActivityScheduleModel gymActivityScheduleModel = gymActivityScheduleService.findById(gymActivityScheduleId);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymActivityScheduleModel.getGymAddressModel().getGymModel().getId(), true, "/gymActivity/activitySchedule/" + gymActivityScheduleId);
        modelAndView.setViewName("gym/activitySchedule");
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymActivityScheduleModel.getGymAddressModel().getGymModel().getId()));
        modelAndView.addObject("gymActivityScheduleModel", gymActivityScheduleModel);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/update-activitySchedule")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView updateActivitySchedule(ModelAndView modelAndView, @ModelAttribute("gymActivityScheduleModel") GymActivityScheduleModel gymActivityScheduleModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymActivityScheduleModel, getClass());
        securityService.userAccessValidation("/gymActivity/update-activitySchedule");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymActivityScheduleModel.getGymAddressModel().getGymModel().getId(), false, "/gymActivity/update-activitySchedule");
        gymActivityScheduleService.update(gymActivityScheduleModel);
        modelAndView.addObject("updateOK", "updateOK");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return activitySchedule(modelAndView, gymActivityScheduleModel.getId());
    }

    @GetMapping("/remove-activitySchedule/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView removeActivitySchedule(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/gymActivity/remove-activitySchedule/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymActivityScheduleModel gymActivityScheduleModel = gymActivityScheduleService.findById(id);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymActivityScheduleModel.getGymAddressModel().getGymModel().getId(), true, "/gymActivity/remove-activitySchedule/" + id);
        gymActivityScheduleService.delete(id);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return activity(modelAndView, gymActivityScheduleModel.getGymActivityModel().getId());
    }

}
