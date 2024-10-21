package com.gymdaus.core.service.impl;

import com.gymdaus.core.exception.SenderException;
import com.gymdaus.core.model.EmailModel;
import com.gymdaus.core.model.TokenModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.model.ManagerParameterModel;
import com.gymdaus.core.service.EmailService;
import com.gymdaus.core.service.ManagerParameterService;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class EmailServiceImpl implements EmailService {
    @Autowired
    private SendMessage sendMessage;
    /*
    @Autowired
    private DocumentManagerService documentManagerService;
    @Autowired
    private GimnasioService gimnasioService;

     */
    @Autowired
    private ManagerParameterService managerParameterService;

    @Override
    public void sendChangePassword(UserModel userModel, TokenModel tokenModel) throws SenderException {//envía plataforma
        try {
            ManagerParameterModel managerParameterModel = managerParameterService.get();
            sendMessage.enviarCorreo(new EmailModel(managerParameterModel.getEmail(), userModel.getEmail(),
                    "Correo para poder modificar contraseña", textMessageChangePassword(userModel, tokenModel),
                    null, managerParameterModel.getEmailHost(), managerParameterModel.getEmailPort(), managerParameterModel.getPassword()));
        } catch (Exception e) {
            throw new SenderException(Constants.AVISO_EMAIL,e.getMessage());
        }

    }
/*
    @Override
    public void sendTournamentRegistration(UserModel userModel, DocumentManagerModel documentManagerModel, TournamentRegistrationModel tournamentRegistrationModel) throws SenderException {// envía gimnasio
        List<File> files = new ArrayList<>();
        files.add(new File(documentManagerModel.getFullPath()));
        try {
            GimnasioModel gimnasioModel = gimnasioService.findById(tournamentRegistrationModel.getIdGym());
            sendMessage.enviarCorreo(new EmailModel(gimnasioModel.getCorreo(), userModel.getCorreo(), "Confirmación inscripción torneo taekwondo"
                    , textMessageTournamentRegistration(userModel), files, gimnasioModel.getEmailHost(), gimnasioModel.getEmailPort(), gimnasioModel.getEmailPassword()));
        } catch (Exception e) {
            throw new SenderException(Constants.AVISO_EMAIL_ARCHIVO_ADJUNTO,e.getMessage());
        }
    }

    @Override
    public void confirmAdminTournamentRegistration(UserAutorizacionModel userAutorizacionModel, TournamentRegistrationModel tournamentRegistrationModel) throws SenderException {// envía plataforma
        try {
            ManagerParameterModel utilManagerModel = managerParameterService.get();
            GimnasioModel gimnasioModel = gimnasioService.findById(tournamentRegistrationModel.getIdGym());
            sendMessage.enviarCorreo(new EmailModel(utilManagerModel.getEmail(), gimnasioModel.getCorreo(), "Nueva inscripción en torneo",
                    textMessageConfirmAdminTournamentRegistration(userAutorizacionModel), null, utilManagerModel.getEmailHost(),
                    utilManagerModel.getEmailPort(), utilManagerModel.getPassword()));
        } catch (Exception e) {
            throw new SenderException(Constants.AVISO_EMAIL,e.getMessage());
        }

    }

    @Override
    public void sendGymJoining(InscripcionTaekwondoModel inscripcionTaekwondoModel, List<File> files) throws SenderException {// envía gimnasio
        try {
            GimnasioModel gimnasioModel = gimnasioService.findById(inscripcionTaekwondoModel.getCodigoGimnasio());
            sendMessage.enviarCorreo(new EmailModel(gimnasioModel.getCorreo(), inscripcionTaekwondoModel.getMayorCorreo(), "Confirmación inscripción gimnasio",
                    textMessageGymJoining(inscripcionTaekwondoModel), files, gimnasioModel.getEmailHost(), gimnasioModel.getEmailPort(), gimnasioModel.getEmailPassword()));
        } catch (Exception e) {
            throw new SenderException(Constants.AVISO_EMAIL_ARCHIVO_ADJUNTO,e.getMessage());
        }
    }

    @Override
    public void confirmAdminGymJoining(InscripcionTaekwondoModel inscripcionTaekwondoModel) throws SenderException {// envía plataforma
        try {
            ManagerParameterModel utilManagerModel = managerParameterService.get();
            GimnasioModel gimnasioModel = gimnasioService.findById(inscripcionTaekwondoModel.getCodigoGimnasio());
            sendMessage.enviarCorreo(new EmailModel(utilManagerModel.getEmail(), gimnasioModel.getCorreo(), "Nueva inscripción en el gimnasio",
                    textMessageConfirmAdminGymJoining(inscripcionTaekwondoModel), null,
                    utilManagerModel.getEmailHost(), utilManagerModel.getEmailPort(), utilManagerModel.getPassword()));
        } catch (Exception e) {
            throw new SenderException(Constants.AVISO_EMAIL,e.getMessage());
        }
    }

    @Override
    public void sendCodeValidation(User user, String code, List<File> files) throws SenderException {// envía plataforma
        try {
            ManagerParameterModel utilManagerModel = managerParameterService.get();
            sendMessage.enviarCorreo(new EmailModel(utilManagerModel.getEmail(), user.getCorreo(),
                    "Código de validación", textMessageCodeValidation(user, code), files,
                    utilManagerModel.getEmailHost(), utilManagerModel.getEmailPort(), utilManagerModel.getPassword()));
        } catch (Exception e) {
            throw new SenderException(Constants.AVISO_EMAIL,e.getMessage());
        }
    }

    @Override
    public void confirmAdminSepaSigned(InscripcionTaekwondoModel inscripcionTaekwondoModel) throws SenderException {// envía plataforma
        try {
            ManagerParameterModel utilManagerModel = managerParameterService.get();
            GimnasioModel gimnasioModel = gimnasioService.findById(inscripcionTaekwondoModel.getCodigoGimnasio());
            sendMessage.enviarCorreo(new EmailModel(utilManagerModel.getEmail(), gimnasioModel.getCorreo(), "Nuevo formulario de domiciliación firmado",
                    textMessageConfirmAdminSepaSigned(inscripcionTaekwondoModel), null, utilManagerModel.getEmailHost(),
                    utilManagerModel.getEmailPort(), utilManagerModel.getPassword()));
        } catch (Exception e) {
            throw new SenderException(Constants.AVISO_EMAIL,e.getMessage());
        }
    }

    @Override
    public void sendUserAdded(User user) throws SenderException {// envía plataforma
        try {
            ManagerParameterModel utilManagerModel = managerParameterService.get();
            sendMessage.enviarCorreo(new EmailModel(utilManagerModel.getEmail(), user.getCorreo(),
                    "Alta de ".concat(user.getName()), textMessageConfirmAddedUser(user), null,
                    utilManagerModel.getEmailHost(), utilManagerModel.getEmailPort(), utilManagerModel.getPassword()));
        } catch (Exception e) {
            throw new SenderException(Constants.AVISO_EMAIL,e.getMessage());
        }
    }

    @Override
    public void sendNewMandato(MandatoModel mandatoModel, List<File> files) throws SenderException {// envía gimnasio
        try {
            GimnasioModel gimnasioModel = gimnasioService.findById(mandatoModel.getCodigoGimnasio());
            sendMessage.enviarCorreo(new EmailModel(gimnasioModel.getCorreo(), mandatoModel.getCorreoMandante(),
                    "Mandato para licencia solicitada por ".concat(mandatoModel.getNombreMandante()),
                    textMessageSendNewMandato(mandatoModel), files, gimnasioModel.getEmailHost(), gimnasioModel.getEmailPort(), gimnasioModel.getEmailPassword()));
        } catch (Exception e) {
            throw new SenderException(Constants.AVISO_EMAIL,e.getMessage());
        }

    }

    @Override
    public void confirmAdminNewMandato(MandatoModel mandatoModel) throws SenderException {// envía plataforma
        try {
            ManagerParameterModel utilManagerModel = managerParameterService.get();
            GimnasioModel gimnasioModel = gimnasioService.findById(mandatoModel.getCodigoGimnasio());
            sendMessage.enviarCorreo(new EmailModel(utilManagerModel.getEmail(), gimnasioModel.getCorreo(), "Nuevo mandato firmado",
                    textMessageConfirmAdminNewMandato(mandatoModel), null, utilManagerModel.getEmailHost(),
                    utilManagerModel.getEmailPort(), utilManagerModel.getPassword()));
        } catch (Exception e) {
            throw new SenderException(Constants.AVISO_EMAIL,e.getMessage());
        }

    }

    @Override
    public void confirmAdminDelete(int codigoGimnasio, String actividad, User user, String nombreMenor) {// envía plataforma
        try {
            ManagerParameterModel utilManagerModel = managerParameterService.get();
            GimnasioModel gimnasioModel = gimnasioService.findById(codigoGimnasio);
            sendMessage.enviarCorreo(new EmailModel(utilManagerModel.getEmail(), gimnasioModel.getCorreo(), "Eliminación de usuario de " + actividad,
                    textMessageConfirmAdminDelete(actividad, user, nombreMenor), null, utilManagerModel.getEmailHost(),
                    utilManagerModel.getEmailPort(), utilManagerModel.getPassword()));
        } catch (Exception e) {
            LoggerMapper.log(Level.ERROR, "confirmAdminDelete", e.getMessage(), getClass());
        }

    }

 */

    private String textMessageChangePassword(UserModel userModel, TokenModel tokenModel) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>");
        stringBuilder.append("<HTML><BODY>");
        String name = userModel.getName() != null ? " " + userModel.getName() : "";
        stringBuilder.append("<table style=\"width:100%; text-align:center;\">");
        stringBuilder.append("<tr><td>");
        stringBuilder.append("<h1>Hola<b>").append(name).append("</b>!</h1><br>");
        stringBuilder.append("<p>Nos ha llegado tu solicitud de cambio de contraseña.</p>");
        stringBuilder.append("<p>Por favor, para poder cambiar tu contraseña, presiona el siguiente botón:</p>");
        stringBuilder.append("<br>");
        stringBuilder.append("<a href=\"").append(managerParameterService.get().getHostPageName()).append("/pass-new?username=")
                .append(userModel.getUsername()).append("&token=").append(tokenModel.getId()).append("\">");
        stringBuilder.append("<button style=\"background-color: #4CAF50; color: white; border: none; ")
                .append("border-radius: 5px; padding: 10px 20px; font-size: 16px;\">Cambiar contraseña</button>");
        stringBuilder.append("</a>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<p>¡Que pases un buen día!</p>");
        stringBuilder.append("</td></tr></table>");
        stringBuilder.append("</BODY></HTML>");
        return stringBuilder.toString();
    }
