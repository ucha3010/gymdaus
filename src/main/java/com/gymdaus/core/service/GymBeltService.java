package com.gymdaus.core.service;


import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.model.GymBeltModel;

import java.util.List;

public interface GymBeltService {

    List<GymBeltModel> findAllByGymId(Long gymId);

    GymBeltModel findById(Long id);

    void add(GymBeltModel model);

    void update(GymBeltModel model);

    void delete(Long id) throws RemoveException;

    void dragOfPosition(Long gymId, int initialPosition, int finalPosition);

    int findMaxPosition(Long gymId);

}