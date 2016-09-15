package com.esy.sv;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.esy.sv.common.IOUtil;

public class Client {

	public static void main(String[] args) throws Exception{
		Socket socket = new Socket("127.0.0.1", 8080);
		boolean autoflush = true;
		PrintWriter out = new PrintWriter(socket.getOutputStream(), autoflush);
		out.println("GET /servlet/HelloServlet HTTP/1.1");
		BufferedReader in = IOUtil.getBufferedReader(socket.getInputStream());
		String buff;
		while((buff = in.readLine()) != null)
			System.out.println(buff);
		socket.close();
	}
}
