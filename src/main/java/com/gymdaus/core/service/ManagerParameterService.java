package com.gymdaus.core.service;


import com.gymdaus.core.model.ManagerParameterModel;

public interface ManagerParameterService {

    ManagerParameterModel get();

    ManagerParameterModel update(ManagerParameterModel managerParameterModel);

    void updateNoPass(ManagerParameterModel managerParameterModel);

    boolean comparePassword(String oldPassword);
}
