package com.gymdaus.core.service;


import com.gymdaus.core.model.GymPoomsaeModel;

import java.util.List;

public interface GymPoomsaeService {

    List<GymPoomsaeModel> findAll();

    GymPoomsaeModel findById(Long id);

    void add(GymPoomsaeModel model);

    void update(GymPoomsaeModel model);

    void delete(Long id);

    void dragOfPosition(int initialPosition, int finalPosition);

    int findMaxPosition();

}