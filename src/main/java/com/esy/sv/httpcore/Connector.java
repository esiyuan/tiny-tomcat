package com.esy.sv.httpcore;


public interface Connector {

    public void initialize() throws Exception;
    
    public Container getContainer();

    public void setContainer(Container container);
}
