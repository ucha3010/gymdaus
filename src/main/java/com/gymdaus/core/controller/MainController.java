package com.gymdaus.core.controller;

import com.gymdaus.core.entity.User;
import com.gymdaus.core.service.MainService;
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
@RequestMapping("/main")
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView mainPage(ModelAndView modelAndView) {
        modelAndView.setViewName("mainPage");
        String path = "src" + File.separator + "main" + File.separator + "resources" + File.separator
                + "static" + File.separator + "imgs" + File.separator + File.separator + "principal";
        User user = mainService.basicCompleteCharge(modelAndView);
        List<String> photoList = new ArrayList<>(Utils.getFileName(path));
        if (!photoList.isEmpty()) {
            modelAndView.addObject("mainPhoto", photoList.get(0));
            photoList.remove(0);
        }
        modelAndView.addObject("photoList", photoList);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

}
