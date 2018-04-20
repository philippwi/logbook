package utility;

public final class GeneralUtility {

    private GeneralUtility(){}

    public static void handleException(Exception e){
        System.err.println(e.toString());
    }


}
