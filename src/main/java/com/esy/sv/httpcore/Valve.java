package com.esy.sv.httpcore;

import java.io.IOException;

import javax.servlet.ServletException;

import com.esy.sv.httpcore.httpfade.Request;
import com.esy.sv.httpcore.httpfade.Response;

public interface Valve {

    public void invoke(Request request, Response response, ValveContext context)
        throws IOException, ServletException;


}
