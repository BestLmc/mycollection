package main.java.cn.lmc.designpatterns.singleton;

/**
 * SingletonLayLoadSynDCL
 * 使用双重校验锁以及volatile关键字确保线程安全的同时兼顾执行效率
 *
 * @author limingcheng
 * @Date 2020/2/20
 */
public class SingletonLayLoadSynDCL {
    // 私有化自身类对象
    //	private static SingletonLayLoadSynDCL SINGLETON;
    private volatile static SingletonLayLoadSynDCL SINGLETON;
    // 私有化构造方法
    private SingletonLayLoadSynDCL() {}

    // 使用双重校验锁确保线程安全的同时兼顾执行效率
    public static SingletonLayLoadSynDCL getInstance() {
        if (SINGLETON == null) {
            synchronized (SingletonLayLoadSynDCL.class) {
                if (SINGLETON == null) {
                    SINGLETON = new SingletonLayLoadSynDCL();
                }
            }
        }
        return SINGLETON;

    }
}