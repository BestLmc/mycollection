package main.java.cn.lmc.collection.test.enumtest;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * 枚举测试
 * @author limingcheng
 *
 */
public class EnumTest {
	
	/**
	 * 枚举值使用和初始化
	 */
	private static void test1() {
		for (EnumDemo e : EnumDemo.values()) {
            System.out.println(e.toString());
        } 
		System.out.println("----------------我是分隔线------------------"); 
		// 枚举类的初始化
		EnumDemo enumdemo = EnumDemo.MON;
        switch (enumdemo) {
        case MON:
            System.out.println("今天是星期一");
            break;
        case TUE:
            System.out.println("今天是星期二");
            break;
        // ... ...
        default:
            System.out.println(enumdemo);
            break;
        } 
	}
	
	/**
	 * 枚举比较
	 */
	private static void enumCompare() {
		EnumDemo test = EnumDemo.MON;
        
        //compareTo(E o)
        switch (test.compareTo(EnumDemo.MON)) {
        case -1:
            System.out.println("TUE 在 MON 之前");
            break;
        case 1:
            System.out.println("TUE 在 MON 之后");
            break;
        default:
            System.out.println("TUE 与 MON 在同一位置");
            break;
        }
         
        //getDeclaringClass()
        System.out.println("getDeclaringClass(): " + test.getDeclaringClass().getName());
         
        //name() 和  toString()
        System.out.println("name(): " + test.name());
        System.out.println("toString(): " + test.toString());
         
        //ordinal()， 返回值是从 0 开始
        System.out.println("ordinal(): " + test.ordinal());
	}
	
	/**
	 * 枚举类2（包括方法）
	 */
	private static void enumDemo2() {
		System.out.println("周五"+EnumDemo2.FRI);
		System.out.println("周五"+EnumDemo2.FRI.isRest());
		System.out.println("周六"+EnumDemo2.SAT);
		System.out.println("周六"+EnumDemo2.SAT.isRest());
		System.out.println("EnumTest.FRI 的 value = " + EnumDemo2.FRI.getValue());
	}
	
	/**
	 * enum的set,map用法
	 */
	private static void enumSet() {
		// EnumSet的使用
        EnumSet<EnumDemo2> weekSet = EnumSet.allOf(EnumDemo2.class);
        for (EnumDemo2 day : weekSet) {
            System.out.println(day);
        }
 
        // EnumMap的使用
        EnumMap<EnumDemo2, String> weekMap = new EnumMap(EnumDemo2.class);
        weekMap.put(EnumDemo2.MON, "星期一");
        weekMap.put(EnumDemo2.TUE, "星期二");
        // ... ...
        for (Iterator<Entry<EnumDemo2, String>> iter = weekMap.entrySet().iterator(); iter.hasNext();) {
            Entry<EnumDemo2, String> entry = iter.next();
            System.out.println(entry.getKey().name() + ":" + entry.getValue());
        }
	}
	
	public static void main(String[] args) {
//		test1();	// 枚举值使用和初始化
//		enumCompare();	// 枚举比较
		enumDemo2();	// 枚举类2
//		enumSet();	// enum的set,map用法
	}
}
