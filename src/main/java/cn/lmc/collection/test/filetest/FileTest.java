package main.java.cn.lmc.collection.test.filetest;

import java.io.*;
import java.util.Base64;

/**
 * 文件测试类
 * @author limingcheng
 *
 */
public class FileTest {
	
	private static final Base64.Decoder decoder = Base64.getDecoder();
	private static final Base64.Encoder encoder = Base64.getEncoder();

	private static void strToInputStream() throws IOException {
		InputStream is = null;
		OutputStream os = null;
		InputStream fis = null;
		OutputStream fos = null;
		String filepath = "E:\\图片\\fileTest.jpg";
		String teststr = "斯蒂芬归还工服v不过东风风度突然发v的胳膊上的发的人的肥肉谷歌日本v为大幅度的德国人女发到饭否嘀咕的华人代表";
		File file = new File("E:\\图片\\Bcc.jpg");
		File osfile = new File("E:\\图片\\osFile.jpg");
		byte[] textByte;
		try {
			fis = new FileInputStream(file);
			fos = new FileOutputStream(osfile);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();  
			  
			//读取缓存  
			byte[] buffer = new byte[2048];  
			int length = 0;  
			while((length = fis.read(buffer)) != -1) {  
			    bos.write(buffer, 0, length);//写入输出流  
			}  
			fis.close();
			fos.close();
			
			// 根据输出流创建字符串对象  
			teststr = new String(bos.toByteArray());
			bos.close();
			
			System.out.println("pic"+teststr);
		
			textByte = teststr.getBytes("UTF-8");
			teststr = encoder.encodeToString(textByte);
			System.out.println("加密"+teststr);
			byte[] deByte = decoder.decode(teststr);
			String newStr = new String(deByte);
			
			System.out.println("解密"+newStr);
			
			is = new ByteArrayInputStream(newStr.getBytes("UTF-8"));
			os = new FileOutputStream(filepath);
			byte[] b = new byte[1000];
			int lean;
			//注意这里read已经将内容写入b中了 
			while ((lean=(is.read(b)))!=-1) {
				os.write(b, 0, lean);
			} 
			
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
			try {
				if(os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	private static void fileFisFosTest() throws IOException {
		InputStream input = null;    
		OutputStream output = null;    
		ByteArrayOutputStream bos = null;
		File file = new File("E:\\图片\\Bcc.jpg");
		File osfile = new File("E:\\图片\\xxxxx.jpg");
		try {
			input = new FileInputStream(file);
			output = new FileOutputStream(osfile);        
			byte[] buf = new byte[1024];        
			int bytesRead = 0;        
			while ((bytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, bytesRead);
			}
			input.close();
			input = new FileInputStream(file);
			bos = new ByteArrayOutputStream();  
			  
			//读取缓存  
			byte[] buffer = new byte[2048];  
			int length = 0;  
			while((length = input.read(buffer)) != -1) {  
			    bos.write(buffer, 0, length);//写入输出流  
			}  
			// 根据输出流创建字符串对象  
			String teststr = new String(bos.toByteArray());
			System.out.println("pic"+teststr);
			
			
		} finally {
			input.close();
			output.close();
			bos.close();
		}
	}
	
	private static void fileFisFosTest1(){
		InputStream input = null;    
		OutputStream output = null;     
		File file = new File("E:\\图片\\Bcc.jpg");
		File osfile = new File("E:\\图片\\xxxxx.jpg");
		try {
			input = new FileInputStream(file);
			output = new FileOutputStream(osfile);        
			// TODO Auto-generated catch block
			
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		} finally {
			// 关闭流资源
			if(input != null) {
				try {
					input.close();
				} catch (IOException e) { 
					e.printStackTrace();
				}
			} 
			if(output != null) {
				try {
					output.close();
				} catch (IOException e) { 
					e.printStackTrace();
				}
			} 
		}
	}
	
	private static void newStream() {
        File file = new File("E:\\图片\\Bcc.jpg");
        File file2 = new File("E:\\图片\\newBcc.jpg");
        try (
            //打开资源代码
            InputStream in = new FileInputStream(file); 
            OutputStream out = new FileOutputStream(file2);
        ) {
            //可能出现异常的代码
            byte[] b = new byte[4];//创建缓存区，存储读取的数据
            int len = -1;//表示已经读取了多少个字节，如果len返回-1，表示已经读到最后
            while ((len = in.read(b)) != -1) {
                //打印出读取的数据
                System.out.println(new String(b, 0, len));
                out.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 空测试
	 */
	private static void nullStream() {
        File file = new File("E:\\图片\\Bcc.jpg");
        File file2 = new File("E:\\图片\\new20190918.jpg");
        File file3 = null;
        InputStream in = null;
        OutputStream out = null;
        
        try {
        	
        	file3 = File.createTempFile("templeftpfile", ".temp");
        	//打开资源代码
        	in = new FileInputStream(file); 
            out = new FileOutputStream(file3);
        	
            //可能出现异常的代码
            byte[] b = new byte[4];//创建缓存区，存储读取的数据
            int len = -1;//表示已经读取了多少个字节，如果len返回-1，表示已经读到最后
            while ((len = in.read(b)) != -1) {
                //打印出读取的数据 
                out.write(b, 0, len);
            } 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	// 关闭流资源
        	if(in != null) {
        		try {
					in.close();
				} catch (IOException e) { 
					e.printStackTrace();
				}
        	}
        	if(out != null) {
        		try {
					out.close();
				} catch (IOException e) { 
					e.printStackTrace();
				}
        	}
        	// 删除临时文件
        	if(file3 != null) {
        		//立即删除文件
        		file3.delete();
        		//在JVM退出时删除文件
        		file3.deleteOnExit();
        	}
        	
        }
    }
	
	public static void main(String[] args) throws IOException {
		nullStream();
	}
}
