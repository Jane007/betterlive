package com.kingleadsw.betterlive.controller.app.comment;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.biz.CommentManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.biz.SysDictManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.constant.OrderConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.page.PageView;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CommentVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.OrderProductVo;
import com.kingleadsw.betterlive.vo.OrderVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SpecialVo;
import com.kingleadsw.betterlive.vo.SysDictVo;

/**
 * 商品评价
 * 2017-05-10 by ltp
 */
@Controller
@RequestMapping(value = "/app/productcomment")
public class AppProductCommentController extends AbstractWebController {
	
	private static Logger logger = Logger.getLogger(AppProductCommentController.class);
	
	@Autowired
	private CommentManager commentManager;
	@Autowired
	private OrderProductManager orderProductManager;
	@Autowired
	private OrderManager orderManager;
	@Autowired
	private SysDictManager sysDictManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private ActivityProductManager activityProductManager;
	@Autowired
	private SpecialMananger specialManager;
	@Autowired
	private MessageManager messageManager;
	
	/**
	 * 根据商品id查询商品评论，分为全部和有图
	 */
    @RequestMapping(value="/list")
    @ResponseBody
	public Map<String,Object> list(HttpServletRequest req, HttpServletResponse resp){
    	logger.info("/app/productcomment/list  begin");
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		String productId = pd.getString("productId");
		if (StringUtil.isNull(productId)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("商品信息不能为空");
		}
		
		String token = pd.getString("token");  	
		int customerId = 0;
		//用户标识
		if (StringUtil.isNotNull(token)) {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if(customer != null){
				customerId = customer.getCustomer_id();
			}
		}
		
		pd.put("parentFlag", 1);	//评论（不包括回复）
		pd.put("status","2"); //评论通过
		pd.put("currentId", customerId);
		String hasImg = pd.getString("hasImg");
		if ("1".equals(hasImg)) {  //全部评论
			
			List<CommentVo> commentList = commentManager.queryCommentInfoListPage(pd);
			resultMap.put("commentList", commentList);
		} else {   //有图片评论
			PageData imgPd = new PageData();
			imgPd.put("productId", pd.getString("productId"));
			imgPd.put("status","2");
			imgPd.put("parentFlag", 1);	//评论（不包括回复）
			imgPd.put("contentImg","1");
			imgPd.put("pageView", ((PageView)pd.get("pageView")));
			imgPd.put("currentId", customerId);
			List<CommentVo> commentList = commentManager.queryCommentInfoListPage(imgPd);
			resultMap.put("commentList", commentList);
		}
		
		int totalCount = commentManager.queryCommentCountByPid(pd);  //商品评价总数
		resultMap.put("total", totalCount);
		
		pd.put("contentImg","1");
		int imgCount = commentManager.queryCommentCountByPid(pd);  //带图商品评价总数
		resultMap.put("imgTotal", imgCount);
		
		logger.info("/app/productcomment/list end");
		return CallBackConstant.SUCCESS.callback(resultMap);
	}
	
	
	
