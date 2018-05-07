package beans;

import db.models.UserEntity;
import db.operation.UserManager;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import static utility.CookieManagement.setUserCookie;
import static utility.Tools.getHash;
import static utility.Tools.isBlankOrNull;

@Named
@RequestScoped
public class LoginBean extends BeanBase {

    private String username;
    private String password;

    //setters & getters
    public String getUsername(){
        return username;
    }
    public void setUsername(String name){
        username = name;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String pw){
        password = pw;
    }

    //methods
    public String tryLogin(){

        //check if entered values are valid
        if(isBlankOrNull(username) || isBlankOrNull(password)){
            provideMessage("Info", "Ungültige Eingabewerte");
            return goToLoginPage();
        }

        UserManager um = UserManager.start();

        //check if user exists
        if(!um.userExists(username)){
            provideMessage("Info", "Nutzer existiert nicht");
            um.stop();
            return goToLoginPage();
        }

        UserEntity user = um.getUser(username);

        //check if password is correct
        if(!getHash(password).equals(user.getPassword())) {
            provideMessage("Info", "Passwort nicht korrekt");
            um.stop();
            return goToLoginPage();
        }

        setUserCookie(username);

        return goToHomePage();
    }
}
