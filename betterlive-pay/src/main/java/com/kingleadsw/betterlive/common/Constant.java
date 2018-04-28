package com.kingleadsw.betterlive.common;

/**
 * 常量
 * @author Administrator
 *
 */
public class Constant {

	//支付方式枚举enum
	public enum payType{
		WXPAY("1", "微信"), ALIPAY("2", "支付宝");
		
		private String index;
		private String name;
		
		//构造方法
		private payType(String  index, String name){
			this.index = index;
			this.name = name;
		}
		
		//get set方法
		public String getIndex() {
			return index;
		}
		
		public void setIndex(String index) {
			this.index = index;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
	}
}
