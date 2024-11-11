package com.gymdaus.core.service.impl;

import com.gymdaus.core.configuration.SessionData;
import com.gymdaus.core.entity.User;
import com.gymdaus.core.exception.ValidationException;
import com.gymdaus.core.model.GymUserModel;
import com.gymdaus.core.model.SignatureCodeModel;
import com.gymdaus.core.model.SignatureModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.*;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;
import java.util.Random;


@Service()
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private SignatureServiceImpl signatureService;
    @Autowired
    private SignatureCodeService signatureCodeService;
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
    public String getCode() {
        String alphabet = "ABCDEFGHIJKLMNPQRSTUVWXYZ123456789";

        Random r = new Random();
        StringBuilder code = new StringBuilder();
        for (int i=0; i < 6; i++) {
            code.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        return code.toString();
    }

    @Override
    public ModelAndView sendSignatureCode(ModelAndView modelAndView, SignatureCodeModel signatureCodeModel, User userLogged, List<File> files) {
        modelAndView.setViewName("firma/envioCodigo");
        try {
            signatureCodeModel = signatureCodeService.add(signatureCodeModel);
            emailService.sendCodeValidation(userLogged, signatureCodeModel.getCode(), files);
        } catch (Exception e) {
//            LoggerMapper.log(Level.ERROR, signatureCodeModel.getOperativaOriginal(), e.getMessage(), getClass());
        }

        if (signatureCodeModel.getId() != 0) {
            modelAndView.addObject("direccionCorreo", Utils.obfuscate(userLogged.getEmail()));
/*            modelAndView.addObject("signatureCodeModel", new SignatureCodeModel(signatureCodeModel.getIdOperacion(),
                    null, null, null, signatureCodeModel.getOperativaOriginal(), signatureCodeModel.getCodigoGimnasio()));*/
        } else {
            modelAndView.addObject("inscripcionError", "Ha ocurrido un error. Por favor contacte con el soporte técnico.");
        }
        return modelAndView;
    }

    @Override
    public void codeValidation(String codeSentByUser, String dni, SignatureCodeModel signatureCodeModel) throws ValidationException {
        if (signatureCodeModel == null || codeSentByUser == null || dni == null) {
//            throw new ValidationException(Constants.AVISO_VALIDACION_ERROR_DATOS_ENTRADA, "No existen datos a validar");
        } else {
            SignatureModel signatureModel = signatureService.findByOperationId(signatureCodeModel.getOperationId());
            if (signatureModel.getId() == 0) {
//                throw new ValidationException(Constants.AVISO_VALIDACION_TIEMPO_EXCEDIDO, "Tiempo de validación de código excedido");
            }
            signatureModel.setAttempts(signatureModel.getAttempts() + 1);
            if (!codeSentByUser.equals(signatureCodeModel.getCode()) || !dni.equals(signatureCodeModel.getUsername())) {
                signatureService.update(signatureModel);
//                throw new ValidationException(Constants.AVISO_VALIDACION_DATOS_NO_VALIDOS, "Datos de entrada no válidos");
            } else {
                signatureModel.setSigned(Boolean.TRUE);
//                signatureModel.setOperativaOriginal(signatureCodeModel.getOperativaOriginal());
                signatureService.update(signatureModel);
            }
        }
    }

    @Override
    public void attemptsValidation(Long operationId) throws ValidationException {
        SignatureModel signatureModel = signatureService.findByOperationId(operationId);
        if (signatureModel.getAttempts() > 2) {
//            throw new ValidationException(Constants.AVISO_VALIDACION_NUMERO_INTENTOS_SUPERADO, "Ha superado el número de intentos válidos");
        } else if (signatureModel.isSigned()) {
//            throw new ValidationException(Constants.AVISO_VALIDACION_OPERACION_FIRMADA_ANTES, "La operación ha sido firmada con anterioridad");
        } else if (signatureModel.getAttempts() == 0) {
            if (signatureModel.getId() != 0) {
                signatureService.delete(signatureModel.getId());
            }
            signatureService.add(new SignatureModel(operationId));
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
            if (gymUserModel.getGymModel().getId().equals(gymId)) {
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
        if (user == null || Utils.isNullOrEmpty(user.getUsername()) || sessionData.getUserModel() == null ||
                Utils.isNullOrEmpty(sessionData.getUserModel().getUsername()) ||
                !user.getUsername().equalsIgnoreCase(sessionData.getUserModel().getUsername())) {
            throw new AccessDeniedException(uri);
        }
    }

    @Override
    public void roleValidation(String username, String role, String uri) throws AccessDeniedException {
        if (Utils.isNullOrEmpty(username)) {
            throw new AccessDeniedException(uri);
        } else {
            UserModel user = userService.findModelByUsername(username);
            if(user.getUserRoles() == null || !user.getUserRoles().contains(role)) {
                throw new AccessDeniedException(uri);
            }
        }
    }
}
