package utility;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class ToolsTest {

    @Test
    public void encryptPw() {
        String plaintext = "Test123";
        String expectedValue = "68EACB97D86F0C4621FA2B0E17CABD8C";
        String returnedValue = null;
        try {
            returnedValue = Tools.encryptPw(plaintext);
        } catch (Exception e) {
            fail(e.toString());
        }
        assertEquals(expectedValue, returnedValue);
    }

    @Test
    public void encryptCookie() {
        String plaintext = "Test123";
        String expectedValue = "VGVzdDEyMw==";
        String returnedValue = null;
        try {
            returnedValue = Tools.encryptCookie(plaintext);
        } catch (Exception e) {
            fail(e.toString());
        }
        assertEquals(expectedValue, returnedValue);
    }

    @Test
    public void decryptCookie() {
        String ciphertext = "VGVzdDEyMw==";
        String expectedValue = "Test123";
        String returnedValue = null;
        try {
            returnedValue = Tools.decryptCookie(ciphertext);
        } catch (Exception e) {
            fail(e.toString());
        }
        assertEquals(expectedValue, returnedValue);
    }

    @Test
    public void precisionRound() {
        float value = 5.123f;
        float result1 = Tools.precisionRound(value, 1);
        float result2 = Tools.precisionRound(value, 2);
        float result3 = Tools.precisionRound(value, 3);
        float result4 = Tools.precisionRound(value, 4);
        float result5 = Tools.precisionRound(value, 0);

        assertEquals(result1, 5.1f, 0.001);
        assertEquals(result2, 5.12f, 0.001);
        assertEquals(result3, 5.123f, 0.001);
        assertEquals(result4, 5.123f, 0.001);
        assertEquals(result5, 5f, 0.001);
    }

    @Test
    public void isBlankOrNull() {
        assertTrue(Tools.isBlankOrNull(null));

        assertTrue(Tools.isBlankOrNull(""));

        assertTrue(Tools.isBlankOrNull("            "));

        assertFalse(Tools.isBlankOrNull("Some random text 12345!"));
    }

    @Test
    public void isValidDate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Date invalidDate1 = formatter.parse("01-01-1850");
        Date invalidDate2 = formatter.parse("01-01-2222");
        Date validDate = formatter.parse("01-01-2018");

        assertFalse(Tools.isValidDate(null));
        assertFalse(Tools.isValidDate(invalidDate1));
        assertFalse(Tools.isValidDate(invalidDate2));
        assertTrue(Tools.isValidDate(validDate));
    }

    @Test
    public void isNameValid() {
        assertFalse(Tools.isNameValid(""));
        assertFalse(Tools.isNameValid("         "));
        assertFalse(Tools.isNameValid("Max Mustermann"));
        assertFalse(Tools.isNameValid("{cr@zy_H4cker"));
        assertFalse(Tools.isNameValid("TooManyCharsTooManyCharsTooManyCharsTooManyChars"));
        assertTrue(Tools.isNameValid("ValidName123"));
    }
}