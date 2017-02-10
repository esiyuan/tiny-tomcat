package com.esy.sv.httpcore.container;

import java.io.IOException;

import javax.servlet.ServletException;

import com.esy.sv.httpcore.Contained;
import com.esy.sv.httpcore.Container;
import com.esy.sv.httpcore.Valve;
import com.esy.sv.httpcore.ValveContext;
import com.esy.sv.httpcore.httpfacade.Request;
import com.esy.sv.httpcore.httpfacade.Response;

public class SimpleValve implements Valve, Contained{
	
	private Container container;
		
	@Override
	public Container getContainer() {
		// TODO Auto-generated method stub
		return container;
	}

	@Override
	public void setContainer(Container container) {
		this.container = container;
	}

	@Override
	public void invoke(Request request, Response response, ValveContext context)
			throws IOException, ServletException {
		System.out.println("SimpleValve.invoke()");
		context.invokeNext(request, response);
	}
	
	
	

}
