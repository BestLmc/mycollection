package main.java.cn.lmc.collection.test;

import java.util.UUID;

/**
 * @author limingcheng
 *
 */
public class HelloWorld {

	public static void getSystemId(){
		System.out.println(UUID.randomUUID().toString().replace("-",""));
	}
	public static void main(String[] args) {

		getSystemId();
		getSystemId();
		getSystemId();
		getSystemId();
		getSystemId();
	}
}
