package com.esy.sv;

import com.esy.sv.httpcore.Contained;
import com.esy.sv.httpcore.Pipeline;
import com.esy.sv.httpcore.Valve;
import com.esy.sv.httpcore.Wrapper;
import com.esy.sv.httpcore.container.ServletVavle;
import com.esy.sv.httpcore.container.SimpleLoader;
import com.esy.sv.httpcore.container.SimplePipeline;
import com.esy.sv.httpcore.container.SimpleValve;
import com.esy.sv.httpcore.container.SimpleWrapper;
import com.esy.sv.httpcore.httpconector.HttpConnector;
import com.esy.sv.httpcore.lifecycle.LifecycleListener;
import com.esy.sv.httpcore.lifecycle.SimpleLifecyleListener;

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
			LifecycleListener lifecyleListener = new SimpleLifecyleListener();
			connector.addLifecycleListener(lifecyleListener);
			
			Wrapper wrapper = new SimpleWrapper();
			Pipeline pipeline = new SimplePipeline(wrapper);
			connector.setContainer(wrapper);
			wrapper.setPipeline(pipeline);
			wrapper.setLoader(new SimpleLoader());
			wrapper.setServletClass("HelloServlet");
			
			ServletVavle servletVavle = new ServletVavle(wrapper);
			pipeline.setBasic(servletVavle);
			Valve valve = new SimpleValve();
			((Contained)valve).setContainer(wrapper);
			pipeline.addValve(valve);
			
			connector.initialize();
			connector.start();
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
