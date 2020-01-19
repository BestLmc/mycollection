package main.java.cn.lmc.collection.test.StringTest;

import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

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
        String ext = url.substring(url.lastIndexOf(".")+1);

//        url = url.substring(1, url.lastIndexOf("/"));
        // 文件名
        String wjm1 = UUID.randomUUID().toString().replace("-","")+"."+ext;

        String filepath = "ftp" + "\\" + "frcl" + "\\" + url;
        String wjlj = filepath + "\\" + wjm;
        System.out.println("文件名："+wjm);
        System.out.println("原始文件名："+url);
        System.out.println("文件路径： "+filepath);
        System.out.println("文件后缀： "+ext);
        System.out.println("文件后缀： "+wjm1);

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

    /**
     * 转时间格式字符串
     */
    private static void timestostr() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat tsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat fsdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = "2019/8/1 8:31:19";
        String time2 = "2019-08-11T21:27:51";
        time2 = time2.replace("T", " ");
        String outstr = time.substring(0,8);
        Date atime = sdf.parse(time);
        Date ttime = tsdf.parse(time2);
        System.out.println("outstr:" +outstr);
        System.out.println("atime:" +atime);
        System.out.println("转化结果:" +fsdf.format(atime));
        System.out.println("转化结果2:" +fsdf.format(ttime));

    }



    public static void main(String[] args) throws IOException, ParseException {
        // 转时间格式字符串
//        timestostr();


        // 自然人文件名路径截取
//        subStr();
        // 枚举
//        enumTest();
        frSubstr();
//        frTest();
    }
}
