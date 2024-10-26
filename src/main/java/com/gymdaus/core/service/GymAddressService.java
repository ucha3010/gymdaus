package com.gymdaus.core.service;


import com.gymdaus.core.model.GymAddressModel;

import java.util.List;

public interface GymAddressService {

    List<GymAddressModel> findAll();

    GymAddressModel findById(Long id);

    void add(GymAddressModel model);

    void update(GymAddressModel model);

    void delete(Long id);

}