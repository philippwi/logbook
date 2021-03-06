package beans;

import db.operation.UserManager;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import static utility.CookieManagement.setUserCookie;
import static utility.Tools.*;

@Named
@RequestScoped
public class LoginBean extends BaseBean {

    //----- variables ------

    private String username;
    private String password;


    //----- getters & setters ------

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pw) {
        password = pw;
    }

    //----- methods ------

    /**
     * Validates user input. If the input is valid the user will be
     * logged in with the respective credentials.
     *
     * @return URL of a page to redirect to depending on validity of the user input
     */
    public String tryLogin() {

        //check if entered values are valid
        if (isBlankOrNull(username) || isBlankOrNull(password)) {
            provideMessage("Info", "Ungültige Eingaben");
            return loginPage;
        }

        UserManager um = UserManager.start();

        //check if user exists
        if (!um.userExists(username)) {
            provideMessage("Info", "Nutzer existiert nicht");
            um.stop();
            return loginPage;
        }

        //check if password is correct
        String encryptedPw;
        try {
            encryptedPw = encryptPw(password);
        } catch (Exception e) {
            handleException(e);
            um.stop();
            provideMessage("Info", "Interner Fehler bei Passwort-Verschlüsselung");
            return loginPage;
        }

        if (!encryptedPw.equals(um.getUser(username).getPassword())) {
            provideMessage("Info", "Passwort nicht korrekt");
            um.stop();
            return loginPage;
        }

        setUserCookie(username);

        return homePage;
    }
}
