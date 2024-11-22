package com.gymdaus.core.service;


import com.gymdaus.core.model.Menu2Model;

import java.util.List;

public interface Menu2Service {

    List<Menu2Model> findAllGymList();

    List<Menu2Model> findAllActivityList();

    List<Menu2Model> findAllMoreRegistrationList();
}
