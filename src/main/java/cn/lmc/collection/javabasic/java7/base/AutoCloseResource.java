package main.java.cn.lmc.collection.javabasic.java7.base;

/**
 *自动关闭
 * AutoCloseResource
 *
 * @author limingcheng
 * @Date 2020/2/19
 */
public class AutoCloseResource {
//    Java 7 中其实已经提供了新的解决方式，Java 7 中对 try 进行了增强，可以保证资源总能被正确释放 。
//    使用增强 try 的前提是 try 中的类实现了 AutoCloseable 接口，
//    在 Java 7 中大量的需要释放资源的操作其实都已经实现了此接口了。
    public static void main(String[] args) throws Exception {
        try (Mysql mysql = new Mysql();
             OracleDatabase oracleDatabase = new OracleDatabase()) {
            mysql.conn();
            oracleDatabase.conn();
        }
    }
}

class Mysql implements AutoCloseable {

    @Override
    public void close() throws Exception {
        System.out.println("mysql 已关闭");
    }

    public void conn() {
        System.out.println("mysql 已连接");
    }
}

class OracleDatabase implements AutoCloseable {

    @Override
    public void close() throws Exception {
        System.out.println("OracleDatabase 已关闭");
    }

    public void conn() {
        System.out.println("OracleDatabase 已连接");
    }
}
