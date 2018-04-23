package beans;

import db.models.UserEntity;
import db.operation.UserManager;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import static config.Configuration.LOGIN_PAGE;
import static config.Configuration.REGISTER_PAGE;

@Named
@RequestScoped
public class RegisterBean {

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

        msg = "";

        UserManager um = UserManager.start();

        //check if user already exists
        if(um.userExists(username)){
            msg = "User existiert bereits";
            return REGISTER_PAGE;
        }

        //check if password was entered correctly both times
        if(!pw1.equals(pw2)){
            msg = "Passwörter stimmen nicht überein";
            return REGISTER_PAGE;
        }

        UserEntity user = new UserEntity(username, pw1, (byte)0);

        um.addUser(user);

        um.stop();

        return LOGIN_PAGE;

    }
}
