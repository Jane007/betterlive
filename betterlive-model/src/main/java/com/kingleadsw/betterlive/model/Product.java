package com.kingleadsw.betterlive.model;

import java.util.List;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 商品 实体类
 * 2017-03-08 by chen
 * @author Coder
 *
 */
public class Product extends BasePO {
	
	private static final long serialVersionUID = 8626195400211880386L;
	
	private Integer product_id;       //商品ID
	private String product_code;      //商品编号
	private String product_name;      //商品名称
	private Integer pt_id;            //商品分类
	private String product_logo;      //商品封面图
	private String price;             //商品基本价格
	private Integer activity_id;      //促销活动id
	private String activity_name;     //活动名称（主题）  
	private String prompt;            //温馨提示
	private String eat_recommend;     //是否推荐 
	private String introduce;         //商品介绍
	private Integer status;           //状态：  1上架 2 下架
	private String details;       	  //商品其他参数。例如: 商品品牌，配送地区，产地，保质期
	private String create_time;         //创建时间
	private int ifCoupon;			//是否允许使用优惠券和单品红包 0允许，1不允许
	private String detailExplain;	//商品详情简介
	
	private List<ProductSpec> listSpecVo;   //商品规格
	
	private List<Extension> extensionVos;		//扩展表，是否每周新品，人气推荐
	
	private List<Pictures> pictures;  //产品轮播图

	private Integer order_num;				//商品排序
	
	private String warehouse;           //仓库所在地址
	
	private Integer deliver_type;       //配送地址类型
	
	private String deliverAddress;	//地址类型；1：同城；2：本省；3：自定义
	private String deliverType;     //全国配送
	private String areaIds;         //地区
	
	private String share_explain;			//分享简介
	
	private Integer is_online;				//1:线上，2:线下
	
	private String homeweekly_first_img;	//首页排第一位置的新品推荐图
	private String homeweekly_after_img;	//首页非第一个位置的新品推荐图
	private String homefame_img;			//首页人气推荐
	private int product_type;	//商品分类 0 无分类，1新品尝鲜，2餐桌必备，3特产集市，套餐组合
	private int is_freight;	//是否免运费 1是 0否
	private int is_quality;	//是否权威质检 1是 0否
	private int is_testing;	//是否专业测评 1是 0否
	private int fake_sales_copy;	//虚拟销量
	//------------------------------
	private String isActivity;	//当前是否有活动
	private String labelName;	//项目标签
	private String activityPrice;		//活动价
	private String discountPrice;		//优惠价
	private int salesVolume;	//销量	
	private int activityType;	//活动类型
	private int total_stock_copy;	//总库存
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Integer getPt_id() {
		return pt_id;
	}
	public void setPt_id(Integer pt_id) {
		this.pt_id = pt_id;
	}
	public String getProduct_logo() {
		return product_logo;
	}
	public void setProduct_logo(String product_logo) {
		this.product_logo = product_logo;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}
	public String getActivity_name() {
		return activity_name;
	}
	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public String getEat_recommend() {
		return eat_recommend;
	}
	public void setEat_recommend(String eat_recommend) {
		this.eat_recommend = eat_recommend;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public List<ProductSpec> getListSpecVo() {
		return listSpecVo;
	}
	public void setListSpecVo(List<ProductSpec> listSpecVo) {
		this.listSpecVo = listSpecVo;
	}
	public List<Extension> getExtensionVos() {
		return extensionVos;
	}
	public void setExtensionVos(List<Extension> extensionVos) {
		this.extensionVos = extensionVos;
	}
	public Integer getOrder_num() {
		return order_num;
	}
	public void setOrder_num(Integer order_num) {
		this.order_num = order_num;
	}

	public List<Pictures> getPictures() {
		return pictures;
	}
	public void setPictures(List<Pictures> pictures) {
		this.pictures = pictures;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getDeliverAddress() {
		return deliverAddress;
	}
	public void setDeliverAddress(String deliverAddress) {
		this.deliverAddress = deliverAddress;
	}
	public String getDeliverType() {
		return deliverType;
	}
	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}
	public String getAreaIds() {
		return areaIds;
	}
	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}
	public Integer getDeliver_type() {
		return deliver_type;
	}
	public void setDeliver_type(Integer deliver_type) {
		this.deliver_type = deliver_type;
	}
	public String getShare_explain() {
		return share_explain;
	}
	public void setShare_explain(String share_explain) {
		this.share_explain = share_explain;
	}
	public Integer getIs_online() {
		return is_online;
	}
	public void setIs_online(Integer is_online) {
		this.is_online = is_online;
	}
	public String getActivityPrice() {
		return activityPrice;
	}
	public void setActivityPrice(String activityPrice) {
		this.activityPrice = activityPrice;
	}

	public String getHomeweekly_first_img() {
		return homeweekly_first_img;
	}
	public void setHomeweekly_first_img(String homeweekly_first_img) {
		this.homeweekly_first_img = homeweekly_first_img;
	}
	public String getHomeweekly_after_img() {
		return homeweekly_after_img;
	}
	public void setHomeweekly_after_img(String homeweekly_after_img) {
		this.homeweekly_after_img = homeweekly_after_img;
	}
	public int getProduct_type() {
		return product_type;
	}
	public void setProduct_type(int product_type) {
		this.product_type = product_type;
	}
	public String getHomefame_img() {
		return homefame_img;
	}
	public void setHomefame_img(String homefame_img) {
		this.homefame_img = homefame_img;
	}
	public String getIsActivity() {
		return isActivity;
	}
	public void setIsActivity(String isActivity) {
		this.isActivity = isActivity;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}	
	public int getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}
	public String getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}
	public int getActivityType() {
		return activityType;
	}
	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}
	public int getTotal_stock_copy() {
		return total_stock_copy;
	}
	public void setTotal_stock_copy(int total_stock_copy) {
		this.total_stock_copy = total_stock_copy;
	}
	public int getIs_freight() {
		return is_freight;
	}
	public void setIs_freight(int is_freight) {
		this.is_freight = is_freight;
	}
	public int getIs_quality() {
		return is_quality;
	}
	public void setIs_quality(int is_quality) {
		this.is_quality = is_quality;
	}
	public int getIs_testing() {
		return is_testing;
	}
	public void setIs_testing(int is_testing) {
		this.is_testing = is_testing;
	}
	public int getFake_sales_copy() {
		return fake_sales_copy;
	}
	public void setFake_sales_copy(int fake_sales_copy) {
		this.fake_sales_copy = fake_sales_copy;
	}
	public int getIfCoupon() {
		return ifCoupon;
	}
	public void setIfCoupon(int ifCoupon) {
		this.ifCoupon = ifCoupon;
	}
	public String getDetailExplain() {
		return detailExplain;
	}
	public void setDetailExplain(String detailExplain) {
		this.detailExplain = detailExplain;
	}		
	
}
