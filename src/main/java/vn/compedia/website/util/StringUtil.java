package vn.compedia.website.util;

import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.Formatter;

public class StringUtil {
    public static String encryptPassword(String password) {
        String sha256 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-256");
            crypt.reset();
            crypt.update(password.getBytes(StandardCharsets.UTF_8));
            sha256 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha256;
    }

    public static String encryptPassword(String password, String salt) {
        return encryptPassword(password + salt);
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public static String generateSalt() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public static String generateNewPassword() {
        return RandomStringUtils.randomAlphanumeric(6);
    }

    public static String generateNewToken() {
        return RandomStringUtils.randomAlphanumeric(50);
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String removeSigned(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.trim().replaceAll("[^\\p{ASCII}]|\\p{M}|[^a-zA-Z0-9]", "");
        str = str.toLowerCase();
        return str;
    }

    public static boolean checkAllZero(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '0') {
                count++;
            }
        }
        return count == str.length();
    }

    public static boolean validateDateWithRegex(String date) {
        String regexDate = "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)";
        return date.matches(regexDate);
    }
}
