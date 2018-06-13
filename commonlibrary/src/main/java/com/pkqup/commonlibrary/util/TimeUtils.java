package com.pkqup.commonlibrary.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    private static final String TAG = "TimeUtil";

    public static final String DATA_AND_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE = "yyyy-MM-dd";

    public static final String TIME = "HH:mm:ss";

    /*
     * 日期转换为毫秒值
     */
    public static long dateToMillis(String dataStr) {
        long milTime = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATA_AND_TIME, Locale.getDefault());
            Date date = simpleDateFormat.parse(dataStr);
            milTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milTime;
    }

    /*
     * 毫秒值转换为日期
     */
    public static String millisToDate(String millis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATA_AND_TIME, Locale.getDefault());
        Date date = new Date(Long.valueOf(millis));
        return simpleDateFormat.format(date);
    }

}
