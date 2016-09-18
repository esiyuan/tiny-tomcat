package com.esy.sv.httpcore.container;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.esy.sv.httpcore.Container;
import com.esy.sv.httpcore.Loader;
import com.esy.sv.httpcore.Pipeline;
import com.esy.sv.httpcore.Wrapper;
import com.esy.sv.httpcore.httpfade.Request;
import com.esy.sv.httpcore.httpfade.Response;

public class SimpleWrapper implements Wrapper{

	private String servletClass;
	private Loader loader;
	protected Container parent = null;
	private String name;
	private Pipeline pipeline;
	private Servlet instance;

	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		pipeline.invoke(request, response);
	}
	
	public Servlet getServlet() throws ServletException {
		if(instance == null)
			load();
		return instance;
	}
	
	@Override
	public void load() throws ServletException {
		if(instance != null)
			return;
		if(servletClass == null)
			throw new ServletException("servlet class has not been specified");
		if(loader == null)
			throw new ServletException("No loader.");
		ClassLoader classLoader = loader.getClassLoader();
		Class<?> clazz;
		try {
			clazz = classLoader.loadClass("web_root." + servletClass);
			instance = (Servlet)clazz.newInstance();
			instance.init(null);
		} catch (ClassNotFoundException e) {
			throw new ServletException("Servlet class not found");
		} catch (Exception e) {
			throw new ServletException("Failed to instantiate servlet");
		}
	}

	@Override
	public void setServletClass(String servletClass) {
		this.servletClass = servletClass;
	}

	@Override
	public void setLoader(Loader loader) {
		this.loader = loader;
	}

	@Override
	public Loader getLoader() {
		return loader;
	}
	@Override
	public void setPipeline(Pipeline pipeline) {
		this.pipeline = pipeline;
	}

	
}
