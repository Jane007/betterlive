package com.kingleadsw.betterlive.core.enums;


public enum PayStatusEnums {
    NO("未支付","no",0),
    YES("已支付","yes",1),
    CANCEL("已取消","cancel",2);
     

    private PayStatusEnums(String desc,String code,Integer status){
        this.desc = desc;
        this.code=code;
        this.status = status;
    }


    private Integer status;
    private String desc;
    private String code;
 

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
 

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
 
}
