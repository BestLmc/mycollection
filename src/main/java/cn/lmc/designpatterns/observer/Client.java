package main.java.cn.lmc.designpatterns.observer;

/**
 * Client
 *
 * @author limingcheng
 * @Date 2020/9/8
 */
public class Client {

    public static void main(String[] args) {

        Teacher teacher = new Teacher();

        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();

        teacher.registerObserver(s1);
        teacher.registerObserver(s2);
        teacher.registerObserver(s3);

        teacher.setAction("老师来了");

        System.out.println(s1.getAction());
        System.out.println(s2.getAction());
        System.out.println(s3.getAction());

        System.out.println("================");

        teacher.setAction("老师走了");

        System.out.println(s1.getAction());
        System.out.println(s2.getAction());
        System.out.println(s3.getAction());
    }
}