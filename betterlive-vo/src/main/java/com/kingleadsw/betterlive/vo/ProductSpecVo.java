package com.kingleadsw.betterlive.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.kingleadsw.betterlive.core.dto.BaseVO;

/**
 * 商品规格
 * 2017-03-08 by chen
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class ProductSpecVo extends BaseVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer spec_id;         //规格id
	private Integer product_id;      //商品id
	private String spec_name;        //规格名称
	private String spec_price;       //规格单价
	private Integer status;          //状态
	private String create_time;      //创建时间
	private String spec_img;		 //商品规格图片
	private Integer stock_copy;			//库存
	private Integer sales_copy;			//销量
	
	private Integer limit_max_copy;		//最大份数 -1代表没限制
	
	private String limit_start_time;		//限份数开始时间
	
	private String limit_end_time;			//限份数结束时间
	
	private String package_desc;		//套餐说明
	
	
	//------------- 非原始表数据库 -------------
	private String product_name;        //商品名称
	private String product_code;		//商品编码
	private Integer amount;             //购买数量
	private String activity_price;      //活动价格
	private Integer limit_pro_buy;      //专题商品限购数量 
	private Integer deliver_status;     //商品配送状态，1：配送；0：不配送
	
	private Integer rest_copy;			//剩余购买数量，供页面展示
	private Integer hasBuy_copy;			//已经购买数量，供页面展示
	private Integer carNums;			//购物车里有多少件限购规格商品
	private Integer carCanAdd;			//购物车还可以添加多少份
	private String promoitionName; //满减活动，供页面显示

	private String fullMoney;	//满多少钱
	private String cutMoney;	//减多少钱
	
	private BigDecimal discount_price;	//优惠价 
	

	private Integer cart_id;  //商品所在购物车id

	private String linkUrl;			//红包页面链接展示，供页面展示
	
	private Integer total_stock_copy;			//总库存
	private Integer total_sales_copy;			//总销量
	private String share_explain;	//商品简介
	private String label_name;	//标签名
	private String product_logo;
	private int productStatus;
	private int fake_sales_copy;	//虚拟销量
	private int ifCoupon;			//是否能使用优惠券和红包 1否 0是
	
	
	private int singleCouponCount; 	//单品红包数量
	
	private int couponCount;		//优惠券数量
	
	private Long redeemSpecId;		//优惠购活动规格ID
	private String redeemDesc;		//优惠购描述
	private BigDecimal needIntegral; //优惠购要求金币数
	private BigDecimal deductibleAmount; //优惠购可抵扣金额
	private Long goldDeductSpecId;	//普通金币抵扣活动规格ID
	
	//------------------------------------
	public Integer getSpec_id() {
		return spec_id;
	}
	public void setSpec_id(Integer spec_id) {
		this.spec_id = spec_id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getSpec_name() {
		return spec_name;
	}
	public void setSpec_name(String spec_name) {
		this.spec_name = spec_name;
	}
	public String getSpec_price() {
		return spec_price;
	}
	public void setSpec_price(String spec_price) {
		this.spec_price = spec_price;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getSpec_img() {
		return spec_img;
	}
	public void setSpec_img(String spec_img) {
		this.spec_img = spec_img;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getActivity_price() {
		return activity_price;
	}
	public void setActivity_price(String activity_price) {
		this.activity_price = activity_price;
	}

	public Integer getStock_copy() {
		return stock_copy;
	}
	public void setStock_copy(Integer stock_copy) {
		this.stock_copy = stock_copy;
	}
	public Integer getSales_copy() {
		return sales_copy;
	}
	public void setSales_copy(Integer sales_copy) {
		this.sales_copy = sales_copy;
	}

	public Integer getLimit_pro_buy() {
		return limit_pro_buy;
	}
	public void setLimit_pro_buy(Integer limit_pro_buy) {
		this.limit_pro_buy = limit_pro_buy;
	}
	public Integer getDeliver_status() {
		return deliver_status;
	}
	public void setDeliver_status(Integer deliver_status) {
		this.deliver_status = deliver_status;
	}
	public Integer getLimit_max_copy() {
		return limit_max_copy;
	}
	public void setLimit_max_copy(Integer limit_max_copy) {
		this.limit_max_copy = limit_max_copy;
	}
	public String getLimit_start_time() {
		return limit_start_time;
	}
	public void setLimit_start_time(String limit_start_time) {
		this.limit_start_time = limit_start_time;
	}
	public String getLimit_end_time() {
		return limit_end_time;
	}
	public void setLimit_end_time(String limit_end_time) {
		this.limit_end_time = limit_end_time;
	}
	public Integer getRest_copy() {
		return rest_copy;
	}
	public void setRest_copy(Integer rest_copy) {
		this.rest_copy = rest_copy;
	}
	public Integer getHasBuy_copy() {
		return hasBuy_copy;
	}
	public void setHasBuy_copy(Integer hasBuy_copy) {
		this.hasBuy_copy = hasBuy_copy;
	}
	public Integer getCarNums() {
		return carNums;
	}
	public void setCarNums(Integer carNums) {
		this.carNums = carNums;
	}
	public Integer getCarCanAdd() {
		return carCanAdd;
	}
	public void setCarCanAdd(Integer carCanAdd) {
		this.carCanAdd = carCanAdd;
	}
	public String getPackage_desc() {
		return package_desc;
	}
	public void setPackage_desc(String package_desc) {
		this.package_desc = package_desc;
	}
	public String getPromoitionName() {
		return promoitionName;
	}
	public void setPromoitionName(String promoitionName) {
		this.promoitionName = promoitionName;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getFullMoney() {
		return fullMoney;
	}
	public void setFullMoney(String fullMoney) {
		this.fullMoney = fullMoney;
	}
	public String getCutMoney() {
		return cutMoney;
	}
	public void setCutMoney(String cutMoney) {
		this.cutMoney = cutMoney;
	}
	

	public Integer getCart_id() {
		return cart_id;
	}
	public void setCart_id(Integer cart_id) {
		this.cart_id = cart_id;
	}
	public BigDecimal getDiscount_price() {
		return discount_price;
	}
	public void setDiscount_price(BigDecimal discount_price) {
		this.discount_price = discount_price;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public Integer getTotal_stock_copy() {
		return total_stock_copy;
	}
	public void setTotal_stock_copy(Integer total_stock_copy) {
		this.total_stock_copy = total_stock_copy;
	}
	public Integer getTotal_sales_copy() {
		return total_sales_copy;
	}
	public void setTotal_sales_copy(Integer total_sales_copy) {
		this.total_sales_copy = total_sales_copy;
	}
	public String getShare_explain() {
		return share_explain;
	}
	public void setShare_explain(String share_explain) {
		this.share_explain = share_explain;
	}
	public String getLabel_name() {
		return label_name;
	}
	public void setLabel_name(String label_name) {
		this.label_name = label_name;
	}
	public String getProduct_logo() {
		return product_logo;
	}
	public void setProduct_logo(String product_logo) {
		this.product_logo = product_logo;
	}
	public int getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(int productStatus) {
		this.productStatus = productStatus;
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
	
	public int getSingleCouponCount() {
		return singleCouponCount;
	}
	public void setSingleCouponCount(int singleCouponCount) {
		this.singleCouponCount = singleCouponCount;
	}
	public int getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(int couponCount) {
		this.couponCount = couponCount;
	}
	
	public Long getRedeemSpecId() {
		return redeemSpecId;
	}
	public void setRedeemSpecId(Long redeemSpecId) {
		this.redeemSpecId = redeemSpecId;
	}
	public String getRedeemDesc() {
		return redeemDesc;
	}
	public void setRedeemDesc(String redeemDesc) {
		this.redeemDesc = redeemDesc;
	}
	public BigDecimal getNeedIntegral() {
		return needIntegral;
	}
	public void setNeedIntegral(BigDecimal needIntegral) {
		this.needIntegral = needIntegral;
	}
	public BigDecimal getDeductibleAmount() {
		return deductibleAmount;
	}
	public void setDeductibleAmount(BigDecimal deductibleAmount) {
		this.deductibleAmount = deductibleAmount;
	}
	public Long getGoldDeductSpecId() {
		return goldDeductSpecId;
	}
	public void setGoldDeductSpecId(Long goldDeductSpecId) {
		this.goldDeductSpecId = goldDeductSpecId;
	}
	
}
