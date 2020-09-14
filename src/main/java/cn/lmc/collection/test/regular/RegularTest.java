package main.java.cn.lmc.collection.test.regular;

/**
 * RegularTest
 *
 * @author limingcheng
 * @Date 2020/9/14
 */
public class RegularTest {

    public static void main(String[] args) {
        emailTest();
        phoneTest();
    }

    private static void emailTest(){
        String email = "test@qq.com";
        System.out.println("原邮箱： " + email);
        email = email.replaceAll("(^\\w)[^@]*(@.*$)", "$1****$2");
        System.out.println("脱敏后邮箱： " + email);
    }

    private static void phoneTest(){
        String phone = "13488889999";
        System.out.println("原手机号码： " + phone);
        phone = phone.replaceAll("(^\\d{3})\\d.*(\\d{4})", "$1****$2");
        System.out.println("脱敏后手机号码： " + phone);
    }
}
