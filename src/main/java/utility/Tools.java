package utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Tools {

    private Tools(){}

    public static void handleException(Exception e){
        System.err.println(e.toString());
    }

    public static void encryptPassword(String pw){
        //TODO
    }

    public static double precisionRound(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static boolean isBlankOrNull(String s){

        //check if parameter is null
        if(s == null) return true;

        //check if string is empty
        if(s.isEmpty()) return true;

        //check if string consists of whitespaces only
        if(s.trim().isEmpty()) return true;

        //after all checks are negative
        return false;

    }

}
