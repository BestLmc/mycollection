package main.java.cn.lmc.collection.javabasic.java7.base;

import java.io.IOException;
/**
 * 释放资源
 * AutoCloseThrow
 *
 * @author limingcheng
 * @Date 2020/2/19
 */
public class AutoCloseThrow {

    public static void main(String[] args) throws Exception {
        try (FileReadAutoClose fileRead = new FileReadAutoClose()) {
            fileRead.read();
        }
    }
}

class FileReadAutoClose implements AutoCloseable {

    public void read() throws Exception {
        System.out.println("资源读取");
        throw new IOException("读取异常");
    }

    @Override
    public void close() throws Exception {
        System.out.println("资源关闭");
        throw new IOException("关闭异常");
    }
}
