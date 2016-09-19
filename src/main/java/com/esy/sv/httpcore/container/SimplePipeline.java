package com.esy.sv.httpcore.container;

import java.io.IOException;

import javax.servlet.ServletException;

import com.esy.sv.httpcore.Contained;
import com.esy.sv.httpcore.Container;
import com.esy.sv.httpcore.Pipeline;
import com.esy.sv.httpcore.Valve;
import com.esy.sv.httpcore.ValveContext;
import com.esy.sv.httpcore.httpfade.Request;
import com.esy.sv.httpcore.httpfade.Response;

public class SimplePipeline implements Pipeline{

	private Container container = null;
	private Valve valves[] = new Valve[0];
	private Valve basic = null;
	
	public SimplePipeline(Container container) {
		this.container = container;
	}

	@Override
	public Valve getBasic() {
		return basic;
	}

	@Override
	public void setBasic(Valve valve) {
		basic = valve;
	}

	@Override
	public void addValve(Valve valve) {
		if(valve instanceof Contained)
			((Contained)valve).setContainer(container);
		synchronized (valves) {
			Valve[] results = new Valve[valves.length + 1];
			System.arraycopy(valves, 0, results, 0, valves.length);
			results[valves.length] = valve;
			valves = results;
		}
	}

	@Override
	public Valve[] getValves() {
		return valves;
	}

	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		(new SimplePipelineValveContext()).invokeNext(request, response);
	}

	@Override
	public void removeValve(Valve valve) {
		// TODO Auto-generated method stub
		
	}
	
	protected class SimplePipelineValveContext implements ValveContext {
		private int index;
		
		@Override
		public void invokeNext(Request request, Response response)
				throws IOException, ServletException {
			int stage = index;
			index++;
			if(stage < valves.length)
				valves[stage].invoke(request, response, this);
			else if(stage == valves.length && basic != null)
				basic.invoke(request, response, this);
			else
				throw new ServletException("no vavle");
		}
		
	}

}
