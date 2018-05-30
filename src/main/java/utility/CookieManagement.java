package utility;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

import static utility.Tools.*;

public final class CookieManagement {

    private static final String usercookieName = "Username";

    private CookieManagement() {
    }

    /**
     * Creates a new cookie in the browser containing the enrypted username.
     *
     * @param username name of the user that should be set as cookie value
     */
    public static void setUserCookie(String username) {
        try {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .addResponseCookie(usercookieName, encryptCookie(username), null);
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Stores an instantly expiring empty cookie in the browser effectively
     * overwriting and deleting a previously stored user cookie.
     */
    public static void deleteUserCookie() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", 0);

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .addResponseCookie(usercookieName, "", properties);
    }

    /**
     * Reads the username from the currently stored cookie. If no cookie
     * containing a valid value is found an empty string is returned.
     *
     * @return current value of the user cookie
     */
    public static String getCurrentUser() {
        try {
            Cookie usercookie = (Cookie)
                    FacesContext.getCurrentInstance()
                            .getExternalContext()
                            .getRequestCookieMap()
                            .get(usercookieName);

            if (usercookie == null) return "";

            return decryptCookie(usercookie.getValue());
        } catch (Exception e) {
            handleException(e);

            //empty string (equals no active user) is returned in case of an error
            return "";
        }
    }
}
