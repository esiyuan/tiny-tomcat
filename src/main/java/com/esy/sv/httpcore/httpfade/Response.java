package com.esy.sv.httpcore.httpfade;

import java.io.OutputStream;
import java.io.PrintWriter;

import com.esy.sv.common.IOUtil;

/**
 * 类同request
 * @author guanjie
 *
 */
public class Response extends ResponseFade{

	private Request request;
	private OutputStream output;
	private PrintWriter writer;
	
	public Response(Request request, OutputStream output) {
		super();
		this.request = request;
		this.output = output;
		writer = IOUtil.getPrintWriter(output);
	}
	
	@Override
	public PrintWriter getWriter(){
		return writer;
	}
}
