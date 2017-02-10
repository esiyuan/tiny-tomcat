package com.esy.sv.httpcore.lifecycle;
/**
 * 组件生命周期监听状态
 * @author guanjie
 *
 */
public enum LifecycleEnum {
	
	/**The LifecycleEvent type for the "component start" event.*/
	START_EVENT("start"),
	/**The LifecycleEvent type for the "component before start" event.*/
	BEFORE_START_EVENT("before_start"),
	/** The LifecycleEvent type for the "component after start" event.*/
	AFTER_START_EVENT("after_start"),
	/** The LifecycleEvent type for the "component stop" event.*/
	STOP_EVENT("stop"),
	/**The LifecycleEvent type for the "component before stop" event.*/
	BEFORE_STOP_EVENT("before_stop"),
	/**The LifecycleEvent type for the "component after stop" event.*/
	AFTER_STOP_EVENT("after_stop");
	
	private String msg;

	private LifecycleEnum(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
}
