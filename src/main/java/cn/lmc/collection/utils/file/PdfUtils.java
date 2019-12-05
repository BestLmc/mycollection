package main.java.cn.lmc.collection.utils.file;

import com.google.zxing.WriterException;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * pdf工具
 * @author limingcheng
 * @Date 2019/11/25
 */
public class PdfUtils {

	private final static Logger LOGGER = LoggerFactory.getLogger(PdfUtils.class);

	/** 单例 */
	private static PdfUtils instance = new PdfUtils();
	
	/**
	 * 获取实例(此处禁止自行实例化对象)
	 * @return
	 */
	public static PdfUtils getInstance() {
		return instance;
	}
	/**
	 * 实例化对象
	 */
	private PdfUtils() {}

	/**
	 * 给pdf的每一页添加注释文字
	 * @param pdfIS 需要增加文字前的pdf输入流
	 * @param pdfOS 增加文字后的pdf输出流
	 * @param text 需要增加的说明文字
	 * @throws WriterException
	 * @throws IOException
	 */
	public void addTest(InputStream pdfIS, OutputStream pdfOS, String text ) throws WriterException, IOException {

		LOGGER.info("添加文字开始！");
		int textLeft = 0;
		int textWidth = 0;
		int textBottom = 0;
		PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfIS), new PdfWriter(pdfOS));
		Document doc = null;
		try {
			doc = new Document(pdfDoc);
			int n = pdfDoc.getNumberOfPages();
			Rectangle pagesize = pdfDoc.getPage(n).getPageSize();
			
			for(int i=0; i<n; i++) {
				textLeft = (int) (pagesize.getWidth()/3);
				textWidth = (int) (pagesize.getWidth()/3);
				textBottom = (int) (pagesize.getHeight()/3);
				System.out.println(textLeft+":"+textWidth+":"+textBottom);
				
				PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
				Paragraph p = new Paragraph(text).setFont(font).setFontSize(50).setFixedPosition(i+1, textLeft, textBottom, textWidth);
				doc.add(p);
			}
			
			LOGGER.info("添加文字结束！");
		} finally {
			if (doc != null) {
				doc.close();
			}
		}
	}
	
	/**
	 * 给pdf的每一页添加注释文字
	 * @param pdfIS 需要增加文字前的pdf输入流
	 * @param pdfOS 增加文字后的pdf输出流
	 * @param text 需要增加的说明文字
	 * @throws WriterException
	 * @throws IOException
	 */
	public void addTestMarkForPDF(InputStream pdfIS, OutputStream pdfOS, String text ) throws WriterException, IOException {

		LOGGER.info("添加文字开始！");
		
		int textLeft = 0;
		int textWidth = 0;
		int textBottom = 0;
		PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfIS), new PdfWriter(pdfOS));
		Document doc = null;
		try {
			doc = new Document(pdfDoc);
			int n = pdfDoc.getNumberOfPages();
			Rectangle pagesize = pdfDoc.getPage(n).getPageSize();
			
			for(int i=0; i<n; i++) {
				
				textLeft = (int) (pagesize.getWidth()/5)*4;
				textWidth = 50;
				textBottom = (int) (pagesize.getHeight()/10);
				PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
				
				Paragraph para = new Paragraph().setFont(font).setFontSize(50);
				DeviceRgb rgbColor = new DeviceRgb(192, 192, 192);
//				para.setFontColor(DeviceRgb.makeLighter(rgbColor));	// 定制字体颜色(偏亮)
				para.setFontColor(DeviceRgb.makeDarker(rgbColor));	// 定制字体颜色(偏暗)
				para.setRotationAngle(120f);	// 设置文字倾斜度
				para.setOpacity(0.5f);	// 设置文字透明化度
				para.setFixedPosition(i+1, textLeft, textBottom, textWidth);	// 设置文字的绝对位置
				para.setTextAlignment(TextAlignment.JUSTIFIED);	// 设置文字排序方式
				para.add(text);
				
				doc.add(para);
			}
			
			LOGGER.info("添加文字结束！");
		} finally {
			if (doc != null) {
				doc.close();
			}
		}
		
	}
	
	public static void main(String[] args) throws FileNotFoundException, WriterException, IOException {
		String text = "内部使用，仅供查看";
		File sourceFile = new File("E:\\图片\\测试文档\\CCGP2018045C.pdf");
		File pdfFile = new File("E:\\图片\\测试文档\\test009.pdf");
		PdfUtils.getInstance().addTestMarkForPDF(new FileInputStream(sourceFile), new FileOutputStream(pdfFile), text);
	}
}
