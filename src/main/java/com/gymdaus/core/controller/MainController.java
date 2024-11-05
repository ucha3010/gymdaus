package com.gymdaus.core.controller;

import com.gymdaus.core.configuration.SessionData;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.UtilService;
import com.gymdaus.core.service.impl.UserService;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UtilService utilService;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionData sessionData;

    @GetMapping("/")
    @PreAuthorize("permitAll()")
    public ModelAndView mainPage(ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        modelAndView.setViewName("main-page");
        String path = "src" + File.separator + "main" + File.separator + "resources" + File.separator
                + "static" + File.separator + "imgs" + File.separator + File.separator + "mainPage";
        utilService.basicDataCharge(modelAndView);
        List<String> photoList = new ArrayList<>(Utils.getFileName(path));
        if (!photoList.isEmpty()) {
            modelAndView.addObject("mainPhoto", photoList.get(0));
            photoList.remove(0);
        }
        modelAndView.addObject("photoList", photoList);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/logged")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView logged(ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        sessionData.setUserModel(userService.getLoggedUserModel());
        return mainPage(modelAndView);
    }

    @GetMapping("/logout-close")
    @PreAuthorize("permitAll()")
    public ModelAndView logoutClose(ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        sessionData.setUserModel(new UserModel());
        return mainPage(modelAndView);
    }

    @GetMapping("/favicon.ico")
    String favicon() {
        return "redirect:/imgs/favicon.ico";
    }

}
