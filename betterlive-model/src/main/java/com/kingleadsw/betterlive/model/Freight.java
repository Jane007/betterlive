package com.kingleadsw.betterlive.model;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 运费对象
 * @author ltp
 *
 */
public class Freight extends BasePO {

	private static final long serialVersionUID = 2113733973361727864L;
	
	private Integer freight_id;// 运费表id
	private Integer freight_type;// 运费类型，1：市内；2：省内；3：省外；4：偏远地区；5：不发货地区
	private String area_ids;// 地区id集合字符串，逗号分隔
	private String area_names;// 地区名字字符串，逗号分隔
	private float freight;// 运费价格
	private float full_cut;// 满减金额，满多少金额免邮费
	
	public Integer getFreight_id() {
		return freight_id;
	}
	public void setFreight_id(Integer freight_id) {
		this.freight_id = freight_id;
	}
	public Integer getFreight_type() {
		return freight_type;
	}
	public void setFreight_type(Integer freight_type) {
		this.freight_type = freight_type;
	}
	public String getArea_ids() {
		return area_ids;
	}
	public void setArea_ids(String area_ids) {
		this.area_ids = area_ids;
	}
	public String getArea_names() {
		return area_names;
	}
	public void setArea_names(String area_names) {
		this.area_names = area_names;
	}
	public float getFreight() {
		return freight;
	}
	public void setFreight(float freight) {
		this.freight = freight;
	}
	public float getFull_cut() {
		return full_cut;
	}
	public void setFull_cut(float full_cut) {
		this.full_cut = full_cut;
	}
	
}
