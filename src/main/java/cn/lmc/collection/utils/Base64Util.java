package main.java.cn.lmc.collection.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;


public class Base64Util {

	
	/**
	 * java8之后提供的base64
	 * 将文件转成base64字符串
	 */
	public static void java8Base64() {
		File file = new File("E:\\华资\\开发任务\\OCR\\2019-09-19\\09118230eebd4bb9bca3fc9c72904414.jpg"); 
		
		try(InputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
//			Base64.Decoder decoder = Base64.getDecoder();
			Base64.Encoder encoder = Base64.getEncoder();
			//读取缓存  
			byte[] buffer = new byte[2048];  
			int length = 0;  
			while((length = fis.read(buffer)) != -1) {  
			    bos.write(buffer, 0, length);//写入输出流  
			}   
			 byte[] byt = bos.toByteArray();
			// 根据输出流创建字符串对象（加密）
			String teststr = encoder.encodeToString(byt);
			// 打印
			System.out.println(teststr);
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	/**
	 * 使用Apache Commons Codec有提供Base64的编码与解码功能
	 * 将文件转成base64字符串
	 */
	public static void apacheBase64() {
		
		File file = new File("E:\\华资\\开发任务\\OCR\\2019-09-19\\09118230eebd4bb9bca3fc9c72904414.jpg"); 
		
		try(InputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
			org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
			//读取缓存  
			byte[] buffer = new byte[2048];  
			int length = 0;  
			while((length = fis.read(buffer)) != -1) {  
			    bos.write(buffer, 0, length);//写入输出流  
			}   
			 byte[] byt = bos.toByteArray();
			// 根据输出流创建字符串对象（加密）
			 byte[] enbytes = base64.encodeBase64Chunked(byt);
			// 打印
			System.out.println(new String(enbytes));
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	/**
	 * 使用jdk自带的sun.misc下的base64工具
	 * 将文件转成base64字符串
	 */
	public static void jdkBase64() {
		File file = new File("E:\\图片\\ocr\\sfzzm.jpg");
		
		try(InputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
			//读取缓存  
			byte[] buffer = new byte[2048];  
			int length = 0;  
			while((length = fis.read(buffer)) != -1) {  
			    bos.write(buffer, 0, length);//写入输出流  
			}   
			 byte[] byt = bos.toByteArray();
			// 根据输出流创建字符串对象（加密）
			String teststr = new sun.misc.BASE64Encoder().encode(byt);
			// 打印
			System.out.println(teststr);
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	public static void main(String[] args) {
		jdkBase64();	// 该方法比较简便快捷
//		apacheBase64();	// 效果还可以
//		java8Base64();	// 该方法网上说好用，但是转化文件产生的字符串会有卡顿，卡死的风险
				
	}
}
