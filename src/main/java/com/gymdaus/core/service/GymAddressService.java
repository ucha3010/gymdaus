package com.gymdaus.core.service;


import com.gymdaus.core.model.GymAddressModel;

import java.util.List;

public interface GymAddressService {

    List<GymAddressModel> findAll();

    GymAddressModel findById(Long id);

    List<GymAddressModel> findByGymId(Long gymId);

    void add(GymAddressModel model);

    void update(GymAddressModel model);

    void delete(Long id);

    GymAddressModel enableDisable(Long gymAddressId);
}