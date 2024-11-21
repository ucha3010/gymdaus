package com.gymdaus.core.service.impl;

import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.CountryService;
import com.gymdaus.core.service.Menu1Service;
import com.gymdaus.core.service.UserDocumentManagerService;
import com.gymdaus.core.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Service()
public class UtilServiceImpl implements UtilService {

    @Autowired
    private CountryService countryService;
    @Autowired
    private Menu1Service menu1Service;
    @Autowired
    private UserDocumentManagerService userDocumentManagerService;
    @Autowired
    private UserService userService;

    @Override
    public void chargeBasicDataSelect(ModelAndView modelAndView) {
        modelAndView.addObject("sexList", chargeListSex());
        modelAndView.addObject("countryList", countryService.findAll());
        modelAndView.addObject("yesNoList", chargeListYesNo());
    }

    @Override
    public UserModel basicDataCharge(ModelAndView modelAndView) {
        UserModel user = null;
        try {
            user = userService.getLoggedUserModel();
            user.setPassword(null);
            modelAndView.addObject("user", user);
            modelAndView.addObject("profilePhoto", userDocumentManagerService.getProfilePhotoPath(user.getUsername()));
            //modelAndView.addObject("menu1List", menu1Service.findAllEnabled());
        } catch (ClassCastException e) {
            modelAndView.addObject("notLogged", "notLogged");
        }
        modelAndView.addObject("menu1GymList", menu1Service.findMenu1GymList());
        modelAndView.addObject("menu1ActivityList", menu1Service.findMenu1ActivityList());
        modelAndView.addObject("menu1MoreRegistrationList", menu1Service.findMenu1MoreRegistrationList());
        return user;
    }

    private List<String> chargeListYesNo() {
        return Arrays.asList("yes", "no");
    }

    private List<String> chargeListSex() {
        return Arrays.asList("male", "female");
    }
}
