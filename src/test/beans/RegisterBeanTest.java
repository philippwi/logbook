package beans;

import db.operation.UserManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.fail;

public class RegisterBeanTest {

    private static final String validUsername = "ChangeIfAlreadyTaken";
    private static final String invalidUsername = "inv@lid-name";
    private static final String password = "12345";
    private static final String differentPassword = "abcde";
    private static final String wrongAdmingKey = "wrong key";

    private static UserManager um;

    @BeforeClass
    public static void initBeforeClass() {
        um = UserManager.start();
    }

    @AfterClass
    public static void closeAfterClass() {
        um.stop();
    }

    @Test
    public void tryRegister_01() {
        RegisterBean rb = new RegisterBean();

        rb.setUsername(validUsername);
        rb.setPw1(password);
        rb.setPw2(password);
        rb.setAdminKey("");

        rb.tryRegister();

        if (um.userExists(validUsername)) {
            um.deleteUser(validUsername);
        } else {
            fail();
        }
    }

    @Test
    public void tryRegister_02() {
        RegisterBean rb = new RegisterBean();

        rb.setUsername(invalidUsername);
        rb.setPw1(password);
        rb.setPw2(password);
        rb.setAdminKey("");

        rb.tryRegister();

        if (um.userExists(invalidUsername)) {
            um.deleteUser(validUsername);
            fail();
        }
    }

    @Test
    public void tryRegister_03() {
        RegisterBean rb = new RegisterBean();

        rb.setUsername(validUsername);
        rb.setPw1(password);
        rb.setPw2(password);
        rb.setAdminKey(wrongAdmingKey);

        rb.tryRegister();

        if (um.userExists(invalidUsername)) {
            um.deleteUser(validUsername);
            fail();
        }
    }

    @Test
    public void tryRegister_04() {
        RegisterBean rb = new RegisterBean();

        rb.setUsername(validUsername);
        rb.setPw1(password);
        rb.setPw2(differentPassword);
        rb.setAdminKey("");

        rb.tryRegister();

        if (um.userExists(validUsername)) {
            um.deleteUser(validUsername);
            fail();
        }
    }
}