package com.esy.sv;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.esy.sv.common.Constants;
import com.esy.sv.httpcore.httpfacade.Request;
import com.esy.sv.httpcore.httpfacade.Response;

/**
 * servlet请求处理
 * @author guanjie
 *
 */
public class ServletProcessor {
	
	private ConcurrentHashMap<String, Servlet> cache = new ConcurrentHashMap<String, Servlet>();
	/**
	 * servlet没有加载，则加载并初始化
	 * <p>如有加载直接取缓存
	 * @param request
	 * @param response
	 */
	public void process(Request request, Response response){
			Servlet servlet = cache.get(request.getServerName());
			if(servlet != null) {
				try {
					servlet.service(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
				return;
			}
			try(URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL("file://" + Constants.TOMCAT_CLASSLOADER_REPOSITORY)});) {
				Class<?> clazz = classLoader.loadClass("web_root." + request.getServerName());
				servlet = (Servlet)clazz.newInstance();
				servlet.init(null);
				servlet.service(request, response);
				cache.putIfAbsent(request.getServerName(), servlet);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("服务异常！");
			}
	}
}