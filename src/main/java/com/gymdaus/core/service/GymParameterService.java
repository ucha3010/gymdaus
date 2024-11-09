package com.gymdaus.core.service;


import com.gymdaus.core.model.GymParameterModel;

import java.util.List;
import java.util.Map;

public interface GymParameterService {

    List<GymParameterModel> get(Long gymId);

    Map<String, String> getStartWith(Long gymId, String keyDataStart);

    GymParameterModel get(Long gymId, String keyData);

    GymParameterModel update(GymParameterModel gymParameterModel);

    boolean comparePassword(Long gymId, String oldPassword);
}
