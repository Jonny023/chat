package com.jonny.chat.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     *  日期转字符
     * @param date
     * @return
     */
    public static String date2String(Date date) {
        return sdf.format(date);
    }
}
