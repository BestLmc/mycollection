package main.java.cn.lmc.collection.javabasic.java7.base;

/**
 * 二进制
 * Binary
 *
 * @author limingcheng
 * @Date 2020/2/19
 */
public class Binary {
//    Java 7 开始，可以直接指定不同的进制数字。
//
//    二进制指定数字值，只需要使用 0b 或者 OB 开头。
//    八进制指定数字值，使用 0 开头。
//    十六进制指定数字值，使用 0x 开头
    public static void main(String[] args) {
        // 二进制
        System.out.println("------2进制-----");
        int a = 0b001;
        int b = 0b010;
        System.out.println(a);
        System.out.println(b);
        // 八进制
        System.out.println("------8进制-----");
        int a1 = 010;
        int b1 = 020;
        System.out.println(a1);
        System.out.println(b1);
        // 十六进制
        System.out.println("------16进制-----");
        int a2 = 0x10;
        int b2 = 0x20;
        System.out.println(a2);
        System.out.println(b2);
    }
}
