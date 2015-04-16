/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Joseph
 */
public class UtilCal {

    public static String getCurrentDateString() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        return sdf.format(cal.getTime());
    }

    public static Calendar setTime(Calendar cal, final int hourOfDay, final int minute, final int second, final int ms) {
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, ms);
        return cal;
    }

    public static Calendar zeroTime(Calendar cal) {
        return setTime(cal, 0, 0, 0, 0);
    }

    public static String getDateString(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
        return sdf.format(cal.getTime());
    }
}
