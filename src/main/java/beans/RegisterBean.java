package beans;

import db.models.UserEntity;
import db.operation.UserManager;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import static config.Configuration.ADMIN_KEY;
import static utility.Tools.*;

@Named
@RequestScoped
public class RegisterBean extends BaseBean {

    //----- getters & setters ------

    private String username;
    private String pw1;
    private String pw2;
    private String adminKey;


    //----- getters & setters ------

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        username = name;
    }

    public String getPw1() {
        return pw1;
    }

    public void setPw1(String pw) {
        pw1 = pw;
    }

    public String getPw2() {
        return pw2;
    }

    public void setPw2(String pw) {
        pw2 = pw;
    }

    public String getAdminKey() {
        return adminKey;
    }

    public void setAdminKey(String adminKey) {
        this.adminKey = adminKey;
    }


    //----- methods ------

    /**
     * Validates user input. If the input is valid the data will be stored
     * as a new user record in the database effectively creating a new
     * user.
     *
     * @return URL of a page to redirect to depending on validity of the user input
     */
    public String tryRegister() {

        byte adminRights = 0;

        //check if entered values are valid
        if (isBlankOrNull(username) || isBlankOrNull(pw1) || isBlankOrNull(pw2)) {
            provideMessage("Info", "Ungültige Eingaben");
            return registerPage;
        }

        if (!isNameValid(username)) {
            provideMessage("Info", "Bitte nur Buchstaben und Zahlen im Nutzername verwenden");
            return registerPage;
        }

        //check if password was entered correctly both times
        if (!pw1.equals(pw2)) {
            provideMessage("Info", "Passwörter stimmen nicht überein");
            return registerPage;
        }

        //check if correct admin key was entered
        if (!isBlankOrNull(adminKey)) {
            if (!adminKey.equals(ADMIN_KEY)) {
                provideMessage("Info", "Administrator-Schlüssel ungültig");
                return registerPage;
            } else {
                adminRights = 1;
            }
        }

        //check if user already exists
        UserManager um = UserManager.start();
        if (um.userExists(username)) {
            provideMessage("Info", "Nutzer existiert bereits");
            um.stop();
            return registerPage;
        }

        UserEntity user;
        try {
            user = new UserEntity(username, encryptPw(pw1), adminRights);
        } catch (Exception e) {
            handleException(e);
            um.stop();
            provideMessage("Info", "Interner Fehler bei Passwort-Verschlüsselung");
            return registerPage;
        }

        um.addUser(user);

        um.stop();

        return loginPage;
    }
}
