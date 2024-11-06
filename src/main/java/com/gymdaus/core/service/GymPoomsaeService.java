package com.gymdaus.core.service;


import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.model.GymPoomsaeModel;

import java.util.List;

public interface GymPoomsaeService {

    List<GymPoomsaeModel> findAllByGymId(Long gymId);

    GymPoomsaeModel findById(Long id);

    void add(GymPoomsaeModel model);

    void update(GymPoomsaeModel model);

    void delete(Long id) throws RemoveException;

    void dragOfPosition(Long gymId, int initialPosition, int finalPosition);

    int findMaxPosition(Long gymId);

}