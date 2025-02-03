package com.gymdaus.core.service;

import com.gymdaus.core.exception.ValidationException;
import com.gymdaus.core.model.SignatureModel;
import com.gymdaus.core.model.UserModel;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;
import java.util.Locale;

public interface SecurityService {

    SignatureModel sendSignatureCode(ModelAndView modelAndView, SignatureModel signatureModel, UserModel userLogged, List<File> files, MessageSource messageSource, Locale locale);

    void codeValidation(String codeSentByUser, String dni, SignatureModel signatureModel) throws ValidationException;

    void enabledAdministrationGym(Long gymId, String uri) throws AccessDeniedException;

    void enabledAdministrationGymUser(String username, Long gymId, boolean managerRequired, String uri) throws AccessDeniedException;

    void userAccessValidation(String uri) throws AccessDeniedException;
    void assignUserLoggedToSession();

    void roleValidation(String username, String role, String uri) throws AccessDeniedException;

    void compareUserValidation(String loggedUsername, String compareUsername, String uri) throws AccessDeniedException;
}
