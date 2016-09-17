package com.esy.sv.httpcore.httpconector;

import java.io.IOException;
import java.net.Socket;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.esy.sv.common.Constants;
import com.esy.sv.common.StringManager;
import com.esy.sv.common.TomcatException;
import com.esy.sv.httpcore.httpfade.Request;
import com.esy.sv.httpcore.httpfade.Response;

public class HttpProcessor implements Runnable{
	
	private static Logger logger = Logger.getLogger(HttpProcessor.class);
	private HttpConnector connector;
	
	private final int id;
	
	private boolean started;
	public HttpProcessor(HttpConnector connector, int id) {
		this.connector = connector;
		this.id = id;
	}
	
	private boolean stoped;
	private static StringManager sm = StringManager.getManager(Constants.HTTPCORE_PACKAGE_NAME);
	
    private Socket socket;
    private boolean newSocketCome;
    
    public void start() throws TomcatException {
        if (started)
            throw new TomcatException(sm.getString("httpProcessor.alreadyStarted"));
        started = true;
        logger.info(sm.getString("httpProcessor.starting") + " HttpProcessor " + id);
        Thread thread = new Thread(this, "HttpProcessor " + id);
        thread.setDaemon(true);
        thread.start();
    }
    
    public synchronized void assign(Socket socket) {
	  while (newSocketCome) {
          try {
              wait();
          } catch (InterruptedException e) {
          }
      }
      this.socket = socket;
      newSocketCome = true;
      notifyAll();
      
    }
    
    private void process(Socket socket) throws IOException, ServletException {
    	Request request = new Request(socket.getInputStream());
		request.parse();
		Response response = new Response(request, socket.getOutputStream());
		connector.getContainer().invoke(request, response);
		socket.close();
		connector.recycle(this);
    }
    
	@Override
	public void run() {
		while(!stoped) {
			Socket socket = waitingToNewSocket();
			try {
				process(socket);
			} catch (IOException | ServletException e) {
				e.printStackTrace();
			}
		}
	}
	
	private synchronized Socket waitingToNewSocket() {
        while (!newSocketCome) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        Socket socket = this.socket;
        newSocketCome = false;
        notifyAll();
        return (socket);
   }
}
