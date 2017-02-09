package com.esy.sv.httpcore.container;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletException;

import com.esy.sv.httpcore.Pipeline;
import com.esy.sv.httpcore.Valve;
import com.esy.sv.httpcore.httpfacade.Request;
import com.esy.sv.httpcore.httpfacade.Response;

/**
 * tomcat组件化实现,在容器启动前进行组件的调用
 * <p>
 * 如何进行组件管理，tomcat引入管道的概念，而组件就是管道中的阀
 * 
 * @author guanjie
 *
 */
public class SimplePipeline implements Pipeline{
	
	private Valve baise;
	
	private List<Valve> valves = new CopyOnWriteArrayList<>();

	@Override
	public Valve getBasic() {
		return baise;
	}

	@Override
	public void setBasic(Valve valve) {
		baise = valve;
	}

	@Override
	public void addValve(Valve valve) {
		valves.add(valve);
	}

	@Override
	public Valve[] getValves() {
		Valve[] valveArr = new Valve[valves.size()];
		return valves.toArray(valveArr);
	}

	@Override
	public void invoke(Request request, Response response) throws IOException,
		ServletException {
		for (Valve each : valves) {
			each.invoke(request, response);
		}
		baise.invoke(request, response);
	}

	@Override
	public void removeValve(Valve valve) {
		valves.remove(valve);
	}
	
	

}
