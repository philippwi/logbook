package utility;

import db.models.TripEntity;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public final class Tools {

    private Tools(){}

    public static void handleException(Exception e){
        System.err.println(e.toString());
    }

    public static void encryptPassword(String pw){
        //TODO
    }

    public static float precisionRound(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
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

    public static boolean isValidDate(Date dt){

        LocalDate date = dt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if(date == null) return false;

        if(date.isBefore(LocalDate.parse("1900-01-01"))) return false;

        if(date.isAfter(LocalDate.now().plusDays(1))) return false;

        return true;
    }
}
