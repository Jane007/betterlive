package com.kingleadsw.betterlive.model;


import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 商品评论 实体类
 * 2017-03-09  by chen
 */
public class Comment  extends BasePO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer comment_id;  		//商品评论ID
	private Integer order_id;           //订单ID
	private String order_code;          //订单编号
	private Integer customer_id;        //客户ID
	private Integer product_id;         //产品Id
	private String content;             //评论内容
	private Integer status;             //状态 1：未审核，2：审核通过，3：审核拒绝，4：已删除
	private String create_time;         //创建时间 
	private Integer replay_staff_id;    //管理员Id
	private String reply_msg;           //回复内容
	private String replay_time;         //回复时间
	private String content_imgs;        //评论图片
	private Integer is_custom_service;	//是否客服评论 	
	private Customer customer;         //客户信息
//	private Admin admin;               //回复评论的客服信息
//	private Reply reply;               //回复信息 
	private int parent_id;		//父节点
	private int root_id;		//根节点
	private int praise_count;	//点赞数
	
	private String product_name;       //商品名称
	private String specName;			//规格名称
	private Integer reply_count; //回复数
	private Integer is_praise; //是否已点赞 （点赞ID  判断大于0）
	private String replyerName;	//被评论人
	private int replyerId;	//被评论人ID
	private String mobile; //联系方式
	
	//------------------------------
	public Integer getComment_id() {
		return comment_id;
	}
	public void setComment_id(Integer comment_id) {
		this.comment_id = comment_id;
	}
	public Integer getOrder_id() {
		return order_id;
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
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Integer getReplay_staff_id() {
		return replay_staff_id;
	}
	public void setReplay_staff_id(Integer replay_staff_id) {
		this.replay_staff_id = replay_staff_id;
	}
	public String getReply_msg() {
		return reply_msg;
	}
	public void setReply_msg(String reply_msg) {
		this.reply_msg = reply_msg;
	}
	public String getReplay_time() {
		return replay_time;
	}
	public void setReplay_time(String replay_time) {
		this.replay_time = replay_time;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
//	public Admin getAdmin() {
//		return admin;
//	}
//	public void setAdmin(Admin admin) {
//		this.admin = admin;
//	}
//	public Reply getReply() {
//		return reply;
//	}
//	public void setReply(Reply reply) {
//		this.reply = reply;
//	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getContent_imgs() {
		return content_imgs;
	}
	public void setContent_imgs(String content_imgs) {
		this.content_imgs = content_imgs;
	}
	public Integer getIs_custom_service() {
		return is_custom_service;
	}
	public void setIs_custom_service(Integer is_custom_service) {
		this.is_custom_service = is_custom_service;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public int getRoot_id() {
		return root_id;
	}
	public void setRoot_id(int root_id) {
		this.root_id = root_id;
	}
	public int getPraise_count() {
		return praise_count;
	}
	public void setPraise_count(int praise_count) {
		this.praise_count = praise_count;
	}
	public Integer getReply_count() {
		return reply_count;
	}
	public void setReply_count(Integer reply_count) {
		this.reply_count = reply_count;
	}
	public Integer getIs_praise() {
		return is_praise;
	}
	public void setIs_praise(Integer is_praise) {
		this.is_praise = is_praise;
	}
	public String getReplyerName() {
		return replyerName;
	}
	public void setReplyerName(String replyerName) {
		this.replyerName = replyerName;
	}
	public int getReplyerId() {
		return replyerId;
	}
	public void setReplyerId(int replyerId) {
		this.replyerId = replyerId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	
	
}
