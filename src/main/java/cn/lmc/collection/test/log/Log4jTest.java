package main.java.cn.lmc.collection.test.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log4jTest
 *
 * @author limingcheng
 * @Date 2020/1/8
 */
public class Log4jTest {

    // 通过slf4j接口创建Logger对象
    private static final Logger logger = LoggerFactory.getLogger(Log4jTest.class);

    public static void print1(){

        System.out.println("=====================================================");
        // 记录debug级别的信息
        logger.debug("This is a debug message.");
        // 记录info级别的信息
        logger.info("This is an info message.");
        // 记录error级别的信息
        logger.error("This is an error message.");
        System.out.println("=====================================================");
    }

    public static void main(String[] args) {
        print1();
    }


}
