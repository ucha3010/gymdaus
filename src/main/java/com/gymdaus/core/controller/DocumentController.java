package com.gymdaus.core.controller;

import com.gymdaus.core.model.EnrollmentModel;
import com.gymdaus.core.model.PdfModel;
import com.gymdaus.core.model.SignatureModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.EnrollmentService;
import com.gymdaus.core.service.SecurityService;
import com.gymdaus.core.service.SignatureService;
import com.gymdaus.core.service.impl.UserService;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.Locale;

@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private SignatureService signatureService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    private final MessageSource messageSource;

    public DocumentController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/download/{documentKind}/{signatureId}")
    @PreAuthorize("isAuthenticated()")
    public void downloadDocument(@PathVariable int documentKind, @PathVariable Long signatureId, HttpServletResponse response) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), "documentKind=" + documentKind + ", signatureId=" + signatureId, getClass());
        securityService.userAccessValidation("/document/download/" + documentKind + "/" + signatureId);
        UserModel userLogged = userService.getLoggedUserModel();
        SignatureModel signatureModel = signatureService.findById(signatureId);
        if (signatureModel != null && userLogged.getUsername().equals(signatureModel.getUsername())) {
            File file;
            // INFORMACIÓN si tengo que buscar documentación en otras tablas, se hace acá
            if (Constants.TABLE_ENROLLMENT.equals(signatureModel.getTableToSearch())) {
                EnrollmentModel enrollmentModel = enrollmentService.findById(signatureModel.getOperationId());
                Locale locale = Locale.forLanguageTag(enrollmentModel.getDocumentLanguage());
                LocaleContextHolder.setLocale(locale);
                PdfModel pdfModel = enrollmentService.getPdfModel(enrollmentModel, messageSource, locale);
                file = enrollmentService.getFile(documentKind, pdfModel, signatureModel.isSigned(), messageSource, locale);
                Utils.downloadFile(file.getAbsolutePath(), file.getName(), response);
            } else if (Constants.TABLE_GYM_DOCUMENT_MANAGER.equals(signatureModel.getTableToSearch())) {
                // TODO hacer envío de documentación agregando hoja con firma electrónica
                securityService.enabledAdministrationGymUser(userLogged.getUsername(), signatureModel.getGymModel().getId(), false, "/document/download/" + documentKind + "/" + signatureId);
            }
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), "Download ok", getClass());
    }

}
