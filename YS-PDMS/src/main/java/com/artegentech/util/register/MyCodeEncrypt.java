package com.artegentech.util.register;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;


public class MyCodeEncrypt {
	// 客户端应该只需要用公钥解密,管理端存在私钥加密.如果客户端有公钥加密和私钥解密,那么程序别破解后就会把公钥和私钥一起暴露
	// 客户端将没有加密过的机器码,或者是用Base64加密过的机器码传给管理端，管理端将机器码和有效期一起加密后传给客户端，客户端使用公钥解密再进行判断
	private static final String SALT = "R2F2aW4=";

	/**
	 * 提供有密码的加密处理操作
	 * 
	 * @param password
	 * @return
	 */
	public static String encryptString(String code) {
		String finalCode = code + "({" + SALT + "})";
		//System.out.println("加密前: \r\n" + finalCode);
		try {
			byte[] data = finalCode.getBytes("UTF-8");
			//System.out.println("Base64加密后: \r\n" + Base64.encodeBase64String(data));
			return Base64.encodeBase64String(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decodeString(String code,String publicKey) {

		try {
			byte[] result = code.getBytes();
			byte[] decodedData = RSAUtils.decryptByPublicKey(result, publicKey);
			String target = new String(decodedData, "UTF-8");
			//System.out.println("解密后: \r\n" + target);
			return target;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decodeString64(String code) {
		try {
			//System.out.println("Base64解密前: \r\n" + code);
			byte[] result = Base64.decodeBase64(code);
			String resultString = new String(result, "UTF-8");
			//System.out.println("Base64解密后: \r\n" + resultString);
			return resultString;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	//上面两个方法结合使用时会出错，判断是String转byte[]，然后byte[]转String过程中数据有变化，造成解密失败
	//故改为以下两个方法配合执行解密动作，直接传递byte[]数组，这样数据不会中转，测试OK
	
	public static String decodeByte(byte[] code,String publicKey) {

		try {
			byte[] decodedData = RSAUtils.decryptByPublicKey(code, publicKey);
			String target = new String(decodedData, "UTF-8");
			//System.out.println("解密后: \r\n" + target);
			return target;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 對字符串進行Base64解密
	 * 
	 * @param code
	 * @return
	 */
	public static byte[] decodeString64Byte(String code) {
		try {
			//System.out.println("Base64解密前: \r\n" + code);
			byte[] result = Base64.decodeBase64(code);
			//String resultString = new String(result, "UTF-8");
			//System.out.println("Base64解密后: \r\n" + resultString);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 對字符串進行Base64解密
	 * 
	 * @param code
	 * @return
	 */
	public static byte[] decodeByte64Byte(byte[] code) {
		try {
			//System.out.println("Base64解密前: \r\n" + new String(code, "UTF-8"));
			byte[] result = Base64.decodeBase64(code);
			//String resultString = new String(result, "UTF-8");
			//System.out.println("Base64解密后: \r\n" + resultString);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
