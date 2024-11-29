package com.gymdaus.core.service;


import com.gymdaus.core.exception.RemoveException;
import com.gymdaus.core.model.ParticipatingEntityModel;

import java.util.List;

public interface ParticipatingEntityService {

    List<ParticipatingEntityModel> findAllByGymId(Long gymId);

    ParticipatingEntityModel findById(Long id);

    void add(ParticipatingEntityModel model);

    void update(ParticipatingEntityModel model);

    void delete(Long id) throws RemoveException;

    void dragOfPosition(Long gymId, int initialPosition, int finalPosition);

    int findMaxPosition(Long gymId);
}