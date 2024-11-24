package com.gymdaus.core.controller;

import com.gymdaus.core.configuration.SessionData;
import com.gymdaus.core.exception.ValidationException;
import com.gymdaus.core.model.TokenModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.*;
import com.gymdaus.core.service.impl.UserService;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private GymUserService gymUserService;
    @Autowired
    private MainService mainService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private SessionData sessionData;

    @GetMapping("/")
    @PreAuthorize("permitAll()")
    public ModelAndView mainPage(ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        modelAndView.setViewName("main-page");
        String path = "files" + File.separator + "photos" + File.separator + "mainPage";
        utilService.basicDataCharge(modelAndView);
        List<String> photoList = new ArrayList<>(Utils.getFileName(path));
        if (!photoList.isEmpty()) {
            modelAndView.addObject("mainPhoto", photoList.get(0));
        }
        modelAndView.addObject("photoList", mainService.getPhotoList());
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/logged")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView logged(ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        securityService.assignUserLoggedToSession();
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

    @GetMapping("/new-manager")
    @PreAuthorize("permitAll()")
    public ModelAndView newManager(@RequestParam String token, ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), token, getClass());
        if (Utils.isNullOrEmpty(token)) {
            throw new AccessDeniedException("/new-manager, token=" + token);
        }
        utilService.basicDataCharge(modelAndView);
        try {
            TokenModel tokenModel = tokenService.verifyToken(token, Utils.getMethodName());
            gymUserService.addNewManager(tokenModel);
            userRoleService.assignOneRole(tokenModel.getUsername(), Constants.ROLE_ADMIN);
            tokenService.tokenUsedOk(tokenModel);
            modelAndView.addObject("gymUserAssigned", "gymUserAssigned");
        } catch (ValidationException ve) {
            modelAndView.addObject("expiredCall", "expiredCall");
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return mainPage(modelAndView);
    }

}
