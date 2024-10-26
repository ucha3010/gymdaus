package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.Signature;
import com.gymdaus.core.mapper.MapperSignature;
import com.gymdaus.core.model.SignatureModel;
import com.gymdaus.core.repository.SignatureRepository;
import com.gymdaus.core.service.SignatureService;
import com.gymdaus.core.util.LoggerMapper;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
public class SignatureServiceImpl implements SignatureService {

    @Autowired
    private SignatureRepository signatureRepository;

    @Autowired
    private MapperSignature mapperSignature;

    @Override
    public List<SignatureModel> findAll() {
        List<SignatureModel> signatureModelList = new ArrayList<>();
        for (Signature signature : signatureRepository.findAll()) {
            signatureModelList.add(mapperSignature.entity2Model(signature));
        }
        return signatureModelList;
    }

    @Override
    public SignatureModel findById(Long id) {
        try {
            return mapperSignature.entity2Model(signatureRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new SignatureModel();
        }
    }

    @Override
    public SignatureModel add(SignatureModel signatureModel) {
        SignatureModel signatureModel1 = mapperSignature.entity2Model(signatureRepository.save(mapperSignature.model2Entity(signatureModel)));
        LoggerMapper.methodOut(Level.INFO, "add", signatureModel1, getClass());
        return signatureModel1;
    }

    @Override
    public SignatureModel update(SignatureModel signatureModel) {
        return mapperSignature.entity2Model(signatureRepository.save(mapperSignature.model2Entity(signatureModel)));
    }

    @Override
    public void delete(Long idSignature) {
        try {
            Signature signature = signatureRepository.findById(idSignature).orElseThrow(() -> new EntityNotFoundException("Object not found"));
            signatureRepository.delete(signature);
            LoggerMapper.methodOut(Level.INFO, "delete", signature, getClass());
        } catch (EntityNotFoundException e) {
            LoggerMapper.log(Level.ERROR, "delete", "id " + idSignature + " not found", this.getClass());
        }
    }

    @Override
    public SignatureModel findByOperationId(Long operationId) {
        try {
            return mapperSignature.entity2Model(signatureRepository.findByOperationId(operationId));
        } catch (EntityNotFoundException e) {
            return new SignatureModel();
        }
    }
}
