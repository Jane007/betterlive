package com.kingleadsw.betterlive.controller.wx.comment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.biz.CommentManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.common.ImageUpload;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.constant.OrderConstants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
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

/**
 * 微信端下单
 * 2017-03-18 by chen
 */
@Controller
@RequestMapping(value = "/weixin/productcomment")
public class WxProductCommentController extends AbstractWebController{
	
	private static Logger logger = Logger.getLogger(WxProductCommentController.class);
	
	@Autowired
	private CommentManager commentManager;
	@Autowired
	private OrderProductManager orderProductManager;
	@Autowired
	private OrderManager orderManager;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private ActivityProductManager activityProductManager;
	@Autowired
	private SpecialMananger specialManager;
	@Autowired
	private MessageManager messageManager;
	
	/**
	 * 单个商品查询全部评论
	 */
    @RequestMapping(value="/findList")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("/weixin/comment/wx_product_list_comment");
		PageData pd=this.getPageData();
		String productId=pd.getString("productId");
		String prepurchase=pd.getString("prepurchase");
		if(null == prepurchase|| "".equals(prepurchase)){
			prepurchase = "0";
		}
		
		pd.put("parentFlag", 1);	//评论（不包括回复）
		pd.put("status","2"); //评论通过
		int totalCount=commentManager.queryCommentCountByPid(pd);         //商品评价总数
		
		pd.put("contentImg","1"); 
		int count=commentManager.queryCommentCountByPid(pd);              //带图商品评价总数
		
		CustomerVo customer=Constants.getCustomer(req);
		int customerId = 0;
		if(customer != null && customer.getCustomer_id() != null){
			customerId = customer.getCustomer_id();
		}
		
