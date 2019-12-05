package main.java.cn.lmc.collection.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 图片套印工具类
 * @author limingcheng
 *
 */
public class PicUtils {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(PdfUtils.class);
	
	/**
     * @description 给图片平铺添加水印
     * @param sourceImgPath 源图片路径
     * @param tarImgPath 保存的图片路径
     * @param waterMarkContent 水印内容
     * @param fileExt 图片格式
     * @return void
     */
    public void addWatermarkTile(String sourceImgPath, String tarImgPath, String waterMarkContent, String fileExt){
        Font font = new Font("宋体", Font.BOLD, 24);//水印字体，大小
        Color markContentColor = Color.white;//水印颜色
        Integer degree = -45;//设置水印文字的旋转角度
        float alpha = 1.0f;//设置水印透明度 默认为1.0  值越小颜色越浅
        OutputStream outImgStream = null;
        try {
            File srcImgFile = new File(sourceImgPath);//得到文件
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();//得到画笔
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(markContentColor); //设置水印颜色
            g.setFont(font);              //设置字体
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));//设置水印文字透明度
            if (null != degree) {
                g.rotate(Math.toRadians(degree),(double)bufImg.getWidth(),(double)bufImg.getHeight());//设置水印旋转
            }
            JLabel label = new JLabel(waterMarkContent);
            FontMetrics metrics = label.getFontMetrics(font);
            int width = metrics.stringWidth(label.getText());//文字水印的宽
            int rowsNumber = srcImgHeight/width+srcImgHeight%width;// 图片的高  除以  文字水印的宽  打印的行数(以文字水印的宽为间隔)
            int columnsNumber = srcImgWidth/width+srcImgWidth%width;//图片的宽 除以 文字水印的宽  每行打印的列数(以文字水印的宽为间隔)
            //防止图片太小而文字水印太长，所以至少打印一次
            if(rowsNumber < 1){
                rowsNumber = 1;
            }
            if(columnsNumber < 1){
                columnsNumber = 1;
            }
            for(int j=0;j<rowsNumber;j++){
                for(int i=0;i<columnsNumber;i++){
                    g.drawString(waterMarkContent, i*width + j*width, -i*width + j*width);//画出水印,并设置水印位置
                }
            }
            g.dispose();// 释放资源
            // 输出图片  
            outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, fileExt, outImgStream);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally{
            try {
                if(outImgStream != null){
                    outImgStream.flush();
                    outImgStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
        }
    }
	
    /**
     * 在图片中间添加文字水印
     * @param imgIS
     * @param imgOS
     * @param textContent
     * @param fileType
     */
    public void addWatermarkCenter(InputStream imgIS, OutputStream imgOS, String textContent, String fileType){
        
        Color markContentColor = Color.GRAY;//水印颜色
        Integer degree = 45;//设置水印文字的旋转角度
        float alpha = 0.5f;//设置水印透明度 默认为1.0  值越小颜色越浅
        try {
            Image srcImg = ImageIO.read(imgIS);	//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            int fontSize = (srcImgWidth*3)/40;
            Font font = new Font("宋体", Font.BOLD, fontSize);//水印字体，大小
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufImg.createGraphics();//得到画笔
            g2d.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g2d.setColor(markContentColor); //设置水印颜色
            g2d.setFont(font);              //设置字体
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));//设置水印文字透明度
            
            g2d.rotate(Math.toRadians(degree),srcImgWidth/5,srcImgHeight/5);//设置水印旋转
            g2d.drawString(textContent, srcImgWidth/5,srcImgHeight/5);//画出水印,并设置水印位置
            g2d.dispose();// 释放资源
            // 输出图片  
            ImageIO.write(bufImg, fileType, imgOS);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally{
        	
        }
    }
	
    public static void main(String[] args) throws FileNotFoundException {
    	InputStream imgIS = null;
    	OutputStream imgOS = null;
        try {
        	File inFile = new File("E:/图片/c43e0f5accb56db2cd75f1e6c6071902.jpg");
            File outFile = new File("E:/图片/test007.jpg");
            imgIS = new FileInputStream(inFile);
            imgOS = new FileOutputStream(outFile);
            new PicUtils().addWatermarkCenter(imgIS, imgOS, "内部使用,仅供查看", "jpg");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(imgIS != null) {
					imgIS.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(imgOS != null) {
					imgOS.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
    }
	
	
}
