package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymCategory;
import com.gymdaus.core.model.*;
import com.gymdaus.core.service.GymBeltService;
import com.gymdaus.core.service.GymCategoryGymBeltService;
import com.gymdaus.core.service.GymPoomsaeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperGymCategory {
    @Autowired
    private GymBeltService gymBeltService;
    @Autowired
    private GymCategoryGymBeltService gymCategoryGymBeltService;
    @Autowired
    private GymPoomsaeService gymPoomsaeService;

    public GymCategoryModel entity2Model(GymCategory externObject) {
        GymCategoryModel localObject = new GymCategoryModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setStartAge(externObject.getStartAge());
            localObject.setEndAge(externObject.getEndAge());
            localObject.setPosition(externObject.getPosition());
            if (externObject.getGymId() != 0) {
                GymModel gymModel = new GymModel();
                gymModel.setId(externObject.getGymId());
                localObject.setGymModel(gymModel);
            }
            if (externObject.getPoomsaeId() != 0) {
                localObject.setGymPoomsaeModel(gymPoomsaeService.findById(externObject.getPoomsaeId()));
            }
            List<GymCategoryGymBeltModel> gymCategoryGymBeltModelList = gymCategoryGymBeltService.findByGymCategory(externObject.getId());
            if (gymCategoryGymBeltModelList != null && !gymCategoryGymBeltModelList.isEmpty()) {
                List<GymBeltModel> gymBeltModelList = new ArrayList<>();
                StringBuilder beltNameList = new StringBuilder();
                for (int i=0; i<gymCategoryGymBeltModelList.size(); i++) {
                    GymBeltModel gymBeltModel = gymBeltService.findById(gymCategoryGymBeltModelList.get(i).getGymBeltModel().getId());
                    gymBeltModelList.add(gymBeltModel);
                    beltNameList.append(gymBeltModel.getColor());
                    if(i < gymCategoryGymBeltModelList.size() - 1) {
                        beltNameList.append(", ");
                    }
                }
                localObject.setBeltNameList(beltNameList.toString());
                localObject.setGymBeltModelList(gymBeltModelList);
            }
        }
        return localObject;
    }

    public GymCategory model2Entity(GymCategoryModel externObject) {
        GymCategory localObject = new GymCategory();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setName(externObject.getName());
            localObject.setStartAge(externObject.getStartAge());
            localObject.setEndAge(externObject.getEndAge());
            localObject.setPosition(externObject.getPosition());
            if (externObject.getGymModel() != null) {
                localObject.setGymId(externObject.getGymModel().getId());
            } else {
                localObject.setGymId(0L);
            }
            if (externObject.getGymPoomsaeModel() != null) {
                localObject.setPoomsaeId(externObject.getGymPoomsaeModel().getId());
            } else {
                localObject.setPoomsaeId(0L);
            }
        }
        return localObject;
    }
}
