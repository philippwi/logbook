package beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import java.io.Serializable;

import static config.Configuration.*;
import static utility.CookieManagement.getCurrentUser;

abstract class BeanBase implements Serializable {

    private final String host =  HOST;
    private final int port =  PORT;
    private final String googleApiKey = GOOGLE_API_KEY;
    private final String webpageURL = WEBPAGE_URL;
    private final String loginPage = LOGIN_PAGE;
    private final String registerPage = REGISTER_PAGE;
    private final String homePage = HOME_PAGE;

    private String activeUser;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getGoogleApiKey() {
        return googleApiKey;
    }

    public String getWebpageURL() {
        return webpageURL;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public String getRegisterPage() {
        return registerPage;
    }

    public String getHomePage() {
        return homePage;
    }

    public String getActiveUser() {
        activeUser = getCurrentUser();

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
        return loginPage;
    }

    public String goToRegisterPage(){
        return registerPage;
    }

    public String goToHomePage(){
        return homePage;
    }
}
