package com.esy.sv.httpcore.container;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.esy.sv.httpcore.Valve;
import com.esy.sv.httpcore.httpfacade.Request;
import com.esy.sv.httpcore.httpfacade.Response;

/**
 * 用于演示：处理servlet前打印用户信息
 * @author guanjie
 *
 */
public class PrintHostValve implements Valve{
	
	private static Logger logger = Logger.getLogger(PrintHostValve.class);
	
	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		logger.info("阀测试：--[ method : " + request.getMethod() + ", requestURI : " + request.getRequestURI() + ", protocol : " + request.getProtocol() + "]");
	}
	
	

}
