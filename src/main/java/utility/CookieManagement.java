package utility;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

import static utility.Tools.handleException;
import static utility.Tools.isBlankOrNull;

public final class CookieManagement {

    private CookieManagement(){}

    private static final String usercookieName = "Username";

    public static void setUserCookie(String username){
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .addResponseCookie(usercookieName, username, null);
    }

    public static void deleteUserCookie(){
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", 0);

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .addResponseCookie(usercookieName, "", properties);
    }

    public static String getCurrentUser(){
        try {
            Cookie usercookie = (Cookie)
                    FacesContext.getCurrentInstance()
                            .getExternalContext()
                            .getRequestCookieMap()
                            .get(usercookieName);

            if (usercookie == null) return "";

            return usercookie.getValue();
        }catch (Exception e){
            handleException(e);

            //empty string (equals no active user) is returned in case of an error
            return "";
        }
    }
}
