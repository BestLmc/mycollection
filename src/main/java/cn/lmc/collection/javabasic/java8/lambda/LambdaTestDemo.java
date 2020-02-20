package main.java.cn.lmc.collection.javabasic.java8.lambda;


import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * LambdaTestDemo
 *
 * @author limingcheng
 * @Date 2020/2/19
 */
public class LambdaTestDemo {

    /**
     * Lambda 的使用，使用 Runnable 例子
     * @throws InterruptedException
     */
    @Test
    public void createLambda() throws InterruptedException {
        // 使用 Lambda 之前
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("JDK8 之前的线程创建");
            }
        };
        new Thread(runnable).start();
        // 使用 Lambda 之后
        Runnable runnable1Jdk8 = () -> System.out.println("JDK8 之后的线程创建");
        new Thread(runnable1Jdk8).start();
        // 更加紧凑的方式
        new Thread(() -> System.out.println("JDK8 之后的线程创建")).start();
    }

    /**
     * 函数接口，Lambda 测试
     */
    @Test
    public void functionLambdaTest() {
        FunctionInterfaceDemo demo = (name, age) -> System.out.println("我叫" + name + "，我今年" + age + "岁了");
        demo.say("金庸", 99);
    }


    public static List<User> userList = new ArrayList<User>();
    static {
        userList.add(new User("A", 26));
        userList.add(new User("B", 18));
        userList.add(new User("C", 23));
        userList.add(new User("D", 19));
    }
    /**
     * 测试方法引用
     */
    @Test
    public void methodRef() {
        User[] userArr = new User[userList.size()];
        userList.toArray(userArr);
        // User::getAge 调用 getAge 方法
        Arrays.sort(userArr, Comparator.comparing(User::getAge));
        for (User user : userArr) {
            System.out.println(user);
        }
    }

    /**
     * 新的遍历方式
     */
    @Test
    public void foreachTest() {
        List<String> skills = Arrays.asList("java", "golang", "c++", "c", "python");
        // 使用 Lambda 之前
        for (String skill : skills) {
            System.out.print(skill+",");
        }
        System.out.println();
        // 使用 Lambda 之后
        // 方式1,forEach+lambda
        skills.forEach((skill) -> System.out.print(skill+","));
        System.out.println();
        // 方式2，forEach+方法引用
        skills.forEach(System.out::print);
    }

    @Test
    public void streamTest() {
        List<String> skills = Arrays.asList("java", "golang", "c++", "c", "python", "java");
        // Jdk8 之前
        for (String skill : skills) {
            System.out.print(skill + ",");
        }
        System.out.println();
        // Jdk8 之后-去重遍历
        skills.stream().distinct().forEach(skill -> System.out.print(skill + ","));
        System.out.println();
        // Jdk8 之后-去重遍历
        skills.stream().distinct().forEach(System.out::print);
        System.out.println();
        // Jdk8 之后-去重，过滤掉 ptyhon 再遍历
        skills.stream().distinct().filter(skill -> skill != "python").forEach(skill -> System.out.print(skill + ","));
        System.out.println();
        // Jdk8 之后转字符串
        String skillString = String.join(",", skills);
        System.out.println(skillString);
    }

    /**
     * 数据转换
     */
    @Test
    public void mapTest() {
        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5);
        // 数据转换
        numList.stream().map(num -> num * num).forEach(num -> System.out.print(num + ","));

        System.out.println();

        // 数据收集
        Set<Integer> numSet = numList.stream().map(num -> num * num).collect(Collectors.toSet());
        numSet.forEach(num -> System.out.print(num + ","));
    }

    /**
     * 数学计算测试
     */
    @Test
    public void mapMathTest() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        IntSummaryStatistics stats = list.stream().mapToInt(x -> x).summaryStatistics();
        System.out.println("最小值：" + stats.getMin());
        System.out.println("最大值：" + stats.getMax());
        System.out.println("个数：" + stats.getCount());
        System.out.println("和：" + stats.getSum());
        System.out.println("平均数：" + stats.getAverage());
        // 求和的另一种方式
        Integer integer = list.stream().reduce((sum, cost) -> sum + cost).get();
        System.out.println(integer);
    }




}

