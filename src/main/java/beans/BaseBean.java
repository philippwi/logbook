package beans;

import db.models.UserEntity;
import db.operation.UserManager;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

import static config.Configuration.*;
import static utility.CookieManagement.deleteUserCookie;
import static utility.CookieManagement.getCurrentUser;
import static utility.Tools.isBlankOrNull;

@Named
@RequestScoped
public class BaseBean implements Serializable {

    //----- variables & constants ------

    private final String googleApiKey = GOOGLE_API_KEY;

    final String webpageURL = WEBPAGE_URL;
    final String loginPage = LOGIN_PAGE;
    final String registerPage = REGISTER_PAGE;
    final String homePage = HOME_PAGE;
    final String adminPage = ADMIN_PAGE;

    private String activeUser;

    private boolean admin;


    //----- getters & setters ------

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

    public String getAdminPage() {
        return adminPage;
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

        String username = getActiveUser();

        if (isBlankOrNull(username)) {
            admin = false;
            return admin;
        }

        UserManager um = UserManager.start();

        UserEntity user = um.getUser(username);

        um.stop();

        admin = (user.getAdmin() == 1);

        return admin;
    }


    //----- methods ------

    /**
     * Passes a message to the user interface by providing data for
     * the Growl function of Primefaces which will display the message
     * as a small pop-up.
     *
     * @param title Header/category of the message
     * @param msg   Body/content of the message
     */
    void provideMessage(String title, String msg) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(title, msg));
        } catch (Exception ignored) {/*shouldn't bother the user*/}
    }

    /**
     * Signs out currently logged in user.
     *
     * @return URL of the login page
     */
    public String doLogout() {

        if (!isBlankOrNull(getActiveUser())) deleteUserCookie();

        return loginPage;
    }

}
