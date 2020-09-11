package main.java.cn.lmc.collection.javabasic.datetime;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTimeTest
 *
 * @author limingcheng
 * @Date 2020/8/20
 */
public class LocalDateTimeTest {

    public static void main(String[] args) {
        // 获取当前时间
//        getNow();
        // 使用LocalDateTime转成字符串
//        getNowStr();
        // 使用LocalDateTime转成long
        getNowLong();
        // 获取时间戳
//        getDuration();

    }

    /**
     * 使用
     */
    private static void getNow(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
    }

    /**
     * 时间转换
     */
    private static void getNowStr(){
        // 转换器
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String today = df.format(now);
        System.out.println(today);
    }

    /**
     * 时间转换
     */
    private static void getNowLong(){
        // 第八区时间
        Long createAt = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        System.out.println("当前时间"+createAt);
    }

    /**
     * 获取时间戳
     */
    private static void getDuration(){

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusDays(-1);

        Duration duration = Duration.between(startTime, endTime);
        long days = duration.toDays(); //相差的天数
        long hours = duration.toHours();//相差的小时数
        long minutes = duration.toMinutes();//相差的分钟数
        long millis = duration.toMillis();//相差毫秒数
        long nanos = duration.toNanos();//相差的纳秒数
        System.out.println("开始时间"+startTime);
        System.out.println("结束时间"+endTime);
        System.out.println("相差的天数： "+days);

    }


}
