package com.esy.sv.httpcore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import com.esy.sv.ServletProcessor;
import com.esy.sv.common.Constants;
import com.esy.sv.common.IOUtil;
import com.esy.sv.httpcore.fade.ResponseFade;

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
	
	public void process() throws ServletException, IOException {
		if(request.getUri().startsWith("/servlet/")) {
			ServletProcessor processor = new ServletProcessor();
			processor.process(request, this);
		} else {
			sendStaticResource();
		}
	}
	
	@Override
	public PrintWriter getWriter() throws IOException {
		return writer;
	}
	
	private void sendStaticResource() throws IOException {
		File file = new File(Constants.WEB_ROOT, request.getUri());
		if (file.exists()) {
			sendFile(file);
		} else {
			sendDefault();
		}
	}
	
	private void sendDefault() {
		String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
				+ "Content-Type: text/html\r\n"
				+ "Content-Length: 23\r\n" + "\r\n"
				+ "<h1>File Not Found</h1>";
		writer.println(errorMessage);
	}
	
	private void sendFile(File file) throws IOException {
		BufferedReader reader = null;
		String buf;
		try {
			reader = IOUtil.getBufferedReader(new FileInputStream(file));
			while((buf = reader.readLine()) != null) {
				writer.println(buf);
			}
		} finally {
			if(reader != null)
				reader.close();
		}
	}
}
