package main.java.cn.lmc.collection.file.base64;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Base64Test
 *
 * @author limingcheng
 * @Date 2020/9/17
 */
public class Base64Test {
    public static void main(String[] args) {
        fileToString();

    }


    private static void fileToString(){
        String base64Str = null;
        File file = new File("E:\\图片\\测试文档\\ttttemp.pdf");
        try(FileInputStream ins = new FileInputStream(file);
            ){
            byte[] bytes = new byte[(int) file.length()];
            ins.read(bytes);
            base64Str = new String(Base64.encodeBase64(bytes),"UTF-8");
            System.out.println("================base64字符串==================");
            System.out.println(base64Str);
        } catch (Exception e){
            e.printStackTrace();
        }

    }


}
