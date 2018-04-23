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

}
