package main.java.cn.lmc.collection.javabasic.classloader;

import java.time.LocalTime;

/**
 * BaseManager 这个接口的子类要实现类的热加载功能。
 * MyManager
 *
 * @author limingcheng
 * @Date 2020/2/20
 */
public class MyManager implements BaseManager {

    @Override
    public void logic() {
        System.out.println(LocalTime.now() + ": Java类的热加载");
    }
}
