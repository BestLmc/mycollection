package main.java.cn.lmc.collection.javabasic.exceptionTest;

/**
 * ExceptionTest
 *
 * @author limingcheng
 * @Date 2020/9/15
 */
public class ExceptionTest {

    public static void main(String[] args) {


        String rtStr = test();
        System.out.println("结果是："+rtStr);
    }

    private static String test(){
        String rtStr = "test";
        try{
            System.out.println("======================");
            rtStr = "123";
            if(true){
                return rtStr;
            }

            if(true) {
                rtStr = "456";
                return rtStr;
            }

            System.out.println("213234234");

        }catch (Exception e){
            e.printStackTrace();
            rtStr = "789";
        }
        return rtStr;
    }

}
