package com.gymdaus.core.util;

import com.gymdaus.core.model.EmailModel;
import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Properties;

@Component
public class SendMessage {

    /**
     * Primero ir a la configuración de la cuenta de Google.
     * Entrar en el apartado Seguridad.
     * La verificación en dos pasos debe estar activada.
     * Entrar en el apartado "Verificación en dos pasos" y tocar "Contraseñas de aplicaciones".
     * Generar nueva contraseña con opción "Otra (nombre personalizado)" y poner un nombre cualquiera.
     * Copiar la contraseña que me genera y guardarla en tabla de utilidades bajo la llave clave.correo
     * Indicaciones www.youtube.com/watch?v=ZggjlwLzrxg
     * Indicaciones archivos adjuntos www.youtube.com/watch?v=o7v0EQgxP50
    */
    public void enviarCorreo(EmailModel emailModel) throws MessagingException {

        LoggerMapper.methodIn(Level.INFO, "enviarCorreo", "fromEmailAddress: " + emailModel.getFromEmailAddress()
                + ", toEmailAddress: " + emailModel.getToEmailAddress(), getClass());

        Properties props = new Properties();
        props.put("mail.smtp.host", emailModel.getHost());//smtp.gmail.com,smtp.office365.com
        props.put("mail.smtp.ssl.trust", emailModel.getHost());//smtp.gmail.com,smtp.office365.com
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", emailModel.getPort());//587
        props.setProperty("mail.smtp.user", emailModel.getFromEmailAddress());
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");//no está
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(emailModel.getFromEmailAddress()));
        email.setRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(emailModel.getToEmailAddress()));
        email.setSubject(emailModel.getMessageSubject());
        if (emailModel.getFiles() == null) {
            email.setText(emailModel.getBodyText(), "ISO-8859-1", "html");
        } else {
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(emailModel.getBodyText(), "text/html; charset=utf-8");
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            for (File file: emailModel.getFiles()) {
                mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setDataHandler(new DataHandler(new FileDataSource(file)));
                mimeBodyPart.setFileName(file.getName());
                multipart.addBodyPart(mimeBodyPart);
            }
            email.setContent(multipart);
        }
        LoggerMapper.log(Level.INFO, "enviarCorreo", email, SendMessage.class);

        //Enviar el correo
        Transport transport = session.getTransport("smtp");
        transport.connect(emailModel.getFromEmailAddress(), emailModel.getPassword());
        transport.sendMessage(email, email.getRecipients(jakarta.mail.Message.RecipientType.TO));
        transport.close();

        LoggerMapper.methodOut(Level.INFO, "enviarCorreo", "Correo enviado", SendMessage.class);
    }

}
