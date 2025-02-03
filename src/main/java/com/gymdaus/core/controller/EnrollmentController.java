package com.gymdaus.core.controller;

import com.gymdaus.core.model.*;
import com.gymdaus.core.service.*;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/enrollment")
public class EnrollmentController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private EnrollmentAsService enrollmentAsService;
    @Autowired
    private GymActivityService gymActivityService;
    @Autowired
    private GymActivityScheduleService gymActivityScheduleService;
    @Autowired
    private GymAddressService gymAddressService;
    @Autowired
    private GymService gymService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UtilService utilService;
    private final MessageSource messageSource;

    public EnrollmentController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

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

    @GetMapping("/activity/{id}")
    @PreAuthorize("permitAll()")
    public ModelAndView activity(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        utilService.basicDataCharge(modelAndView);
        modelAndView.setViewName("enrollment/select-address");
        ActivityModel activityModel = activityService.findById(id);
        List<GymActivityModel> gymActivityModelList = gymActivityService.findAllByActivityId(id);
        modelAndView.addObject("gymActivityModelList", gymActivityService.sortByZipCode(gymActivityModelList));
        modelAndView.addObject("activity", activityModel.getName());
        return modelAndView;
    }

    @GetMapping("/form/{activityId}/{gymAddressId}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView activityForm(ModelAndView modelAndView, @PathVariable Long activityId, @PathVariable Long gymAddressId, @RequestParam String language) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), "activityId: " + activityId + ", gymAddressId: " + gymAddressId, getClass());
        securityService.userAccessValidation("/enrollment/form/" + activityId + "/" + gymAddressId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        utilService.chargeBasicDataSelect(modelAndView);
        modelAndView.setViewName("enrollment/activity-enrollment");
        Locale locale = Locale.forLanguageTag(language);
        LocaleContextHolder.setLocale(locale);
        List<GymActivityScheduleModel> gymActivityScheduleModelList = gymActivityScheduleService.findAllByGymAddressIdAndActivityId(gymAddressId, activityId);
        gymActivityScheduleService.fillDescription(gymActivityScheduleModelList, messageSource, locale);
        EnrollmentModel enrollmentModel = new EnrollmentModel();
        enrollmentModel.setAuthorizerEnrollmentName(user.getName());
        enrollmentModel.setAuthorizerEnrollmentLastname(user.getLastname());
        enrollmentModel.setAuthorizerEnrollmentSecondLastname(user.getSecondLastname());
        enrollmentModel.setAuthorizerEnrollmentIdCard(user.getUsername());
        modelAndView.addObject("activity", activityService.findById(activityId));
        modelAndView.addObject("gymAddress", gymAddressService.findById(gymAddressId));
        modelAndView.addObject("enrollmentModel", enrollmentModel);
        modelAndView.addObject("gymActivityScheduleModelList", gymActivityScheduleModelList);
        modelAndView.addObject("enrollmentAsModelList", enrollmentAsService.findAll());
        return modelAndView;
    }

    @PostMapping("/activity")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addEnrollmentActivity(ModelAndView modelAndView, @ModelAttribute("enrollmentModel") EnrollmentModel enrollmentModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), enrollmentModel, getClass());
        securityService.userAccessValidation("/enrollment/activity");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.compareUserValidation(user.getUsername(), enrollmentModel.getAuthorizerEnrollmentIdCard(), "/enrollment/activity");
        enrollmentModel.setUserModel(user);
        enrollmentModel.setGymActivityScheduleModel(gymActivityScheduleService.findById(enrollmentModel.getGymActivityScheduleModel().getId()));
        enrollmentService.fillActivityEnrollment(enrollmentModel);
        enrollmentModel = enrollmentService.add(enrollmentModel);
        SignatureModel signatureModel = new SignatureModel(enrollmentModel.getId(), enrollmentModel.getActivityName(),
                user.getUsername(), enrollmentModel.getGymModel(), Constants.TABLE_ENROLLMENT, enrollmentModel.getDocumentLanguage());
        Locale locale = Locale.forLanguageTag(enrollmentModel.getDocumentLanguage());
        LocaleContextHolder.setLocale(locale);
        signatureModel = securityService.sendSignatureCode(modelAndView, signatureModel, user, null, messageSource, locale);
        List<DownloadDocumentModel> downloadDocumentModelList = new ArrayList<>();
        enrollmentService.fillFilesAndDownloadDocumentModelList(downloadDocumentModelList, null, enrollmentModel, signatureModel, messageSource, locale);
        modelAndView.addObject("downloadDocumentModelList", downloadDocumentModelList);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

}
