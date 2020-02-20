package main.java.cn.lmc.collection.javabasic.classloader;

/**
 * ClassLoadTest
 *
 * @author limingcheng
 * @Date 2020/2/20
 */
public class ClassLoadTest {

    public static void main(String[] args) {
        new Thread(new MsgHandle()).start();
    }
}
