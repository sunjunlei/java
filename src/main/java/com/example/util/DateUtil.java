package com.example.util;


import com.example.enums.DateEnum;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author sjl
 */
public class DateUtil {
    /**
     * 时间转换字符串
     * @param date
     * @param dateEnum
     * @return
     */
    public static String date2Str(Date date, @NotNull DateEnum dateEnum){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateEnum.getFormat());
        return simpleDateFormat.format(date);
    }

    /**
     * 字符串转换为时间
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date string2Date(String dateStr,String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(dateStr);
    }

    public static void main(String[] args) throws ParseException {
        Date date = string2Date("2016-12-12", "yyyy-MM-dd");
        System.out.println(date);
    }

    /**
     * 根据开始日期和结束日期, 计算日期差
     *
     * @param from 开始日期
     * @param to   结束日期
     * @return 两个日期之间的天数
     */
    public static int getDays(Date from, Date to) {
        int days = 0;
        if (from.before(to)) {
            while (from.before(to)) {
                days++;
                from = addDays(from, 1);
            }
        } else {
            days = -1;
        }
        return days;
    }

    /**
     * 根据今天的时间 添加 numDay 天的时间
     *
     * @param numDay 要改变的 天数
     * @return
     */
    public static Date addDays(Date date,int numDay) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(Calendar.DATE, numDay);
        Date tempDay = calendar.getTime();
        return tempDay;
    }

}
