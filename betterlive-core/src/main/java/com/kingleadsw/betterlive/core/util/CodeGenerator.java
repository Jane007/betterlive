package com.kingleadsw.betterlive.core.util;



import java.util.Random;

public class CodeGenerator {
	
	
	
	/**
	 * 验证码长度
	 */
	public final static int CODE_LENGTH=6;
	
	static char[] codeSequence = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	
	/* 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
	'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
	'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
	'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',*/
	
	
	public static String generateCode(int length){
		StringBuilder appender = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 得到随机产生的验证码数字。
			String strRand = String.valueOf(codeSequence[random.nextInt(10)]);
			appender.append(strRand);
		}
		return appender.toString();
	}
	
}