/*
    private String textMessageTournamentRegistration(UserModel userModel) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>");
        stringBuilder.append("<HTML><BODY>");
        String name = userModel.getName() != null ? " " + userModel.getName() : "";
        stringBuilder.append("<h1>Hola <b>").append(name).append("</b>!</h1><br>");
        stringBuilder.append("<p>Te adjuntamos la confirmación de inscripción al Torneo.</p>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<p>¡Que pases un buen día!</p>");
        stringBuilder.append("</BODY></HTML>");
        return stringBuilder.toString();
    }

    private String textMessageGymJoining(InscripcionTaekwondoModel inscripcionTaekwondoModel) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>");
        stringBuilder.append("<HTML><BODY>");
        stringBuilder.append("<h1>Hola <b>").append(inscripcionTaekwondoModel.getMayorNombre()).append("</b>!</h1><br>");
        stringBuilder.append("<p>Te adjuntamos la confirmación de inscripción al Gimnasio.</p>");
        if (inscripcionTaekwondoModel.isDomiciliacionSEPA()) {
            stringBuilder.append("<p>Por favor no olvides enviarnos la autorización de domiciliación firmada.</p>");
            stringBuilder.append("<p>Es un proceso muy sencillo y lo debes hacer desde la misma página de inscripción.</p>");
            stringBuilder.append("<br><br>");
        }
        stringBuilder.append("<br><br>");
        stringBuilder.append("<p>¡Que pases un buen día!</p>");
        stringBuilder.append("</BODY></HTML>");
        return stringBuilder.toString();
    }

    private String textMessageConfirmAdminGymJoining(InscripcionTaekwondoModel insc) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>");
        stringBuilder.append("<HTML><BODY>");
        String mayor = insc.getMayorNombre() + " " + insc.getMayorApellido1() +
                (Utils.isNullOrEmpty(insc.getMayorApellido2()) ? "" : " " + insc.getMayorApellido2());
        String autorizado = null;
        if(!Utils.isNullOrEmpty(insc.getAutorizadoNombre())) {
            autorizado = insc.getAutorizadoNombre() + " " + insc.getAutorizadoApellido1() +
                    (Utils.isNullOrEmpty(insc.getAutorizadoApellido2()) ? "" : " " + insc.getAutorizadoApellido2());
        }
        stringBuilder.append("<h1>Hola<b>").append("</b>!</h1><br>");
        if (autorizado == null) {
            stringBuilder.append("<p>¡Se acaba de inscribir ").append(mayor).append(" en el gimnasio!</p>");
        } else {
            stringBuilder.append("<p>¡Se acaba de inscribir ").append(autorizado).append(" (que es menor de edad) en el gimnasio!</p>");
            stringBuilder.append("<p>Está autorizado por su ").append(insc.getMayorCalidad()).append(" que se llama ").append(mayor).append(".</p>");
        }
        stringBuilder.append("<br><br>");
        stringBuilder.append("<p>¡Que pases un buen día!</p>");
        stringBuilder.append("</BODY></HTML>");
        return stringBuilder.toString();
    }

    private String textMessageConfirmAdminTournamentRegistration(UserAutorizacionModel userAutorizacionModel) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>");
        stringBuilder.append("<HTML><BODY>");
        UserModel mayor = userAutorizacionModel.getMayorAutorizador();
        UserModel autorizado = userAutorizacionModel.getAutorizado();
        if (autorizado == null) {
            stringBuilder.append("<p>¡Se acaba de inscribir ").append(mayor.getName()).append(" ")
                    .append(mayor.getLastname()).append(" ").append(mayor.getSecondLastname()).append(" en el torneo!</p>");
        } else {
            stringBuilder.append("<p>¡Se acaba de inscribir ").append(autorizado.getName()).append(" ")
                    .append(autorizado.getLastname()).append(" ").append(autorizado.getSecondLastname())
                    .append(autorizado.isInclusivo() ? " (categoría inclusiva)" : " (categoría menor de edad)")
                    .append(" en el torneo!</p>");
            stringBuilder.append("<p>Está autorizado por ").append(mayor.getName()).append(" ").append(mayor.getLastname())
                    .append(" ").append(mayor.getSecondLastname()).append(".</p>");
        }
        stringBuilder.append("<br><br>");
        stringBuilder.append("<p>¡Que pases un buen día!</p>");
        stringBuilder.append("</BODY></HTML>");
        return stringBuilder.toString();
    }

    private String textMessageCodeValidation(User user, String code) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>");
        stringBuilder.append("<HTML><BODY>");
        String name = user.getName() != null ? " " + user.getName() : "";
        stringBuilder.append("<h1>Hola<b>").append(name).append("</b>!</h1><br>");
        stringBuilder.append("<p>El código de validación que debes utilizar es el siguiente</p>");
        stringBuilder.append("<br>");
        stringBuilder.append("<h2>").append(code).append("</h2>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<p>Este código tiene una validez de 15 minutos.</p>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<p>Al firmar con este código, estarás firmando todos los documentos que requieran " +
                "firma y estén adjuntos en este correo.</p>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<p>¡Que pases un buen día!</p>");
        stringBuilder.append("</BODY></HTML>");
        return stringBuilder.toString();
    }

    private String textMessageConfirmAdminSepaSigned(InscripcionTaekwondoModel insc) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>");
        stringBuilder.append("<HTML><BODY>");
        String mayor = insc.getMayorNombre() + " " + insc.getMayorApellido1() +
                (Utils.isNullOrEmpty(insc.getMayorApellido2()) ? "" : " " + insc.getMayorApellido2());
        stringBuilder.append("<h1>Hola<b>").append("</b>!</h1><br>");
        stringBuilder.append("<p>El cliente ").append(mayor).append(" acaba de subir un formulario de domiciliación bancaria firmado.</p>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<p>¡Que pases un buen día!</p>");
        stringBuilder.append("</BODY></HTML>");
        return stringBuilder.toString();
    }

    private String textMessageConfirmAddedUser(User user) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>");
        stringBuilder.append("<HTML><BODY>");
        String mayor = user.getName() + " " + user.getLastname() +
                (Utils.isNullOrEmpty(user.getSecondLastname()) ? "" : " " + user.getSecondLastname());
        stringBuilder.append("<h1>Hola <b>").append(mayor).append("</b>!</h1><br>");
        stringBuilder.append("<p>Te confirmamos que tu usuario ha sido dado de alta de forma exitosa en la plataforma.</p>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<p>¡Que pases un buen día!</p>");
        stringBuilder.append("</BODY></HTML>");
        return stringBuilder.toString();
    }

    private String textMessageSendNewMandato(MandatoModel mandatoModel) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>");
        stringBuilder.append("<HTML><BODY>");
        stringBuilder.append("<h1>Hola <b>").append(mandatoModel.getNombreMandante()).append("</b>!</h1><br>");
        stringBuilder.append("<p>Te confirmamos que tu solicitud de mandato para la licencia federativa");
        if (!Utils.isNullOrEmpty(mandatoModel.getNombreAutorizado())) {
            stringBuilder.append(" de ").append(mandatoModel.getNombreAutorizado());
        }
        stringBuilder.append(" ha sido enviada de forma exitosa en la plataforma.</p>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<p>¡Que pases un buen día!</p>");
        stringBuilder.append("</BODY></HTML>");
        return stringBuilder.toString();
    }

    private String textMessageConfirmAdminNewMandato(MandatoModel mandatoModel) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>");
        stringBuilder.append("<HTML><BODY>");
        stringBuilder.append("<h1>Hola!</h1><br>");
        stringBuilder.append("<p>El cliente ").append(mandatoModel.getNombreMandante()).append(" acaba de subir un mandato de licencia firmado.</p>");
        if (!Utils.isNullOrEmpty(mandatoModel.getNombreAutorizado())) {
            stringBuilder.append("<p>El mandato es para ").append(mandatoModel.getNombreAutorizado()).append(".</p>");
        }
        stringBuilder.append("<br><br>");
        stringBuilder.append("<p>¡Que pases un buen día!</p>");
        stringBuilder.append("</BODY></HTML>");
        return stringBuilder.toString();
    }

    private String textMessageConfirmAdminDelete(String actividad, User user, String nombreMenor) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>");
        stringBuilder.append("<HTML><BODY>");
        stringBuilder.append("<h1>Hola!</h1><br>");
        stringBuilder.append("<p>El usuario ").append(user.getName()).append(" ").append(user.getLastname());
        if(!Utils.isNullOrEmpty(user.getSecondLastname())) {
            stringBuilder.append(" ").append(user.getSecondLastname());
        }
        stringBuilder.append(" acaba de eliminar la suscripción a ").append(actividad);
        if (!Utils.isNullOrEmpty(nombreMenor)) {
            stringBuilder.append(" a nombre de ").append(nombreMenor).append(".</p>");
        } else {
            stringBuilder.append(" que estaba a su nombre.</p>");
        }
        stringBuilder.append("<br><br>");
        stringBuilder.append("<p>¡Que pases un buen día!</p>");
        stringBuilder.append("</BODY></HTML>");
        return stringBuilder.toString();
    }

 */
}