		mv.addObject("productId",productId);
		mv.addObject("prepurchase",prepurchase);       //查看是否为预购商品详情页面 查看的用户评论
		mv.addObject("totalCount",totalCount);
		mv.addObject("totalImg",count);
		mv.addObject("type", pd.getString("type"));	//1普通商品详情,2限量抢购,3团购
		mv.addObject("specialId", pd.getString("specialId"));
		mv.addObject("customerId", customerId);
		return  mv;
	}
	
	
	/**
	 * 查询单个商品全部评论
	 */                     
	@RequestMapping(value="/queryProductCommentAllJson")
	@ResponseBody
	public String queryOrderAllJson(HttpServletRequest req, HttpServletResponse resp){
		JSONObject json = new JSONObject();
		PageData pd=this.getPageData();
		pd.put("status","2"); //评论通过审核
		
		List<CommentVo> listComment = null;
		try {
			CustomerVo customer=Constants.getCustomer(req);
			int customerId = 0;
			if(customer != null && customer.getCustomer_id() != null){
				customerId = customer.getCustomer_id();
			}
			
			pd.put("parentFlag", 1);	//评论（不包括回复）
			pd.put("currentId", customerId);
			listComment = commentManager.queryCommentInfoListPage(pd);
			json.put("result", "succ");
			json.put("data",listComment);
			json.put("pageInfo", pd.get("pageView"));
		} catch (Exception e) {
			logger.error("weixin/productcomment/queryProductCommentAllJson ---error", e);
			
			json.put("result", "exec");
		}
		
		return json.toString();
	}
	
	
	/**
	 * 保存商品评价
	 * @param httpRequest
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/saveComment",method={RequestMethod.POST})
	@ResponseBody
	public String saveComment(HttpServletRequest httpRequest, HttpServletResponse response) {
		logger.info("/weixin/order/saveComment begin");
		//获得文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)httpRequest;
		String orderId = multipartRequest.getParameter("orderId");
		String orderCode = multipartRequest.getParameter("orderCode");
		String productId = multipartRequest.getParameter("productId");
		String url = multipartRequest.getParameter("urlimages");
		String content = multipartRequest.getParameter("textval");
		//MultipartFile multipartFiles = multipartRequest.getFile("filepic");
		String filename=null;
		/*if(multipartFiles!=null){ 
			filename = UUID.randomUUID().toString().replaceAll("-", "")+".png";
		}*/
		//文件保存目录路径  
		/*String savePath = WebConstant.UPLOAT_ROOT_PATH_IMAGE_ARTICLE;
		//创建文件夹  
		/*File dirFile = new File(savePath);  
		if (!dirFile.exists()) {  
			dirFile.mkdirs();  
		}  */
		/*try{
			SaveFileFromInputStream(multipartFiles.getInputStream(),savePath, filename);
		}catch(Exception e){
			logger.error("评论图片上传失败", e);
		}*/
		//String url = WebConstant.MAIN_SERVER + File.separator + "images" + File.separator + filename;
		PageData pd = new PageData();
		pd.put("orderCode", orderCode);
		OrderVo orders = orderManager.findOrder(pd);
		int result = 0;
		if (orders != null) {
			
			CommentVo comment = new CommentVo();
			
			comment.setCustomer_id(orders.getCustomer_id());
			comment.setOrder_id(orders.getOrder_id());
			comment.setOrder_code(orders.getOrder_code());
			comment.setContent(content);
			comment.setProduct_id(Integer.parseInt(productId));
			if(StringUtil.isNotNull(url)){
			  String  url1 = url.substring(0,url.length()-1);
			  comment.setContent_imgs(url1);
			}
			result =commentManager.insertComment(comment);
			
			PageData pds=new PageData();
			pds.put("isEvaluate","1");
			pds.put("productId",Integer.parseInt(productId));
		//	for (int i = 0; i < orders.getListOrderProductVo().size(); i++) {
		//		comment.setProduct_id(orders.getListOrderProductVo().get(i).getProduct_id());
				pds.put("orderId",orders.getOrder_id());
				pds.put("status", OrderConstants.STATUS_ORDER_FINSH);
				orderProductManager.editOrderProductByPdId(pds);     //根据订单ID修改评论订单商品状态
		//	}
			
			//这里有问题，应该是所有的订单商品评论状态完成才修改。
			//应该要再进行一个查询，
				PageData pds1=new PageData();
				pds1.put("orderId", orders.getOrder_id());
				pds1.put("isEvaluate", "0");
				List<OrderProductVo> list = orderProductManager.findListOrderProduct(pds1);
				if(null == list){
					//根据订单ID与客户ID修改订单评论状态
					pds.put("status",OrderConstants.STATUS_ORDER_FINSH);  //完成
					pds.put("customerId",String.valueOf(orders.getCustomer_id()));
					orderManager.editOder(pds);   
				}
			/*   	
			 * pds.put("status","5");  //完成
			pds.put("customerId",String.valueOf(orders.getCustomer_id()));
			orderManager.editOder(pds);   
		    */			
		}
		
		JSONObject json=new JSONObject();
		json.put("result", "succ");
		json.put("msg", "评论成功");
		logger.info("--->结束调用/weixin/order/saveComment");
		return json.toString();
	}
	
	  public void SaveFileFromInputStream(HttpServletRequest request, HttpServletResponse response) throws IOException{
	    	 request.setCharacterEncoding("UTF-8");  
	         response.setContentType("text/html; charset=UTF-8");  
	         PrintWriter out = response.getWriter();  
	         
	    	 //文件保存目录路径  
	        String savePath = WebConstant.UPLOAT_ROOT_PATH;
	        
	        //String savePath = this.getServletContext().getRealPath("/") + configPath;  
	          
	        // 临时文件目录   
	        String tempPath = this.getServletContext().getRealPath("/") ;  
	          
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");  
	        String ymd = sdf.format(new Date());  
	        savePath += "/" + ymd + "/";  
	        //创建文件夹  
	        File dirFile = new File(savePath);  
	        if (!dirFile.exists()) {  
	            dirFile.mkdirs();  
	        }  
	          
	        tempPath += "/" + ymd + "/";  
	        //创建临时文件夹  
	        File dirTempFile = new File(tempPath);  
	        if (!dirTempFile.exists()) {  
	            dirTempFile.mkdirs();  
	        }  
	          
	        DiskFileItemFactory  factory = new DiskFileItemFactory();  
	        factory.setSizeThreshold(20 * 1024 * 1024); //设定使用内存超过5M时，将产生临时文件并存储于临时目录中。     
	        factory.setRepository(new File(tempPath)); //设定存储临时文件的目录。     
	        ServletFileUpload upload = new ServletFileUpload(factory);  
	        upload.setHeaderEncoding("UTF-8");  
	        try {  
	            List items = upload.parseRequest(request);  
	            Iterator itr = items.iterator();  
	              
	            while (itr.hasNext())   
	            {  
	                FileItem item = (FileItem) itr.next();  
	                String fileName = item.getName();  
	                long fileSize = item.getSize();  
	                if (!item.isFormField()) {  
	                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();  
	                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  
	                    String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;  
	                    try{  
	                        File uploadedFile = new File(savePath, newFileName);  
	                        
	                        OutputStream os = new FileOutputStream(uploadedFile);  
	                        InputStream is = item.getInputStream();  
	                        byte buf[] = new byte[1024];//可以修改 1024 以提高读取速度  
	                        int length = 0;    
	                        while( (length = is.read(buf)) > 0 ){    
	                            os.write(buf, 0, length);    
	                        }    
	                        //关闭流    
	                        os.flush();  
	                        os.close();    
	                        is.close();    
	                        out.print(savePath+"/"+newFileName);  
	                    }catch(Exception e){  
	                    	logger.error("/weixin/productcomment/SaveFileFromInputStream --error", e);
	                    }  
	                }         
	            }   
	              
	        } catch (FileUploadException e) {  
	           logger.error("/weixin/productcomment/SaveFileFromInputStream --error", e);
	        }  
	        out.flush();  
	        out.close(); 
			
	    }       
		
	    
	    
	    public void SaveFileFromInputStream(InputStream stream,String path,String[] filesname) throws IOException{
	    	FileOutputStream fs=null;
	    	
	    	for (int i = 0; i < filesname.length; i++) {
	    		if(null ==filesname[i] || "".equals(filesname[i])){
	    			continue;
	    		}
	   // 		fs=new FileOutputStream( path + "/"+ filesname);
	    		fs=new FileOutputStream( path + "/"+ filesname[i]);
	            byte[] buffer =new byte[1024*1024];
	            int byteread = 0; 
	            while ((byteread=stream.read(buffer))!=-1)
	            {
	               fs.write(buffer,0,byteread);
	               fs.flush();
	            } 
			}
	    	
	        fs.close();
	        stream.close();      
	    }  
	    
		 /**
		  * 保存上传的图片
		  * @param stream
		  * @param path
		  * @param filename
		  * @throws IOException
		  */
		 private void SaveFileFromInputStream(InputStream stream,String path,String filename) throws IOException{      
	        FileOutputStream fs=new FileOutputStream( path + File.separator + filename);
	        byte[] buffer =new byte[1024*1024];
	        int byteread = 0; 
	        while ((byteread=stream.read(buffer))!=-1)
	        {
	           fs.write(buffer,0,byteread);
	           fs.flush();
	        } 
	        fs.close();
	        stream.close();      
	    } 
		 
		 /**
		 * 上传图片通用方法，上传成功后，图片存在本地及七牛云
		 * @param request
		 * @param response
		 */
		@RequestMapping(value = "/uploadImg")
		@ResponseBody
		public Map<String, Object> uploadImg(HttpServletRequest request,
				HttpServletResponse response) throws UnsupportedEncodingException {
			logger.info("/admin/uploadImg，开始");
			Map<String, Object> respMap = new HashMap<String, Object>();

			try {
				// 获得文件
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile multipartFiles = multipartRequest.getFile("filepic");

				String filename = null;
				if (multipartFiles != null) {
					filename = multipartFiles.getOriginalFilename(); // 原始文件名
					String prefix = filename.substring(filename.lastIndexOf(".")); // 文件后缀名
					filename = StringUtil.get32UUID() + prefix; // 新的文件名称
					logger.info("图片名称：" + filename);
				} else {
					logger.error("上传图片内容为空");
					throw new Exception();
				}

				// 文件保存目录路径
				String savePath = WebConstant.UPLOAT_ROOT_PATH + "/images/productcomment/";
				// 创建文件夹
				File dirFile = new File(savePath);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
				SaveFileFromInputStream(multipartFiles.getInputStream(), savePath, filename);

				String filePath = savePath + File.separator + filename;
				logger.info("图片存储在本地路径：" + filename);
				 String isMember = WebConstant.ISMEMBER;
				 String imgUrl ="";
		         if(isMember.equals("true")){
		        	 imgUrl = ImageUpload.simpleUpload(filePath, filename);
		         }else{
		        	 imgUrl = WebConstant.MAIN_SERVER + "/images/productcomment/" + filename;
		         }
				logger.info("上传图片地址:" + imgUrl);
				respMap.put("result", "success");
				respMap.put("imgurl", imgUrl);
				respMap.put("msg", "上传图片成功！");
			} catch (Exception e) {
				logger.error("/admin/uploadImg，error", e);
				respMap.put("result", "failure");
				respMap.put("msg", "上传图片,出现异常！");
				return respMap;
			}

			logger.info("--->结束调用/admin/uploadImg，结束");
			return respMap;
		} 
	 
		/**
		 *  评论回复详情
		 */
		@RequestMapping(value = "/productMessageReplyDetails")
		@ResponseBody
	    public Map<String,Object> productMessageReplyDetails(HttpServletRequest request) throws Exception {  
	    	PageData pd=this.getPageData();

			int customerId = 0;
			//用户标识
			if(StringUtil.isNull(pd.getString("rootId"))){
				return CallBackConstant.PARAMETER_ERROR.callbackError("商品评论ID为空");
			}
			CustomerVo customer=Constants.getCustomer(request);
			if(customer == null || customer.getCustomer_id() == null){
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
				Map<String, Object> map = CallBackConstant.SUCCESS.callback(replyList);
				map.put("pageInfo", pd.get("pageView"));
				return map;
			} catch (Exception e) {
				logger.error("/weixin/productcomment/productMessageReplyDetails --error", e);
				return CallBackConstant.FAILED.callback();
			}
			
	    }
		
		/**
		 *  评论回复详情
		 */
		@RequestMapping(value = "/commentReplyDetails")
		@ResponseBody
	    public Map<String,Object> commentReplyDetails(HttpServletRequest request) throws Exception {  
	    	PageData pd=this.getPageData();

			int customerId = 0;
			//用户标识
			if(StringUtil.isNull(pd.getString("commentId"))){
				return CallBackConstant.PARAMETER_ERROR.callbackError("商品评论ID为空");
			}
			CustomerVo customer=Constants.getCustomer(request);
			if(customer != null && customer.getCustomer_id() != null){
				customerId = customer.getCustomer_id();
			}
			try {
				//评论内容
				PageData cpd = new PageData();
				cpd.put("commentId", pd.getInteger("commentId"));
				cpd.put("currentId", customerId);
				CommentVo commentVo = this.commentManager.queryCommentListByCommId(cpd);
				if(commentVo == null){
					return CallBackConstant.DATA_NOT_FOUND.callbackError("评论不存在");
				}
				
				PageData replypd = new PageData();
				replypd.put("rootId", commentVo.getComment_id());
				replypd.put("status", 2);
				replypd.put("currentId", customerId);
				List<CommentVo> replyList = this.commentManager.queryCommentInfoListPage(replypd);
				if(replyList == null){
					replyList = new ArrayList<CommentVo>();
				}
				Map<String, Object> map = CallBackConstant.SUCCESS.callback(replyList);
				map.put("pageInfo", pd.get("pageView"));
				return map;
			} catch (Exception e) {
				logger.error("/weixin/productcomment/commentReplyDetails --error", e);
				return CallBackConstant.FAILED.callback();
			}
			
	    }
		
		/**
		 *  回复评论
		 */
		@RequestMapping(value = "/replyComment")
		@ResponseBody
	    public Map<String,Object> replyComment(HttpServletRequest request) throws Exception {  
			PageData pd=this.getPageData();

			int customerId = 0;
			
			CustomerVo customer=Constants.getCustomer(request);
			if(customer == null || customer.getCustomer_id() == null){
				return CallBackConstant.LOGIN_TIME_OUT.callback();
			}
			customerId = customer.getCustomer_id();
			
			if(StringUtil.isNull(pd.getString("commentId"))){
				return CallBackConstant.PARAMETER_ERROR.callbackError("商品回复的评论ID为空");
			}
			
//			if(StringUtil.isNull(pd.getString("rootId"))){
//				return CallBackConstant.PARAMETER_ERROR.callbackError("商品评论ID为空");
//			}
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
		 * 去评论页
		 * @param request
		 * @param response
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "/toAddCommentByMsg")
		public ModelAndView toAddCommentByMsg(HttpServletRequest request,HttpServletResponse response,Model model) {
			ModelAndView modelAndView=new ModelAndView("weixin/news/add_comment");
			PageData pd = this.getPageData();
			CustomerVo customer=Constants.getCustomer(request);
			if(customer == null || customer.getCustomer_id() == null){
				return new ModelAndView("redirect:/weixin/tologin");
			}
			model.addAttribute("customerId", customer.getCustomer_id());
			//评论内容
			PageData cpd = new PageData();
			cpd.put("commentId", pd.getInteger("commentId"));
			cpd.put("currentId", customer.getCustomer_id());
			CommentVo commentVo = this.commentManager.queryCommentListByCommId(cpd);
			if(commentVo == null){
				modelAndView.addObject("tipsTitle", "评论信息提示");
				modelAndView.addObject("tipsContent", "您查看的评论不存在或已被删除");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
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
			if(specVo == null){
				modelAndView.addObject("tipsTitle", "商品信息提示");
				modelAndView.addObject("tipsContent", "您查看的商品规格已下架");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}
			
			productVo.setPrice(specVo.getSpec_price());
			if(specVo.getDiscount_price() != null && specVo.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
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
			model.addAttribute("myCommentId", pd.getString("myCommentId"));
			model.addAttribute("replyId", pd.getString("replyId"));
			model.addAttribute("commentVo", commentVo);
			model.addAttribute("productVo", productVo);
			return modelAndView;
		}
		
		/**
		 * 商品评论详情页
		 * @param request
		 * @param response
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "/toProductCommentDetail")
		public ModelAndView toProductCommentDetail(HttpServletRequest request,HttpServletResponse response) {
			ModelAndView modelAndView=new ModelAndView("/weixin/comment/product_comment_detail");
			try {
				PageData pd = this.getPageData();
				CustomerVo customer=Constants.getCustomer(request);
				//评论内容
				PageData cpd = new PageData();
				cpd.put("commentId", pd.getInteger("commentId"));
				if(customer != null && customer.getCustomer_id() != null){
					cpd.put("currentId", customer.getCustomer_id());
				}
				CommentVo commentVo = this.commentManager.queryCommentListByCommId(cpd);
				if(commentVo == null){
					modelAndView.addObject("tipsTitle", "评论信息提示");
					modelAndView.addObject("tipsContent", "您查看的评论不存在或已被删除");
					modelAndView.setViewName("/weixin/fuwubc");
					return modelAndView;
				}
				
				ProductVo productVo = this.productManager.selectByPrimaryKey(commentVo.getProduct_id());
				PageData specpd = new PageData();
				specpd.put("productId", productVo.getProduct_id());
				specpd.put("status", 1);
				specpd.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				specpd.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
				SpecialVo specialVo = specialManager.queryOneByProductId(specpd);
				
				specpd.clear();
				specpd.put("proStatus", 1);
				specpd.put("specStatus", 1);
				specpd.put("productId", productVo.getProduct_id());
				if(specialVo != null){
					specpd.put("activityId", specialVo.getSpecialId());
				}
				ProductSpecVo specVo = this.productSpecManager.queryProductSpecByOption(specpd);
				if(specVo == null){
					modelAndView.addObject("tipsTitle", "商品信息提示");
					modelAndView.addObject("tipsContent", "您查看的商品规格已下架");
					modelAndView.setViewName("/weixin/fuwubc");
					return modelAndView;
				}
				
				productVo.setPrice(specVo.getSpec_price());
				if(specVo.getDiscount_price() != null && specVo.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
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
				int customerId = 0;
				if(customer != null && customer.getCustomer_id() != null){
					customerId = customer.getCustomer_id();
				}
				
				PageData oppd = new PageData();
				oppd.put("orderId", commentVo.getOrder_id());
				oppd.put("productId", commentVo.getProduct_id());
				OrderProductVo opvo = this.orderProductManager.queryOne(specpd);
				String commentProductDesc = opvo.getProduct_name() + opvo.getSpec_name();
				
				modelAndView.addObject("customerId", customerId);
				modelAndView.addObject("commentVo", commentVo);
				modelAndView.addObject("productVo", productVo);
				modelAndView.addObject("type", pd.getString("type"));
				modelAndView.addObject("productId", pd.getString("productId"));
				modelAndView.addObject("specialId", pd.getString("specialId"));
				modelAndView.addObject("commentProductDesc", commentProductDesc);
			} catch (Exception e) {
				logger.error("weixin/productcomment/toProductCommentDetail ---error", e);
			}
			return modelAndView;
		}
		
		/**
		 * 去商品评论页
		 * @param request
		 * @param response
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "/toAddProductComment")
		public ModelAndView toAddProductComment(HttpServletRequest request,HttpServletResponse response,Model model) {
			ModelAndView modelAndView=new ModelAndView("/weixin/comment/add_product_comment");
			PageData pd = this.getPageData();
			CustomerVo customer=Constants.getCustomer(request);
			if(customer == null || customer.getCustomer_id() == null){
				return new ModelAndView("redirect:/weixin/tologin");
			}
			
			//评论内容
			PageData cpd = new PageData();
			cpd.put("commentId", pd.getInteger("commentId"));
			cpd.put("currentId", customer.getCustomer_id());
			CommentVo commentVo = this.commentManager.queryCommentListByCommId(cpd);
			
			if(commentVo == null){
				modelAndView.addObject("tipsTitle", "评论信息提示");
				modelAndView.addObject("tipsContent", "您查看的评论不存在或已被删除");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}
			Integer commentId = commentVo.getComment_id();
			
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
			if(specVo == null){
				modelAndView.addObject("tipsTitle", "商品信息提示");
				modelAndView.addObject("tipsContent", "您查看的商品规格已下架");
				modelAndView.setViewName("/weixin/fuwubc");
				return modelAndView;
			}
			
			productVo.setPrice(specVo.getSpec_price());
			if(specVo.getDiscount_price() != null && specVo.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
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
			model.addAttribute("type", pd.getString("type"));
			model.addAttribute("commentVo", commentVo);
			model.addAttribute("replyId", pd.getString("replyId"));
			model.addAttribute("productVo", productVo);
			return modelAndView;
		}
}
