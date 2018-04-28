package com.kingleadsw.betterlive.controller.web.giftcard;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.GiftCardManager;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.init.FileUpload;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.vo.GiftCardVo;

@Controller
@RequestMapping(value = "/admin/giftcard")
public class GiftCardController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(GiftCardController.class);

    private static int startRow = 1;
    
	
	@Autowired
	private GiftCardManager giftCardManager;
	//专题活动上传到服务器存储路径
		private static final String GIFTCARD_PATH = WebConstant.UPLOAT_ROOT_PATH 
				+ File.separator + "giftcard" + File.separator;
	
	/**
	 * 跳转礼品卡管理页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/toGiftCard")
	public ModelAndView toGiftCard(HttpServletRequest httpRequest) {
		ModelAndView modelAndView =new ModelAndView("admin/giftcard/list_giftcard");
		return modelAndView;
	}
	
	
	/**
	 * 查询礼品卡管理
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value="/queryGiftCardAllJson",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void queryGiftCardAllJson(HttpServletRequest httpRequest,HttpServletResponse response){
		String msg = "queryCouponManagerByPages";
		logger.info("/admin/giftcard/"+msg+" begin");
		
		PageData pd = this.getPageData();
		List<GiftCardVo> list=null; 
	    		
		try {
			list=giftCardManager.findGiftCardListPage(pd);
		} catch (Exception e) {
			logger.error("查询优惠券管理出现异常.... ", e);
		}
	    
	    if(null==list || list.isEmpty()){
	    	list=new ArrayList<GiftCardVo>();
	    }
		
		this.outEasyUIDataToJson(pd, list, response);
        
		logger.info("--->结束调用/admin/giftcard/"+msg);
		
	} 
	@RequestMapping(value = "/upload")
	@ResponseBody
	public void upload(HttpServletRequest request,HttpServletResponse response) {
		
		JSONObject json=new JSONObject();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		MultipartFile multipartFiles = multipartRequest.getFile("file");
		String filename = UUID.randomUUID().toString().replaceAll("-", "");
		String realName = FileUpload.fileUp(multipartFiles, GIFTCARD_PATH, filename);
		String importExcel = importExcelToGiftCard(GIFTCARD_PATH+"/"+realName);
		if(importExcel.equals("1")){
			json.put("success", "success");
		}else{
			json.put("success", "faild");
		}
		this.outObjectToJson(json, response);
		
	}
	

	
	
    public String importExcelToGiftCard(String fullPath) {
        //解析excel
        Workbook wookbook = null;
        
        //将Excel的各行记录放入ImpExcelBean的list里面
        try {
            //WorkbookFactory是用来将Excel内容导入数据库的一个类
            wookbook = WorkbookFactory.create(new FileInputStream(fullPath));
            Sheet sheet = wookbook.getSheetAt(0);//统计excel的行数
            int rowLen = sheet.getPhysicalNumberOfRows();//excel总行数，记录数=行数-1
            GiftCardVo card;
            //导入各条记录
            for (int i = startRow; i < rowLen; i++) {
                Row row = sheet.getRow(i);
                card = new GiftCardVo();
                int startCol = 0;
                PageData pd = this.getPageData();
                //将Excel中各行记录依次导入到ImpExcelBean的list中
                if (row != null) {
                    // 表英文名
                    String cardNo = getValue(row.getCell(startCol++)).toUpperCase();
                    card.setCard_no(cardNo);
                    pd.put("giftCardNo", cardNo);
                    String smoney = getValue(row.getCell(startCol++)).toUpperCase();
                    card.setCard_money(smoney);
                    //pd.put("couponMoney", smoney);
                    //表描述
                    String pwd = getValue(row.getCell(startCol++)).toUpperCase();
                    card.setCard_pwd(pwd);
                    //pd.put("giftCardPw", pwd);
                }
                card.setStatus(0);
               
                GiftCardVo cardVo = giftCardManager.findGiftCard(pd);//去重
                if(cardVo==null){
                	 giftCardManager.insertGiftCard(card);
                }
               
            }
            return "1";
        } catch (Exception e) {
            logger.error("/admin/giftecard/importExcelToGiftCard --error", e);
            return "0|excel解析失败";
        }

    }
    
    /**
     * 判断表格中值的类型并且返回一个String类型的值
     * @param cell
     * @return
     */
    private String getValue(Cell cell) {
        if (cell == null) {
			return "";
		}
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue()).trim();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue()).trim();
        } else {
            return String.valueOf(cell.getStringCellValue()).trim();
        }
    }
	
	/**
	 * 跳转用户优惠券管理页面
	 * @param httpRequest
	 * @return
	 *//*
	@RequestMapping(value = "/toUserCoupon")
	public ModelAndView toUserCoupon(HttpServletRequest httpRequest) {
		ModelAndView modelAndView =new ModelAndView("admin/coupon/list_user_couponinfo");
		return modelAndView;
	}
	
	
	*//**
	 * 分页查询优惠券管理
	 * @param httpRequest
	 * @return
	 *//*
	@RequestMapping(value="/queryUserCouponAllJson",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void queryUserCouponAllJson(HttpServletRequest httpRequest,HttpServletResponse response,Page page){
		String msg = "queryUserCouponAllJson";
		logger.debug("/admin/coupon/"+msg+" begin");
		
		PageData pd = this.getPageData();
		page.setPd(pd);
	    
		List<CouponInfoVo> list=null; 
	    		
		try {
			list=couponInfoManager.findUserCouponListPage(pd);
		} catch (Exception e) {
			logger.debug("查询优惠券管理出现异常.... ");
			
			e.printStackTrace();
		}
	    
	    if(null==list || list.isEmpty()){
	    	list=new ArrayList<CouponInfoVo>();
	    }
		
		this.outEasyUIDataToJson(pd, list, response);
        
		logger.info("--->结束调用/admin/coupon/"+msg);
		
	} 
	
	*//**
	 * 查询优惠券类型为补偿券 的优惠券
	 * @param httpRequest
	 * @return
	 *//*
	@RequestMapping(value="/queryCompensateCouponAllJson")
	@ResponseBody
	public Map<String,Object> queryCompensateCouponAllJson(HttpServletRequest httpRequest,HttpServletResponse response){
		String msg = "queryCompensateCouponAllJson";
		logger.debug("/admin/coupon/"+msg+" begin");
		
		JSONObject json=new JSONObject();
		
		PageData pd = new PageData();
		pd.put("couponType","2");
		
		List<CouponManagerVo> list=null; 
	    		
		try {
			list=couponManagerManager.findListCouponManager(pd);
			if(!list.isEmpty() && list.size()>0){
				json.put("result","succ");
				json.put("list",list);
			}else{
				json.put("result","fail");
				json.put("msg","没有查询到类型为补偿券的优惠券");
			}
		} catch (Exception e) {
			logger.debug("查询优惠券管理出现异常.... ");
			
			json.put("result","exec");
			json.put("msg","查询出现异常");
			
			e.printStackTrace();
		}
	    
	    logger.info("--->结束调用/admin/coupon/"+msg);
		
	    return CallBackConstant.SUCCESS.callback(json);
        
	} 
	*/
	
	/**
	 * 后台管理员增加用户优惠券
	 * @param httpRequest
	 * @return
	 *//*
	@RequestMapping(value="/addUserCouponInfo",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void addUserCouponInfo(HttpServletRequest request,HttpServletResponse response){
		String msg = "addUserCouponInfo";
		logger.debug("/admin/coupon/"+msg+" begin");
		
        JSONObject json=new JSONObject();
        CouponInfoVo couponInfoVo=null;
        CustomerVo customerVo=null;
        		
        AdminVo admin=Constants.getAdmin(request);
        PageData pd=this.getPageData();
        
        String toObject=pd.getString("toObject"); 
        String mobile=pd.getString("mobile");
        String cmId=pd.getString("cmId");
        
        if(null==toObject || "".equals(toObject)){
        	json.put("result", "failure");
			json.put("msg", "请选择发放对象");
			outJson(json.toString(), response);
			return;
        }else{
        	if("2".equals(toObject)){
        		if(null==mobile || "".equals(mobile)){
        			json.put("result", "failure");
        			json.put("msg", "请输入用户手机号码");
        			outJson(json.toString(), response);
        			return;
        		}else{
        			customerVo=customerManager.findCustomer(pd);
        			if(null ==customerVo){
        				json.put("result", "failure");
            			json.put("msg", "用户不存在");
            			outJson(json.toString(), response);
            			return;
        			}
        		}
        	}
        }
        
        if(null ==cmId || "".equals(cmId)){
        	json.put("result", "failure");
			json.put("msg", "请选择优惠券");
			outJson(json.toString(), response);
			return;
        }
        
        
        //根据ID查询优惠券信息
		CouponManagerVo couponManager=null;
		int result=0; 
		try {
			couponManager = couponManagerManager.findCouponManager(pd);
			
			if(null==couponManager){
				json.put("result", "failure");
				json.put("msg", "优惠券不存在");
				outJson(json.toString(), response);
				return;
			}
			
			String currentDate=DateUtil.FormatDate(new Date(),"yyyy-MM-dd");
			
			
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE,Integer.parseInt(couponManager.getUsemax_date()));
			
			String endDate=DateUtil.FormatDate(calendar.getTime(),"yyyy-MM-dd");
			
			
			if("1".equals(toObject)){
				List<CustomerVo> listcustomerVo=customerManager.findListCustomer(pd);
				for (int i = 0; i < listcustomerVo.size(); i++) {
					couponInfoVo=new CouponInfoVo();
					
					couponInfoVo.setMobile(listcustomerVo.get(i).getMobile());
					couponInfoVo.setCustomer_id(listcustomerVo.get(i).getCustomerId());
					
					couponInfoVo.setCm_id(couponManager.getCm_id());
					couponInfoVo.setCoupon_money(couponManager.getCoupon_money());
					couponInfoVo.setStarttime(currentDate);
					couponInfoVo.setEndtime(endDate);
					couponInfoVo.setStart_money(couponManager.getUsemin_money());
					couponInfoVo.setCoupon_from(2);
					couponInfoVo.setStatus("1");
					couponInfoVo.setFrom_user_id(admin.getStaffId());
					
					result = couponInfoManager.insertUserCoupon(couponInfoVo);
				}
			}else{
				couponInfoVo=new CouponInfoVo();
				
				couponInfoVo.setMobile(customerVo.getMobile());
				couponInfoVo.setCustomer_id(customerVo.getCustomerId());
				
				couponInfoVo.setCm_id(couponManager.getCm_id());
				couponInfoVo.setCoupon_money(couponManager.getCoupon_money());
				couponInfoVo.setStarttime(currentDate);
				couponInfoVo.setEndtime(endDate);
				couponInfoVo.setStart_money(couponManager.getUsemin_money());
				couponInfoVo.setCoupon_from(2);
				couponInfoVo.setStatus("1");
				couponInfoVo.setFrom_user_id(admin.getStaffId());
				
				result = couponInfoManager.insertUserCoupon(couponInfoVo);
			}
			
			if(result>0){
				json.put("result", "succ");
				json.put("msg", "添加成功");
			}else{
				json.put("result", "fail");
				json.put("msg", "添加失败");
			}
			
		} catch (Exception e) {
			logger.debug("系统后台管理人员发放用户优惠券失败...");
			
			json.put("result", "exec");
			json.put("msg", "添加异常");
			
			e.printStackTrace();
		}
        
		outJson(json.toString(), response);
		logger.info("--->结束调用/admin/coupon/"+msg);
		
	}
	*/
	/**
	 * 根据id查询优惠券管理
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value="/findGiftCard",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void findGiftCard(HttpServletRequest httpRequest,HttpServletResponse response){
		String msg = "findGiftCard";
		logger.info("/admin/giftcard/"+msg+" begin");
		
		JSONObject json=new JSONObject();
		PageData pd=this.getPageData();
		String cardId=pd.getString("cardId");
		
		if(StringUtil.isEmpty(cardId)){
			json.put("result", "failure");
			json.put("msg", "请选择要修改的记录");
			outJson(json.toString(), response);
			return ;
		}
		
		GiftCardVo giftCardVo=null;
		try {
			giftCardVo=giftCardManager.findGiftCard(pd);
			if(null !=giftCardVo ){
				json.put("result", "succ");
			}else{
				json.put("result", "fail");
			}
		} catch (Exception e) {
			logger.error("查询礼品卡管理详细 异常  方法： /admin/giftcard/"+msg, e);
			
			json.put("result", "exec");
		}
		
		json.put("giftCardInfo", giftCardVo);
		
		
		outJson(json.toString(), response);
		
		logger.info("--->结束调用/admin/giftcard/"+msg);
	}
	
	
	/**
	 * 增加礼品卡管理
	 * @param httpRequest
	 * @return
	 * 
	 * 新增时先查询礼品卡卡号是否存在    是:新增失败   否： 开始新增
	 */
	@RequestMapping(value="/addGiftCardManager",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void addGiftCardManager(HttpServletRequest request,HttpServletResponse response){
		String msg = "addGiftCardManager";
		logger.info("/admin/giftcard/"+msg+" begin");
		
	    JSONObject json=new JSONObject();
	    
//	    AdminVo admin=Constants.getAdmin(request);
	    
	    PageData pd=this.getPageData();
		String giftCardNo=pd.getString("giftCardNo").replaceAll(" ","");     //礼品卡卡号
		String giftCardPw=pd.getString("giftCardPw").replaceAll(" ","");     //礼品卡密码
		String giftCardMoney=pd.getString("giftCardMoney");   				 //礼品卡金额
		Integer status=pd.getInteger("status");     						 //礼品卡状态
		
	    int result=0;
	    
		try {
			PageData pagedata=new PageData();
			pagedata.put("giftCardNo", giftCardNo);
			GiftCardVo giftCardVo=giftCardManager.findGiftCard(pagedata);
			
			if(null ==giftCardVo){
				GiftCardVo giftCard=new GiftCardVo();
				giftCard.setCard_no(giftCardNo);
				giftCard.setCard_pwd(giftCardPw);
				giftCard.setStatus(status);
				giftCard.setCard_money(giftCardMoney);
				
				result =giftCardManager.insertGiftCard(giftCard);
				if(result>0){
					json.put("result", "succ");
					json.put("msg", "新增成功");
				}else{
					json.put("result", "fail");
					json.put("msg", "新增失败");
				}
			}else{
				json.put("result", "failure");
				json.put("msg", "已存在相同卡号的礼品卡");
			}
			
		} catch (Exception e) {
			logger.error("新增礼品卡管理异常    方法:  /admin/giftcard/"+msg, e);
			
			json.put("result", "exec");
	    	json.put("msg", "出现异常");
		}
	   
	    
    	outJson(json.toString(), response);
	   
        logger.info("--->结束调用/admin/giftcard/"+msg);
	} 
	
	
	
	/**
	 * 更新礼品卡管理
	 * @param httpRequest
	 * @return
	 * 
	 * 更新时先根据ID查询礼品卡是否存在    是:开始更新   否： 跟新失败
	 * 
	 * 注意： 需要根据 卡号与ID ,如果存在 就判断 是不是同一个  是 ：开始更新  否：更新失败 
	 */
	@RequestMapping(value="/editGiftCardManager",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void editGiftCardManager(HttpServletRequest request,HttpServletResponse response){
		String msg = "editGiftCardManager";
		logger.info("/admin/giftcard/"+msg+" begin");
		
	    JSONObject json=new JSONObject();
//	    AdminVo admin=Constants.getAdmin(request);
	    
	    PageData pd=this.getPageData();
		String giftCardNo=pd.getString("giftCardNo").replaceAll(" ","");     //礼品卡卡号
		String cardId=pd.getString("cardId");   				 			 //礼品卡ID
		
		
	    int result=0;
	    boolean bool=true;
		try {
			if(null==cardId|| "".equals(cardId)){
				throw new Exception();
			}
			
			PageData pageData=new PageData();
			pageData.put("cardId",cardId);
			GiftCardVo giftCardVo=giftCardManager.findGiftCard(pageData);
			if(null !=giftCardVo){
				
				PageData pagedata=new PageData();
				pagedata.put("giftCardNo", giftCardNo);
				GiftCardVo giftCard=giftCardManager.findGiftCard(pagedata);
				
				if(null !=giftCard){
					if(giftCard.getCard_id() !=giftCardVo.getCard_id()){   //根据ID查询的礼品卡编号与根据礼品卡卡号查询的Id不一样就不能更新 
						bool=false;
						
						json.put("result", "failure");
						json.put("msg", "已存在相同卡号的礼品卡");
					}
				}
				
				if(bool){
					pd.put("couponMoney", pd.getString("giftCardMoney"));
					result =giftCardManager.updateGiftCardByGid(pd);
					if(result>0){
						json.put("result", "succ");
						json.put("msg", "更新成功");
					}else{
						json.put("result", "fail");
						json.put("msg", "更新失败");
					}
				}	
			}else{
				json.put("result", "failure");
				json.put("msg", "没有查询到礼品卡");
			}
			
		} catch (Exception e) {
			logger.error("更新礼品卡管理异常    方法:  /admin/giftcard/"+msg, e);
			
			json.put("result", "exec");
	    	json.put("msg", "出现异常");
		}
	   
	    
    	outJson(json.toString(), response);
	   
        logger.info("--->结束调用/admin/giftcard/"+msg);
	} 
}
