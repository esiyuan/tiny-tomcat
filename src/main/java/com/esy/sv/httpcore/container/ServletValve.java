package com.esy.sv.httpcore.container;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.esy.sv.common.Constants;
import com.esy.sv.common.IOUtil;
import com.esy.sv.httpcore.Valve;
import com.esy.sv.httpcore.httpfacade.Request;
import com.esy.sv.httpcore.httpfacade.Response;

public class ServletValve implements Valve {

	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		if (request.getRequestURI().startsWith("/servlet/")) {
			process(request, response);
		} else {
			sendStaticResource(request, response);
		}
	}

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
	
	private void sendStaticResource(Request request, Response response) throws IOException {
		File file = new File(Constants.WEB_ROOT, request.getRequestURI());
		if (file.exists()) {
			sendFile(file, response);
		} else {
			sendDefault(response);
		}
	}
	
	
	private void sendDefault(Response response){
		String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
				+ "Content-Type: text/html\r\n"
				+ "Content-Length: 23\r\n" + "\r\n"
				+ "<h1>File Not Found</h1>";
		response.getWriter().println(errorMessage);
	}
	
	private void sendFile(File file, Response response) throws IOException {
		BufferedReader reader = null;
		String buf;
		try {
			reader = IOUtil.getBufferedReader(new FileInputStream(file));
			while((buf = reader.readLine()) != null) {
				response.getWriter().println(buf);
			}
		} finally {
			if(reader != null)
				reader.close();
		}
	}
}
