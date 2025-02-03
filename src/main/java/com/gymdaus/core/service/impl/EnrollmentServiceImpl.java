package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.Enrollment;
import com.gymdaus.core.exception.SenderException;
import com.gymdaus.core.mapper.MapperEnrollment;
import com.gymdaus.core.model.*;
import com.gymdaus.core.repository.EnrollmentRepository;
import com.gymdaus.core.service.EnrollmentService;
import com.gymdaus.core.service.PdfService;
import com.gymdaus.core.util.Constants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service()
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private MapperEnrollment mapperEnrollment;
    @Autowired
    private PdfService pdfService;

    @Override
    public List<EnrollmentModel> findAll() {
        List<EnrollmentModel> enrollmentModelList = new ArrayList<>();
        for (Enrollment enrollment : enrollmentRepository.findAll()) {
            enrollmentModelList.add(mapperEnrollment.entity2Model(enrollment));
        }
        return enrollmentModelList;
    }

    @Override
    public List<EnrollmentModel> findByUsername(String username) {
        List<EnrollmentModel> enrollmentModelList = new ArrayList<>();
        for (Enrollment enrollment : enrollmentRepository.findByUsername(username)) {
            enrollmentModelList.add(mapperEnrollment.entity2Model(enrollment));
        }
        return enrollmentModelList;
    }

    @Override
    public List<EnrollmentModel> findByGymId(Long gymId) {
        List<EnrollmentModel> enrollmentModelList = new ArrayList<>();
        for (Enrollment enrollment : enrollmentRepository.findByGymId(gymId)) {
            enrollmentModelList.add(mapperEnrollment.entity2Model(enrollment));
        }
        return enrollmentModelList;
    }

    @Override
    public EnrollmentModel findById(Long id) {
        try {
            return mapperEnrollment.entity2Model(enrollmentRepository.findById(id).orElse(null));
        } catch (EntityNotFoundException e) {
            return new EnrollmentModel();
        }
    }

    @Override
    public EnrollmentModel add(EnrollmentModel enrollmentModel) {
        return mapperEnrollment.entity2Model(enrollmentRepository.save(mapperEnrollment.model2Entity(enrollmentModel)));
    }

    @Override
    public void update(EnrollmentModel enrollmentModel) {
        enrollmentRepository.save(mapperEnrollment.model2Entity(enrollmentModel));
    }

    @Override
    public void delete(Long id) {
        enrollmentRepository.deleteById(id);
    }

    @Override
    public void fillActivityEnrollment(EnrollmentModel enrollmentModel) {
        enrollmentModel.setEnrollmentDate(new Date());
        enrollmentModel.setName(enrollmentModel.getGymActivityScheduleModel().getName());
        enrollmentModel.setGymActivityModel(enrollmentModel.getGymActivityScheduleModel().getGymActivityModel());
        GymAddressModel gymAddressModel = enrollmentModel.getGymActivityScheduleModel().getGymAddressModel();
        enrollmentModel.setAddressStreet(gymAddressModel.getAddressStreet());
        enrollmentModel.setAddressNumber(gymAddressModel.getAddressNumber());
        enrollmentModel.setAddressOther(gymAddressModel.getAddressOther());
        enrollmentModel.setAddressCity(gymAddressModel.getAddressCity());
        enrollmentModel.setAddressZip(gymAddressModel.getAddressZip());
        enrollmentModel.setAddressCountry(gymAddressModel.getCountryModel().getName());
        UserModel userModel = enrollmentModel.getUserModel();
        enrollmentModel.setAuthorizerEnrollmentName(userModel.getName());
        enrollmentModel.setAuthorizerEnrollmentLastname(userModel.getLastname());
        enrollmentModel.setAuthorizerEnrollmentSecondLastname(userModel.getSecondLastname());
        enrollmentModel.setAuthorizerEnrollmentBirthdate(userModel.getBirthdate());
        enrollmentModel.setAuthorizerEnrollmentAddressStreet(userModel.getAddressStreet());
        enrollmentModel.setAuthorizerEnrollmentAddressNumber(userModel.getAddressNumber());
        enrollmentModel.setAuthorizerEnrollmentAddressOther(userModel.getAddressOther());
        enrollmentModel.setAuthorizerEnrollmentAddressCity(userModel.getAddressCity());
        enrollmentModel.setAuthorizerEnrollmentAddressZip(userModel.getAddressZip());
        enrollmentModel.setAuthorizerEnrollmentAddressCountry(userModel.getCountryModel().getName());
        enrollmentModel.setEmail(userModel.getEmail());
        enrollmentModel.setPhone(userModel.getPhone());
        if (enrollmentModel.isOwn()) {
            enrollmentModel.setUserEnrollmentName(userModel.getName());
            enrollmentModel.setUserEnrollmentLastname(userModel.getLastname());
            enrollmentModel.setUserEnrollmentSecondLastname(userModel.getSecondLastname());
            enrollmentModel.setUserEnrollmentIdCard(userModel.getUsername());
            enrollmentModel.setUserEnrollmentBirthdate(userModel.getBirthdate());
            enrollmentModel.setUserEnrollmentSex(userModel.getSex());
        }
        if (!enrollmentModel.getGymActivityScheduleModel().isFederationForm()) {
            enrollmentModel.setFederationForm(Boolean.FALSE);
        }
        if (!enrollmentModel.getGymActivityScheduleModel().isWhatsappForm()) {
            enrollmentModel.setWhatsappForm(Boolean.FALSE);
        }
    }

    @Override
    public PdfModel getPdfModel(EnrollmentModel enrollmentModel, MessageSource messageSource, Locale locale) {
        return pdfService.getPdfModel(enrollmentModel, messageSource, locale);
    }

    @Override
    public File getFile(int documentKind, PdfModel pdfModel, boolean withSignature, MessageSource messageSource, Locale locale) {
        File file = null;
        switch (documentKind) {
            case Constants.AUTHORIZATION:
                if (pdfModel.isOwn()) {
                    file = pdfService.createAuthorizationAdult(pdfModel, withSignature, messageSource, locale);
                } else {
                    file = pdfService.createAuthorizationAuthorized(pdfModel, withSignature, messageSource, locale);
                }
                break;
            case Constants.FEDERATIVE_MANDATE:
                file = pdfService.createFederativeLicenseMandate(pdfModel, withSignature, messageSource, locale);
                break;
            case Constants.WHATSAPP:
                file = pdfService.createWhatsAppAuthorization(pdfModel, withSignature, messageSource, locale);
                break;
            case Constants.SEPA_DIRECT_DEBIT:
                file = pdfService.createSepaDirectDebitForm(pdfModel, withSignature, messageSource, locale);
                break;
        }
        return file;
    }

    @Override
    public List<DownloadDocumentModel> createAndSendActivityEnrollmentFiles(SignatureModel signatureModel, MessageSource messageSource, Locale locale) throws SenderException {
        EnrollmentModel enrollmentModel = findById(signatureModel.getOperationId());
        enrollmentModel.setSigned(Boolean.TRUE);
        enrollmentModel.setSignedDate(new Date());
        update(enrollmentModel);
        List<DownloadDocumentModel> downloadDocumentModelList = new ArrayList<>();
        List<File> files = new ArrayList<>();
        fillFilesAndDownloadDocumentModelList(downloadDocumentModelList, files, enrollmentModel, signatureModel, messageSource, locale);
        // TODO hacer envío
        return downloadDocumentModelList;

    }

    @Override
    public void fillFilesAndDownloadDocumentModelList(List<DownloadDocumentModel> downloadDocumentModelList, List<File> files, EnrollmentModel enrollmentModel, SignatureModel signatureModel, MessageSource messageSource, Locale locale) {
        PdfModel pdfModel = getPdfModel(enrollmentModel, messageSource, locale);
        if (files != null) {
            files.add(getFile(Constants.AUTHORIZATION, pdfModel, signatureModel.isSigned(), messageSource, locale));
        }
        downloadDocumentModelList.add(new DownloadDocumentModel(Constants.AUTHORIZATION, signatureModel.getId(),
                enrollmentModel.getId(), messageSource.getMessage(Constants.DOCUMENT_AUTHORIZATION, null, locale)));
        if (enrollmentModel.isFederationForm()) {
            if (files != null) {
                files.add(getFile(Constants.FEDERATIVE_MANDATE, pdfModel, signatureModel.isSigned(), messageSource, locale));
            }
            downloadDocumentModelList.add(new DownloadDocumentModel(Constants.FEDERATIVE_MANDATE, signatureModel.getId(),
                    enrollmentModel.getId(), messageSource.getMessage(Constants.DOCUMENT_FEDERATIVE_MANDATE, null, locale)));
        }
        if (enrollmentModel.isWhatsappForm()) {
            if (files != null) {
                files.add(getFile(Constants.WHATSAPP, pdfModel, signatureModel.isSigned(), messageSource, locale));
            }
            downloadDocumentModelList.add(new DownloadDocumentModel(Constants.WHATSAPP, signatureModel.getId(),
                    enrollmentModel.getId(), messageSource.getMessage(Constants.DOCUMENT_WHATSAPP, null, locale)));
        }
        if (enrollmentModel.isSepaDirectDebit()) {
            if (files != null) {
                files.add(getFile(Constants.SEPA_DIRECT_DEBIT, pdfModel, signatureModel.isSigned(), messageSource, locale));
            }
            downloadDocumentModelList.add(new DownloadDocumentModel(Constants.SEPA_DIRECT_DEBIT, signatureModel.getId(),
                    enrollmentModel.getId(), messageSource.getMessage(Constants.DOCUMENT_SEPA_DIRECT_DEBIT, null, locale)));
        }
    }
}
