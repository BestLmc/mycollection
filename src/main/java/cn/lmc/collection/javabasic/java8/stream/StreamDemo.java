package main.java.cn.lmc.collection.javabasic.java8.stream;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * StreamDemo
 *
 * @author limingcheng
 * @Date 2020/2/19
 */
public class StreamDemo {


    /**
     * 数据处理/转换（intermedia）
     * @Author: limingcheng on 2020/2/19 10:50
     * @param:  * @param
     * @return: void
     * @Description:
     */
    @Test
    public void streamDemo(){
        List<String> nameList = Arrays.asList("Darcy", "Chris", "Linda", "Sid", "Kim", "Jack", "Poul", "Peter");
        // 1. 筛选出名字长度为4的
        // 2. 名字前面拼接 This is
        // 3. 遍历输出
        nameList.stream()
                .filter(name -> name.length() == 4)
                .map(name -> "This is "+name)
                .forEach(name -> System.out.println(name));
    }

    /**
     * 结果处理（terminal ）
     * 转换成为大写然后收集结果，遍历输出
     */
    @Test
    public void toUpperCaseDemo() {
        List<String> nameList = Arrays.asList("Darcy", "Chris", "Linda", "Sid", "Kim", "Jack", "Poul", "Peter");
        List<String> upperCaseNameList = nameList.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        upperCaseNameList.forEach(name -> System.out.println(name + ","));
    }

    /**
     * Stream 流获取
     * @Author: limingcheng on 2020/2/19 10:50
     * @param:  * @param
     * @return: void
     * @Description:
     */
    @Test
    public void createStream() throws FileNotFoundException {
        List<String> nameList = Arrays.asList("Darcy", "Chris", "Linda", "Sid", "Kim", "Jack", "Poul", "Peter");
        String[] nameArr = {"Darcy", "Chris", "Linda", "Sid", "Kim", "Jack", "Poul", "Peter"};
        // 集合获取 Stream 流
        Stream<String> nameListStream = nameList.stream();
        // 集合获取并行 Stream 流
        Stream<String> nameListStream2 = nameList.parallelStream();
        // 数组获取 Stream 流
        Stream<String> nameArrStream = Stream.of(nameArr);
        // 数组获取 Stream 流
        Stream<String> nameArrStream1 = Arrays.stream(nameArr);
        // 文件流获取 Stream 流
        BufferedReader bufferedReader = new BufferedReader(new FileReader("README.md"));
        Stream<String> linesStream = bufferedReader.lines();
        // 从静态方法获取流操作
        IntStream rangeStream = IntStream.range(1, 10);
        rangeStream.limit(10).forEach(num -> System.out.print(num+","));
        System.out.println();
        IntStream intStream = IntStream.of(1, 2, 3, 3, 4);
        intStream.forEach(num -> System.out.print(num+","));
    }

    /**
     * map / flatMap
     * 把数字值乘以2
     */
    @Test
    public void mapTest() {
        List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        // 映射成 2倍数字
        List<Integer> collect = numberList.stream()
                .map(number -> number * 2)
                .collect(Collectors.toList());
        collect.forEach(number -> System.out.print(number + ","));
        System.out.println();

        numberList.stream()
                .map(number -> "数字 " + number + ",")
                .forEach(number -> System.out.println(number));
    }

    /**
     * flatmap把对象扁平化
     */
    @Test
    public void flatMapTest() {
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        List<Integer> collect = inputStream
                .flatMap((childList) -> childList.stream())
                .collect(Collectors.toList());
        collect.forEach(number -> System.out.print(number + ","));
    }

    /**
     * filter 数据筛选
     * 筛选出偶数数字
     */
    @Test
    public void filterTest() {
        List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> collect = numberList.stream()
                .filter(number -> number % 2 == 0)
                .collect(Collectors.toList());
        collect.forEach(number -> System.out.print(number + ","));
    }

    /**
     * 查找第一个数据
     * 返回的是一个 Optional 对象
     */
    @Test
    public void findFirstTest(){
        List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Optional<Integer> firstNumber = numberList.stream()
                .findFirst();
        System.out.println(firstNumber.orElse(-1));
        // 输出结果
        // 1
    }

