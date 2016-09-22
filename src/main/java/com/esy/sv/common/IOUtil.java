package com.esy.sv.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public final class IOUtil {

	private IOUtil() {
	}

	public final static BufferedReader getBufferedReader(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream));
	}
	
	public final static BufferedReader getBufferedReader(InputStream inputStream, String charSet) throws UnsupportedEncodingException {
		return new BufferedReader(new InputStreamReader(inputStream, charSet));
	}
	
	public final static BufferedWriter getBufferedWriter(OutputStream outputStream) {
		return new BufferedWriter(new OutputStreamWriter(outputStream));
	}
	public final static PrintWriter getPrintWriter(OutputStream outputStream) {
		return new PrintWriter(outputStream ,true);
	}
	
	
}
