package com.esy.sv.httpcore.container;

import java.net.URL;
import java.net.URLClassLoader;

import com.esy.sv.common.Constants;
import com.esy.sv.httpcore.Container;
import com.esy.sv.httpcore.Loader;

public class SimpleLoader implements Loader {

	private ClassLoader classLoader = null;
	private Container container = null;

	public SimpleLoader() {
		try {
			classLoader = new URLClassLoader(new URL[]{new URL("file://" + Constants.TOMCAT_CLASSLOADER_REPOSITORY)});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ClassLoader getClassLoader() {
		return classLoader;
	}

	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void setContainer(Container container) {
		this.container = container;
	}

}
