package main.java.cn.lmc.collection.javabasic.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MapToListTest
 *
 * @author limingcheng
 * @Date 2020/9/15
 */
public class MapToListTest {

    public static void main(String[] args) {
        mapTest();
        mapsTest();
    }

    private static void mapTest(){
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put("temp", "123");
        Map<String, Object> temp1Map = new HashMap<>();
        temp1Map.put("temp", "456");
        List<Map<String, Object>> tempList = new ArrayList<>();
        tempList.add(tempMap);
        tempList.add(temp1Map);
        System.out.println("单个map: " + tempList.toString());
    }

    private static void mapsTest(){
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put("temp", "123");
        Map<String, Object> tempMap1 = new HashMap<>();
        tempMap1.put("temp", "456");
        tempMap1.put("temp", "7789");
        List<Map<String, Object>> tempList = new ArrayList<>();
        tempList.add(tempMap);
        tempList.add(tempMap1);
        System.out.println("单个map: " + tempList.toString());
    }

}
