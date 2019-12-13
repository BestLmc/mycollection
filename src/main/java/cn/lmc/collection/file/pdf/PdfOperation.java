package main.java.cn.lmc.collection.file.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * PdfOperation
 *
 * Pdf操作
 * @author limingcheng
 * @Date 2019/12/5
 */
public class PdfOperation {


    public static void PdfCustomPage(String filename) throws FileNotFoundException, DocumentException {
        // step 1
        // 自定义页面大小使用
        Rectangle pagesize = new Rectangle(216f, 720f);
        Document document = new Document(pagesize, 36f, 72f, 108f, 180f);
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        document.add(new Paragraph(
                "Hello World! Hello People! " +
                        "Hello Sky! Hello Sun! Hello Moon! Hello Stars!"));
        // step 5
        document.close();
    }

    /**
     *   创建PDF文档.
     * @param filename 新PDF文档的路径
     * @throws    DocumentException
     * @throws    IOException
     */
    public static void createPdf(String filename) throws DocumentException, IOException {
        // 第一步 创建文档实例
        Document document = new Document();
        // 第二步 获取PdfWriter实例
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // 第三步 打开文档
        document.open();
        // 第四步 添加段落内容
        document.add(new Paragraph("Hello World!"));
        // 第五部 操作完成后必须执行文档关闭操作。
        document.close();
    }

    public static void main(String[] args) throws IOException, DocumentException {
//        createPdf("E:\\图片\\测试文档\\pdftest001.pdf");
        PdfCustomPage("E:\\图片\\测试文档\\pdftest002.pdf");
    }

}
