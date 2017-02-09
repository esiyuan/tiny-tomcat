package com.esy.sv.httpcore.httpfacade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.esy.sv.common.IOUtil;

/**
 * 通过RequestFade 类实现HttpServletRequest接口 ，此类再继承，避免项目接口污染，使代码更加清晰
 * 前面的request对象是模拟请求而创建的非常简单的类，真实的request必然包含很多请求的信息，例如：
 *  1、协议、uri、方法
 *  2、请求头信息
 *  3、请求体
 * 这些信息无非就是按照协议的格式解析字符串而已 
 * 本程序为了模拟tomcat实现，没有完全的实现请求信息的解析，有兴趣的可以去看tomcat源码
 * @author guanjie
 */
public class Request extends RequestFacade{
	
	private static Logger logger = Logger.getLogger(Request.class);
	
	private String method;
	private String protocol;
	private String requestURI;
	private InputStream inputStream;
	/**
	 * 保存请求头信息
	 */
	private Map<String, String> headers = new HashMap<String, String>(); 
	
	public Request(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	
	public void parse() throws IOException {
		BufferedReader reader = IOUtil.getBufferedReader(inputStream);
		String requestString = reader.readLine();
		String[] requests = requestString.split(" ");
		int length = requests.length;
		if(length >= 0)
			method = requests[0];
		if(length >= 1)
			requestURI = requests[1];
		if(length >= 2)
			protocol = requests[2];
//		logger.info("解析url--[ method : " + method + ", requestURI : " + requestURI + ", protocol : " + protocol + "]");
	}
	
	@Override
	public String getServerName() {
		return requestURI.substring(requestURI.lastIndexOf("/") + 1);
	}
	
	@Override
	public String getRequestURI() {
		return requestURI;
	}
	
	
	
}
