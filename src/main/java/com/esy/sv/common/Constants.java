package com.esy.sv.common;


public class Constants {
	/**
	 * 放置web页面的根目录
	 */
	public static final String WEB_ROOT;
	static {
		WEB_ROOT = System.getProperty("java.class.path").split(";")[0] + "/web_root";
	}
	
	public static final int BUFFER_SIZE = 1024;
	
}

