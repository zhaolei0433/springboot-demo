package com.example.utilsdemo.utils;

import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;

/**
 * @author zhaolei
 * Create: 2019/7/30 18:16
 * Modified By:
 * Description:
 */
public class TimeUtil {

    /**
     * 获取当前时间Unix时间戳,转换时为毫秒
     * @return
     */
    public static Long getNowLongTime(){
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(Instant.now().toEpochMilli());
        return c.getTime().getTime();
    }

    /**
     * 获取当前时间yyyy-MM-dd HH:mm:ss格式时间
     * @return
     */
    public static String getNowStringTime(){
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(Instant.now().toEpochMilli());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(c.getTime());
    }

    /**
     * 将Long类型的时间戳转换成String 类型的时间格式，时间格式为：yyyy-MM-dd HH:mm:ss
     */

    public static String convertTimeToString(Long time){
        Assert.notNull(time, "time is null");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time) ;
    }

    /**
     * 将Long类型的时间戳转换成String 类型的时间格式，时间格式为：yyyy-MM-dd HH:mm:ss
     */

    public static String convertTimeToString2(Long time){
        Assert.notNull(time, "time is null");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time) ;
    }



    /**
     * 将字符串转日期成Long类型的时间戳，格式为：yyyy-MM-dd HH:mm:ss
     */
    public static Long convertTimeToLong(String time) throws ParseException {
        Assert.notNull(time, "time is null");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.parse(time).getTime();
    }

    public static void main(String[] a){
        System.out.println(convertTimeToString2(1564483216719L));
    }

}
