package main.java.cn.lmc.collection.test.NumberTest;

/**
 * BaseNumberTest
 *
 * @author limingcheng
 * @Date 2020/1/6
 */
public class BaseNumberTest {


    private static void divisionTest(){
       int a = 101, b = 10;
        int totalPageNum = 0;
        int total = 20;
        int pageSize = 10;

        totalPageNum = total % pageSize==0?(total/pageSize):(total/pageSize)+1;
        System.out.println("a/b:"+a/b);
        System.out.println("a%b:"+a%b);
        System.out.println("a%b:"+(100%10));
        System.out.println("totalPageNum:"+totalPageNum);
    }

    private static void nums(){
        float num = 0.01f;
        int i = 0;
        while(i<29) {
            i += 1;
            num = num + num * 2;
        }
        System.out.println("总数为："+String.valueOf(num));
    }

    public static void main(String[] args) {

//        divisionTest();
        nums();
    }
}
