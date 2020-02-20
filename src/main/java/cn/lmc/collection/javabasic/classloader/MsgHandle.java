package main.java.cn.lmc.collection.javabasic.classloader;

/**
 * MsgHandle
 * 后台启动一条线程，不断检测是否要刷新重新加载，实现了热加载的类
 *
 * @author limingcheng
 * @Date 2020/2/20
 */
public class MsgHandle implements Runnable {
    @Override
    public void run() {
        while (true) {
            BaseManager manager = ManagerFactory.getManager(ManagerFactory.MY_MANAGER);
            manager.logic();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
