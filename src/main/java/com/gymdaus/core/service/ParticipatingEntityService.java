package com.gymdaus.core.service;


import com.gymdaus.core.model.ParticipatingEntityModel;

import java.util.List;

public interface ParticipatingEntityService {

    List<ParticipatingEntityModel> findAll();

    ParticipatingEntityModel findById(Long id);

    void add(ParticipatingEntityModel model);

    void update(ParticipatingEntityModel model);

    void delete(Long id);

    void dragOfPosition(int initialPosition, int finalPosition);

    int findMaxPosition();

}