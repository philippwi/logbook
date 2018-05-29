package beans;

import db.models.UserEntity;
import db.operation.UserManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static utility.Tools.encryptPw;

public class LoginBeanTest {


    private static final String testUserName = "Test-User";
    private static final String invalidUsername = "inv@lid-name";
    private static final String password = "12345";
    private static final String wrongPassword = "abcde";


    private static UserManager um;

    @BeforeClass
    public static void initBeforeClass() throws NoSuchAlgorithmException {
        um = UserManager.start();

        UserEntity user = new UserEntity(testUserName, encryptPw(password), (byte) 0);

        um.addUser(user);
    }

    @AfterClass
    public static void closeAfterClass() {
        um.deleteUser(testUserName);
        um.stop();
    }

    @Test
    public void tryLogin_01() {
        LoginBean lb = new LoginBean();

        lb.setUsername(testUserName);
        lb.setPassword(password);

        //will throw harmless NullPointerException because cookie can't be set
        String redirect = lb.tryLogin();

        assertEquals(lb.homePage, redirect);
    }

    @Test
    public void tryLogin_02() {
        LoginBean lb = new LoginBean();

        lb.setUsername(invalidUsername);
        lb.setPassword(password);

        String redirect = lb.tryLogin();

        assertEquals(lb.loginPage, redirect);
    }

    @Test
    public void tryLogin_03() {
        LoginBean lb = new LoginBean();

        lb.setUsername(testUserName);
        lb.setPassword(wrongPassword);

        String redirect = lb.tryLogin();

        assertEquals(lb.loginPage, redirect);
    }
}