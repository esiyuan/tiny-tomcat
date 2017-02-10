package com.esy.sv.httpcore.lifecycle;
/**
 * 简单的生命周期监听
 * @author guanjie
 *
 */
public class SimpleLifecyleListener implements LifecycleListener {

	@Override
	public void lifecycleEvent(LifecycleEvent event) {
		switch (event.getType()) {
			case START_EVENT :
				System.out.println(event.getData() + "容器启动！");
				break;
			case STOP_EVENT :
				System.out.println(event.getData() + "容器停止！");
				break;
			default:
				break;
		}
	}
	
	

}
