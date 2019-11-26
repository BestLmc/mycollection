package main.java.cn.lmc.collection.test.filetest;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Base64;
import java.util.UUID;

public class FileToAll {

	private static final Base64.Decoder decoder = Base64.getDecoder();
	private static final Base64.Encoder encoder = Base64.getEncoder();
	
//	Base64 base64 = new Base64();
	
	/**
	 * 将一个文件转成base64字符串
	 * @throws IOException 
	 */
	public static void fileToBase64() throws IOException {
		InputStream is = null; 
		InputStream fis; 
		File file = new File("E:\\华资\\开发任务\\OCR\\2019-09-19\\09118230eebd4bb9bca3fc9c72904414.jpg"); 
		String teststr = null;
		byte[] textByte;
		
		try {
			fis = new FileInputStream(file); 
			ByteArrayOutputStream bos = new ByteArrayOutputStream();  
			  
			//读取缓存  
			byte[] buffer = new byte[2048];  
			int length = 0;  
			while((length = fis.read(buffer)) != -1) {  
			    bos.write(buffer, 0, length);//写入输出流  
			}   
			 byte[] byt = bos.toByteArray();
			// 根据输出流创建字符串对象  
//			teststr = new String(bos.toByteArray());
			teststr = new sun.misc.BASE64Encoder().encode(byt);
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(teststr);
	        System.out.println(jsonStr);
//			System.out.println("base64加密:"+teststr); 
			bos.close(); 
		
			
//			textByte = teststr.getBytes("UTF-8");
//			teststr = encoder.encodeToString(textByte);
			
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			// 关闭流资源
			try {
				if(is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
	}
	
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
//		try {
//			fileToBase64(); 
//		} catch (IOException e) { 
//			e.printStackTrace();
//		}
	}
}
