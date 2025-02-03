package com.gymdaus.core.service;


import com.gymdaus.core.exception.SenderException;
import com.gymdaus.core.model.DownloadDocumentModel;
import com.gymdaus.core.model.EnrollmentModel;
import com.gymdaus.core.model.PdfModel;
import com.gymdaus.core.model.SignatureModel;
import org.springframework.context.MessageSource;

import java.io.File;
import java.util.List;
import java.util.Locale;

public interface EnrollmentService {

    List<EnrollmentModel> findAll();

    List<EnrollmentModel> findByUsername(String username);

    List<EnrollmentModel> findByGymId(Long gymId);

    EnrollmentModel findById(Long id);

    EnrollmentModel add(EnrollmentModel model);

    void update(EnrollmentModel model);

    void delete(Long id);

    void fillActivityEnrollment(EnrollmentModel enrollmentModel);

    List<DownloadDocumentModel> createAndSendActivityEnrollmentFiles(SignatureModel signatureModel, MessageSource messageSource, Locale locale) throws SenderException;

    void fillFilesAndDownloadDocumentModelList(List<DownloadDocumentModel> downloadDocumentModelList, List<File> files, EnrollmentModel enrollmentModel, SignatureModel signatureModel, MessageSource messageSource, Locale locale);

    PdfModel getPdfModel(EnrollmentModel enrollmentModel, MessageSource messageSource, Locale locale);

    File getFile(int documentKind, PdfModel pdfModel, boolean withSignature, MessageSource messageSource, Locale locale);
}