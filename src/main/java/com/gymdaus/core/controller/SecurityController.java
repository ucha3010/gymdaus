package com.gymdaus.core.controller;

import com.gymdaus.core.exception.SenderException;
import com.gymdaus.core.exception.ValidationException;
import com.gymdaus.core.model.DownloadDocumentModel;
import com.gymdaus.core.model.SignatureModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.EnrollmentService;
import com.gymdaus.core.service.SecurityService;
import com.gymdaus.core.service.SignatureService;
import com.gymdaus.core.service.UtilService;
import com.gymdaus.core.service.impl.UserService;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    private SignatureService signatureService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @Autowired
    private UtilService utilService;
    private final MessageSource messageSource;

    public SecurityController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostMapping("/validate-code")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView validateCode(@ModelAttribute("signatureModel") SignatureModel signatureModel) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), signatureModel, getClass());
        securityService.userAccessValidation("/security/validate-code");
        ModelAndView modelAndView = new ModelAndView();
        UserModel userLogged = utilService.basicDataCharge(modelAndView);
        try {
            List<DownloadDocumentModel> downloadDocumentModelList = new ArrayList<>();
            String codeSentByUser = signatureModel.getCode();
            signatureModel = signatureService.findByOperationIdAndOperationName(signatureModel.getOperationId(), signatureModel.getOperationName());
            securityService.codeValidation(codeSentByUser, userLogged.getUsername(), signatureModel);

            // INFORMACIÓN si tengo que buscar documentación a firmar en otras tablas, se hace acá
            Locale locale = Locale.forLanguageTag(signatureModel.getLanguage());
            LocaleContextHolder.setLocale(locale);
            if (Constants.TABLE_ENROLLMENT.equals(signatureModel.getTableToSearch())) {
                downloadDocumentModelList = enrollmentService.createAndSendActivityEnrollmentFiles(signatureModel, messageSource, locale);
            } else if (Constants.TABLE_GYM_DOCUMENT_MANAGER.equals(signatureModel.getTableToSearch())) {
                // TODO hacer envío de documentación del gimnasio agregando hoja con firma electrónica
            } else {
                throw new ValidationException(Constants.VALIDATION_ADVICE_DATA_IN_ERROR, "there.is.no.data.to.validate");
            }

            modelAndView.addObject("downloadDocumentModelList", downloadDocumentModelList);
            modelAndView.addObject("enrollmentOk", "enrollment.ok");
            modelAndView.addObject("signatureId", signatureModel.getId());
            modelAndView.setViewName("signature/signingProcessCompleted");
        } catch (ValidationException ve) {
            LoggerMapper.log(Level.ERROR, Utils.getMethodName(), ve.getMessage(), getClass());
            modelAndView.addObject("signatureModel", new SignatureModel(signatureModel.getOperationId(), signatureModel.getOperationName()));
            modelAndView.addObject("enrollmentError", ve.getMessage());
            modelAndView.setViewName("signature/sendCode");
        } catch (SenderException se) {
            LoggerMapper.log(Level.ERROR, Utils.getMethodName(), se.getMessage(), getClass());
            modelAndView.addObject("signatureModel", new SignatureModel(signatureModel.getOperationId(), signatureModel.getOperationName()));
            modelAndView.addObject("enrollmentError", "error.sending.email");
            modelAndView.setViewName("signature/sendCode");
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @GetMapping("/send-new-code/{operationName}/{operationId}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView sendNewCode(ModelAndView modelAndView, @PathVariable String operationName, @PathVariable Long operationId, @RequestParam String language) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), "operationName=" + operationName + ", operationId=" + operationId, getClass());
        securityService.userAccessValidation("/security/send-new-code/" + operationName + "/" + operationId);
        UserModel userLogged = utilService.basicDataCharge(modelAndView);
        SignatureModel signatureModel = new SignatureModel(operationId, operationName);
        Locale locale = Locale.forLanguageTag(language);
        LocaleContextHolder.setLocale(locale);
        securityService.sendSignatureCode(modelAndView, signatureModel, userLogged,null, messageSource, locale);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

}
