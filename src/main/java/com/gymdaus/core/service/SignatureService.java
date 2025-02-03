package com.gymdaus.core.service;


import com.gymdaus.core.model.SignatureModel;

import java.util.List;

public interface SignatureService {

    List<SignatureModel> findAll();

    SignatureModel findById(Long id);

    SignatureModel addOrUpdate(SignatureModel model);

    void delete(Long id);

    SignatureModel findByOperationIdAndOperationName(Long operationId, String operationName);
}