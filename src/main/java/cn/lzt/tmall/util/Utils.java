package cn.lzt.tmall.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author: lzt
 * @Date: 2019/12/3 23:07
 */
public class Utils {

    /**
     * 在输入日期上增加（+）或减去（-）天数
     *
     * @param date   输入日期
     * @param number 要增加或减少的天数
     */
    public static Date addDay(Date date, int number) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.DAY_OF_MONTH, number);
        return cd.getTime();
    }
}
