package com.esy.sv.common;


public class Constants {
	/**
	 * 自定义类加载器加载仓库
	 */
	public static final String TOMCAT_CLASSLOADER_REPOSITORY;
	static {
		TOMCAT_CLASSLOADER_REPOSITORY = System.getProperty("java.class.path").split(";")[0];
	}
	/**
	 * 放置web页面的根目录
	 */
	public static final String WEB_ROOT = TOMCAT_CLASSLOADER_REPOSITORY + "/web_root";
	
	public static final int BUFFER_SIZE = 1024;
	
}

