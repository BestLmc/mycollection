package main.java.cn.lmc.collection.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * java8Date
 *
 * @author limingcheng
 * @Date 2020/1/3
 */
public class java8Date {

    /**
     *
     */
    private static void localDatetest(){
        System.out.println("时间："+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")));


    }

    public static void main(String[] args) {
        // 测试java8新时间类
        localDatetest();
    }
}
