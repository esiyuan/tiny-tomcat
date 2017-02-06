package com.esy.sv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.esy.sv.common.Constants;
import com.esy.sv.common.IOUtil;
/**
 * 响应对象，输出响应信息
 * @author guanjie
 *
 */
public class Response {

	private String uri;
	private OutputStream outputStream;
	/**
	 * ServletResponse中用的输出流
	 */
	private PrintWriter writer;
	
	public Response(String uri, OutputStream outputStream) {
		this.uri = uri;
		this.outputStream = outputStream;
		writer = IOUtil.getPrintWriter(outputStream);
	}
	/**
	 * 输出响应
	 * <p>文件找到uri对应的文件，则进行输出，否则输出404
	 * @throws IOException
	 */
	public void sendStaticResource() throws IOException {
		try {
			File file = new File(Constants.WEB_ROOT, uri);
			sendFile(file);
		} catch (FileNotFoundException e) {
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
	
	/**
	 * 输出响应
	 * @param file
	 * @throws IOException
	 */
	private void sendFile(File file) throws IOException {
		try(FileReader fis = new FileReader(file);
			BufferedReader br = new BufferedReader(fis);) {
			String read = null;
			while((read = br.readLine()) != null) {
				this.writer.println(read);
			}
		}
	}
	
}
