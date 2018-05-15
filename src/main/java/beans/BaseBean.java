package beans;

import db.models.UserEntity;
import db.operation.UserManager;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import java.io.Serializable;

import static config.Configuration.*;
import static utility.CookieManagement.getCurrentUser;

@Named
@RequestScoped
public class BaseBean implements Serializable {

    private final String googleApiKey = GOOGLE_API_KEY;

    final String webpageURL = WEBPAGE_URL;
    final String loginPage = LOGIN_PAGE;
    final String registerPage = REGISTER_PAGE;
    final String homePage = HOME_PAGE;

    private String activeUser;


    private boolean admin;

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

        if (activeUser == null) {
            return "";
        } else {
            return activeUser;
        }
    }

    public void setActiveUser(String activeUser) {
        this.activeUser = activeUser;
    }

    public boolean isAdmin() {

        UserManager um = UserManager.start();

        UserEntity user = um.getUser(getActiveUser());

        um.stop();

        admin = (user.getAdmin() == 1);

        return admin;
    }

    void provideMessage(String title, String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(title, msg));
    }

}