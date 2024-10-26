package com.gymdaus.core.service;


import com.gymdaus.core.entity.User;
import com.gymdaus.core.model.MainUserModel;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface MainService {

    List<MainUserModel> findByUsername(String username);

    void deleteEnrollment(Long enrollmentId);

    User basicCompleteCharge(ModelAndView modelAndView);
}
