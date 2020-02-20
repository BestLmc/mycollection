package main.java.cn.lmc.designpatterns.singleton;

/**
 * Singleton
 *
 * @author limingcheng
 * @Date 2020/2/20
 */
// 单例模式的饿汉模式实现
public class Singleton {
    private final static Singleton SINGLETON= new Singleton();
    // Private constructor suppresses
    private Singleton() {}

    // default public constructor
    public static Singleton getInstance() {
        return SINGLETON;
    }
}
