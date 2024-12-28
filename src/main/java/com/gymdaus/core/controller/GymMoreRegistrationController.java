package com.gymdaus.core.controller;

import com.gymdaus.core.model.GymMoreRegistrationDateModel;
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
    private GymCategoryService gymCategoryService;
    @Autowired
    private GymMoreRegistrationGymCategoryService gymMoreRegistrationGymCategoryService;
    @Autowired
    private GymMoreRegistrationParticipatingEntityService gymMoreRegistrationParticipatingEntityService;
    @Autowired
    private GymMoreRegistrationGymBeltService gymMoreRegistrationGymBeltService;
    @Autowired
    private GymMoreRegistrationDateService gymMoreRegistrationDateService;
    @Autowired
    private GymService gymService;
    @Autowired
    private MoreRegistrationService moreRegistrationService;
    @Autowired
    private ParticipatingEntityService participatingEntityService;
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
    public ModelAndView addMoreRegistration(ModelAndView modelAndView, @ModelAttribute("gymMoreRegistrationModel") GymMoreRegistrationModel gymMoreRegistrationModel) {
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
        modelAndView.addObject("gymBeltModelList", gymMoreRegistrationGymBeltService.findAllByGymMoreRegistration(gymMoreRegistrationId));
        modelAndView.addObject("participatingEntityModelList", gymMoreRegistrationParticipatingEntityService.findAllByGymMoreRegistration(gymMoreRegistrationId));
        modelAndView.addObject("gymCategoryModelList", gymMoreRegistrationGymCategoryService.findAllByGymMoreRegistration(gymMoreRegistrationId));
        modelAndView.addObject("gymMoreRegistrationDateModelList", gymMoreRegistrationDateService.findAllByGymMoreRegistration(gymMoreRegistrationId));
        modelAndView.addObject("countryModelList", countryService.findAll());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/update-more-registration")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView updateMoreRegistration(ModelAndView modelAndView, @ModelAttribute("gymMoreRegistrationModel") GymMoreRegistrationModel gymMoreRegistrationModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymMoreRegistrationModel, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/update-more-registration");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationModel.getGymModel().getId(), true, "/gymMoreRegistration/update-more-registration");
        gymMoreRegistrationModel.setModificationUser(user.getUsername());
        gymMoreRegistrationService.update(gymMoreRegistrationModel);
        modelAndView.addObject("updateOK", "updateOK");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return moreRegistration(modelAndView, gymMoreRegistrationModel.getId());
    }

    @GetMapping("/remove-more-registration/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView removeMoreRegistration(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/remove-more-registration/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymMoreRegistrationModel gymMoreRegistrationModel = gymMoreRegistrationService.findById(id);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationModel.getGymModel().getId(), true, "/gymMoreRegistration/remove-more-registration/" + id);
        gymMoreRegistrationDateService.emptyByGymMoreRegistrationId(id);
        gymMoreRegistrationGymCategoryService.emptyByGymMoreRegistrationId(id);
        gymMoreRegistrationParticipatingEntityService.emptyByGymMoreRegistrationId(id);
        gymMoreRegistrationService.delete(id);
        modelAndView.addObject("eraseOK", "eraseOK");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return moreRegistrations(modelAndView, gymMoreRegistrationModel.getGymModel().getId());
    }

    @GetMapping("/more-registration-date/{gymMoreRegistrationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView moreRegistrationDate(ModelAndView modelAndView, @PathVariable Long gymMoreRegistrationId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymMoreRegistrationId, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/more-registration-date/" + gymMoreRegistrationId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymMoreRegistrationModel gymMoreRegistrationModel = gymMoreRegistrationService.findById(gymMoreRegistrationId);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationModel.getGymModel().getId(), true, "/gymMoreRegistration/more-registration-date/" + gymMoreRegistrationId);
        modelAndView.setViewName("gym/more-registration-date");
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymMoreRegistrationModel.getGymModel().getId()));
        modelAndView.addObject("gymMoreRegistrationModel", gymMoreRegistrationModel);
        modelAndView.addObject("gymMoreRegistrationDateModel", new GymMoreRegistrationDateModel());
        modelAndView.addObject("gymMoreRegistrationDateModelList", gymMoreRegistrationDateService.findAllByGymMoreRegistration(gymMoreRegistrationId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/add-more-registration-date")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addMoreRegistrationDate(ModelAndView modelAndView, @ModelAttribute("gymMoreRegistrationDateModel") GymMoreRegistrationDateModel gymMoreRegistrationDateModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymMoreRegistrationDateModel, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/add-more-registration-date");
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymMoreRegistrationModel gymMoreRegistrationModel = gymMoreRegistrationService.findById(gymMoreRegistrationDateModel.getGymMoreRegistrationModel().getId());
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationModel.getGymModel().getId(), true, "/gymMoreRegistration/add-more-registration-date");
        gymMoreRegistrationDateModel = gymMoreRegistrationDateService.add(gymMoreRegistrationDateModel);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return moreRegistrationDate(modelAndView, gymMoreRegistrationDateModel.getGymMoreRegistrationModel().getId());
    }

    @GetMapping("/remove-more-registration-date/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView removeMoreRegistrationDate(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/remove-more-registration-date/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymMoreRegistrationDateModel gymMoreRegistrationDateModel = gymMoreRegistrationDateService.findById(id);
        GymMoreRegistrationModel gymMoreRegistrationModel = gymMoreRegistrationService.findById(gymMoreRegistrationDateModel.getGymMoreRegistrationModel().getId());
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationModel.getGymModel().getId(), true, "/gymMoreRegistration/remove-more-registration-date/" + id);
        gymMoreRegistrationDateService.delete(id);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return moreRegistrationDate(modelAndView, gymMoreRegistrationDateModel.getGymMoreRegistrationModel().getId());
    }

    @GetMapping("/more-registration-category/{gymMoreRegistrationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView moreRegistrationCategory(ModelAndView modelAndView, @PathVariable Long gymMoreRegistrationId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymMoreRegistrationId, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/more-registration-category/" + gymMoreRegistrationId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymMoreRegistrationModel gymMoreRegistrationModel = gymMoreRegistrationService.findById(gymMoreRegistrationId);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationModel.getGymModel().getId(), true, "/gymMoreRegistration/more-registration-category/" + gymMoreRegistrationId);
        modelAndView.setViewName("gym/more-registration-category");
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymMoreRegistrationModel.getGymModel().getId()));
        modelAndView.addObject("gymMoreRegistrationModel", gymMoreRegistrationModel);
        modelAndView.addObject("gymCategoryList", gymCategoryService.findAllByGymIdAndSelected(gymMoreRegistrationModel.getGymModel().getId(), gymMoreRegistrationId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/add-more-registration-category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addMoreRegistrationCategory(ModelAndView modelAndView, @ModelAttribute("gymMoreRegistrationModel") GymMoreRegistrationModel gymMoreRegistrationModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymMoreRegistrationModel, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/add-more-registration-category");
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymMoreRegistrationModel gymMoreRegistrationModelAux = gymMoreRegistrationService.findById(gymMoreRegistrationModel.getId());
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationModelAux.getGymModel().getId(), true, "/gymMoreRegistration/add-more-registration-category");
        gymMoreRegistrationGymCategoryService.addGymCategoryList(gymMoreRegistrationModel, user.getUsername());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return moreRegistration(modelAndView, gymMoreRegistrationModel.getId());
    }

    @GetMapping("/participating-entities/{gymMoreRegistrationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView participatingEntities(ModelAndView modelAndView, @PathVariable Long gymMoreRegistrationId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymMoreRegistrationId, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/participating-entities/" + gymMoreRegistrationId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymMoreRegistrationModel gymMoreRegistrationModel = gymMoreRegistrationService.findById(gymMoreRegistrationId);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationModel.getGymModel().getId(), true, "/gymMoreRegistration/participating-entities/" + gymMoreRegistrationId);
        modelAndView.setViewName("gym/participating-entities");
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymMoreRegistrationModel.getGymModel().getId()));
        modelAndView.addObject("gymMoreRegistrationModel", gymMoreRegistrationModel);
        modelAndView.addObject("participatingEntityList", participatingEntityService.findAllByGymIdAndSelected(gymMoreRegistrationModel.getGymModel().getId(), gymMoreRegistrationId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/add-participating-entity")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addParticipatingEntity(ModelAndView modelAndView, @ModelAttribute("gymMoreRegistrationModel") GymMoreRegistrationModel gymMoreRegistrationModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymMoreRegistrationModel, getClass());
        securityService.userAccessValidation("/gymMoreRegistration/add-participating-entity");
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymMoreRegistrationModel gymMoreRegistrationModelAux = gymMoreRegistrationService.findById(gymMoreRegistrationModel.getId());
        securityService.enabledAdministrationGymUser(user.getUsername(), gymMoreRegistrationModelAux.getGymModel().getId(), true, "/gymMoreRegistration/add-participating-entity");
        gymMoreRegistrationParticipatingEntityService.addParticipatingEntityList(gymMoreRegistrationModel, user.getUsername());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return moreRegistration(modelAndView, gymMoreRegistrationModel.getId());
    }
}
