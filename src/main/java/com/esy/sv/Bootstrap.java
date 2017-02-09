package com.esy.sv;

import java.io.IOException;
import java.net.UnknownHostException;

import com.esy.sv.common.TomcatException;
import com.esy.sv.httpcore.container.PrintHostValve;
import com.esy.sv.httpcore.container.ServletValve;
import com.esy.sv.httpcore.container.SimpleContainer;
import com.esy.sv.httpcore.httpconector.HttpConnector;

/**
 * 服务器启动类 本节服务器更加模块化，分离出了连接器模块，想正式的tomcat实现靠拢，能处理更为复杂的servlet请求
 * 本人的目的是一步步向真实的tomcat靠拢
 * tomcat作为一个产品，肯定要考虑扩展的问题，如果每次扩展都是大刀阔斧的修改，那么tomcat也活不起来。
 * 要考虑可扩展的问题，那么必须要定义接口，接口才是组件的规范，只有遵守规范的组件，tomcat才能调用。
 * @author guanjie
 */
public final class Bootstrap {
	public static void main(String[] args) {
		try {
			HttpConnector connector = new HttpConnector("localhost", 8080);
			ServletValve servletValve = new ServletValve();
			SimpleContainer simpleContainer = new SimpleContainer();
			
			PrintHostValve hostValve = new PrintHostValve();
			simpleContainer.setBasic(servletValve);
			simpleContainer.addValve(hostValve);
			connector.setContainer(simpleContainer);
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
