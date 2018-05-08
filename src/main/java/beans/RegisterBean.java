package beans;

import db.models.UserEntity;
import db.operation.UserManager;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import static utility.Tools.getHash;
import static utility.Tools.isBlankOrNull;

@Named
@RequestScoped
public class RegisterBean extends BaseBean {

    private String username;
    private String pw1;
    private String pw2;
    private String msg;

    //setters & getters
    public String getUsername(){
        return username;
    }
    public void setUsername(String name){
        username = name;
    }
    public String getPw1(){
        return pw1;
    }
    public void setPw1(String pw){
        pw1 = pw;
    }
    public String getPw2(){
        return pw2;
    }
    public void setPw2(String pw){
        pw2 = pw;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    //methods
    public String tryRegister(){

        //check if entered values are valid
        if(isBlankOrNull(username) || isBlankOrNull(pw1) || isBlankOrNull(pw2)){
            provideMessage("Info", "Ungültige Eingabewerte");
            return registerPage;
        }

        //check if password was entered correctly both times
        if(!pw1.equals(pw2)){
            provideMessage("Info", "Passwörter stimmen nicht überein");
            return registerPage;
        }

        UserManager um = UserManager.start();

        //check if user already exists
        if(um.userExists(username)){
            provideMessage("Info", "Nutzer existiert bereits");
            um.stop();
            return registerPage;
        }


        UserEntity user = new UserEntity(username, getHash(pw1), (byte)0);

        um.addUser(user);

        um.stop();

        return loginPage;

    }
}