    /**
     * Stream 转换为其他数据结构
     */
    @Test
    public void collectTest() {
        List<Integer> numberList = Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5);
        // to array
        Integer[] toArray = numberList.stream()
                .toArray(Integer[]::new);
        // to List
        List<Integer> integerList = numberList.stream()
                .collect(Collectors.toList());
        // to set
        Set<Integer> integerSet = numberList.stream()
                .collect(Collectors.toSet());
        System.out.println(integerSet);
        // to string
        String toString = numberList.stream()
                .map(number -> String.valueOf(number))
                .collect(Collectors.joining()).toString();
        System.out.println(toString);
        // to string split by ,
        String toStringbJoin = numberList.stream()
                .map(number -> String.valueOf(number))
                .collect(Collectors.joining(",")).toString();
        System.out.println(toStringbJoin);
        // 输出结果
        // [1, 2, 3, 4, 5]
        // 112233445
        // 1,1,2,2,3,3,4,4,5
    }

    /**
     * 获取 / 扔掉前 n 个元素
     */
    @Test
    public void limitOrSkipTest() {
        // 生成自己的随机数流
        List<Integer> ageList = Arrays.asList(11, 22, 13, 14, 25, 26);
        ageList.stream()
                .limit(3)
                .forEach(age -> System.out.print(age+","));
        System.out.println();

        ageList.stream()
                .skip(3)
                .forEach(age -> System.out.print(age+","));
        // 输出结果
        // 11,22,13,
        // 14,25,26,
    }

    /**
     * 数学计算测试
     */
    @Test
    public void mathTest() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        IntSummaryStatistics stats = list.stream().mapToInt(x -> x).summaryStatistics();
        System.out.println("最小值：" + stats.getMin());
        System.out.println("最大值：" + stats.getMax());
        System.out.println("个数：" + stats.getCount());
        System.out.println("和：" + stats.getSum());
        System.out.println("平均数：" + stats.getAverage());
        // 输出结果
        // 最小值：1
        // 最大值：6
        // 个数：6
        // 和：21
        // 平均数：3.5
    }

    /**
     * groupingBy
     * 按年龄分组
     */
    @Test
    public void groupByTest() {
        List<Integer> ageList = Arrays.asList(11, 22, 13, 14, 25, 26);
        Map<String, List<Integer>> ageGrouyByMap = ageList.stream()
                .collect(Collectors.groupingBy(age -> String.valueOf(age / 10)));
        ageGrouyByMap.forEach((k, v) -> {
            System.out.println("年龄" + k + "0多岁的有：" + v);
        });
        // 输出结果
        // 年龄10多岁的有：[11, 13, 14]
        // 年龄20多岁的有：[22, 25, 26]
    }

    /**
     * partitioningBy
     * 按某个条件分组
     * 给一组年龄，分出成年人和未成年人
     */
    public void partitioningByTest() {
        List<Integer> ageList = Arrays.asList(11, 22, 13, 14, 25, 26);
        Map<Boolean, List<Integer>> ageMap = ageList.stream()
                .collect(Collectors.partitioningBy(age -> age > 18));
        System.out.println("未成年人：" + ageMap.get(false));
        System.out.println("成年人：" + ageMap.get(true));
        // 输出结果
        // 未成年人：[11, 13, 14]
        // 成年人：[22, 25, 26]
    }

    /**
     * 生成自己的 Stream 流
     */
    @Test
    public void generateTest(){
        // 生成自己的随机数流
        Random random = new Random();
        Stream<Integer> generateRandom = Stream.generate(random::nextInt);
        generateRandom.limit(5).forEach(System.out::println);
        // 生成自己的 UUID 流
        Stream<UUID> generate = Stream.generate(UUID::randomUUID);
        generate.limit(5).forEach(System.out::println);
        // 输出结果
        // 793776932
        // -2051545609
        // -917435897
        // 298077102
        // -1626306315
        // 31277974-841a-4ad0-a809-80ae105228bd
        // f14918aa-2f94-4774-afcf-fba08250674c
        // d86ccefe-1cd2-4eb4-bb0c-74858f2a7864
        // 4905724b-1df5-48f4-9948-fa9c64c7e1c9
        // 3af2a07f-0855-455f-a339-6e890e533ab3
    }

    /**
     * 惰性计算
     * 找出偶数
     */
    @Test
    public void lazyTest() {
        // 生成自己的随机数流
        List<Integer> numberLIst = Arrays.asList(1, 2, 3, 4, 5, 6);
        // 找出偶数
        Stream<Integer> integerStream = numberLIst.stream()
                .filter(number -> {
                    int temp = number % 2;
                    if (temp == 0 ){
                        System.out.println(number);
                    }
                    return temp == 0;
                });

        System.out.println("分割线");
        List<Integer> collect = integerStream.collect(Collectors.toList());
    }

    /**
     * 并行计算
     */
    @Test
    public void parallelStreamTest() {
        // 生成自己的随机数流，取一千万个随机数
        Random random = new Random();
        Stream<Integer> generateRandom = Stream.generate(random::nextInt);
        List<Integer> numberList = generateRandom.limit(10000000).collect(Collectors.toList());

        // 串行 - 把一千万个随机数，每个随机数 * 2 ，然后求和
        long start = System.currentTimeMillis();
        int sum = numberList.stream()
                .map(number -> number * 2)
                .mapToInt(x -> x)
                .sum();
        long end = System.currentTimeMillis();
        System.out.println("串行耗时："+(end - start)+"ms，和是:"+sum);

        // 并行 - 把一千万个随机数，每个随机数 * 2 ，然后求和
        start = System.currentTimeMillis();
        sum = numberList.parallelStream()
                .map(number -> number * 2)
                .mapToInt(x -> x)
                .sum();
        end = System.currentTimeMillis();
        System.out.println("并行耗时："+(end - start)+"ms，和是:"+sum);
    }

}
