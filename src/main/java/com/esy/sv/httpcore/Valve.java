package com.esy.sv.httpcore;

import java.io.IOException;

import javax.servlet.ServletException;

import com.esy.sv.httpcore.httpfacade.Request;
import com.esy.sv.httpcore.httpfacade.Response;

public interface Valve {

    public void invoke(Request request, Response response)
        throws IOException, ServletException;


}
