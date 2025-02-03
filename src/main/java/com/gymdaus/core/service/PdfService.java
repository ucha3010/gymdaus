package com.gymdaus.core.service;


import com.gymdaus.core.model.EnrollmentModel;
import com.gymdaus.core.model.PdfModel;
import org.springframework.context.MessageSource;

import java.io.File;
import java.util.Locale;

public interface PdfService {
    File createTournament(PdfModel pdfModel, boolean withSignature, MessageSource messageSource, Locale locale);
    File createFederativeLicenseMandate(PdfModel pdfModel, boolean withSignature, MessageSource messageSource, Locale locale);
    File createAuthorizationAdult(PdfModel pdfModel, boolean withSignature, MessageSource messageSource, Locale locale);
    File createAuthorizationAuthorized(PdfModel pdfModel, boolean withSignature, MessageSource messageSource, Locale locale);
    File createSepaDirectDebitForm(PdfModel pdfModel, boolean withSignature, MessageSource messageSource, Locale locale);
    File createWhatsAppAuthorization(PdfModel pdfModel, boolean withSignature, MessageSource messageSource, Locale locale);
    PdfModel getPdfModel (EnrollmentModel enrollmentModel, MessageSource messageSource, Locale locale);
//    void deleteFilesTaekwondoRegistration(InscripcionTaekwondoModel inscripcionTaekwondoModel, User usuario);
//    void deleteByIdOriginalOperativeAndSectionAndIdCard(Integer idOriginalOperative, String section, String idCard);
//    void eraseByIdOriginalOperativeAndSectionAndIdCard(Integer idOriginalOperative, String section, String idCard);
}