package main.java.cn.lmc.collection.file.pdf;

import com.itextpdf.io.util.FileUtil;
import main.java.cn.lmc.collection.common.utils.PropertyUtil;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import java.io.File;
import java.util.regex.Pattern;

/**
 * PdfOpenOffice
 *
 * @author limingcheng
 * @Date 2019/12/5
 */
public class PdfOpenOffice {

    private static OfficeManager officeManager;
    private static String OPEN_OFFICE_HOME = (String) PropertyUtil.getParamFromConfig("open_office_home");
    private static int OPEN_OFFICE_PORT = Integer.parseInt((String) PropertyUtil.getParamFromConfig("open_offic_port"));

    /**
     * 将Office文档转换为PDF. 运行该函数需要用到OpenOffice, OpenOffice
     * 源文件,绝对路径. 可以是Office2003-2007全部格式的文档, Office2010的没测试. 包括.doc,
     * .docx, .xls, .xlsx, .ppt, .pptx等.
     * 目标文件.绝对路径.
     * @param inputFilePath
     */
    public static File word2pdf(String inputFilePath) {

        //把输入的word路径，后缀替换为pdf.
        String pdfPath = inputFilePath.substring(0, inputFilePath.lastIndexOf(".") + 1)+".pdf";

        //启动openoffice服务
        startService();
        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);

        File inputFile = new File(inputFilePath);
        File outputFile = new File(pdfPath);
        if (inputFile.exists()) {// 找不到源文件, 则返回

            //假如目标路径不存在,则新建该路径
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            converter.convert(inputFile, outputFile);
        }
        //关闭openoffice服务
        stopService();
        return outputFile;
    }
    /**
     * 根据操作系统获取openoffice安装地址
     * @return office_home
     */
    public static String getOfficeHome() {

        String osName = System.getProperty("os.name");

        if (Pattern.matches("Linux.*", osName)) {
            return (String) PropertyUtil.getParamFromConfig("open_office_home");
        } else if (Pattern.matches("Windows.*", osName)) {
            return (String) PropertyUtil.getParamFromConfig("win_open_office_home");
        }
        return null;
    }
    /**
     * 启动openoffice服务
     */
    public static void startService() {

        DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();

        try {

            System.out.println("准备启动服务....");
            configuration.setOfficeHome(getOfficeHome());//设置OpenOffice.org安装目录
            configuration.setPortNumbers(OPEN_OFFICE_PORT); //设置转换端口，默认为8100
            configuration.setTaskExecutionTimeout(1000 * 60 * 5L);//设置任务执行超时为5分钟
            configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);//设置任务队列超时为24小时

            officeManager = configuration.buildOfficeManager();
            officeManager.start();// 启动服务
            System.out.println("office转换服务启动成功!");
        } catch (Exception ce) {
            System.out.println("office转换服务启动失败!详细信息:" + ce);
        }
    }
    /**
     * 关闭openoffice服务
     */
    public static void stopService() {
        System.out.println("关闭office转换服务....");
        if (officeManager != null) {
            officeManager.stop();
        }
        System.out.println("关闭office转换成功!");
    }

}
