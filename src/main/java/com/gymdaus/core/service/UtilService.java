package com.gymdaus.core.service;

import com.gymdaus.core.model.UserModel;
import org.springframework.web.servlet.ModelAndView;

public interface UtilService {

    void chargeBasicDataSelect(ModelAndView modelAndView);
    UserModel basicDataCharge(ModelAndView modelAndView);

}