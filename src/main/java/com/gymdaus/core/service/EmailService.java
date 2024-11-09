package com.gymdaus.core.service;


import com.gymdaus.core.entity.User;
import com.gymdaus.core.exception.SenderException;
import com.gymdaus.core.model.EmailModel;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.TokenModel;
import com.gymdaus.core.model.UserModel;
import org.springframework.context.MessageSource;

import java.io.File;
import java.util.List;
import java.util.Locale;

public interface EmailService {

    EmailModel getGymParameters(Long gymId);

    void updateGymParameters(EmailModel emailModel, String modificationUsername);

    void sendAdminInvitation(UserModel userGymAdmin, UserModel userInvited, GymModel gymModel, MessageSource messageSource, Locale locale) throws SenderException;

    void sendChangePassword(UserModel userModel, TokenModel tokenModel) throws SenderException;

    void sendCodeValidation(User userModel, String code, List<File> files) throws SenderException;
/*
    void sendTournamentRegistration(UserModel userModel, DocumentManagerModel documentManagerModel, TournamentRegistrationModel tournamentRegistrationModel) throws SenderException;

    void confirmAdminTournamentRegistration(UserAutorizacionModel userAutorizacionModel, TournamentRegistrationModel tournamentRegistrationModel) throws SenderException;

    void sendGymJoining(InscripcionTaekwondoModel inscripcionTaekwondoModel, List<File> files) throws SenderException;

    void confirmAdminGymJoining(InscripcionTaekwondoModel inscripcionTaekwondoModel) throws SenderException;

    void confirmAdminSepaSigned(InscripcionTaekwondoModel inscripcionTaekwondoModel) throws SenderException;

    void sendUserAdded(User user) throws SenderException;

    void sendNewMandato(MandatoModel mandatoModel, List<File> files) throws SenderException;

    void confirmAdminNewMandato(MandatoModel mandatoModel) throws SenderException;

    void confirmAdminDelete(int codigoGimnasio, String actividad, User user, String nombreMenor);

 */
}
