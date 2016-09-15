package com.esy.sv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import com.esy.sv.common.IOUtil;

public class Request {

	private InputStream inputStream;
	private String uri;

	public Request(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public void parse() throws IOException {
		BufferedReader reader = IOUtil.getBufferedReader(inputStream);
		String requestString = reader.readLine();
		this.uri = parseUri(requestString);
	}
	/**
	 * 解析http协议 的uri
	 * @param requestString
	 * @return
	 */
	private String parseUri(String requestString) {
		int index1, index2;
		index1 = requestString.indexOf(' ');
		if (index1 != -1) {
			index2 = requestString.indexOf(' ', index1 + 1);
			if (index2 > index1)
				return requestString.substring(index1 + 1, index2);
		}
		return null;
	}
	
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
}
