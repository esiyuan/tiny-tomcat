package com.esy.sv.httpcore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import com.esy.sv.common.IOUtil;
import com.esy.sv.httpcore.facade.RequestFacade;

/**
 * 通过RequestFade 类实现HttpServletRequest接口 
 * 此类再继承，避免项目接口污染，使代码更加清晰
 * @author guanjie
 *
 */
public class Request extends RequestFacade{

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
	 * 解析出servlet名字
	 */
	@Override
	public String getServerName() {
		return uri.substring(uri.lastIndexOf("/") + 1);
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
	
	
}
