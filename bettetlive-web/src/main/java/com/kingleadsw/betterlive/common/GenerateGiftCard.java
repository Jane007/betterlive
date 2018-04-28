package com.kingleadsw.betterlive.common;


/**
 * @author ltp
 * @since JDK1.7
 * @history 2017年4月27日 上午11:13:32 
 */
public class GenerateGiftCard {

	/**
	 * 
	 */
	public GenerateGiftCard() {
		
	}
	
	/** 
     * 随机生成 num位数字字符数组 
     *  
     * @param num 
     * @return 
     */  
    public static char[] generateRandomArray(int num) {  
        String chars = "0123456789";  
        char[] rands = new char[num];  
        for (int i = 0; i < num; i++) {  
            int rand = (int) (Math.random() * 10);  
            rands[i] = chars.charAt(rand);  
        }  
        return rands;  
    }  
  
    public static void main(String[] args) {  
    	for (int i = 0; i < 75; i++) {
    		// 随机生成16位数字  
    		System.out.println(generateRandomArray(16));  
		}
  
    }  

}
