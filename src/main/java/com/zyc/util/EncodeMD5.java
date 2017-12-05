package com.zyc.util;

import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

/**
 * MD5 加密工具类
 */
public class EncodeMD5 {
	private static final Integer SALT_LENGTH =12 ;
	public static String encodeMD5(String string,String salt){
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BASE64Encoder base64Encoder = new BASE64Encoder();
		string = string+getSalt(salt);
        base64Encoder.encode(messageDigest.digest(string.getBytes()));
		string=base64Encoder.encode(messageDigest.digest(string.getBytes()));
		string = DigestUtils.md5Hex(string.getBytes());
		return string;
	}
	public static String encodeSHA(String string){
		MessageDigest messageDigest = null;
		try {
			messageDigest=messageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		messageDigest.update(string.getBytes());
		BASE64Encoder base64Encoder = new BASE64Encoder();
		return base64Encoder.encode(messageDigest.digest(string.getBytes()));
	}
	public static void main(String[] args) {
		System.out.println(Calendar.getInstance().get(Calendar.YEAR)+"_"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"_"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	}
	public static String getSalt(String string){
		StringBuffer stringBuffer = new StringBuffer();
		for(int i=0;i<string.length();i++){
			if(i%2==0){
				stringBuffer.append(string.charAt(i));
			}
		}
		return stringBuffer.toString();
	}
}
	
