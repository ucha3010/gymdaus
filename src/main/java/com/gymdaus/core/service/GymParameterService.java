package com.gymdaus.core.service;


import com.gymdaus.core.model.GymParameterModel;

import java.util.List;

public interface GymParameterService {

    List<GymParameterModel> get(Long gymId);

    List<GymParameterModel> getStartWith(Long gymId, String keyDataStart);

    GymParameterModel get(Long gymId, String keyData);

    GymParameterModel update(GymParameterModel gymParameterModel);

    boolean comparePassword(Long gymId, String oldPassword);
}
