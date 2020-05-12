package com.xl.ems.userservice.utils;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */

public class DateUtils {

    /**
     * 获取本月第一天
     * @return
     */
    public static String getMonthFirstDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //获取当前月
        calendar.add(Calendar.MONTH,0);
        //当前月的第一天
        calendar.set(Calendar.DAY_OF_MONTH,1);
        return getDateString(calendar);
    }

    /**
     * 获取当天
     * @return
     */
    public static String getMonthNowDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return getDateString(calendar);
    }

    /**
     * 获取当前天的前一天
     * @return
     */
    public static String getMonthPreDay(){
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,-1);
        return getDateString(calendar);
    }

    /*public static void main(String[] args) {
        String ss = "0.00000000";
        System.out.println(Double.valueOf(ss));
        System.out.println(getMonthPreDay());
        System.out.println(getYearFirstMonth());
        System.out.println(getMonth());
        System.out.println(getLastYearFirstMonth());
        System.out.println(getLastYearLastMonth());
    }*/

    public static String getLastMonthFirstDay(){
        Calendar calendar = Calendar.getInstance();
        //获取上个月
        calendar.add(Calendar.MONTH,-1);
        //第一天
        calendar.set(Calendar.DAY_OF_MONTH,1);
        return getDateString(calendar);
    }

    /**
     * 获取上个月最后一天
     * @return
     */
    public static String getLastMonthLastDay(){
        //获得当前时间
        Calendar calendar = Calendar.getInstance();
        //获取当前月第一天
        calendar.set(Calendar.DAY_OF_MONTH,1);
        //当前月的第一天的前一天就是 上个月的最后一天
        calendar.add(Calendar.DATE,-1);
        return getDateString(calendar);
    }

    /**
     * 获取上上个月的第一天
     * @return
     */
    public static String getLastLastMonthFirstDay(){
        Calendar calendar = Calendar.getInstance();
        //获取上上个月
        calendar.add(Calendar.MONTH,-2);
        //获取第一天
        calendar.set(Calendar.DAY_OF_MONTH,1);
        return getDateString(calendar);
    }

    /**
     * 获取上上个月的最后一天
     * @return
     */
    public static String getLastLastMonthLastDay(){
        Calendar calendar =Calendar.getInstance();
        //获取上个月
        calendar.add(Calendar.MONTH,-1);
        //上个月第一天的前一天
        calendar.set(Calendar.DAY_OF_MONTH,0);
        return getDateString(calendar);

    }

    /**
     * 获取当前月
     * @return
     */
    public static String getMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,0);
        return getMonthString(calendar);
    }

    public static String getLastMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        return getMonthString(calendar);
    }

    public static String getLastLastMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-2);
        return getMonthString(calendar);
    }

    /**
     * 获取今年第一个月
     * @return
     */
    public static String getYearFirstMonth(){
        Calendar calendar = Calendar.getInstance();
        //获取今年
        calendar.add(Calendar.YEAR,0);
        //设置第一个月
        calendar.set(Calendar.MONTH,0);
        return getMonthString(calendar);
    }

    /**
     * 获取去年第一个月
     * @return
     */
    public static String getLastYearFirstMonth(){
        Calendar calendar = Calendar.getInstance();
        //获取去年
        calendar.add(Calendar.YEAR,-1);
        //设置第一个月
        calendar.set(Calendar.MONTH,0);
        return getMonthString(calendar);
    }

    /**
     * 获取去年最后一个月
     * @return
     */
    public static String getLastYearLastMonth(){
        Calendar calendar = Calendar.getInstance();
        //获取今年
        calendar.add(Calendar.YEAR,0);
        //今年第一个月的前一个月就是去年的最后一个月
        calendar.set(Calendar.MONTH,-1);
        return getMonthString(calendar);
    }



    private static String getDateString(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(calendar.getTime());
    }

    private static String getMonthString(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        return simpleDateFormat.format(calendar.getTime());
    }



}
