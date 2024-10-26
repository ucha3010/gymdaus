package com.gymdaus.core.service;


import com.gymdaus.core.model.GymParameterModel;

import java.util.List;

public interface GymParameterService {

    List<GymParameterModel> get(Long gymId);

    GymParameterModel get(Long gymId, String keyData);

    GymParameterModel update(GymParameterModel gymParameterModel);
}
