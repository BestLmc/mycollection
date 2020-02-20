package main.java.cn.lmc.collection.javabasic.java7.base;

/**
 * 数字下环线
 * NumberLine
 *
 * @author limingcheng
 * @Date 2020/2/19
 */
public class NumberLine {
//    Java 7 开始支持在数字定义时候使用下划线分割，增加了数字的可读性。
    public static void main(String[] args) {
        int a = 1_000;
        int b = 1_0__0_0_0_____00;
        System.out.println(a);
        System.out.println(b);
    }
}
