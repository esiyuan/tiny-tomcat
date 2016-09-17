package com.esy.sv.httpcore;

import com.esy.sv.common.TomcatException;
/**
 * 生命周期接口，规定组件启动和暂停的方法
 * @author guanjie
 *
 */
public interface Lifecycle {

	 public void start() throws TomcatException ;
	 
	 public void stop();
}
