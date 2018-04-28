package com.kingleadsw.betterlive.model;

import java.math.BigDecimal;
import java.util.List;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 商品 实体类
 * 2017-03-08 by chen
 * @author Coder
 *
 */
public class Order  extends BasePO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer order_id;       //订单id
	private String order_code;      //订单编号
	private Integer order_type;      //订单类型；1：普通订单，2：预购订单
	private Integer pre_id;         //预购单id
	private Integer customer_id;     //订单所属用户id
	private Integer pay_type;        //支付方式，1：微信支付，2：支付宝支付  3:一卡通  4：礼品卡支付
	private String total_price;      //用户预计付款的总价格，消费总金额
	private String pay_money;        //用户实际付款的金额
	private String freight;          //运费
	private String message_info;     //留言 
	private Integer status;          //订单状态，0：已删除；1：待付款；2：待发货；3：已发货；4：待评价；5：已完成；6：已取消
	private Integer use_coupon;      //优惠券id 
	private String conpon_money;     //优惠券金额
	private String use_gift_card;   //使用礼品卡id
	private String gitf_card_money;  //礼品卡金额
	private String mobile;           //手机号码
	private String receiver;        //收货人 
	private String address;          //收货人详细地址
	private String order_time;       //下单时间
	private Integer is_comment;      //是否已评价，1：是；0：否
	private String send_time;//发货时间
	private Integer use_single_coupon_id; //使用单品红包的id
	private int groupJoinId;		//参团ID
	private String orderSource;		//订单来源
	private BigDecimal totalRedeemIntegral;		//金币优惠购扣除金币
	private BigDecimal totalGoldIntegral;		//普通金币扣除
	private BigDecimal totalRedeemMoney;		//金币优惠购抵扣金额
	private BigDecimal totalGoldMoney;		//普通抵扣金额
	
	//---------------- 非原始表字段  -----------------------
	private List<OrderProduct>  listOrderProduct;
	private String customer_name;
	private String trans_id; 	//支付流水号，供导出用
	//--------------------------------------------
	
	private String customerSource;		//用户来源
	//t_order_product表数据
	private Integer orderpro_id;         //订单商品id
	private Integer product_id;          //商品id
	private String product_name;         //商品名称
	private Integer spec_id;             //用户选择规格id，关联商品规格id
	private String spec_name;            //商品规格名称
	private String spec_img;			 //商品规格图片
	private Integer quantity;            //用户购买的数量
	private String price;                //价格 
	private Integer is_evaluate;         //是否已评价，1：是；0：否 
	private String activity_price;      //活动优惠价格
	private BigDecimal full_money;	//满减
	private Integer promotion_id;	//满减活动id
	private BigDecimal cut_money;	//减多少钱
	private Integer user_single_coupon_id;		//用户使用单品红包
	private BigDecimal coupon_money;			//优惠金额
	private String logistics_code;//快递单号
	private String company_code;  //要查询的快递公司代码
	private String send_time2;     //发货时间 
	private Integer sub_status;       //订单状态，0：已删除；1：待付款；2：待发货；3：待签收；4：待评价；5：已完成;6:系统取消;7:已退款 
	private String sub_order_code;//子订单编号
	private String product_status;      //商品状态  
	private int quantityTotal;	//购买数量
	private String discount_price;	//优惠额
	private String product_label;	//商品标签
	private int quantity_detail;	//订单详情记录数
	
	
	/**
	 * @return the orderpro_id
	 */
	public Integer getOrderpro_id() {
		return orderpro_id;
	}

	/**
	 * @param orderpro_id the orderpro_id to set
	 */
	public void setOrderpro_id(Integer orderpro_id) {
		this.orderpro_id = orderpro_id;
	}

	/**
	 * @return the product_id
	 */
	public Integer getProduct_id() {
		return product_id;
	}

	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	/**
	 * @return the product_name
	 */
	public String getProduct_name() {
		return product_name;
	}

	/**
	 * @param product_name the product_name to set
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	/**
	 * @return the spec_id
	 */
	public Integer getSpec_id() {
		return spec_id;
	}

	/**
	 * @param spec_id the spec_id to set
	 */
	public void setSpec_id(Integer spec_id) {
		this.spec_id = spec_id;
	}

	/**
	 * @return the spec_name
	 */
	public String getSpec_name() {
		return spec_name;
	}

	/**
	 * @param spec_name the spec_name to set
	 */
	public void setSpec_name(String spec_name) {
		this.spec_name = spec_name;
	}

	/**
	 * @return the spec_img
	 */
	public String getSpec_img() {
		return spec_img;
	}

	/**
	 * @param spec_img the spec_img to set
	 */
	public void setSpec_img(String spec_img) {
		this.spec_img = spec_img;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the is_evaluate
	 */
	public Integer getIs_evaluate() {
		return is_evaluate;
	}

	/**
	 * @param is_evaluate the is_evaluate to set
	 */
	public void setIs_evaluate(Integer is_evaluate) {
		this.is_evaluate = is_evaluate;
	}

	/**
	 * @return the activity_price
	 */
	public String getActivity_price() {
		return activity_price;
	}

	/**
	 * @param activity_price the activity_price to set
	 */
	public void setActivity_price(String activity_price) {
		this.activity_price = activity_price;
	}

	/**
	 * @return the full_money
	 */
	public BigDecimal getFull_money() {
		return full_money;
	}

	/**
	 * @param full_money the full_money to set
	 */
	public void setFull_money(BigDecimal full_money) {
		this.full_money = full_money;
	}

	/**
	 * @return the promotion_id
	 */
	public Integer getPromotion_id() {
		return promotion_id;
	}

	/**
	 * @param promotion_id the promotion_id to set
	 */
	public void setPromotion_id(Integer promotion_id) {
		this.promotion_id = promotion_id;
	}

	/**
	 * @return the cut_money
	 */
	public BigDecimal getCut_money() {
		return cut_money;
	}

	/**
	 * @param cut_money the cut_money to set
	 */
	public void setCut_money(BigDecimal cut_money) {
		this.cut_money = cut_money;
	}

	/**
	 * @return the user_single_coupon_id
	 */
	public Integer getUser_single_coupon_id() {
		return user_single_coupon_id;
	}

	/**
	 * @param user_single_coupon_id the user_single_coupon_id to set
	 */
	public void setUser_single_coupon_id(Integer user_single_coupon_id) {
		this.user_single_coupon_id = user_single_coupon_id;
	}

	/**
	 * @return the coupon_money
	 */
	public BigDecimal getCoupon_money() {
		return coupon_money;
	}

	/**
	 * @param coupon_money the coupon_money to set
	 */
	public void setCoupon_money(BigDecimal coupon_money) {
		this.coupon_money = coupon_money;
	}

	/**
	 * @return the logistics_code
	 */
	public String getLogistics_code() {
		return logistics_code;
	}

	/**
	 * @param logistics_code the logistics_code to set
	 */
	public void setLogistics_code(String logistics_code) {
		this.logistics_code = logistics_code;
	}

	/**
	 * @return the company_code
	 */
	public String getCompany_code() {
		return company_code;
	}

	/**
	 * @param company_code the company_code to set
	 */
	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	/**
	 * @return the send_time2
	 */
	public String getSend_time2() {
		return send_time2;
	}

	/**
	 * @param send_time2 the send_time2 to set
	 */
	public void setSend_time2(String send_time2) {
		this.send_time2 = send_time2;
	}

	/**
	 * @return the sub_status
	 */
	public Integer getSub_status() {
		return sub_status;
	}

	/**
	 * @param sub_status the sub_status to set
	 */
	public void setSub_status(Integer sub_status) {
		this.sub_status = sub_status;
	}

	/**
	 * @return the sub_order_code
	 */
	public String getSub_order_code() {
		return sub_order_code;
	}

	/**
	 * @param sub_order_code the sub_order_code to set
	 */
	public void setSub_order_code(String sub_order_code) {
		this.sub_order_code = sub_order_code;
	}

	/**
	 * @return the product_status
	 */
	public String getProduct_status() {
		return product_status;
	}

	/**
	 * @param product_status the product_status to set
	 */
	public void setProduct_status(String product_status) {
		this.product_status = product_status;
	}

	public Integer getOrder_id() {
		return order_id;
	}
	
	public Integer getUse_single_coupon_id() {
		return use_single_coupon_id;
	}
	
	public void setUse_single_coupon_id(Integer use_single_coupon_id) {
		this.use_single_coupon_id = use_single_coupon_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public Integer getOrder_type() {
		return order_type;
	}
	public void setOrder_type(Integer order_type) {
		this.order_type = order_type;
	}
	public Integer getPre_id() {
		return pre_id;
	}
	public void setPre_id(Integer pre_id) {
		this.pre_id = pre_id;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getPay_type() {
		return pay_type;
	}
	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}
	public String getTotal_price() {
		return total_price;
	}
	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}
	public String getPay_money() {
		return pay_money;
	}
	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getMessage_info() {
		return message_info;
	}
	public void setMessage_info(String message_info) {
		this.message_info = message_info;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getUse_coupon() {
		return use_coupon;
	}
	public void setUse_coupon(Integer use_coupon) {
		this.use_coupon = use_coupon;
	}
	public String getConpon_money() {
		return conpon_money;
	}
	public void setConpon_money(String conpon_money) {
		this.conpon_money = conpon_money;
	}
	public String getUse_gift_card() {
		return use_gift_card;
	}
	public void setUse_gift_card(String use_gift_card) {
		this.use_gift_card = use_gift_card;
	}
	public String getGitf_card_money() {
		return gitf_card_money;
	}
	public void setGitf_card_money(String gitf_card_money) {
		this.gitf_card_money = gitf_card_money;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}
	public Integer getIs_comment() {
		return is_comment;
	}
	public void setIs_comment(Integer is_comment) {
		this.is_comment = is_comment;
	}
	public List<OrderProduct> getListOrderProduct() {
		return listOrderProduct;
	}
	public void setListOrderProduct(List<OrderProduct> listOrderProduct) {
		this.listOrderProduct = listOrderProduct;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getTrans_id() {
		return trans_id;
	}
	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	public int getQuantityTotal() {
		return quantityTotal;
	}

	public void setQuantityTotal(int quantityTotal) {
		this.quantityTotal = quantityTotal;
	}

	public int getGroupJoinId() {
		return groupJoinId;
	}

	public void setGroupJoinId(int groupJoinId) {
		this.groupJoinId = groupJoinId;
	}

	public String getDiscount_price() {
		return discount_price;
	}

	public void setDiscount_price(String discount_price) {
		this.discount_price = discount_price;
	}

	public String getProduct_label() {
		return product_label;
	}

	public void setProduct_label(String product_label) {
		this.product_label = product_label;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public int getQuantity_detail() {
		return quantity_detail;
	}

	public void setQuantity_detail(int quantity_detail) {
		this.quantity_detail = quantity_detail;
	}

	public BigDecimal getTotalRedeemIntegral() {
		return totalRedeemIntegral;
	}

	public void setTotalRedeemIntegral(BigDecimal totalRedeemIntegral) {
		this.totalRedeemIntegral = totalRedeemIntegral;
	}

	public BigDecimal getTotalGoldIntegral() {
		return totalGoldIntegral;
	}

	public void setTotalGoldIntegral(BigDecimal totalGoldIntegral) {
		this.totalGoldIntegral = totalGoldIntegral;
	}

	public BigDecimal getTotalRedeemMoney() {
		return totalRedeemMoney;
	}

	public void setTotalRedeemMoney(BigDecimal totalRedeemMoney) {
		this.totalRedeemMoney = totalRedeemMoney;
	}

	public BigDecimal getTotalGoldMoney() {
		return totalGoldMoney;
	}

	public void setTotalGoldMoney(BigDecimal totalGoldMoney) {
		this.totalGoldMoney = totalGoldMoney;
	}
	
}
