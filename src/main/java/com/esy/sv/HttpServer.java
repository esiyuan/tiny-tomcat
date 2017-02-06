package com.esy.sv;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

/**
 * 简单处理http请求的服务器
 * 
 * @author guanjie
 *
 */
public class HttpServer { 
	
	private static Logger logger = Logger.getLogger(HttpServer.class);
	
	private static ServerSocket serverSocket;
	private int port;
	private String host;

	public HttpServer(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public static void main(String[] args) {
		HttpServer httpServer = new HttpServer("localhost", 8080);
		try {
			httpServer.createServer();
			httpServer.handlerRequest();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handlerRequest() throws IOException {
		while(true) {
			Socket socket = serverSocket.accept();
			logger.info("获取连接[ address: " + socket.getInetAddress() + ", port: " + socket.getPort() + " ]");
			Request request = new Request(socket.getInputStream());
			request.parse();
			Response response = new Response(request.getUri(), socket.getOutputStream());
			response.sendStaticResource();
			socket.close();
		}
	}

	private void createServer() throws UnknownHostException, IOException {
		if (serverSocket == null) {
			synchronized (this) {
				if (serverSocket == null) {
					serverSocket = new ServerSocket(port, -1, InetAddress.getByName(host));
				}
			}
		}
	}
}
