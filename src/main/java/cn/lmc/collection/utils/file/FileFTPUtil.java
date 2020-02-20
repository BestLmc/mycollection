package main.java.cn.lmc.collection.utils.file;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;

/**
 *
 * @author liangcaitong
 * @date 2019年8月8日 下午4:17:24
 */
public class FileFTPUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileFTPUtil.class);
	
	/** FTP服务器IP **/
	private String FTP_HOSTNAME = "";
	
	/** FTP端口号 **/
	private Integer FTP_PORT = 21; // 默认21
	
	/** FTP用户 **/
	private String FTP_USERNAME = "";
	
	/** 密码 **/
	private String FTP_PASSWORD = "";
	
	/** 本地字符编码 **/
	private static String localCharset = "GBK";
	
	/** FTP协议里面，规定文件名编码为iso-8859-1 **/
	private static String serverCharset = "ISO-8859-1";
	
	/** UTF-8字符编码 **/
	private static final String CHARSET_UTF8 = "UTF-8";
	
	/** OPTS UTF8字符串常量 **/
	private static final String OPTS_UTF8 = "OPTS UTF8";

	public FileFTPUtil(String hostName, Integer port, String userName, String passWord) {
		this.FTP_HOSTNAME = hostName;
		this.FTP_PORT = port;
		this.FTP_USERNAME = userName;
		this.FTP_PASSWORD = passWord;
	}

	public FileFTPUtil() {

	}

	/**
	 * 连接ftp服务器
	 * 
	 * @return
	 */
	private FTPClient loginFtpClient() {
		long startTime = System.currentTimeMillis();
		FTPClient ftpClient = new FTPClient();
		try {
			LOGGER.info("connecting...ftp服务器:" + this.FTP_HOSTNAME + ":" + this.FTP_PORT);
			ftpClient.connect(this.FTP_HOSTNAME, this.FTP_PORT); // 连接ftp服务器
			ftpClient.login(this.FTP_USERNAME, this.FTP_PASSWORD); // 登录ftp服务器
			int replyCode = ftpClient.getReplyCode(); // 是否成功登录服务器
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				LOGGER.error("connect failed...ftp服务器:" + this.FTP_HOSTNAME + ":" + this.FTP_PORT);
			} else {
				// 设置编码：开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）
				if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
					localCharset = CHARSET_UTF8;
				}
				ftpClient.setControlEncoding(localCharset);
				ftpClient.enterLocalPassiveMode();
				ftpClient.setBufferSize(4096);
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				LOGGER.info("connect successfu...ftp服务器:" + this.FTP_HOSTNAME + ":" + this.FTP_PORT);
				LOGGER.info("连接ftp服务器，总耗时：" + (System.currentTimeMillis() - startTime) + " 毫秒");
				return ftpClient;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.error("connect failed...ftp服务器:" + this.FTP_HOSTNAME + ":" + this.FTP_PORT);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 上传文件
	 * 
	 * @param pathName
	 *            ftp服务保存文件路径，格式：/aaa/bbb/ccc
	 * @param tempPathFileName
	 *            暂存在本地的文件地址和文件名，格式：D:/aaa/bbb/test.txt
	 * @param inputStream
	 *            文件流
	 * @return
	 */
	public boolean uploadFile(String pathName, String tempPathFileName) {
		FTPClient ftpClient = loginFtpClient();
		boolean flag = false;
		InputStream inputStream = null;
		pathName = changeEncoding(pathName.replace("\\", "/").replace("//", "/"));
		tempPathFileName = tempPathFileName.replace("\\", "/").replace("//", "/");
		String fileName = tempPathFileName.substring(tempPathFileName.lastIndexOf("/") + 1);// 文件名
		try {
			LOGGER.info("开始上传文件");
			createDirecroty(pathName, ftpClient);
			changeWorkingDirectory(pathName, ftpClient);
			inputStream = new FileInputStream(tempPathFileName);
			ftpClient.storeFile(fileName, inputStream);
			flag = true;
			LOGGER.info("上传文件" + fileName + "成功");
		} catch (Exception e) {
			LOGGER.error("上传" + fileName + "文件失败");
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// 先退出登录，然后关闭连接
			logout(ftpClient);
			// 不管上传成功还是上传失败，都删除本地文件
			File tempFile = new File(tempPathFileName);
			tempFile.delete();
		}
		return flag;
	}
	/**
	 * 根据输入流上传文件到FTP服务器
	 * @param pathName
	 * @param fileName
	 * @param inputStream
	 * @return
	 */
	public boolean uploadFile(String pathName, String fileName, InputStream inputStream) {
		FTPClient ftpClient = loginFtpClient();
		boolean flag = false;
		pathName = changeEncoding(pathName.replace("\\", "/").replace("//", "/"));
		try {
			LOGGER.info("开始上传文件");
			createDirecroty(pathName, ftpClient);
			changeWorkingDirectory(pathName, ftpClient);
			/*inputStream = new FileInputStream(tempPathFileName);*/
			ftpClient.storeFile(fileName, inputStream);
			flag = true;
			LOGGER.info("上传文件" + fileName + "成功");
		} catch (Exception e) {
			LOGGER.error("上传" + fileName + "文件失败");
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// 先退出登录，然后关闭连接
			logout(ftpClient);
		}
		return flag;
	}

	/**
	 * 改变目录路径
	 * 
	 * @param directory
	 *            ftp路径
	 * @param ftpClient
	 * @return
	 */
	private boolean changeWorkingDirectory(String directory, FTPClient ftpClient) {
		boolean flag = true;
		try {
			flag = ftpClient.changeWorkingDirectory(directory);
			if (flag) {
				LOGGER.info("进入文件夹" + directory + " 成功！");

			} else {
				LOGGER.error("进入文件夹" + directory + " 失败！不存在该文件夹");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return flag;
	}

	/**
	 * 创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
	 * 
	 * @param pathName
	 *            ftp服务保存文件路径
	 * @param ftpClient
	 * @return
	 * @throws IOException
	 */
	private boolean createDirecroty(String pathName, FTPClient ftpClient) throws IOException {
		boolean success = true;
		String directory = pathName.endsWith("/") ? pathName : pathName + "/";
		// 如果远程目录不存在，则递归创建远程服务器目录
		if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(directory, ftpClient)) {
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			String path = "";
			String paths = "";
			while (true) {
				String subDirectory = pathName.substring(start, end);
				path = path + "/" + subDirectory;
				if (!existFile(path, ftpClient)) {
					if (makeDirectory(subDirectory, ftpClient)) {
						changeWorkingDirectory(subDirectory, ftpClient);
					} else {
						LOGGER.error("创建目录[" + subDirectory + "]失败");
						changeWorkingDirectory(subDirectory, ftpClient);
					}
				} else {
					changeWorkingDirectory(subDirectory, ftpClient);
				}

				paths = paths + "/" + subDirectory;
				start = end + 1;
				end = directory.indexOf("/", start);
				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return success;
	}

	// 判断ftp服务器文件是否存在
	private boolean existFile(String path, FTPClient ftpClient) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr.length > 0) {
			flag = true;
		}
		return flag;
	}

	// 创建目录
	private boolean makeDirectory(String dir, FTPClient ftpClient) {
		boolean flag = true;
		try {
			flag = ftpClient.makeDirectory(dir);
			if (flag) {
				LOGGER.info("创建文件夹" + dir + " 成功！");

			} else {
				LOGGER.error("创建文件夹" + dir + " 失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * * 下载文件 *
	 * 
	 * @param pathFileName
	 *            FTP服务器文件目录和文件名 ，格式：/test/test01/aaa.txt
	 * @param tempPathFileName 
	 *            缓存文件路径和文件名
	 * @return
	 */
	public File downloadFile(String pathFileName) {
		File file = null;
		FTPClient ftpClient = loginFtpClient();// 连接服务器
		pathFileName = pathFileName.replace("\\", "/").replace("//", "/");
		String pathName = changeEncoding(pathFileName.substring(0, pathFileName.lastIndexOf("/")));// 文件路径
		String fileName = pathFileName.substring(pathFileName.lastIndexOf("/") + 1);// 文件名
		boolean flag = false;
		OutputStream os = null;
		try {
			LOGGER.info("开始下载" + fileName + "文件");
			// 切换FTP目录
			flag = changeWorkingDirectory(pathName, ftpClient);
			if(!flag){
				return file;
			}
			file = File.createTempFile("templeftpfile", null);
			os = new FileOutputStream(file);
			ftpClient.retrieveFile(fileName, os);
		} catch (Exception e) {
			LOGGER.error("下载" + fileName + "文件失败");
			e.printStackTrace();
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(file != null) {
//				file.delete();
//				file.deleteOnExit();
			}
			logout(ftpClient);
		}
		return file;
	}

	/**
	 * * 删除文件 *
	 * 
	 * @param pathname
	 *            FTP服务器保存目录 *
	 * @param filename
	 *            要删除的文件名称 *
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean deleteFile(String pathName, String fileName, FTPClient ftpClient) {
		boolean flag = false;
		try {
			LOGGER.info("开始删除文件:" + fileName);
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(pathName);
			ftpClient.dele(fileName);
			ftpClient.logout();
			flag = true;
			LOGGER.info("删除文件:" + fileName + " 成功");
		} catch (Exception e) {
			LOGGER.error("删除文件:" + fileName + " 失败");
			e.printStackTrace();
		} finally {
			logout(ftpClient);
		}
		return flag;
	}

	/**
	 * 登出服务
	 * 
	 * @param ftpClient
	 * @return
	 */
	private boolean logout(FTPClient ftpClient) {
		boolean logoutRes = false;
		try {
			if (ftpClient.isConnected()) {
				logoutRes = ftpClient.logout();
				ftpClient.disconnect();
			}
			return logoutRes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return logoutRes;
	};

	/**
	 * FTP服务器路径编码转换
	 * 
	 * @param ftpPath
	 *            FTP服务器路径
	 * @return String
	 */
	private String changeEncoding(String ftpPath) {
		String directory = null;
		try {
			directory = new String(ftpPath.getBytes(localCharset), serverCharset);
		} catch (Exception e) {
			LOGGER.error("路径编码转换失败", e);
		}
		return directory;
	}
}
