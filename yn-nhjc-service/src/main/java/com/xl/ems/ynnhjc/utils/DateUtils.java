package com.xl.ems.ynnhjc.utils;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static String getFormatTime(String text,String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = format.parse(text);
        return format.format(date);
    }

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

    public static String getDay(){
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


    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyyMMddHHmmss");
    }

    public static String formatDateTime1(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDateTime2(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static String formatDateTime3(Date date) {
        return formatDate(date, "yyyy-MM");
    }

    public static String formatDateTime4(Date date) {
        return formatDate(date, "yyyy");
    }

    public static String formatDateTime5(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm");
    }

    public static String getTime(String date){
        String[] dates = date.split(":");
        StringBuffer result = new StringBuffer();
        for (String time:dates){
            result.append(time);
        }
        return result.toString();
    }
    public static String getMin(String date){
        String[] dates = date.split(":");
        return dates[1];
    }

    public static String[] formatDateTime(String date) {
        return date.split(" ");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonthStr() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDayStr() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null){
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }


    /**
     * 获取过去的天数
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/(24*60*60*1000);
    }


    /**
     * 获取过去的小时
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/(60*60*1000);
    }

    /**
     * 获取过去的分钟
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/(60*1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis){
        long day = timeMillis/(24*60*60*1000);
        long hour = (timeMillis/(60*60*1000)-day*24);
        long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
        long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
        long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
        return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 获取时间的前十五分钟
     * @param edt
     * @return
     */
    public static Date getTimeBefore(Date edt) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,-15);
        System.out.println(calendar.getTime());
        return calendar.getTime();
    }

    public static String getTimeBeforeDay(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        Date date = calendar.getTime();
        return dateFormat.format(date);
    }



}
