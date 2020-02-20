package main.java.cn.lmc.collection.javabasic.java8.base;

import org.junit.Test;

import java.util.Optional;

/**
 * OptionalTestDemo
 *
 * @author limingcheng
 * @Date 2020/2/19
 */
public class OptionalTestDemo {


    /**
     * 创建一个 Optional
     */
    @Test
    public void createOptionalTest() {
        // Optional 构造方式1 - of 传入的值不能为 null
        Optional<String> helloOption = Optional.of("hello");

        // Optional 构造方式2 - empty 一个空 optional
        Optional<String> emptyOptional = Optional.empty();

        // Optional 构造方式3 - ofNullable 支持传入 null 值的 optional
        Optional<String> nullOptional = Optional.ofNullable(null);
    }

    /**
     * 检查是否有值
     */
    @Test
    public void checkOptionalTest() {
        Optional<String> helloOptional = Optional.of("Hello");
        System.out.println(helloOptional.isPresent());

        Optional<Object> emptyOptional = Optional.empty();
        System.out.println(emptyOptional.isPresent());
    }

    /**
     * 如果有值，输出长度
     */
    @Test
    public void whenIsPresent() {
        // 如果没有值，获取默认值
        Optional<String> helloOptional = Optional.of("Hello");
        Optional<String> emptyOptional = Optional.empty();
        helloOptional.ifPresent(s -> System.out.println(s.length()));
        emptyOptional.ifPresent(s -> System.out.println(s.length()));
    }

    /**
     * 如果没有值，会抛异常
     */
    @Test
    public void getTest() {
        Optional<String> stringOptional = Optional.of("hello");
        System.out.println(stringOptional.get());
        // 如果没有值，会抛异常
        Optional<String> emptyOptional = Optional.empty();
        System.out.println(emptyOptional.get());
    }

    /**
     * 如果没有值，获取默认值
     */
    @Test
    public void whenIsNullGetTest() {
        // 如果没有值，获取默认值
        Optional<String> emptyOptional = Optional.empty();
        String orElse = emptyOptional.orElse("orElse default");
        String orElseGet = emptyOptional.orElseGet(() -> "orElseGet default");
        System.out.println(orElse);
        System.out.println(orElseGet);
    }

    /**
     * orElse 和 orElseGet 的区别
     */
    @Test
    public void orElseAndOrElseGetTest() {
        // 如果没有值，默认值
        Optional<String> emptyOptional = Optional.empty();
        System.out.println("空Optional.orElse");
        String orElse = emptyOptional.orElse(getDefault());
        System.out.println("空Optional.orElseGet");
        String orElseGet = emptyOptional.orElseGet(() -> getDefault());
        System.out.println("空Optional.orElse结果："+orElse);
        System.out.println("空Optional.orElseGet结果："+orElseGet);
        System.out.println("--------------------------------");
        // 如果没有值，默认值
        Optional<String> stringOptional = Optional.of("hello");
        System.out.println("有值Optional.orElse");
        orElse = stringOptional.orElse(getDefault());
        System.out.println("有值Optional.orElseGet");
        orElseGet = stringOptional.orElseGet(() -> getDefault());
        System.out.println("有值Optional.orElse结果："+orElse);
        System.out.println("有值Optional.orElseGet结果："+orElseGet);
    }

    public String getDefault() {
        System.out.println("   获取默认值中..run getDeafult method");
        return "hello1";
    }

    /**
     * 如果没有值，抛出异常
     */
    @Test
    public void whenIsNullThrowExceTest() throws Exception {
        // 如果没有值，抛出异常
        Optional<String> emptyOptional = Optional.empty();
        String value = emptyOptional.orElseThrow(() -> new Exception("发现空值"));
        System.out.println(value);
    }

    @Test
    public void functionTest() {
        // filter 过滤
        Optional<Integer> optional123 = Optional.of(123);
        optional123.filter(num -> num == 123).ifPresent(num -> System.out.println(num));

        Optional<Integer> optional456 = Optional.of(456);
        optional456.filter(num -> num == 123).ifPresent(num -> System.out.println(num));

        // map 转换
        Optional<Integer> optional789 = Optional.of(789);
        optional789.map(String::valueOf).map(String::length).ifPresent(length -> System.out.println(length));
    }



}
