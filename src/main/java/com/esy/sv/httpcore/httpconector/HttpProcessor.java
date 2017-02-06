package com.esy.sv.httpcore.httpconector;

import java.io.IOException;
import java.net.Socket;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.esy.sv.common.Constants;
import com.esy.sv.common.StringManager;
import com.esy.sv.common.TomcatException;
import com.esy.sv.httpcore.Request;
import com.esy.sv.httpcore.Response;

public class HttpProcessor implements Runnable{
	
	private static Logger logger = Logger.getLogger(HttpProcessor.class);
	private HttpConnector connector;
	
	private final int id;
	
	public HttpProcessor(HttpConnector connector, int id) {
		this.connector = connector;
		this.id = id;
	}
	
	private static StringManager sm = StringManager.getManager(Constants.HTTPCORE_PACKAGE_NAME);
	
    private Socket socket;
    private boolean newSocketCome;
    
    public void start() throws TomcatException {
        logger.info(sm.getString("httpProcessor.starting") + " HttpProcessor " + id);
        Thread thread = new Thread(this, "HttpProcessor " + id);
        thread.setDaemon(true);
        thread.start();
    }
    
    public synchronized void assign(Socket socket) {
		this.socket = socket;
		newSocketCome = true;
		notifyAll();
    }
    
    private void process(Socket socket) throws IOException, ServletException {
    	Request request = new Request(socket.getInputStream());
		request.parse();
		Response response = new Response(request, socket.getOutputStream());
		response.process();
		socket.close();
		connector.recycle(this);
    }
    
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				Socket socket = waitingToNewSocket();
				process(socket);
			}
		} catch (InterruptedException | IOException | ServletException e) {
			System.out.println("处理器异常。。。");
		}
	}
	
	private synchronized Socket waitingToNewSocket() throws InterruptedException {
        while (!newSocketCome) {
        	wait();
        }
        Socket socket = this.socket;
        newSocketCome = false;
        return (socket);
   }
}
