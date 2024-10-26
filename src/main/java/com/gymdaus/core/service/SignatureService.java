package com.gymdaus.core.service;


import com.gymdaus.core.model.SignatureModel;

import java.util.List;

public interface SignatureService {

    List<SignatureModel> findAll();

    SignatureModel findById(Long id);

    SignatureModel add(SignatureModel model);

    SignatureModel update(SignatureModel model);

    void delete(Long id);

    SignatureModel findByOperationId(Long operationId);

}