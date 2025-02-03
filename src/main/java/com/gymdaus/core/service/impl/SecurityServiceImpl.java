package com.gymdaus.core.service.impl;

import com.gymdaus.core.configuration.SessionData;
import com.gymdaus.core.entity.User;
import com.gymdaus.core.exception.ValidationException;
import com.gymdaus.core.model.GymUserModel;
import com.gymdaus.core.model.SignatureModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.*;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;


@Service()
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private SignatureServiceImpl signatureService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private GymService gymService;
    @Autowired
    private GymUserService gymUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionData sessionData;

    @Override
    public SignatureModel sendSignatureCode(ModelAndView modelAndView, SignatureModel signatureModel, UserModel userLogged, List<File> files, MessageSource messageSource, Locale locale) {
        modelAndView.setViewName("signature/sendCode");
        try {
            SignatureModel signatureModelPrevious = signatureService.findByOperationIdAndOperationName(signatureModel.getOperationId(), signatureModel.getOperationName());
            if (signatureModelPrevious != null) {
                fillSignature(signatureModel, signatureModelPrevious);
            }
            if (signatureModel.isSigned()) {
                throw new ValidationException(Constants.VALIDATION_ADVICE_OPERATION_SIGNED_PREVIOUSLY, "Operation.signed.previously");
            } else if (signatureModel.isSignatureLocked()) {
                throw new ValidationException(Constants.VALIDATION_ADVICE_OPERATION_SIGNATURE_LOCKED, "Operation.signature.locked");
            }
            signatureModel.setCode(getCode());
            signatureModel.setAttempts(0);
            signatureModel.setRegistrationDate(new Date());
            signatureModel.setExpirationDate(Utils.addSubtractMinutes(15));
            SignatureModel signatureModelAux = signatureModel;
            signatureModel = signatureService.addOrUpdate(signatureModelAux);
            emailService.sendCodeValidation(userLogged, signatureModel.getCode(), files, messageSource, locale);
            modelAndView.addObject("userEmail", Utils.obfuscate(userLogged.getEmail()));
            modelAndView.addObject("signatureModel", new SignatureModel(signatureModel.getOperationId(), signatureModel.getOperationName()));
        } catch (ValidationException e) {
            LoggerMapper.log(Level.ERROR, signatureModel.getOperationName(), e.getMessage(), getClass());
            modelAndView.addObject("enrollmentError", messageSource.getMessage( e.getMessage(), null, locale));
        } catch (Exception e) {
            LoggerMapper.log(Level.ERROR, signatureModel.getOperationName(), e.getMessage(), getClass());
            modelAndView.addObject("enrollmentError", messageSource.getMessage("error.sending.email", null, locale));
        }
        return signatureModel;
    }

    @Override
    public void codeValidation(String codeSentByUser, String dni, SignatureModel signatureModel) throws ValidationException {
        if (Utils.isNullOrEmpty(codeSentByUser) || Utils.isNullOrEmpty(dni)) {
            throw new ValidationException(Constants.VALIDATION_ADVICE_DATA_IN_ERROR, "there.is.no.data.to.validate");
        } else if (signatureModel == null || signatureModel.isSignatureLocked()) {
            throw new ValidationException(Constants.VALIDATION_ADVICE_TIMEOUT, "Code.validation.timeout");
        }
        if (signatureModel.isSigned()) {
            throw new ValidationException(Constants.VALIDATION_ADVICE_OPERATION_SIGNED_PREVIOUSLY, "Operation.signed.previously");
        }
        signatureModel.setAttempts(signatureModel.getAttempts() + 1);
        if (signatureModel.getAttempts() > Constants.MAXIMUM_ATTEMPTS_TO_EVALUATE_CODE) {
            signatureService.addOrUpdate(signatureModel);
            throw new ValidationException(Constants.VALIDATION_ADVICE_EXCEEDED_VALID_ATTEMPTS, "You.have.exceeded.valid.attempts");
        }
        if (!codeSentByUser.equals(signatureModel.getCode()) || !dni.equals(signatureModel.getUsername())) {
            signatureService.addOrUpdate(signatureModel);
            throw new ValidationException(Constants.VALIDATION_ADVICE_INVALID_INPUT_DATA, "Invalid.input.data");
        } else {
            signatureModel.setSigned(Boolean.TRUE);
            signatureService.addOrUpdate(signatureModel);
        }
    }

    @Override
    public void enabledAdministrationGym(Long gymId, String uri) throws AccessDeniedException {
        if (gymService.findByIdEnabled(gymId) == null) {
            throw new AccessDeniedException(uri);
        }
    }

    @Override
    public void enabledAdministrationGymUser(String username, Long gymId, boolean managerRequired, String uri) throws AccessDeniedException {

        List<GymUserModel> gymUserModelList = gymUserService.findByGymId(gymId);
        boolean validAccess = false;
        for (GymUserModel gymUserModel : gymUserModelList) {
            if (gymUserModel.getGymModel().getId().equals(gymId) && gymUserModel.getUserModel().getUsername().equals(username)) {
                if (managerRequired && Constants.ROLE_MANAGER.equals(gymUserModel.getGymRole())) {
                    validAccess = true;
                    break;
                } else if (!managerRequired) {
                    validAccess = true;
                    break;
                }
            }
        }
        if (!validAccess || gymService.findByIdEnabled(gymId) == null) {
            throw new AccessDeniedException(uri + ", gymId=" + gymId);
        }
    }

    @Override
    public void userAccessValidation(String uri) throws AccessDeniedException {
        User user = userService.getLoggedUser();
        if (sessionData.getUserModel() == null || Utils.isNullOrEmpty(sessionData.getUserModel().getUsername())) {
            assignUserLoggedToSession();
        }
        if (user == null || Utils.isNullOrEmpty(user.getUsername()) ||
                Utils.isNullOrEmpty(sessionData.getUserModel().getUsername()) ||
                !user.getUsername().equalsIgnoreCase(sessionData.getUserModel().getUsername())) {
            throw new AccessDeniedException(uri);
        }
    }

    @Override
    public void compareUserValidation(String loggedUsername, String compareUsername, String uri) throws AccessDeniedException {
        if (Utils.isNullOrEmpty(loggedUsername) || Utils.isNullOrEmpty(compareUsername) || !loggedUsername.equalsIgnoreCase(compareUsername)) {
            throw new AccessDeniedException(uri);
        }
    }

    @Override
    public void assignUserLoggedToSession() {
        sessionData.setUserModel(userService.getLoggedUserModel());
    }

    @Override
    public void roleValidation(String username, String role, String uri) throws AccessDeniedException {
        if (Utils.isNullOrEmpty(username)) {
            throw new AccessDeniedException(uri);
        } else {
            UserModel user = userService.findModelByUsername(username);
            if (user.getUserRoles() == null || !user.getUserRoles().contains(role)) {
                throw new AccessDeniedException(uri);
            }
        }
    }

    private void fillSignature(SignatureModel signatureModel, SignatureModel signatureModelPrevious) {
        signatureModel.setId(signatureModelPrevious.getId());
        signatureModel.setGymModel(signatureModelPrevious.getGymModel());
        signatureModel.setSigned(signatureModelPrevious.isSigned());
        signatureModel.setSentCodeAttempts(signatureModelPrevious.getSentCodeAttempts() + 1);
        if (signatureModel.getSentCodeAttempts() > Constants.MAXIMUM_SENT_CODE_ATTEMPTS && !signatureModelPrevious.isSigned()) {
            signatureModel.setSignatureLocked(Boolean.TRUE);
        }
        signatureModel.setTableToSearch(signatureModelPrevious.getTableToSearch());
    }

    private String getCode() {
        String alphabet = "ABCDEFGHIJKLMNPQRSTUVWXYZ123456789";

        Random r = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        return code.toString();
    }
}
