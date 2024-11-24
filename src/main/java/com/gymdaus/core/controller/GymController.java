package com.gymdaus.core.controller;

import com.gymdaus.core.configuration.SessionData;
import com.gymdaus.core.exception.SenderException;
import com.gymdaus.core.model.*;
import com.gymdaus.core.service.*;
import com.gymdaus.core.service.impl.UserService;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import jakarta.persistence.NoResultException;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/gym")
public class GymController {

    @Autowired
    private GymService gymService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private GymPhotoService gymPhotoService;
    @Autowired
    private GymParameterService gymParameterService;
    @Autowired
    private GymUserService gymUserService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private SessionData sessionData;

    private final MessageSource messageSource;

    public GymController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView gymMainPage(ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
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
            modelAndView.addObject("gymUserModel", gymUserModelList.get(0));
            modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymUserModelList.get(0).getGymModel().getId()));
        } else {
            List<GymModel> gymModelList = new ArrayList<>();
            GymModel gymModel;
            GymUserModel gymUserModelUnique = new GymUserModel();
            for (GymUserModel gymUserModel : gymUserModelList) {
                gymModel = gymService.findByIdEnabled(gymUserModel.getGymModel().getId());
                if (gymModel != null) {
                    gymModelList.add(gymModel);
                    gymUserModelUnique = gymUserModel;
                }
            }
            if (gymModelList.isEmpty()) {
                modelAndView.setViewName("gym/gym-admin");
                modelAndView.addObject("backButton", "/");
                modelAndView.addObject("gymModel", null);
            } else if (gymModelList.size() == 1) {
                modelAndView.setViewName("gym/gym-admin");
                modelAndView.addObject("backButton", "/");
                modelAndView.addObject("gymUserModel", gymUserModelUnique);
                modelAndView.addObject("gymModel", gymModelList.get(0));
            } else {
                modelAndView.setViewName("gym/select-gym");
                modelAndView.addObject("gymModelList", gymModelList);
            }
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/gym-admin/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView gymAdmin(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gym/gym-admin/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, false, "/gym/gym-admin/");
        modelAndView.setViewName("gym/gym-admin");
        modelAndView.addObject("backButton", "/gym/");
        modelAndView.addObject("gymUserModel", gymUserService.findByUsernameAndGymId(user.getUsername(), gymId));
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/gym-user/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView gymUser(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gym/gym-user/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, true, "/gym/gym-user/");
        modelAndView.setViewName("gym/gym-user");
        List<GymUserModel> gymUserModelList = gymUserService.findByGymId(gymId);
        for (GymUserModel gymUserModel : gymUserModelList) {
            gymUserModel.setUserModel(userService.findModelByUsername(gymUserModel.getUserModel().getUsername()));
        }
        modelAndView.addObject("gymUserModelList", gymUserModelList);
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymId));
        modelAndView.addObject("gymUserRoles", Utils.getGymUserRoles());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/role/gym-user/{username}/{gymId}/{role}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView roleGymUser(ModelAndView modelAndView, @PathVariable String username, @PathVariable Long gymId, @PathVariable String role) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/role/gym-user/" + username + "/" + gymId + "/" + role);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, true, "/role/gym-user/");
        GymUserModel gymUserModel = gymUserService.findByUsernameAndGymId(username, gymId);
        gymUserModel.setGymRole(role);
        gymUserService.update(gymUserModel);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return gymUser(modelAndView, gymId);
    }

    @GetMapping("/delete/gym-user/{gymUserId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteGymUser(ModelAndView modelAndView, @PathVariable Long gymUserId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymUserId, getClass());
        securityService.userAccessValidation("/gym/delete/gym-user/" + gymUserId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymUserModel gymUserModel = gymUserService.findById(gymUserId);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymUserModel.getGymModel().getId(), true, "/gym/delete/gym-user/");
        gymUserService.delete(gymUserId);
        if (gymUserService.findByUsername(gymUserModel.getUserModel().getUsername()).isEmpty()) {
            UserRoleModel userRoleModel = new UserRoleModel();
            userRoleModel.setUsername(gymUserModel.getUserModel().getUsername());
            userRoleModel.setRoles(List.of(Constants.ROLE_USER));
            userRoleService.updateRoles(userRoleModel);
        }
        modelAndView.addObject("confirmationOk", "deleted.user.ok");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return gymUser(modelAndView, gymUserModel.getGymModel().getId());
    }

    @GetMapping("/send-invitation/gym-user/{gymId}/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView sendInvitationGymUser(ModelAndView modelAndView, @PathVariable Long gymId, @PathVariable String username, @RequestParam String language) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), "gymId=" + gymId + ", username=" + username, getClass());
        securityService.userAccessValidation("/gym/send-invitation/gym-user/" + gymId + "/" + username);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, true, "/gym/send-invitation/gym-user/");
        try {
            UserModel userInvited = userService.findModelByUsername(username);
            GymModel gymModel = gymService.findByIdEnabled(gymId);
            Locale locale = Locale.of(language);
            LocaleContextHolder.setLocale(locale);
            emailService.sendAdminInvitation(user, userInvited, gymModel, messageSource, locale);
            modelAndView.addObject("confirmationOk", "email.sent.ok");
        } catch (NoResultException nre) {
            modelAndView.addObject("invitationSentFail", "invitationSentFail");
        } catch (SenderException e) {
            modelAndView.addObject("errorSendingEmail", "errorSendingEmail");
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return gymUser(modelAndView, gymId);
    }

    @GetMapping("/parameters/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView gymParameters(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gym/parameters/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, true, "/gym/parameters/");
        modelAndView.setViewName("gym/gym-parameter");
        EmailModel emailModel = emailService.getGymParameters(gymId);
        if (Utils.isNullOrEmpty(emailModel.getPassword())) {
            modelAndView.addObject("emptyPass", true);
        } else {
            modelAndView.addObject("emptyPass", false);
            emailModel.setPassword(null);
        }
        modelAndView.addObject("emailModel", emailModel);
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymId));
        modelAndView.addObject("utilListHost", Utils.chargeListHostProvider());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/update-parameters")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView gymUpdateParameters(ModelAndView modelAndView, @ModelAttribute("emailModel") EmailModel emailModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), emailModel, getClass());
        securityService.userAccessValidation("/gym/update-parameters");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), emailModel.getGymId(), true, "/gym/update-parameters");
        emailService.updateGymParameters(emailModel, user.getUsername());
        modelAndView.addObject("updateOK", "updateOK");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return gymParameters(modelAndView, emailModel.getGymId());
    }

    @GetMapping("/email-pass/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView emailPassword(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gym/email-pass/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, true, "/gym/email-pass/");
        modelAndView.setViewName("gym/gym-parameter-change-pass");
        modelAndView.addObject("passwordModel", new PasswordModel());
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/update-email-pass")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView gymUpdateEmailPass(ModelAndView modelAndView, @ModelAttribute("passwordModel") PasswordModel passwordModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), passwordModel.getGymId(), getClass());
        securityService.userAccessValidation("/gym/update-email-pass");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), passwordModel.getGymId(), true, "/gym/update-email-pass");
        if (!gymParameterService.comparePassword(passwordModel.getGymId(), passwordModel.getOldPassword())) {
            modelAndView.addObject("oldDifferent", "oldDifferent");
        } else {
            GymParameterModel gymParameterModel = new GymParameterModel();
            gymParameterModel.setKeyData(Constants.EMAIL_PASSWORD);
            GymModel gymModel = new GymModel();
            gymModel.setId(passwordModel.getGymId());
            gymParameterModel.setGymModel(gymModel);
            gymParameterModel.setValue(passwordModel.getNewPassword());
            gymParameterModel.setModificationDate(new Date());
            gymParameterModel.setModificationUsername(user.getUsername());
            gymParameterService.update(gymParameterModel);
            modelAndView.addObject("modifiedPass", "modifiedPass");
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return emailPassword(modelAndView, passwordModel.getGymId());
    }

    @GetMapping("/enrollments/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView gymEnrollments(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gym/enrollments/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, true, "/gym/enrollments/" + gymId);
        modelAndView.setViewName("gym/enrollments");
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymId));
        modelAndView.addObject("enrollmentModelList", enrollmentService.findByGymId(gymId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/enrollment/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView gymEnrollment(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/gym/enrollment/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        EnrollmentModel enrollmentModel = enrollmentService.findById(id);
        securityService.enabledAdministrationGymUser(user.getUsername(), enrollmentModel.getGymModel().getId(), true, "/gym/enrollment/" + id);
        modelAndView.setViewName("gym/enrollment-detail");
        modelAndView.addObject("enrollment", enrollmentModel);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/photos/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView gymPhotos(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gym/photos/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, true, "/gym/photos/" + gymId);
        modelAndView.setViewName("gym/photos");
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymId));
        modelAndView.addObject("gymPhotoModelList", gymPhotoService.findByGymId(gymId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/upload-photo/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView gymUploadPhoto(ModelAndView modelAndView, @PathVariable Long gymId, @RequestParam("file") MultipartFile file) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gym/upload-photo/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, true, "/gym/upload-photo/" + gymId);
        if (gymPhotoService.addPhoto(gymId, file)) {
            modelAndView.addObject("uploadOk", "uploadOk");
        } else {
            modelAndView.addObject("uploadError", "uploadError");
        }
        return gymPhotos(modelAndView, gymId);
    }

    @GetMapping("/remove-photo/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView removePhoto(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/root/remove-photo/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymPhotoModel gymPhotoModel = gymPhotoService.findById(id);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymPhotoModel.getGymModel().getId(), true, "/gym/upload-photo/" + id);
        if (gymPhotoService.delete(id)) {
            modelAndView.addObject("eraseOk", "eraseOk");
        } else {
            modelAndView.addObject("eraseError", "eraseError");
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return gymPhotos(modelAndView, gymPhotoModel.getGymModel().getId());
    }

    @GetMapping("/main-photo/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView mainPhoto(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/root/main-photo/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymPhotoModel gymPhotoModel = gymPhotoService.findById(id);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymPhotoModel.getGymModel().getId(), true, "/gym/main-photo/" + id);
        gymPhotoService.doMain(id);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return gymPhotos(modelAndView, gymPhotoModel.getGymModel().getId());
    }

}
