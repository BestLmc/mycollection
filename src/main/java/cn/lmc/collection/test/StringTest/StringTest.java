package main.java.cn.lmc.collection.test.StringTest;

import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * StringTest
 *
 * @author limingcheng
 * @Date 2019/12/16
 */
public class StringTest {



    private static enum TYPE {
        high,
        big
    }

    public static void enumTest(){
        System.out.println("name: "+TYPE.high.name());
        System.out.println("toString: "+TYPE.high.toString());
        System.out.println("compare: "+TYPE.high.name().equals(TYPE.high.name()));
        System.out.println("compare: "+TYPE.high.name().equals(TYPE.big.name()));
    }

    private static void subStr(){
         String url = "9999\\2019-08-01\\SB5081_1_3_151_20190801083518.pdf";
         String wjm = url.substring(url.lastIndexOf("\\")+1);
         String wjlj = url.substring(0, url.lastIndexOf("\\"));
         System.out.println("文件名："+wjm);
         System.out.println("原始文件名："+url);
         System.out.println("文件路径： "+wjlj);
    }

    public static void frSubstr(){
        String url = "/files/cl/2019/0809/ca36ae06-b6d7-4e5a-a3ea-135774d5e8be_ac805b07eaea47fcb1d9b17ac05772d.jpg";
        url = url.replace("/","\\");
        String wjm = url.substring(url.lastIndexOf("\\")+1);

//        url = url.substring(1, url.lastIndexOf("/"));

        String filepath = "ftp" + "\\" + "frcl" + "\\" + url;
        String wjlj = filepath + "\\" + wjm;
        System.out.println("文件名："+wjm);
        System.out.println("原始文件名："+url);
        System.out.println("文件路径： "+filepath);

    }

    public static void frTest() throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        String byInfo = "dddddd";
        InputStream is = null;
        InputStream bis = null;
        byte[] imgbyte = decoder.decodeBuffer(byInfo);

        try{
            is = new ByteArrayInputStream(imgbyte);
            System.out.println("byte: "+imgbyte.length);

            bis = new ByteArrayInputStream(imgbyte);

            System.out.println("is :" + is.toString());
            System.out.println("bis :" + bis.toString());
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        // 自然人文件名路径截取
//        subStr();
        // 枚举
//        enumTest();
//        frSubstr();
        frTest();
    }
}
