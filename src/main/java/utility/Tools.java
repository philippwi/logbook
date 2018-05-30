package utility;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

public final class Tools {

    private Tools() {
    }

    /**
     * Defines how to handle a given exception.
     *
     * @param e any exception
     */
    public static void handleException(Exception e) {
        System.err.println(e.toString());
    }

    /**
     * Encrypts a given string (password) using the MD5-algorithm.
     *
     * @param string password to be encrypted
     */
    public static String encryptPw(String string) throws NoSuchAlgorithmException {

        if (isBlankOrNull(string)) return "";

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(string.getBytes());
        byte[] digest = md.digest();

        String hashedString = DatatypeConverter
                .printHexBinary(digest).toUpperCase();

        return hashedString;
    }

    /**
     * Encrypts a given string (cookie value) using the Base64-algorithm.
     *
     * @param string cookie value to be encrypted
     */
    public static String encryptCookie(String string) {
        byte[] encryptArray = Base64.getEncoder().encode(string.getBytes());
        String encstr = null;
        try {
            encstr = new String(encryptArray, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encstr;
    }

    /**
     * Decrypts a Base64-encrypted string (cookie value).
     *
     * @param string encrypted cookie value to be decrypted
     */
    public static String decryptCookie(String string) {
        byte[] decryptArray = Base64.getDecoder().decode(string);
        String decstr = null;
        try {
            decstr = new String(decryptArray, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decstr;
    }

    /**
     * Rounds a float value to given amount of decimal places.
     *
     * @param value  number to be rounded
     * @param places decimal places number should be rounded to
     * @return rounded number
     */
    public static float precisionRound(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    /**
     * Checks if a given string is null, empty or consists of
     * only whitespaces.
     *
     * @param string string to be tested
     * @return whether or not string is blank or null
     */
    public static boolean isBlankOrNull(String string) {

        //check if parameter is null
        if (string == null) return true;

        //check if string is empty
        if (string.isEmpty()) return true;

        //check if string consists of whitespaces only
        if (string.trim().isEmpty()) return true;

        //after all checks are negative
        return false;

    }

    /**
     * Checks if a given date is null or too far in the past/future.
     *
     * @param date date to be tested
     * @return whether or not date is valid
     */
    public static boolean isValidDate(Date date) {

        if (date == null) return false;

        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (localDate.isBefore(LocalDate.parse("2000-01-01"))) return false;

        if (localDate.isAfter(LocalDate.now().plusDays(30))) return false;

        return true;
    }

    /**
     * Checks if a given string complies the username requirements. A name
     * must not be blank and no longer than 45 characters. Additionally it
     * can only consist of Roman letters and Arabic numerals.
     *
     * @param string string to be tested
     * @return whether or not name is valid
     */
    public static boolean isNameValid(String string) {
        if (isBlankOrNull(string)) return false;
        if (string.length() > 45) return false;

        return string.matches("[a-zA-Z0-9]*");
    }
}
