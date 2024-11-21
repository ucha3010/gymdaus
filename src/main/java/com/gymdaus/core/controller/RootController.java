package com.gymdaus.core.controller;

import com.gymdaus.core.configuration.SessionData;
import com.gymdaus.core.model.GymAddressModel;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.model.UserRoleModel;
import com.gymdaus.core.service.*;
import com.gymdaus.core.service.impl.UserService;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/root")
public class RootController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private EnrollmentAsService enrollmentAsService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private GymAddressService gymAddressService;
    @Autowired
    private GymService gymService;
    @Autowired
    private MoreRegistrationService moreRegistrationService;
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

    @GetMapping("/countries")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView countries(ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.userAccessValidation("/root/countries");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/countries");
        modelAndView.setViewName("root/countries");
        modelAndView.addObject("countryModelList", countryService.findAll());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/change-country/{oldIndex}/{newIndex}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView changeCountry(ModelAndView modelAndView, @PathVariable int oldIndex, @PathVariable int newIndex) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), "oldIndex=" + oldIndex + ", newIndex=" + newIndex, getClass());
        securityService.userAccessValidation("/root/change-country/" + oldIndex + "/" + newIndex);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/change-country");
        countryService.dragOfPosition(oldIndex, newIndex);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return countries(modelAndView);
    }

    @GetMapping("/remove-country/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView removeCountry(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/root/remove-country/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/remove-country");
        countryService.delete(id);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return countries(modelAndView);
    }

    @GetMapping("/enrollment-as")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView enrollmentAs(ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.userAccessValidation("/root/enrollment-as");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/enrollment-as");
        modelAndView.setViewName("root/enrollment-as");
        modelAndView.addObject("enrollmentAsModelList", enrollmentAsService.findAll());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/change-enrollment-as/{oldIndex}/{newIndex}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView changeEnrollmentAs(ModelAndView modelAndView, @PathVariable int oldIndex, @PathVariable int newIndex) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), "oldIndex=" + oldIndex + ", newIndex=" + newIndex, getClass());
        securityService.userAccessValidation("/root/change-enrollment-as/" + oldIndex + "/" + newIndex);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/change-enrollment-as");
        enrollmentAsService.dragOfPosition(oldIndex, newIndex);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return enrollmentAs(modelAndView);
    }

    @GetMapping("/remove-enrollment-as/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView removeEnrollmentAs(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/root/remove-enrollment-as/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/remove-enrollment-as");
        enrollmentAsService.delete(id);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return enrollmentAs(modelAndView);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView users(ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.userAccessValidation("/root/users");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/users");
        modelAndView.setViewName("root/users");
        modelAndView.addObject("userModelList", userService.findAll());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView userDetail(ModelAndView modelAndView, @PathVariable String username) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.userAccessValidation("/root/user/" + username);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/user/");
        modelAndView.setViewName("root/user-detail");
        modelAndView.addObject("userModel", userService.findModelByUsername(username));
        modelAndView.addObject("userRoleList", userRoleService.rootAvailableRoles());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/user/role/{username}/{role}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView userUserRole(ModelAndView modelAndView, @PathVariable String username, @PathVariable String role) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), "username=" + username + ", role=" + role, getClass());
        securityService.userAccessValidation("/root/user/role/" + username + "/" + role);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/user/role/");
        UserRoleModel userRoleModel = new UserRoleModel();
        userRoleModel.setUsername(username);
        userRoleModel.setRoles(List.of(role));
        userRoleService.updateRoles(userRoleModel);
        modelAndView.addObject("updateOK", "updateOK");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return userDetail(modelAndView, username);
    }

    @GetMapping("/user/enabled/{username}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView updateEnabledUser(ModelAndView modelAndView, @PathVariable String username) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), "username=" + username, getClass());
        securityService.userAccessValidation("/root/user/enabled/" + username);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/user/enabled/");
        userService.enableDisable(username);
        modelAndView.addObject("updateOK", "updateOK");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return userDetail(modelAndView, username);
    }

    @GetMapping("/gyms")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView gyms(ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.userAccessValidation("/root/gyms");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/gyms");
        modelAndView.setViewName("root/gyms");
        modelAndView.addObject("gymModelList", gymService.findAll());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/gym/{gymId}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView gymDetail(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.userAccessValidation("/root/gym/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/gym/" + gymId);
        modelAndView.setViewName("root/gym-detail");
        modelAndView.addObject("gymModel", gymService.findById(gymId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/gym")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView updateGym(ModelAndView modelAndView, @ModelAttribute("gymModel") GymModel gymModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymModel, getClass());
        securityService.userAccessValidation("/root/gym");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/gym");
        gymService.update(gymModel);
        modelAndView.addObject("updateOK", "updateOK");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return gymDetail(modelAndView, gymModel.getId());
    }

    @GetMapping("/gym/enabled/{gymId}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView updateEnabledGym(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), "gymId=" + gymId, getClass());
        securityService.userAccessValidation("/root/gym/enabled/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/gym/enabled/");
        gymService.enableDisable(gymId);
        modelAndView.addObject("updateOK", "updateOK");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return gymDetail(modelAndView, gymId);
    }

    @GetMapping("/gym/{gymId}/addresses")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView gymAddresses(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.userAccessValidation("/root/gym/" + gymId + "/addresses");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/gym/" + gymId + "/addresses");
        modelAndView.setViewName("root/gym-addresses");
        modelAndView.addObject("gymModel", gymService.findById(gymId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/gym/enabled/address/{gymAddressId}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView updateEnabledGymAddress(ModelAndView modelAndView, @PathVariable Long gymAddressId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), "gymAddressId=" + gymAddressId, getClass());
        securityService.userAccessValidation("/root/gym/enabled/" + gymAddressId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/gym/enabled/");
        GymAddressModel gymAddressModel = gymAddressService.enableDisable(gymAddressId);
        modelAndView.addObject("updateOK", "updateOK");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return gymAddresses(modelAndView, gymAddressModel.getGymModel().getId());
    }

    @GetMapping("/gym/{gymId}/address/{gymAddressId}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView gymAddressDetail(ModelAndView modelAndView, @PathVariable Long gymId, @PathVariable Long gymAddressId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.userAccessValidation("/root/gym/" + gymId + "/address/" + gymAddressId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/gym/" + gymId + "/address/" + gymAddressId);
        modelAndView.setViewName("root/gym-address");
        modelAndView.addObject("gymAddressModel", gymAddressService.findById(gymAddressId));
        modelAndView.addObject("utilListHost", Utils.chargeListHostProvider());
        utilService.chargeBasicDataSelect(modelAndView);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/gym/address")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView updateGymAddress(ModelAndView modelAndView, @ModelAttribute("gymAddressModel") GymAddressModel gymAddressModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymAddressModel, getClass());
        securityService.userAccessValidation("/root/gym/address");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/gym/address");
        gymAddressModel.setEmailPassword(gymAddressService.findById(gymAddressModel.getId()).getEmailPassword());
        gymAddressService.update(gymAddressModel);
        modelAndView.addObject("updateOK", "updateOK");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return gymAddresses(modelAndView, gymAddressModel.getGymModel().getId());
    }


    @GetMapping("/gym/{gymId}/new-address")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView newGymAddress(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.userAccessValidation("/root/gym/" + gymId + "/new-address");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/gym/" + gymId + "/new-address");
        modelAndView.setViewName("root/gym-new-address");
        GymModel gymModel = new GymModel();
        gymModel.setId(gymId);
        GymAddressModel gymAddressModel = new GymAddressModel();
        gymAddressModel.setGymModel(gymModel);
        modelAndView.addObject("gymAddressModel", gymAddressModel);
        modelAndView.addObject("utilListHost", Utils.chargeListHostProvider());
        utilService.chargeBasicDataSelect(modelAndView);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/gym/address/new")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView addGymAddress(ModelAndView modelAndView, @ModelAttribute("gymAddressModel") GymAddressModel gymAddressModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymAddressModel, getClass());
        securityService.userAccessValidation("/root/gym/address/new");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/gym/address/new");
        gymAddressService.add(gymAddressModel);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return gymAddresses(modelAndView, gymAddressModel.getGymModel().getId());
    }

    @GetMapping("/remove-gym-address/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView removeGymAddress(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/root/remove-gym-address/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/remove-gym-address/" + id);
        GymAddressModel gymAddressModel = gymAddressService.findById(id);
        gymAddressService.delete(id);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return gymAddresses(modelAndView, gymAddressModel.getGymModel().getId());
    }

    @GetMapping("/enrollments")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView enrollments(ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.userAccessValidation("/root/enrollments");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/enrollments");
        modelAndView.setViewName("root/enrollments");
        modelAndView.addObject("enrollmentModelList", enrollmentService.findAll());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/enrollment/{enrollmentId}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView enrollmentDetail(ModelAndView modelAndView, @PathVariable Long enrollmentId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.userAccessValidation("/root/enrollment/" + enrollmentId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/enrollment/" + enrollmentId);
        modelAndView.setViewName("root/enrollment-detail");
        modelAndView.addObject("enrollment", enrollmentService.findById(enrollmentId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/menu-bar")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView menuBar(ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.userAccessValidation("/root/menu-bar");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/menu-bar");
        modelAndView.setViewName("root/menu-bar");
        modelAndView.addObject("activityModelList", activityService.findAll());
        modelAndView.addObject("moreRegistrationModelList", moreRegistrationService.findAll());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

}
