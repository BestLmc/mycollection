package main.java.cn.lmc.collection.algorithm.stack;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StackTest
 *
 * @author limingcheng
 * @Date 2019/12/9
 */
public class StackTest {
    private static final Logger logger = LoggerFactory.getLogger(StackTest.class);
    /**
     * 测试数组栈
     */
    @Test
    public static void ArrayStackTest(){
        ArrayStack arr = new ArrayStack(5);
        arr.push("aa");
        arr.push("bb");
        arr.push("cc");
        arr.push("dd");
        arr.push("ee");
        System.out.println("结果："+arr.pop());

        logger.debug("结果："+arr.pop());
        logger.trace("结果："+arr.pop());
        logger.error("结果："+arr.pop());
        logger.warn("结果："+arr.pop());

    }


    public static void main(String[] args) {
        ArrayStackTest();
    }
}
