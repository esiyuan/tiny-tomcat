package com.esy.sv;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.esy.sv.common.Constants;
import com.esy.sv.httpcore.Request;
import com.esy.sv.httpcore.Response;

public class ServletProcessor {
	
	public void process(Request request, Response response) throws ServletException, IOException{
		URLClassLoader classLoader = null;
		try {
			try {
				classLoader = new URLClassLoader(new URL[]{new URL("file://" + Constants.TOMCAT_CLASSLOADER_REPOSITORY)});
				Class<?> clazz = classLoader.loadClass("web_root." + request.getServerName());
				Servlet servlet = (Servlet)clazz.newInstance();
				servlet.service(request, response);
			} finally { 
				classLoader.close();
			}
		} catch(ServletException e) {
			throw e;
		} catch(IOException e1) {
			throw e1;
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
}
