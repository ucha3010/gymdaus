package com.gymdaus.core.controller;

import com.gymdaus.core.configuration.SessionData;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.CountryService;
import com.gymdaus.core.service.EnrollmentAsService;
import com.gymdaus.core.service.SecurityService;
import com.gymdaus.core.service.UtilService;
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

@Controller
@RequestMapping("/root")
public class RootController {

    @Autowired
    private CountryService countryService;
    @Autowired
    private EnrollmentAsService enrollmentAsService;
    @Autowired
    private SecurityService securityService;
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

}
