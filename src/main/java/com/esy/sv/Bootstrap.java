package com.esy.sv;

import java.io.IOException;
import java.net.UnknownHostException;

import com.esy.sv.common.TomcatException;
import com.esy.sv.httpcore.httpconector.HttpConnector;

/**
 * 服务器启动类 本节服务器更加模块化，分离出了连接器模块，想正式的tomcat实现靠拢，能处理更为复杂的servlet请求
 * 本人的目的是一步步向真实的tomcat靠拢
 * @author guanjie
 */
public final class Bootstrap {
	public static void main(String[] args) {
		HttpConnector connector = new HttpConnector("localhost", 8080);
		try {
			connector.initialize();
			connector.start();
			System.in.read();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (TomcatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
