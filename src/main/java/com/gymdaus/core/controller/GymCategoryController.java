package com.gymdaus.core.controller;

import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.model.GymBeltModel;
import com.gymdaus.core.model.GymCategoryGymBeltModel;
import com.gymdaus.core.model.GymCategoryModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.*;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/gymCategory")
public class GymCategoryController {

    @Autowired
    private GymBeltService gymBeltService;
    @Autowired
    private GymCategoryGymBeltService gymCategoryGymBeltService;
    @Autowired
    private GymCategoryService gymCategoryService;
    @Autowired
    private GymPoomsaeService gymPoomsaeService;
    @Autowired
    private GymService gymService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UtilService utilService;

    @GetMapping("/categories/{gymId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView categories(ModelAndView modelAndView, @PathVariable Long gymId) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gymCategory/categories/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, false, "/gymCategory/categories/");
        modelAndView.setViewName("gym/categories");
        modelAndView.addObject("gymCategoryModel", new GymCategoryModel());
        modelAndView.addObject("gymCategoryModelList", gymCategoryService.findAllByGymId(gymId));
        modelAndView.addObject("gymPoomsaeModelList", gymPoomsaeService.findAllByGymId(gymId));
        modelAndView.addObject("gymBeltModelList", gymBeltService.findAllByGymId(gymId));
        modelAndView.addObject("gymModel", gymService.findByIdEnabled(gymId));
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/change-category/{gymId}/{oldIndex}/{newIndex}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView changeCategory(ModelAndView modelAndView, @PathVariable Long gymId, @PathVariable int oldIndex, @PathVariable int newIndex) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymId, getClass());
        securityService.userAccessValidation("/gymCategory/change-category/" + gymId);
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymId, false, "/gymCategory/change-category/" + gymId);
        gymCategoryService.dragOfPosition(gymId, oldIndex, newIndex);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return categories(modelAndView, gymId);
    }

    @PostMapping("/category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addCategory(ModelAndView modelAndView, @ModelAttribute("gymCategoryModel") GymCategoryModel gymCategoryModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), gymCategoryModel, getClass());
        securityService.userAccessValidation("/gymCategory/category");
        UserModel user = utilService.basicDataCharge(modelAndView);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymCategoryModel.getGymModel().getId(), false, "/gymCategory/category");
        gymCategoryModel.setPosition(gymCategoryService.findMaxPosition(gymCategoryModel.getGymModel().getId()) + 1);
        List<Long> beltIdList = gymCategoryModel.getBeltIdList();
        gymCategoryModel = gymCategoryService.add(gymCategoryModel);
        if (beltIdList != null) {
            GymCategoryGymBeltModel gymCategoryGymBeltModel = new GymCategoryGymBeltModel();
            gymCategoryGymBeltModel.setRegistrationUser(user.getUsername());
            gymCategoryGymBeltModel.setRegistrationDate(new Date());
            gymCategoryGymBeltModel.setGymCategoryModel(gymCategoryModel);
            GymBeltModel gymBeltModel = new GymBeltModel();
            for (Long id : beltIdList) {
                gymBeltModel.setId(id);
                gymCategoryGymBeltModel.setGymBeltModel(gymBeltModel);
                gymCategoryGymBeltService.add(gymCategoryGymBeltModel);
            }
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return categories(modelAndView, gymCategoryModel.getGymModel().getId());
    }

    @GetMapping("/remove-category/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView removeCategory(ModelAndView modelAndView, @PathVariable Long id) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), id, getClass());
        securityService.userAccessValidation("/gymCategory/remove-category/" + id);
        UserModel user = utilService.basicDataCharge(modelAndView);
        GymCategoryModel gymCategoryModel = gymCategoryService.findById(id);
        securityService.enabledAdministrationGymUser(user.getUsername(), gymCategoryModel.getGymModel().getId(), false, "/gymCategory/remove-category/");
        try {
            gymCategoryService.delete(id);
        } catch (RemoveException re) {
            modelAndView.addObject("removeProblem", re.getMessage());
            LoggerMapper.log(Level.ERROR, Utils.getMethodName(), re.getMessage(), this.getClass());
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return categories(modelAndView, gymCategoryModel.getGymModel().getId());
    }

}
