package com.esy.sv.httpcore;

import javax.servlet.ServletException;

/**
 * tomcat抽象出来的表示servlet容器的接口
 * 
 * @author guanjie
 *
 */
public interface Wrapper extends Container {

	public void load() throws ServletException;

	public void setServletClass(String servletClass);

	public void setLoader(Loader loader);

	public Loader getLoader();
	
	public void setPipeline(Pipeline pipeline);
}
