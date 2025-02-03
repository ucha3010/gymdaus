package com.gymdaus.core.service;


import com.gymdaus.core.entity.User;
import com.gymdaus.core.exception.SenderException;
import com.gymdaus.core.model.*;
import org.springframework.context.MessageSource;

import java.io.File;
import java.util.List;
import java.util.Locale;

public interface EmailService {

    EmailModel getGymParameters(Long gymId);

    void updateGymParameters(EmailModel emailModel, String modificationUsername);

    void sendAdminInvitation(UserModel userGymAdmin, UserModel userInvited, GymModel gymModel, MessageSource messageSource, Locale locale) throws SenderException;

    void sendChangePassword(UserModel userModel, TokenModel tokenModel) throws SenderException;

    void sendCodeValidation(UserModel userModel, String code, List<File> files, MessageSource messageSource, Locale locale) throws SenderException;
    void sendUserEnrollment(PdfModel pdfModel, List<File> files, MessageSource messageSource, Locale locale) throws SenderException;
    void confirmAdminGymEnrollment(PdfModel pdfModel, MessageSource messageSource, Locale locale) throws SenderException;
/*
    void sendTournamentRegistration(UserModel userModel, DocumentManagerModel documentManagerModel, TournamentRegistrationModel tournamentRegistrationModel) throws SenderException;

    void confirmAdminTournamentRegistration(UserAutorizacionModel userAutorizacionModel, TournamentRegistrationModel tournamentRegistrationModel) throws SenderException;

    void confirmAdminSepaSigned(InscripcionTaekwondoModel inscripcionTaekwondoModel) throws SenderException;

    void sendUserAdded(User user) throws SenderException;

    void sendNewMandato(MandatoModel mandatoModel, List<File> files) throws SenderException;

    void confirmAdminNewMandato(MandatoModel mandatoModel) throws SenderException;

    void confirmAdminDelete(int codigoGimnasio, String actividad, User user, String nombreMenor);

 */
}
