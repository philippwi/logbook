package beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import java.io.Serializable;

import static config.Configuration.HOME_PAGE;
import static config.Configuration.LOGIN_PAGE;
import static config.Configuration.REGISTER_PAGE;
import static utility.CookieManagement.getCurrentUser;

abstract class BeanBase implements Serializable {

    private String activeUser;

    public String getActiveUser() {
        activeUser =getCurrentUser();

        if(activeUser == null) {
            return "";
        }
        else{
            return activeUser;
        }
    }

    public void setActiveUser(String activeUser) {
        this.activeUser = activeUser;
    }

    void provideMessage(String title, String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(title, msg));
    }

    public String goToLoginPage(){
        return LOGIN_PAGE;
    }

    public String goToRegisterPage(){
        return REGISTER_PAGE;
    }

    public String goToHomePage(){
        return HOME_PAGE;
    }
}
