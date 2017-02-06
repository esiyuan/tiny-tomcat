package com.esy.sv.httpcore.httpconector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.esy.sv.common.Constants;
import com.esy.sv.common.Stack;
import com.esy.sv.common.StringManager;
import com.esy.sv.common.TomcatException;
/**
 * 接受请求和处理请求在同一个线程，那么会阻塞并发请求，使得系统吞吐率低下
 * 为了进一步实现高效的服务器程序，需要把费时的处理操作单独分离出去，作为独立的线程
 * @author guanjie
 *
 */
public class HttpConnector implements Runnable{

	private static Logger logger = Logger.getLogger(HttpConnector.class);
	private static ServerSocket serverSocket;
	private int port;
	private String host;

	
	public HttpConnector(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	private Stack<HttpProcessor> processors = new Stack<HttpProcessor>();
	
	private int curProcessors;
	private int minProcessors = 5;
	private int maxProcessors = 20;
	
	private String threadName ;
	/**
	 * 设置最大处理器数量
	 * @param maxProcessors
	 * throw IllegalArgumentException if max < 0
	 */
	public void setMaxProcessors(int maxProcessors) {
		if(maxProcessors < 0)
			throw new IllegalArgumentException();
		this.maxProcessors = maxProcessors;
	}
	private final static StringManager sm = StringManager.getManager(Constants.HTTPCORE_PACKAGE_NAME);
	
	public void recycle(HttpProcessor processor) {
		processors.push(processor);
	}
	
	
	@Override
	public void run() {
		while(true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				logger.info("获取连接[ address: " + socket.getInetAddress() + ", port: " + socket.getPort() + " ]");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			HttpProcessor processor = getProcessor();
			if (processor == null) {
				try {
					logger.info(sm.getString("httpConnector.noProcessor"));
					socket.close();
				} catch (IOException e) {
				}
				continue;
			}
			processor.assign(socket);
		}
	}
	
	/**
	 * 启动连接器
	 * @throws TomcatException
	 */
	public void start() throws TomcatException {
		threadName = "HttpConnector[" + port + "]";
		Thread thread = new Thread(this, threadName);
		thread.setDaemon(true);
		thread.start();
		logger.info(sm.getString("httpConnector.starting"));
	}
	/**
	 * 初始化连接处理器和处理器
	 * @throws TomcatException
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void initialize() throws TomcatException, UnknownHostException, IOException {
		createServer();
		while (curProcessors < minProcessors) {
			if (curProcessors >= maxProcessors)
				break;
			HttpProcessor processor = newProcessor();
			recycle(processor);
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
	
	private HttpProcessor getProcessor() {
		if (processors.size() > 0)
			return processors.pop();
		if (curProcessors < maxProcessors) {
			return newProcessor();
		}  
		return (null);
	}
	
	private HttpProcessor newProcessor() {
		HttpProcessor processor = new HttpProcessor(this, curProcessors++);
			try {
				processor.start();
			} catch (TomcatException e) {
				return (null);
			}
		return processor;
	}
}
