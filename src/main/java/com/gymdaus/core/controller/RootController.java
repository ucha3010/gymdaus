package com.gymdaus.core.controller;

import com.gymdaus.core.configuration.SessionData;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/root")
public class RootController {

    @Autowired
    private CountryService countryService;
    @Autowired
    private EnrollmentAsService enrollmentAsService;
    @Autowired
    private GymService gymService;
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
    public ModelAndView userRole(ModelAndView modelAndView, @PathVariable String username, @PathVariable String role) {
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
    public ModelAndView updateEnabled(ModelAndView modelAndView, @PathVariable String username) {
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
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/gym/user/");
        modelAndView.setViewName("root/gym-detail");
        modelAndView.addObject("gymModel", gymService.findById(gymId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/gym/enabled/{gymId}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView updateEnabled(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), "gymId=" + gymId, getClass());
        securityService.userAccessValidation("/root/gym/enabled/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.roleValidation(user.getUsername(), Constants.ROLE_ROOT, "/root/gym/enabled/");
        gymService.enableDisable(gymId);
        modelAndView.addObject("updateOK", "updateOK");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return gymDetail(modelAndView, gymId);
    }

}
