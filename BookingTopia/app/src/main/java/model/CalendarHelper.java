package model;

import java.util.Calendar;

/**
 * Created by Ivan on 08-Apr-16.
 */
public class CalendarHelper {

    public static Calendar fromCal = Calendar.getInstance();
    public static Calendar toCal = Calendar.getInstance();

    public static Calendar fromDBCal = Calendar.getInstance();
    public static Calendar toDBCal = Calendar.getInstance();

    public static void setToCalDatePlusOne(){
        toCal.add(Calendar.DATE, 1);
        toDBCal.add(Calendar.DATE, 1);
    }

    public static void setFromCal(Calendar fromCal) {
        CalendarHelper.fromCal = fromCal;
    }

    public static void setToCal(Calendar toCal) {
        CalendarHelper.toCal = toCal;
    }

}
