package com.kingleadsw.betterlive.model;


import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 商品评论 实体类
 */
public class SpecialComment  extends BasePO{
	
	private static final long serialVersionUID = 723583942591064855L;
	
	private Integer commentId;  		//评论ID
	private Integer customerId;        //客户ID
	private String content;             //评论内容
	private Integer status;             //状态 1：未审核，2：审核通过，3：审核拒绝
	private String createTime;         //创建时间 
	private String contentImgs;        //评论图片
	private Integer isCustomService;	//是否客服评论	
	private int parentId;		//父节点
	private int rootId;		//根节点
	private int specialId;	//专题ID
	private int specialType;	//专题类型: 1 限时活动(预留), 2 限量抢购(预留), 3 团购活动(预留) , 4 美食教程 ,5 文章（精选和动态）
	private String opinion; //评论意见
	
	
	//----------------   非原始表字段   --------------
	private Customer customerVo;         //客户信息
	private String author;		//作者名字
	private String articleTitle; //标题
	private int specialTypeChild;//判断是精选还是动态(0,1)
	private String[] commentArrayImgs ;      //客户名称
	private Integer replyCount; //回复数
	private Integer isPraise; //是否已点赞 1是 0否
	private int praiseCount;	//点赞数
	private String replyerName;	//被评论人
	private int replyerId;	//被评论人Id
	
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getContentImgs() {
		return contentImgs;
	}
	public void setContentImgs(String contentImgs) {
		this.contentImgs = contentImgs;
	}
	public Integer getIsCustomService() {
		return isCustomService;
	}
	public void setIsCustomService(Integer isCustomService) {
		this.isCustomService = isCustomService;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getRootId() {
		return rootId;
	}
	public void setRootId(int rootId) {
		this.rootId = rootId;
	}
	public int getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}
	public Customer getCustomerVo() {
		return customerVo;
	}
	public void setCustomerVo(Customer customerVo) {
		this.customerVo = customerVo;
	}
	public String[] getCommentArrayImgs() {
		return commentArrayImgs;
	}
	public void setCommentArrayImgs(String[] commentArrayImgs) {
		this.commentArrayImgs = commentArrayImgs;
	}
	public Integer getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}
	public Integer getIsPraise() {
		return isPraise;
	}
	public void setIsPraise(Integer isPraise) {
		this.isPraise = isPraise;
	}
	public int getSpecialId() {
		return specialId;
	}
	public void setSpecialId(int specialId) {
		this.specialId = specialId;
	}
	public int getSpecialType() {
		return specialType;
	}
	public void setSpecialType(int specialType) {
		this.specialType = specialType;
	}
	
	public int getSpecialTypeChild() {
		return specialTypeChild;
	}
	public void setSpecialTypeChild(int specialTypeChild) {
		this.specialTypeChild = specialTypeChild;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
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
	
}
