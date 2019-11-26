package main.java.cn.lmc.collection.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;


public class PropertyUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
	
	// 读取配置文件中的值
    public static Object getParamFromConfig(String key){  
        InputStream is = PropertyUtil.class.getClassLoader().getResourceAsStream("config.properties");  
        BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			Properties props = new Properties();   
            props.load(br);  
            return props.get(key);
        } catch (IOException e) {
        	logger.error("获取config.properties的配置信息失败");
            e.printStackTrace();
        } finally {
        	// 关闭流资源
        	if(is != null) {
        		try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	if(br != null) {
        		try {
					br.close();
				} catch (IOException e) { 
					e.printStackTrace();
				}
        	}
        }
        return null;
    } 
}
