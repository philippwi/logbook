package beans;

import db.models.UserEntity;
import db.operation.UserManager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;

import static utility.GeneralUtility.handleException;

@Named
@RequestScoped
public class LoginBean {

    private String username;
    private String password;
    private String msg;


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
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    //methods
    public void tryLogin(){

        msg = "";

        UserManager um = UserManager.start();

        //check if user exists
        if(!um.userExists(username)){
            msg = "User/Passwort-Kombination nicht gefunden";
            return;
        }

        UserEntity user = um.getUser(username);

        //check if password is correct
        if(!password.equals(user.getPassword())) {
            msg = "User/Passwort-Kombination nicht gefunden";
            return;
        }

        //correct credentials --> redirect to homepage
        try {
            FacesContext.getCurrentInstance().getExternalContext().dispatch("/pages/home.xhtml");
        } catch (IOException e) {
            handleException(e);
        }
    }
}
