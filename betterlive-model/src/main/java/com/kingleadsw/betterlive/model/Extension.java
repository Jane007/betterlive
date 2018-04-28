package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;
/**
 *	商品推荐 模型
 * @author zhangjing
 * @date 2017年3月13日 下午1:56:49
 */
public class Extension extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6368604441641908596L;
	
	private Integer extensionId; 	//商品推荐id
	
	private Integer extensionType; 	//推荐类型，1：每周新品；2：人气推荐
	
	private Integer productId; 		//商品id
	
	private Integer isHomepage; 		//是否首页展示，1：是；0：否，最多只能有4个
	
/*	private Integer status;	//商品状态，1：上架，2：下架，3：删除
*/
	public Integer getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(Integer extensionId) {
		this.extensionId = extensionId;
	}

	public Integer getExtensionType() {
		return extensionType;
	}

	public void setExtensionType(Integer extensionType) {
		this.extensionType = extensionType;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getIsHomepage() {
		return isHomepage;
	}

	public void setIsHomepage(Integer isHomepage) {
		this.isHomepage = isHomepage;
	}

	
/*	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}*/

}
