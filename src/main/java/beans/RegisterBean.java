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

    private String username;
    private String pw1;
    private String pw2;
    private String msg;
    private String adminKey;

    //setters & getters
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAdminKey() {
        return adminKey;
    }

    public void setAdminKey(String adminKey) {
        this.adminKey = adminKey;
    }

    //methods
    public String tryRegister() {

        byte adminRights = 0;

        //check if entered values are valid
        if (isBlankOrNull(username) || isBlankOrNull(pw1) || isBlankOrNull(pw2)) {
            provideMessage("Info", "Ungültige Eingaben");
            return registerPage;
        }

        if(!isNameValid(username)){
            provideMessage("Info", "Bitte nur Buchstaben und Zahlen im Nutzername verwenden");
            return registerPage;
        }

        //check if password was entered correctly both times
        if (!pw1.equals(pw2)) {
            provideMessage("Info", "Passwörter stimmen nicht überein");
            return registerPage;
        }

        //check if correct admin key was entered
        if(!isBlankOrNull(adminKey)){
            if(!adminKey.equals(ADMIN_KEY)){
                provideMessage("Info", "Administrator-Schlüssel ungültig");
                return registerPage;
            }
            else{
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

        UserEntity user = null;
        try {
            user = new UserEntity(username, encrypt(pw1), adminRights);
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
