package com.esy.sv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.esy.sv.common.Constants;
import com.esy.sv.common.IOUtil;

public class Response {

	
	private String uri;
	private OutputStream outputStream;
	
	private PrintWriter writer;
	public Response(String uri, OutputStream outputStream) {
		this.uri = uri;
		this.outputStream = outputStream;
		writer = IOUtil.getPrintWriter(outputStream);
	}

	public void sendStaticResource() throws IOException {
		File file = new File(Constants.WEB_ROOT, uri);
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
