package com.gymdaus.core.util;

import com.gymdaus.core.model.UtilModel;
import org.apache.commons.lang3.StringUtils;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    public static String date2String (Date date) {
        if (date != null) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            return df.format(date);
        } else {
            return null;
        }
    }

    public static String generateSecurePassword() {

        // create character rule for lower case
        CharacterRule LCR = new CharacterRule(EnglishCharacterData.LowerCase);
        // set number of lower case characters
        LCR.setNumberOfCharacters(4);

        // create character rule for upper case
        CharacterRule UCR = new CharacterRule(EnglishCharacterData.UpperCase);
        // set number of upper case characters
        UCR.setNumberOfCharacters(2);

        // create character rule for digit
        CharacterRule DR = new CharacterRule(EnglishCharacterData.Digit);
        // set number of digits
        DR.setNumberOfCharacters(2);

        /* create character rule for lower case
        CharacterRule SR = new CharacterRule(EnglishCharacterData.Special);
        // set number of special characters
        SR.setNumberOfCharacters(2);
        */

        // create instance of the PasswordGenerator class
        PasswordGenerator passGen = new PasswordGenerator();

        // call generatePassword() method of PasswordGenerator class to get Passay generated password
        // return Passay generated password to the main() method
        return passGen.generatePassword(8, LCR, UCR, DR);
    }

    public static long milisegEntreDosFechas(Date fechaMayor, Date fechaMenor) {
        return (fechaMayor.getTime() - fechaMenor.getTime());
    }
    public static Date sumaRestaMinutos (int minutos) {
        Date ahora = new Date();
        Calendar caducidad = Calendar.getInstance();
        caducidad.setTime(ahora);
        caducidad.add(Calendar.MINUTE, minutos);
        return caducidad.getTime();
    }
    public static Date cambiarHMS(Date fechaOriginal, int hora, int min, int seg) {

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fechaOriginal);
        // si deseo no cambiar hora, minutos o segundos envío ese dato como negativo
        if (hora >= 0) {
            calendario.set(Calendar.HOUR_OF_DAY, hora);
        }
        if (min >= 0) {
            calendario.set(Calendar.MINUTE, min);
        }
        if (seg >= 0) {
            calendario.set(Calendar.SECOND, seg);
        }
        return calendario.getTime();
    }

    public static String ofuscar(String entrada) {
        StringBuilder sb = new StringBuilder();
        if(entrada != null) {
            int tamanio = entrada.length();
            int visible = tamanio/3;
            if (tamanio > 3) {
                sb.append(entrada, 0, visible);
                for(int j=visible;j<(tamanio-visible);j++) {
                    sb.append("*");
                }
                sb.append(entrada, tamanio-visible, tamanio);
            } else {
                sb.append("***");
            }
        }
        return sb.toString();
    }

    public static List<UtilModel> cargarListaSiNo() {
        List<UtilModel> listaSiNo = new ArrayList<>();
        listaSiNo.add(new UtilModel("Si", Constants.TRUE));
        listaSiNo.add(new UtilModel("No", Constants.FALSE));
        return listaSiNo;
    }

    public static List<UtilModel> cargarListaProveedoresHost() {
        List<UtilModel> listaHost = new ArrayList<>();
        for (EmailEnum emailEnum : EmailEnum.values()) {
            listaHost.add(new UtilModel(emailEnum.getProveedor(),emailEnum.getHost()));
        }
        return listaHost;
    }

    public static String calculateSeason(Date date) {
        String[] dateArray = new SimpleDateFormat("dd-MM-yyyy").format(date).split("-");
        int season = Integer.parseInt(dateArray[2]);
        // en noviembre y diciembre se hace la autorización para la temporada del año siguiente
        if ("11".equals(dateArray[1]) || "12".equals(dateArray[1])) {
            season++;
        }
        return String.valueOf(season);
    }

    public static String obtenerNombreMetodo() {
        // Obtiene la pila de llamadas (stack trace) del hilo actual
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        // El índice 1 representa el método que llamó a esta función
        // El índice 0 es getStackTrace() y el índice 1 es el método actual
        StackTraceElement metodoActual = stackTrace[2];

        // Devuelve el nombre del método actual
        return metodoActual.getMethodName();
    }

    public static List<String> obtenerNombresArchivos(String rutaCarpeta) {
        File directorio = new File(rutaCarpeta);
        if (directorio.list() != null) {
            return Arrays.asList(directorio.list());
        } else {
            return new ArrayList<>();
        }
    }

    public static boolean isNullOrEmpty(String string) {
        return StringUtils.isBlank(string);
    }
}
