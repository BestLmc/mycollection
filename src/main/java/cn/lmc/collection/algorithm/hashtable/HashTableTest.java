package main.java.cn.lmc.collection.algorithm.hashtable;

import java.io.Console;

/**
 * HashTableTest
 *
 * @author limingcheng
 * @Date 2020/2/21
 */
public class HashTableTest {

    private void singleIntSet(){
        SingleIntSet set = new SingleIntSet();
        set.Add(3);
        set.Add(7);
        System.out.println(set.Contains(3));// 输出 true
        System.out.println(set.Contains(5));// 输出 false
    }

    private static void singleIntSet2(){
        SingleIntSet2 set = new SingleIntSet2();
        set.Add(13);
        set.Add(17);
        System.out.println(set.Contains(13));// 输出 true
        System.out.println(set.Contains(15));// 输出 false
    }

    public static void main(String[] args) {
        singleIntSet2();
    }
}
