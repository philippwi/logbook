package config;

public final class Configuration {

    private Configuration(){}

    //admin registration
    public static final String ADMIN_KEY = "admin";

    //navigation
    public static final String HOST = "localhost";
    public static final int PORT =  8080;
    public static final String WEBPAGE_URL = "http://" + HOST + ":" + PORT + "/logbook/" + "pages/";
    public static final String LOGIN_PAGE = "login.xhtml";
    public static final String REGISTER_PAGE = "register.xhtml";
    public static final String HOME_PAGE = "home.xhtml";
    public static final String ADMIN_PAGE = "admin.xhtml";

    //jpa
    public static final String PERSISTENCE_UNIT_NAME =  "logbookPU";

    //google
    public static final String GOOGLE_API_KEY = "AIzaSyCWr_YG4zNfwED_ND9ykhtBfNWx9yRbZ-U";


}
