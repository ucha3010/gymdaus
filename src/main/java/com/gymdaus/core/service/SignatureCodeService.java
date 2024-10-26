package com.gymdaus.core.service;


import com.gymdaus.core.model.SignatureCodeModel;

import java.util.List;

public interface SignatureCodeService {

    List<SignatureCodeModel> findAll();

    SignatureCodeModel findById(Long id);

    SignatureCodeModel add(SignatureCodeModel model);

    SignatureCodeModel update(SignatureCodeModel model);

    void delete(Long id);

    SignatureCodeModel findByOperationId(Long operationId);

    void cleanExpiredSignatures();

}