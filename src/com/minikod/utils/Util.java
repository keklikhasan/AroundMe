package com.minikod.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

/**
 * 
 * @author hkeklik 
 * this class  for general methods
 */
public class Util {

	private static String TAG = "Maps";
	private static int LOG_LEVEL=3;
	/*
	 * 4-> igorned message
	 * 3-> debug
	 * 2-> info
	 * 1-> warning
	 * 0-> error
	 */

	/*
	 * Convert inputstream to string
	 */
	public static String convertInputStreamToString(InputStream in) throws IOException {
		StringBuffer sb = new StringBuffer();
		byte[] buffer = new byte[256];
		while (true) {
			int byteRead = in.read(buffer);
			if (byteRead == -1)
				break;
			for (int i = 0; i < byteRead; i++) {
				sb.append((char) buffer[i]);
			}
		}
		return sb.toString();
	}

	/*
	 * Get time patern : yyyy.MM.dd HH:mm:ss
	 */
	public static String time() {
		StringBuffer date = new StringBuffer();
		Calendar cal = Calendar.getInstance();
		date.append(cal.get(Calendar.YEAR)).append('.');
		date.append(cal.get(Calendar.MONTH) + 1).append('.');
		date.append(cal.get(Calendar.DATE)).append(" ");
		date.append(cal.get(Calendar.HOUR_OF_DAY)).append(':');
		date.append(cal.get(Calendar.MINUTE)).append(':');
		date.append(cal.get(Calendar.SECOND));
		return date.toString();
	}

	public static void logE(String e) {
		System.err.println(TAG + ", E, " + time() + " - " + e);
	}

	public static void logE(Throwable e) {
		System.err.println(TAG + ", E, " + time() + " - " + e.toString());
	}
	
	public static void logEIngonred(Throwable e) {
		if(LOG_LEVEL>3)
		{
			e.printStackTrace();
			System.err.println(TAG + ", Error Ignored, " + time() + " - " + e.toString());
		}
	}


	public static void logI(String i) {
		if(LOG_LEVEL>1)
		{
			System.err.println(TAG + ", I, " + time() + " - " + i);
		}
	}

	public static void logD(String d) {
		if(LOG_LEVEL>2)
		{
			System.err.println(TAG + ", D, " + time() + " - " + d);
		}
		
	}

	public static void logW(String w) {
		if(LOG_LEVEL>1)
		{
			System.err.println(TAG + ", W, " + time() + " - " + w);
		}
	}
}
