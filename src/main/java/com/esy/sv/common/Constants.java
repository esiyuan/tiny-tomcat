package com.esy.sv.common;



public class Constants {
	/**
	 * 为什么这种方式能够获取到classpath（类加载路径）
	 * <p>
	 * 查看源码发现，是通过类加载器来找的，此类的类加载器是系统类加载器，返回的路径就是 我们自己类的路径。
	 * <p>
	 *  参数为'/'返回/D:/codingspace/spare_workspace/tiny-tomcat/target/classes 类根路径路径
	 *  参数为 ''时返回/D:/codingspace/spare_workspace/tiny-tomcat/target/classes/com/esy/sv/common 当前目录
	 */
	public static final String WEB_ROOT = Constants.class.getResource("").getPath() + "web_root";
	
	public static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		System.out.println(WEB_ROOT);
	}
}

