package com.esy.sv.common;

public final class StringHelper {

	private StringHelper() {
	}
	
	public static boolean isEmptyOrNull(String str) {
		if(str == null || str.equals(""))
			return true;
		return false;
	}

	
}
