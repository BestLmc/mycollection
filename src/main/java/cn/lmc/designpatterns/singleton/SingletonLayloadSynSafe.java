package main.java.cn.lmc.designpatterns.singleton;

/**
 * SingletonLayloadSynSafe
 * 利用静态内部类实现线程安全且兼顾效率的单例模式
 *
 * @author limingcheng
 * @Date 2020/2/20
 */
public class SingletonLayloadSynSafe {
    //静态内部类
    public static class SingletonHolder{
        static final SingletonLayloadSynSafe INSTANCE =
                new SingletonLayloadSynSafe();
    }
    // 私有化构造方法
    private SingletonLayloadSynSafe() {}

    // 公有方法获取实例
    public static SingletonLayloadSynSafe getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
