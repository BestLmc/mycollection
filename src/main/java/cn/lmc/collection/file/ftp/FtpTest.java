package main.java.cn.lmc.collection.file.ftp;

import main.java.cn.lmc.collection.common.utils.PropertyUtil;
import main.java.cn.lmc.collection.utils.file.FileFTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

/**
 * FtpTest
 * FTP测试类
 *
 * @author limingcheng
 * @Date 2020/2/18
 */
public class FtpTest {

    // 通过slf4j接口创建Logger对象
    private static final Logger LOGGER = LoggerFactory.getLogger(FtpTest.class);

    // ftp服务器配置
    // ftp的ip地址
    private static final String FTP_HOSTNAME = (String) PropertyUtil.getParamFromProperties("FTP_HOSTNAME");
    // ftp端口
    private static final int FTP_PORT = Integer.parseInt((String) PropertyUtil.getParamFromProperties("FTP_PORT"));
    // ftp用户名
    private static final String FTP_USERNAME = (String) PropertyUtil.getParamFromProperties("FTP_USERNAME");
    // ftp密码
    private static final String FTP_PASSWORD = (String) PropertyUtil.getParamFromProperties("FTP_PASSWORD");
    // ftp文件路径
    private static final String FTP_DIR = (String) PropertyUtil.getParamFromProperties("FTP_DIR");


    /**
     * @Author: limingcheng on 2020/2/18 11:05
     * @param:  * @param
     * @return: void
     * @Description:
     */
    private static void ftpTest(){

        LOGGER.info("测试开始");

        String ymswjlj = "/FTP/zzzf/2019-09-19/b77911b0b61e4a0a806164b56d098c17.jpg";

        FileInputStream fis = null;
        FileInputStream bcfis = null;
        // 文件后缀
        String ext = ymswjlj.substring(ymswjlj.lastIndexOf("."));
        // 文件名
        String wjm = UUID.randomUUID().toString().replace("-","")+ext;

        try{
            // 初始化一门式ftp
            FileFTPUtil ftpUtil = new FileFTPUtil(FTP_HOSTNAME,FTP_PORT,FTP_USERNAME,FTP_PASSWORD);
            File file = ftpUtil.downloadFile(ymswjlj);
            if(file == null){
                LOGGER.info("没有找到该文件");
            } else {
                // 获取材料上传文件路径
                String filepath = "mytest";
                String wjlj = filepath + "\\" + wjm;
                fis = new FileInputStream(file);
                ftpUtil = new FileFTPUtil(FTP_HOSTNAME,FTP_PORT,FTP_USERNAME,FTP_PASSWORD);
                boolean flag = ftpUtil.uploadFile(filepath, wjm, fis);
                // 若上传成功
                if(flag){
                    file.delete();
                    LOGGER.info("成功上传文件！");
                } else {
                    LOGGER.info("上传文件失败！");
                }
            }
        }catch(Exception e){
            LOGGER.info("FTP获取自然人影像材料信息异常");
            e.printStackTrace();
        }finally{
            // 关闭流资源
            if(fis != null) {
                try {
                    fis.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if(bcfis != null) {
                try {
                    bcfis.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        LOGGER.info("测试结束");
    }


    public static void main(String[] args) {
        ftpTest();
    }


}
