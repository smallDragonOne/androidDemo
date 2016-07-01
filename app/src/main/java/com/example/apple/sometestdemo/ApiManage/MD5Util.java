package com.example.apple.sometestdemo.ApiManage;

import java.security.MessageDigest;

public class MD5Util {
	// md5
	public  static String MD5(String ss)  {
		try{
			byte[] buf = ss.getBytes();
			MessageDigest md5;
			md5 = MessageDigest.getInstance("MD5");
			md5.update(buf);
			byte[] tmp = md5.digest();

			String HEX = "0123456789abcdef";
			StringBuilder ret = new StringBuilder();
			for (int i = 0; i < tmp.length; i++) {
				byte b = tmp[i];
				ret.append(HEX.charAt((b >> 4) & 0x0f));
				ret.append(HEX.charAt(b & 0x0f));
			}
			return ret.toString();
		}catch(Exception e){
			return "00000000000000000000000000000000";
		}
	}

	public static String getSubString(String s, int beginIndex, int endIndex) {
		String str = MD5Util.MD5(s);
		if (str == null) {
			return null;
		} else {
			return str.toLowerCase().substring(beginIndex, endIndex);
		}
	}
}