	/**
	 *  评价订单商品
	 */
	@RequestMapping(value = "/toCommentProduct")
	@ResponseBody
    public Map<String,Object> toCommentProduct(HttpServletRequest request) throws Exception {  
    	PageData pd=this.getPageData();
    	CustomerVo customerVo = Constants.getCustomer(request);
    	
    	String  productId = (String) pd.get("productId");
    	//订单编号
		String orderCode=null;
		String orderId=null;
		OrderVo order=null;
		logger.info("用户id为"+customerVo.getCustomer_id()+"评价订单商品开始");
		try {
			orderCode = pd.getString("orderCode");
			orderId = pd.getString("orderId");
			if(null==orderCode || "".equals(orderCode) ||  null==orderId || "".equals(orderId) ){
				logger.info(" 订单ID ："+orderCode+" 编号:"+orderCode);  
				throw new Exception();
			}else {
				pd.put("customerId",String.valueOf(customerVo.getCustomer_id()));
				
				PageData pageData=new PageData();
				pageData.put("dictCode","SYSTEM_ORDER_EXPIRE");
				SysDictVo sysDict = sysDictManager.selectByCode(pageData);
				if(null !=sysDict&& sysDict.getStatus()==1 ){
					pd.put("expiretime",sysDict.getDictValue());
				}
				
				order=orderManager.findOrder(pd);
				
				//上方法有问题，按道理应该评论那个商品，就显示那个商品，但是这里是将所有的订单商品查出。
				//由于上方法可能其他其他地方会用到，所有不修改底层方法，选择剃除相应的商品
				//弊端：可能消耗一定的性能，优势：但对现有功能影响较小。
				List<OrderProductVo> listOrderProductVo = order.getListOrderProductVo();
				for(OrderProductVo op : listOrderProductVo){
					if(op.getProduct_id() == Integer.parseInt(productId)){
						List<OrderProductVo> op1 = new ArrayList<OrderProductVo>();
						op1.add(op);
						order.setListOrderProductVo(op1);
					}
				}
			}
		} catch (Exception e) {
			logger.error("/app/productcomment/toCommentProduct 进入订单支付详情页面异常 :  订单ID或编号为空! ", e);
			return CallBackConstant.FAILED.callback();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderInfo", order);
		map.put("productId", productId);
		
		return CallBackConstant.SUCCESS.callback(map);
    }
	
	
	
	
	/**
	 * 保存商品评价
	 * @param httpRequest
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/save",method={RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> save(HttpServletRequest httpRequest, HttpServletResponse response) {
		logger.info("/app/comment/saveComment begin");
		PageData pd = this.getPageData();
		String orderId = pd.getString("orderId");
		String productId = pd.getString("productId");
		String imgUrl = pd.getString("urlimages");
		String content = pd.getString("content");
		logger.info("订单id：" + orderId);
		logger.info("商品id：" + productId);
		logger.info("评论图片：" + imgUrl);
		logger.info("评论内容：" + content);
		
		if (!StringUtil.isIntger(orderId)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("订单id不能为空");
		}
		
		String token = pd.getString("token");
		if (StringUtil.isNull(token)) {
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customerVo = customerManager.queryCustomerByToken(token);
		if (null == customerVo) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		if (StringUtil.isNull(content)) {
			logger.error("用户评价商品，评价内容为空");
			return CallBackConstant.PARAMETER_ERROR.callbackError("评论内容不能为空");
		}
		try {
			content = URLDecoder.decode(content, "UTF-8");
			OrderVo orders = orderManager.findOrder(pd);
			int result = 0;
			if (orders != null) {
				if (!customerVo.getCustomer_id().equals(orders.getCustomer_id())) {
					logger.error("评价商品的用户和下单的用户信息不匹配");
					return CallBackConstant.FAILED.callbackError("评价商品的用户和下单的用户信息不匹配");
				}
				
				CommentVo comment = new CommentVo();
				comment.setCustomer_id(orders.getCustomer_id());
				comment.setOrder_id(orders.getOrder_id());
				comment.setOrder_code(orders.getOrder_code());
				comment.setContent(content);
				comment.setProduct_id(Integer.parseInt(productId));
				comment.setIs_custom_service(0);
				
				if(StringUtil.isNotNull(imgUrl)){
					comment.setContent_imgs(imgUrl);
				}
				result = commentManager.insertComment(comment);
				
				if (result > 0) {
					PageData pds=new PageData();
					pds.put("isEvaluate","1");
					pds.put("productId",Integer.parseInt(productId));
					pds.put("orderId",orders.getOrder_id());
					pds.put("status", OrderConstants.STATUS_ORDER_FINSH);
					orderProductManager.editOrderProductByPdId(pds);     //根据订单ID修改评论订单商品状态
					
					//这里有问题，应该是所有的订单商品评论状态完成才修改。
					//应该要再进行一个查询，
					PageData pds1 = new PageData();
					pds1.put("orderId", orders.getOrder_id());
					pds1.put("isEvaluate", "0");
					List<OrderProductVo> list = orderProductManager.findListOrderProduct(pds1);
					if(null == list){
						//根据订单ID与客户ID修改订单评论状态
						pds.put("status", OrderConstants.STATUS_ORDER_FINSH);  //完成
						pds.put("customerId", orders.getCustomer_id());
						orderManager.editOder(pds);   
					}
				} else {
					return CallBackConstant.FAILED.callback();
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("/app/order/saveComment --error", e);
		}
		
		return CallBackConstant.SUCCESS.callback();
	}
	
	/**
	 *  app1.1评论详情页
	 */
	@RequestMapping(value = "/showCommentDetail")
	@ResponseBody
    public Map<String,Object> showCommentDetail(HttpServletRequest request) throws Exception {  
    	PageData pd=this.getPageData();

    	String token = pd.getString("token");  	
		int customerId = 0;
		
		//用户标识
		if(StringUtil.isNull(pd.getString("commentId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("商品评论ID为空");
		}else {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if(customer != null){
				customerId = customer.getCustomer_id();
			}
		}
		
		try {
			//评论内容
			PageData cpd = new PageData();
			cpd.put("commentId", pd.getInteger("commentId"));
			cpd.put("currentId", customerId);
			CommentVo commentVo = this.commentManager.queryCommentListByCommId(cpd);
			if(commentVo == null){
				return CallBackConstant.FAILED.callbackError("评论不存在");
			}
			
			ProductVo productVo = this.productManager.selectByPrimaryKey(commentVo.getProduct_id());
			PageData specpd = new PageData();
			specpd.put("productId", productVo.getProduct_id());
			specpd.put("status", 1);
			specpd.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			specpd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			SpecialVo specialVo = specialManager.queryOneByProductId(specpd);
			
			specpd.clear();
			specpd.put("productId", productVo.getProduct_id());
			specpd.put("proStatus", 1);
			specpd.put("specStatus", 1);
			if(specialVo != null){
				specpd.put("activityId", specialVo.getSpecialId());
			}
			ProductSpecVo specVo = this.productSpecManager.queryProductSpecByOption(specpd);
			
			productVo.setPrice(specVo.getSpec_price());
			if (specVo.getDiscount_price() != null && specVo.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
				productVo.setDiscountPrice(specVo.getDiscount_price()+"");
			}
			if(specVo.getTotal_sales_copy() != null){
				productVo.setSalesVolume(specVo.getTotal_sales_copy().intValue());
			}else{
				productVo.setSalesVolume(0);
			}
			
			if(specialVo != null){
				productVo.setActivityType(specialVo.getSpecialType());
				productVo.setIsActivity("NO");
				if(StringUtil.isNotNull(specVo.getActivity_price()) && new BigDecimal(specVo.getActivity_price()).compareTo(BigDecimal.ZERO) >= 0){
					productVo.setActivityPrice(specVo.getActivity_price());
					productVo.setIsActivity("YES");
				}
				productVo.setActivity_id(specialVo.getSpecialId());
			}
			
			PageData oppd = new PageData();
			oppd.put("orderId", commentVo.getOrder_id());
			oppd.put("productId", commentVo.getProduct_id());
			OrderProductVo opvo = this.orderProductManager.queryOne(specpd);
			String commentProductDesc = opvo.getProduct_name() + opvo.getSpec_name();
			
			PageData replypd = new PageData();
			replypd.put("rootId", pd.getString("commentId"));
			replypd.put("status", 2);
			replypd.put("currentId", customerId);
			List<CommentVo> replyList = this.commentManager.queryCommentInfoListPage(replypd);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("commentVo", commentVo);
			map.put("productVo", productVo);
			map.put("replyList", replyList);
			map.put("commentProductDesc", commentProductDesc);
			return CallBackConstant.SUCCESS.callback(map);
		} catch (Exception e) {
			logger.error("/app/order/showCommentDetail --error", e);
			return CallBackConstant.FAILED.callback();
		}
		
    }
	
	
	/**
	 *  app1.1回复评论
	 */
	@RequestMapping(value = "/replyComment")
	@ResponseBody
    public Map<String,Object> replyComment(HttpServletRequest request) throws Exception {  
		PageData pd=this.getPageData();

    	String token = pd.getString("token");  	
		int customerId = 0;
		
		//用户标识
		if(StringUtil.isNull(token)){
			return CallBackConstant.PARAMETER_ERROR.callbackError("用户信息为空");
		}else{
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if(customer == null){
				return CallBackConstant.FAILED.callbackError("用户信息不存在");
			}
			customerId = customer.getCustomer_id();
		}
		
		if(StringUtil.isNull(pd.getString("commentId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("商品回复的评论ID为空");
		}
		
//		if(StringUtil.isNull(pd.getString("rootId"))){
//			return CallBackConstant.PARAMETER_ERROR.callbackError("商品评论ID为空");
//		}
		if(StringUtil.isNull(pd.getString("content"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("商品评论内容为空");
		}
	
		try {
			PageData cpd = new PageData();
			cpd.put("commentId", pd.getInteger("commentId"));
			CommentVo commentVo = this.commentManager.queryCommentListByCommId(cpd);
			if(commentVo == null){
				return CallBackConstant.FAILED.callbackError("被回复的内容不存在");
			}
			
			int rootId = 0;
			if(pd.getInteger("rootId") != null){
				rootId = pd.getInteger("rootId");
			}else if (commentVo.getRoot_id() > 0){
				rootId = commentVo.getRoot_id();
			}else{
				rootId = commentVo.getComment_id();
			}
			
			CommentVo replyVo = new CommentVo();
			replyVo.setRoot_id(rootId);
			replyVo.setParent_id(commentVo.getComment_id());
			replyVo.setIs_custom_service(0);
			replyVo.setOrder_id(commentVo.getOrder_id());
			replyVo.setOrder_code(commentVo.getOrder_code());
			replyVo.setCustomer_id(customerId);
			replyVo.setProduct_id(commentVo.getProduct_id());
			replyVo.setContent(pd.getString("content"));
			
			int count = this.commentManager.insertComment(replyVo);
			if(count > 0){
				MessageVo msgVo = new MessageVo();
				msgVo.setMsgType(MessageVo.MSGTYPE_FRIENDS);
				msgVo.setSubMsgType(9);
				msgVo.setMsgTitle("您有一条评论消息");
				msgVo.setMsgDetail(pd.getString("content"));
				msgVo.setIsRead(0);
				msgVo.setCustomerId(commentVo.getCustomer_id());
				msgVo.setObjId(replyVo.getComment_id());
				messageManager.insert(msgVo);
				
				return CallBackConstant.SUCCESS.callback();
			}
			return CallBackConstant.FAILED.callback();
		} catch (Exception e) {
			logger.error("/app/productcomment/replyComment ----error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	
	/**
	 *  app1.1消息页我的评论回复详情
	 */
	@RequestMapping(value = "/messageCommentDetail")
	@ResponseBody
    public Map<String,Object> messageCommentDetail(HttpServletRequest request) throws Exception {  
    	PageData pd=this.getPageData();

    	String token = pd.getString("token");  	
		String customerId = "";
		
		//用户标识
		if(StringUtil.isNull(pd.getString("commentId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("商品评论ID为空");
		}else {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if(customer != null){
				customerId = customer.getCustomer_id()+"";
			}
		}
		
		try {
			//评论内容
			PageData cpd = new PageData();
			cpd.put("commentId", pd.getInteger("commentId"));
			CommentVo replyCommentVo = this.commentManager.queryCommentListByCommId(cpd);
			if(replyCommentVo == null){
				return CallBackConstant.FAILED.callbackError("评论不存在");
			}
			
			cpd.clear();
			cpd.put("commentId", replyCommentVo.getRoot_id());
			cpd.put("currentId", customerId);
			CommentVo commentVo = this.commentManager.queryCommentListByCommId(cpd);
			if(commentVo == null){
				return CallBackConstant.FAILED.callbackError("评论不存在");
			}
			
			ProductVo productVo = this.productManager.selectByPrimaryKey(commentVo.getProduct_id());
			PageData specpd = new PageData();
			specpd.put("productId", productVo.getProduct_id());
			specpd.put("status", 1);
			specpd.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			specpd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			SpecialVo specialVo = specialManager.queryOneByProductId(specpd);
			
			specpd.clear();
			specpd.put("productId", productVo.getProduct_id());
			specpd.put("proStatus", 1);
			specpd.put("specStatus", 1);
			if(specialVo != null){
				specpd.put("activityId", specialVo.getSpecialId());
			}
			ProductSpecVo specVo = this.productSpecManager.queryProductSpecByOption(specpd);
			productVo.setPrice(specVo.getSpec_price());
			if(specVo.getDiscount_price() != null && specVo.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
				productVo.setDiscountPrice(specVo.getDiscount_price()+"");
			}
			
			if(specVo.getTotal_sales_copy() != null){
				productVo.setSalesVolume(specVo.getTotal_sales_copy());
			}else{
				productVo.setSalesVolume(0);
			}
			
			if(specialVo != null){

				productVo.setActivityType(specialVo.getSpecialType());
				productVo.setIsActivity("NO");
				if(StringUtil.isNotNull(specVo.getActivity_price()) && new BigDecimal(specVo.getActivity_price()).compareTo(BigDecimal.ZERO) >= 0){
					productVo.setActivityPrice(specVo.getActivity_price());
					productVo.setIsActivity("YES");
				}
				productVo.setActivity_id(specialVo.getSpecialId());
			}
			
			PageData oppd = new PageData();
			oppd.put("orderId", commentVo.getOrder_id());
			oppd.put("productId", commentVo.getProduct_id());
			OrderProductVo opvo = this.orderProductManager.queryOne(specpd);
			String commentProductDesc = opvo.getProduct_name() + opvo.getSpec_name();
			
			PageData replypd = new PageData();
			replypd.put("rootId", commentVo.getComment_id());
			replypd.put("status", 2);
			replypd.put("currentId", customerId);
			List<CommentVo> replyList = this.commentManager.queryCommentInfoListPage(replypd);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("commentVo", commentVo);
			map.put("replyList", replyList);
			map.put("productVo", productVo);
			map.put("commentProductDesc", commentProductDesc);
			return CallBackConstant.SUCCESS.callback(map);
		} catch (Exception e) {
			logger.error("/app/productcomment/messageCommentDetail --error", e);
			return CallBackConstant.FAILED.callback();
		}
		
    }
	
	/**
	 *  app1.2.4商品评论回复详情列表
	 */
	@RequestMapping(value = "/productMessageReplyDetails")
	@ResponseBody
    public Map<String,Object> productMessageReplyDetails(HttpServletRequest request) throws Exception {  
    	PageData pd=this.getPageData();

    	String token = pd.getString("token");  	
		int customerId = 0;
		
		//用户标识
		if(StringUtil.isNull(pd.getString("rootId"))){
			return CallBackConstant.PARAMETER_ERROR.callbackError("商品评论ID为空");
		}
		
		if(StringUtil.isNull(pd.getString("token"))){
			return CallBackConstant.TOKEN_ERROR.callback();
		}
		
		CustomerVo customer = customerManager.queryCustomerByToken(token);
		if(customer == null){
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		customerId = customer.getCustomer_id();
		
		try {
			//评论内容
			CommentVo commentVo = this.commentManager.queryCommentById(pd.getInteger("rootId"));
			if(commentVo == null || commentVo.getStatus().intValue() != 2){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("评论不存在");
			}
			
			pd.put("status", 2);
			pd.put("currentId", customerId);
			List<CommentVo> replyList = this.commentManager.queryCommentInfoListPage(pd);
			if(replyList == null){
				replyList = new ArrayList<CommentVo>();
			}
			return CallBackConstant.SUCCESS.callback(replyList);
		} catch (Exception e) {
			logger.error("/app/productcomment/productMessageReplyDetails --error", e);
			return CallBackConstant.FAILED.callback();
		}
		
    }
	
}
