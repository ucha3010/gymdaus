package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.SignatureCode;
import com.gymdaus.core.mapper.MapperSignatureCode;
import com.gymdaus.core.model.SignatureCodeModel;
import com.gymdaus.core.model.SignatureModel;
import com.gymdaus.core.repository.SignatureCodeRepository;
import com.gymdaus.core.service.SignatureCodeService;
import com.gymdaus.core.service.SignatureService;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service()
public class SignatureCodeServiceImpl implements SignatureCodeService {

    @Autowired
    private SignatureCodeRepository signatureCodeRepository;

    @Autowired
    private MapperSignatureCode mapperSignatureCode;
    @Autowired
    private SignatureService signatureService;

    @Override
    public List<SignatureCodeModel> findAll() {
        List<SignatureCodeModel> signatureCodeModelList = new ArrayList<>();
        for (SignatureCode signatureCode : signatureCodeRepository.findAll()) {
            signatureCodeModelList.add(mapperSignatureCode.entity2Model(signatureCode));
        }
        return signatureCodeModelList;
    }

    @Override
    public SignatureCodeModel findById(Long id) {
        try {
            return mapperSignatureCode.entity2Model(signatureCodeRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new SignatureCodeModel();
        }
    }

    @Override
    public SignatureCodeModel add(SignatureCodeModel signatureCodeModel) {
        Date now = new Date();
        signatureCodeModel.setRegistrationDate(now);
        signatureCodeModel.setExpirationDate(Utils.addSubtractMinutes(15));
        List<SignatureCode> signatureCodeList = signatureCodeRepository.findByOperationId(signatureCodeModel.getOperationId());
        if (signatureCodeList != null && !signatureCodeList.isEmpty()) {
            for (SignatureCode signatureCode : signatureCodeList) {
                signatureCodeRepository.delete(signatureCode);
            }
        }
        SignatureModel signatureModel = signatureService.findByOperationId(signatureCodeModel.getOperationId());
        if (signatureModel.getAttempts() > 0) {
            signatureModel.setAttempts(0);
            signatureService.update(signatureModel);
        }
        SignatureCodeModel signatureCodeModel1 = mapperSignatureCode.entity2Model(signatureCodeRepository.save(mapperSignatureCode.model2Entity(signatureCodeModel)));
        LoggerMapper.methodOut(Level.INFO, "add", signatureCodeModel1, getClass());
        return signatureCodeModel1;
    }

    @Override
    public SignatureCodeModel update(SignatureCodeModel signatureCodeModel) {
        return mapperSignatureCode.entity2Model(signatureCodeRepository.save(mapperSignatureCode.model2Entity(signatureCodeModel)));
    }

    @Override
    public void delete(Long idSignatureCode) {
        try {
            SignatureCode signatureCode = signatureCodeRepository.findById(idSignatureCode).orElseThrow(() -> new EntityNotFoundException("Object not found"));
            signatureCodeRepository.delete(signatureCode);
            LoggerMapper.methodOut(Level.INFO, "delete", signatureCode, getClass());
        } catch (EntityNotFoundException e) {
            LoggerMapper.log(Level.ERROR, "delete", "id " + idSignatureCode + " not found", this.getClass());
        }
    }

    @Override
    public SignatureCodeModel findByOperationId(Long operationId) {
        List<SignatureCode> signatureCodeList = signatureCodeRepository.findByOperationId(operationId);
        if (signatureCodeList == null || signatureCodeList.isEmpty()) {
            return new SignatureCodeModel();
        } else {
            return mapperSignatureCode.entity2Model(signatureCodeList.get(0));
        }
    }

    @Override
    public void cleanExpiredSignatures() {
        List<SignatureCode> signatureCodeList = signatureCodeRepository.findAllByOrderByExpirationDateDesc();
        for (SignatureCode signatureCode : signatureCodeList) {
            if (Utils.millisecondsBetweenTwoDates(signatureCode.getExpirationDate(), new Date()) < 0) {
                delete(signatureCode.getId());
            }
        }
    }
}
