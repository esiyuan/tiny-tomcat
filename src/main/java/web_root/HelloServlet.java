package web_root;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = -2585140950753353037L;

	private static final Logger logger = Logger.getLogger(HelloServlet.class);


	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		logger.info("service()");
		PrintWriter out = response.getWriter();
		out.println("HelloServlet.class");
	}
}
