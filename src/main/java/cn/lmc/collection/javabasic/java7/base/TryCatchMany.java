package main.java.cn.lmc.collection.javabasic.java7.base;

import java.io.IOException;

/**
 * 多异常捕获
 * TryCatchMany
 *
 * @author limingcheng
 * @Date 2020/2/19
 */
public class TryCatchMany {

//    在 Java 7 之前，一个 catch 只能捕获一个异常信息，当异常种类非常多的时候就很麻烦，
//    但是在 Java 7 中，一个 catch 可以捕获多个异常信息，每个异常捕获之间使用 | 分割，
//    需要注意的是，一个 catch 捕获多个异常时，不能出现重复的异常类型，也不能出现一个异常类型是另一个类的子类的情况。
    public static void main(String[] args) {
        try (TxtRead txtRead = new TxtRead()) {
            txtRead.reader();
        } catch (IOException | NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class TxtRead implements AutoCloseable {

    @Override
    public void close() throws Exception {
        System.out.println("资源释放");
    }

    public void reader() throws IOException, NoSuchFieldException {
        System.out.println("数据读取");
    }
}