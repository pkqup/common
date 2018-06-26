package com.pkqup.commonlibrary.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    public static final String DATA_AND_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE = "yyyy-MM-dd";

    public static final String TIME = "HH:mm:ss";

    /*
     * 完整日期转换为毫秒值
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
     * 毫秒值转换为完整日期
     */
    public static String millisToDate(String millis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATA_AND_TIME, Locale.getDefault());
        Date date = new Date(Long.valueOf(millis));
        return simpleDateFormat.format(date);
    }

    /*
     * 将HH:mm:ss转换为毫秒值
     */
    public static long dateToStamp(String s) {
        long milTime = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME, Locale.getDefault());
            Date date = simpleDateFormat.parse(s);
            milTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milTime;
    }

    /*
     * 将毫秒值转换为HH:mm:ss
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME, Locale.getDefault());
        long lt = Long.valueOf(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }



}
