package com.gymdaus.core.controller;


import com.gymdaus.core.configuration.SessionData;
import com.gymdaus.core.entity.UserRole;
import com.gymdaus.core.model.UserDocumentManagerModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.model.UserPasswordModel;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import static com.google.common.io.Files.getFileExtension;

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
		securityService.userAccessValidation("/user/profile");
		modelAndView.setViewName("user/user-profile");
		UserModel user = utilService.basicDataCharge(modelAndView);
		utilService.chargeBasicDataSelect(modelAndView);
		modelAndView.addObject("profilePhoto", userDocumentManagerService.findByUsernameEnabled(user.getUsername()));
		LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
		return modelAndView;
	}
	
	@PostMapping("/update")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView update(@ModelAttribute("user") UserModel userModel) {
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
		securityService.userAccessValidation("/user/photo");
		modelAndView.setViewName("user/user-profile-photo");
		UserModel user = utilService.basicDataCharge(modelAndView);
		modelAndView.addObject("profilePhoto", userDocumentManagerService.findByUsernameEnabled(user.getUsername()));
		LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
		return modelAndView;
	}

	@PostMapping("/upload-photo")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView uploadPhoto(ModelAndView modelAndView, @RequestParam("file") MultipartFile file) {

		LoggerMapper.methodIn(Level.INFO, "user/upload-photo", file.getOriginalFilename(), getClass());
		securityService.userAccessValidation("/user/upload-photo");
		UserModel userModel = utilService.basicDataCharge(modelAndView);
		UserDocumentManagerModel userDocumentManagerModel = userDocumentManagerService.findByUsernameEnabled(userModel.getUsername());
		if(!userDocumentManagerService.addPhoto(userModel, file)) {
			modelAndView.addObject("uploadError", "uploadError");
		}
		return photo(modelAndView);
	}

	@GetMapping("/change-pass")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView formularioCambioClave(ModelAndView modelAndView) {
		modelAndView.setViewName("formularioCambioClave");
		UserModel user = utilService.basicDataCharge(modelAndView);
		UserPasswordModel userPasswordModel = new UserPasswordModel();
		userPasswordModel.setUsername(user.getUsername());
		modelAndView.addObject("userPasswordModel", userPasswordModel);
		LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
		return modelAndView;
	}

	@PostMapping("/actualizarClaveUsuario")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView actualizarClaveUsuario(@ModelAttribute("userPasswordModel") UserPasswordModel userPasswordModel ) {
		ModelAndView modelAndView = new ModelAndView();
		UserModel userModel = userService.findModelByUsername(userPasswordModel.getUsername());
		securityService.userAccessValidation("/usuario/actualizarClaveUsuario");
		if (userService.comparePassword(userPasswordModel.getOldPassword(), userModel.getPassword())) {
			userModel.setPassword(userService.encodePassword(userPasswordModel.getNewPassword()));
			userService.updatePass(userModel);
			modelAndView.addObject("claveModificada", "claveModificada");
			LoggerMapper.log(Level.INFO, "actualizarUsuario", "Contraseña actualizada", getClass());
		} else {
			modelAndView.addObject("antiguaDistinta", "antiguaDistinta");
			LoggerMapper.log(Level.INFO, "actualizarUsuario", "Contraseña antigua distinta", getClass());
		}
		modelAndView.addObject("usuario", userModel);
		modelAndView.addObject("userPasswordModel", userPasswordModel);
		LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
		return formularioCambioClave(modelAndView);
	}

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
		LoggerMapper.methodIn(Level.INFO, "/users/"+username, username, this.getClass());
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
		securityService.enabledAdministrationGym(sessionData.getGymModel().getId(), "/usuario/gymUsers");
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
		securityService.enabledAdministrationGym(sessionData.getGymModel().getId(), "/usuario/gymUsers/activity/" + activity);
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
		securityService.enabledAdministrationGym(sessionData.getGymModel().getId(), "/usuario/gymUsers/" + username);
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
