package com.esy.sv.common;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class StringManager {

	private ResourceBundle bundle;

	private StringManager(String packageName) {
		String bundleName = packageName + ".LocalStrings";
		bundle = ResourceBundle.getBundle(bundleName);
	}
	
	public static void main(String[] args) {
		StringManager sm = getManager(Constants.HTTPCORE_PACKAGE_NAME);
		System.out.println(sm.getString("httpConnector.starting"));
	}
	
	
	private static ConcurrentHashMap<String, StringManager> managers = new ConcurrentHashMap<>();

	public static StringManager getManager(String packageName) {
		StringManager mgr = (StringManager) managers.get(packageName);
		if (mgr == null) {
			mgr = new StringManager(packageName);
			StringManager oldmgr = managers.putIfAbsent(packageName, mgr);
			if(oldmgr != null)
				return oldmgr;
		} 
		return mgr;
	}

	public String getString(String key) {
		if (key == null) {
			String msg = "key is null";
			throw new NullPointerException(msg);
		}
		String str = null;
		try {
			str = bundle.getString(key);
		} catch (MissingResourceException mre) {
			str = "Cannot find message associated with key '" + key + "'";
		}
		return str;
	}
}
