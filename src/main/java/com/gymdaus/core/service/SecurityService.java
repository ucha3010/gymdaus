package com.gymdaus.core.service;

import com.gymdaus.core.entity.User;
import com.gymdaus.core.exception.ValidationException;
import com.gymdaus.core.model.SignatureCodeModel;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

public interface SecurityService {

    String getCode();

    ModelAndView sendSignatureCode(ModelAndView modelAndView, SignatureCodeModel signatureCodeModel, User userLogged, List<File> files);

    void codeValidation(String codeSentByUser, String dni, SignatureCodeModel signatureCodeModel) throws ValidationException;

    void attemptsValidation(Long operationId) throws ValidationException;

    void enabledAdministrationGym(Long gymId, String uri) throws AccessDeniedException;

    void enabledAdministrationGymUser(String username, Long gymId, String uri) throws AccessDeniedException;

    void userAccessValidation(String uri) throws AccessDeniedException;

    void roleValidation(String username, String role, String uri) throws AccessDeniedException;
}
