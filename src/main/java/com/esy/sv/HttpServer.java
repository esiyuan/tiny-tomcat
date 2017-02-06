package com.esy.sv;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.esy.sv.httpcore.Request;
import com.esy.sv.httpcore.Response;

/**
 * 简单处理http请求的服务器(ServerSocket/Socket 是建立在可靠的传输层协议tcp基础上的抽象的通信接口)
 * 次节--增加了动态响应的功能，及简单的单线程servlet响应 
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
		httpServer.start();
	}
	/**
	 * 服务器程序的入口
	 */
	public void start() {
		try {
			createServer();
			handlerRequest();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 处理客户端连接请求
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handlerRequest() throws IOException, ServletException {
		while(true) {
			Socket socket = serverSocket.accept();
			logger.info("获取连接[ address: " + socket.getInetAddress() + ", port: " + socket.getPort() + " ]");
			Request request = new Request(socket.getInputStream());
			request.parse();
			Response response = new Response(request, socket.getOutputStream());
			response.process();
			socket.close();
		}
	}
	/**
	 * 创建监听服务器端口的ServerSocket
	 * @throws UnknownHostException
	 * @throws IOException
	 */
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
