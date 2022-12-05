package utils;

import java.util.Calendar;
import java.util.Date;

public class DateCommon {

    public static Date addPlusDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 5);
        return calendar.getTime();
    }
}
