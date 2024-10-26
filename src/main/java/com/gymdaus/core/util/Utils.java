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

        // call generatePassword() method of PasswordGenerator class to get Pass generated password
        // return Pass generated password to the main() method
        return passGen.generatePassword(8, LCR, UCR, DR);
    }

    public static long millisecondsBetweenTwoDates(Date majorDate, Date minorDate) {
        return (majorDate.getTime() - minorDate.getTime());
    }
    public static Date addSubtractMinutes(int minutes) {
        Date now = new Date();
        Calendar expiration = Calendar.getInstance();
        expiration.setTime(now);
        expiration.add(Calendar.MINUTE, minutes);
        return expiration.getTime();
    }
    public static Date changeHMS(Date originalDate, int hora, int min, int seg) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(originalDate);
        // If I do not want to change the hour, minutes or seconds, I send that data as negative
        if (hora >= 0) {
            calendar.set(Calendar.HOUR_OF_DAY, hora);
        }
        if (min >= 0) {
            calendar.set(Calendar.MINUTE, min);
        }
        if (seg >= 0) {
            calendar.set(Calendar.SECOND, seg);
        }
        return calendar.getTime();
    }

    public static String obfuscate(String toObfuscate) {
        StringBuilder sb = new StringBuilder();
        if(toObfuscate != null) {
            int size = toObfuscate.length();
            int visible = size/3;
            if (size > 3) {
                sb.append(toObfuscate, 0, visible);
                for(int j=visible;j<(size-visible);j++) {
                    sb.append("*");
                }
                sb.append(toObfuscate, size-visible, size);
            } else {
                sb.append("***");
            }
        }
        return sb.toString();
    }

    public static List<UtilModel> chargeListYesNo() {
        List<UtilModel> listYesNo = new ArrayList<>();
        listYesNo.add(new UtilModel("Si", Constants.TRUE));
        listYesNo.add(new UtilModel("No", Constants.FALSE));
        return listYesNo;
    }

    public static List<UtilModel> chargeListHostProvider() {
        List<UtilModel> listHost = new ArrayList<>();
        for (EmailEnum emailEnum : EmailEnum.values()) {
            listHost.add(new UtilModel(emailEnum.getProveedor(),emailEnum.getHost()));
        }
        return listHost;
    }

    public static String getMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement currentMethod = stackTrace[2];
        return currentMethod.getMethodName();
    }

    public static List<String> getFileName(String folderPath) {
        File folder = new File(folderPath);
        if (folder.list() != null) {
            return Arrays.asList(folder.list());
        } else {
            return new ArrayList<>();
        }
    }

    public static boolean isNullOrEmpty(String string) {
        return StringUtils.isBlank(string);
    }
}
