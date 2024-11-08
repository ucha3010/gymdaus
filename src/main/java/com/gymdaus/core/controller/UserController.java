package com.gymdaus.core.controller;


import com.gymdaus.core.configuration.SessionData;
import com.gymdaus.core.entity.UserRole;
import com.gymdaus.core.model.PasswordModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.SecurityService;
import com.gymdaus.core.service.UserDocumentManagerService;
import com.gymdaus.core.service.UserRoleService;
import com.gymdaus.core.service.UtilService;
import com.gymdaus.core.service.impl.UserService;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserDocumentManagerService userDocumentManagerService;
    @Autowired
    private SecurityService securityService;
    //	@Autowired
//	private UserRegistrationService userRegistrationService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private SessionData sessionData;

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.userAccessValidation("/user/profile");
        modelAndView.setViewName("user/user-profile");
        UserModel user = utilService.basicDataCharge(modelAndView);
        utilService.chargeBasicDataSelect(modelAndView);
        modelAndView.addObject("profilePhoto", userDocumentManagerService.getProfilePhotoPath(user.getUsername()));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView update(@ModelAttribute("user") UserModel userModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), userModel, getClass());
        ModelAndView modelAndView = new ModelAndView();
        securityService.userAccessValidation("/user/update");
        try {
            userService.addOrUpdate(userModel);
            modelAndView.addObject("updateUserOk", "updateUserOk");
        } catch (Exception e) {
            modelAndView.addObject("updateUserError", "updateUserError");
            LoggerMapper.log(Level.ERROR, "update", e.getMessage(), getClass());
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return profile(modelAndView);
    }

    @GetMapping("/photo")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView photo(ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.userAccessValidation("/user/photo");
        modelAndView.setViewName("user/user-profile-photo");
        UserModel user = utilService.basicDataCharge(modelAndView);
        modelAndView.addObject("profilePhoto", userDocumentManagerService.getProfilePhotoPath(user.getUsername()));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/upload-photo")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView uploadPhoto(ModelAndView modelAndView, @RequestParam("file") MultipartFile file) {

        LoggerMapper.methodIn(Level.INFO, "user/upload-photo", file.getOriginalFilename(), getClass());
        securityService.userAccessValidation("/user/upload-photo");
        UserModel userModel = utilService.basicDataCharge(modelAndView);
        if (!userDocumentManagerService.addPhoto(userModel, file)) {
            modelAndView.addObject("uploadError", "uploadError");
        } else {
            modelAndView.addObject("uploadOk", "uploadOk");
        }
        return photo(modelAndView);
    }

    @GetMapping("/change-pass")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView changePass(ModelAndView modelAndView, PasswordModel passwordModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.userAccessValidation("/user/change-pass");
        modelAndView.setViewName("user/user-change-password");
        UserModel user = utilService.basicDataCharge(modelAndView);
        passwordModel.setUsername(user.getUsername());
        modelAndView.addObject("passwordModel", passwordModel);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/update-pass")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView updatePass(@ModelAttribute("passwordModel") PasswordModel passwordModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), null, getClass());
        securityService.userAccessValidation("/user/update-pass");
        if (passwordModel == null || Utils.isNullOrEmpty(passwordModel.getUsername()) || sessionData.getUserModel() == null ||
                Utils.isNullOrEmpty(sessionData.getUserModel().getUsername()) ||
                !passwordModel.getUsername().equalsIgnoreCase(sessionData.getUserModel().getUsername())) {
            throw new AccessDeniedException("/user/update-pass");
        }
        ModelAndView modelAndView = new ModelAndView();
        UserModel userModel = userService.findModelByUsername(passwordModel.getUsername());
        if (userService.comparePassword(passwordModel.getOldPassword(), userModel.getPassword())) {
            userModel.setPassword(userService.encodePassword(passwordModel.getNewPassword()));
            userService.updatePass(userModel);
            modelAndView.addObject("modifiedPass", "modifiedPass");
            LoggerMapper.log(Level.INFO, Utils.getMethodName(), "Password updated " + userModel.getUsername(), getClass());
        } else {
            modelAndView.addObject("oldDifferent", "oldDifferent");
            LoggerMapper.log(Level.INFO, Utils.getMethodName(), "Old password different", getClass());
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return changePass(modelAndView, passwordModel);
    }

    //TODO hasta acá lo nuevo

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView users(ModelAndView modelAndView) {
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/usuario/users");
        modelAndView.setViewName("management/users");
//		modelAndView.addObject("userList", userService.findAll());
        modelAndView.addObject("userRoleList", userRoleService.adminAvailableRoles());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/users/{username}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView userDetail(ModelAndView modelAndView, @PathVariable String username) {
        LoggerMapper.methodIn(Level.INFO, "/users/" + username, username, this.getClass());
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/usuario/users/" + username);
        modelAndView.setViewName("management/user");
        UserModel userModel = userService.findModelByUsername(username);
        modelAndView.addObject("user", userModel);
        modelAndView.addObject("userRoleList", userRoleService.adminAvailableRoles());
//		modelAndView.addObject("loggedUser", userService.isLoggedUser(user.getUsername(), userModel.getUsername()));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/enabled/{username}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView updatePay(ModelAndView modelAndView, @PathVariable String username) {
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/usuario/enabled/" + username);
        UserModel usuario = userService.findModelByUsername(username);
        usuario.setEnabled(!usuario.isEnabled());
//		usuario.setUsernameModificacion(user.getUsername());
//		userService.addOrUpdate(usuario);
        modelAndView.addObject("updateOK", "Habilitación de " + usuario.getName()
                + " " + usuario.getLastname()
                + (!Utils.isNullOrEmpty(usuario.getSecondLastname()) ? " " + usuario.getSecondLastname() : "")
                + " actualizada correctamente");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return userDetail(modelAndView, username);
    }

    @GetMapping("/rol/{username}/{rol}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView update(ModelAndView modelAndView, @PathVariable String username, @PathVariable String rol) {
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/user/rol/" + username + "/" + rol);
        UserRole userRole = new UserRole();
        com.gymdaus.core.entity.User userEntity = userService.findByUsername(username);
        userRole.setUser(userEntity);
        userRole.setRole(rol);
        userRoleService.deleteByUsername(username);
        userRoleService.save(userRole);
        modelAndView.addObject("updateOK", "Rol de " + userEntity.getName()
                + " " + userEntity.getLastname()
                + (!Utils.isNullOrEmpty(userEntity.getSecondLastname()) ? " " + userEntity.getSecondLastname() : "")
                + " actualizado correctamente");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return userDetail(modelAndView, username);
    }

    @GetMapping("/registrations")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView registrations(ModelAndView modelAndView) {
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/usuario/registrations");
        modelAndView.setViewName("management/registrations");
//		modelAndView.addObject("activities", userRegistrationService.getActivities());
//		modelAndView.addObject("userRegistrationList", userRegistrationService.findAll());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/registrations/{activity}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView registrations(ModelAndView modelAndView, @PathVariable String activity) {
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/usuario/registrations/" + activity);
        modelAndView.setViewName("management/registrations");
//		modelAndView.addObject("activities", userRegistrationService.getActivities());
        if (!Utils.isNullOrEmpty(activity)) {
            modelAndView.addObject("selectedActivity", activity);
//			modelAndView.addObject("userRegistrationList", userRegistrationService.findByActivity(activity));
        } else {
//			modelAndView.addObject("userRegistrationList", userRegistrationService.findAll());
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/gymUsers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView gymUsers(ModelAndView modelAndView) {
        UserModel user = utilService.basicDataCharge(modelAndView);
//        securityService.enabledAdministrationGym(sessionData.getGymModel().getId(), "/usuario/gymUsers");
        securityService.enabledAdministrationGymUser(user.getUsername(), sessionData.getGymModel().getId(), "/usuario/gymUsers");
        modelAndView.setViewName("gimnasio/adminUsers");
//		modelAndView.addObject("activities", userRegistrationService.getActivities());
//		modelAndView.addObject("userRegistrationList", userRegistrationService.findByGymSigned(sessionData.getGimnasioModel().getId()));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/gymUsers/activity/{activity}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView gymUsersActivity(ModelAndView modelAndView, @PathVariable String activity) {
        UserModel user = utilService.basicDataCharge(modelAndView);
//        securityService.enabledAdministrationGym(sessionData.getGymModel().getId(), "/usuario/gymUsers/activity/" + activity);
        securityService.enabledAdministrationGymUser(user.getUsername(), sessionData.getGymModel().getId(), "/usuario/gymUsers/activity/" + activity);
        modelAndView.setViewName("gimnasio/adminUsers");
//		modelAndView.addObject("activities", userRegistrationService.getActivities());
        if (!Utils.isNullOrEmpty(activity)) {
            modelAndView.addObject("selectedActivity", activity);
//			modelAndView.addObject("userRegistrationList", userRegistrationService.findByActivityAndGym(activity, sessionData.getGimnasioModel().getId()));
        } else {
//			modelAndView.addObject("userRegistrationList", userRegistrationService.findByGym(sessionData.getGimnasioModel().getId()));
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/gymUsers/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView gymUserDetail(ModelAndView modelAndView, @PathVariable String username) {
        UserModel user = utilService.basicDataCharge(modelAndView);
//        securityService.enabledAdministrationGym(sessionData.getGymModel().getId(), "/usuario/gymUsers/" + username);
        securityService.enabledAdministrationGymUser(user.getUsername(), sessionData.getGymModel().getId(), "/usuario/gymUsers/" + username);
        modelAndView.setViewName("gimnasio/userAdmin");
        modelAndView.addObject("user", userService.findModelByUsername(username));
//		modelAndView.addObject("documentManagerModel", new DocumentManagerModel());
//		modelAndView.addObject("documentManagerList", documentManagerService.findByIdGymAndIdCardAndSections(sessionData.getGimnasioModel().getId(),username,
//				Arrays.asList(Constants.SECCION_AUTORIZACION_MENOR_18, Constants.SECCION_AUTORIZACION_MAYOR_18)));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

/*	@PostMapping("/descargarPdf")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void descargarPdf(@ModelAttribute("documentManagerModel") DocumentManagerModel documentManagerModel, HttpServletResponse response) {
		securityService.roleValidation(userService.getLoggedUser().getUsername(), Constants.ROLE_ADMIN, "/usuario/descargarPdf");
		documentManagerService.downloadFile(documentManagerModel.getId(), response);
		LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), "Descarga de documento correcta", getClass());
	}*/

}
