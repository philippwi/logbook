package utility;

import javax.faces.context.FacesContext;
import java.util.Map;

public final class CookieManagement {

    public static void setUserCookie(String username){
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .addResponseCookie("Username", username, null);
    }

    public static void deleteUserCookie(String username){
        //TODO: testing
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .addResponseCookie("Username", "", null);
    }

    public static String getCurrentUser(){
        //TODO: testing
        Map cookieMap = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestCookieMap();

        return (String)cookieMap.get("Username");
    }

}
