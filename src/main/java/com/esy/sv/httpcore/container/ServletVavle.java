package com.esy.sv.httpcore.container;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.esy.sv.httpcore.Contained;
import com.esy.sv.httpcore.Container;
import com.esy.sv.httpcore.Valve;
import com.esy.sv.httpcore.ValveContext;
import com.esy.sv.httpcore.httpfade.Request;
import com.esy.sv.httpcore.httpfade.Response;

public class ServletVavle  implements Valve, Contained{
	
	private Container container;
	
	public ServletVavle(Container container) {
		this.container = container;
	}

	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void setContainer(Container container) {
		this.container = container;
	}

	@Override
	public void invoke(Request request, Response response, ValveContext context)
			throws IOException, ServletException {
		Servlet servlet = ((SimpleWrapper)container).getServlet();
		servlet.service(request, response);
	}
	
	

}
