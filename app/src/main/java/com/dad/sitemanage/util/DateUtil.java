package com.dad.sitemanage.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * 指定日期格式 yyyy-MM-dd HH:mm:ss
     */
    public static final String FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
    /**
     * 指定日期格式 yyyy-MM-dd
     */
    public static final String FORMAT_2 = "yyyy-MM-dd";
    /**
     * 指定日期格式 HH:mm:ss
     */
    public static final String FORMAT_3 ="HH:mm:ss";
    /**
     * 指定日期格式 yyyy年MM月dd日
     */
    public static final String FORMAT_4 = "yyyy年MM月dd日";
    /**
     * 指定日期格式 yyyy年MM月dd日 HH时mm秒
     */
    public static final String FORMAT_5 = "yyyy年MM月dd日 HH时mm分";

    /**
     * 根据指定格式，获取当前的时间
     * @param format
     * @return
     */
    public static String getCurTimeFormat(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

}
