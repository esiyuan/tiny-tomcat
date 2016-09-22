package com.esy.sv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.esy.sv.common.IOUtil;

public class ClientTest {
	@Test
	public void testConnector() {
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for(int i = 0; i < 50; i++) {
			executor.execute(new ClientRun());
		}
		executor.shutdown();
	}
	
	private static class ClientRun implements Runnable {
		@Override
		public void run() {
			Socket socket;
			try {
				socket = new Socket("127.0.0.1", 8080);
				boolean autoflush = true;
				PrintWriter out = new PrintWriter(socket.getOutputStream(), autoflush);
				out.println("GET /servlet/HelloServlet HTTP/1.1");
				BufferedReader in = IOUtil.getBufferedReader(socket.getInputStream());
				String buff;
				while((buff = in.readLine()) != null)
					System.out.println(buff);
				Thread.sleep(1000);
				socket.close();			
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
