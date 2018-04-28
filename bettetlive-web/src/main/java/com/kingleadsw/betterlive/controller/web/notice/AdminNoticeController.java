package com.kingleadsw.betterlive.controller.web.notice;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.NoticeManager;
import com.kingleadsw.betterlive.biz.PreProductManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.vo.AdminVo;
import com.kingleadsw.betterlive.vo.NoticeVo;
import com.kingleadsw.betterlive.vo.PreProductVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SpecialVo;


/**
 * 首页banner图管理controller
 */
@Controller
@RequestMapping(value = "/admin/notice")
public class AdminNoticeController extends AbstractWebController{
	
	private static Logger logger = Logger.getLogger(AdminNoticeController.class);
	
	@Autowired
	private NoticeManager noticeManager;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private PreProductManager preProductManager;
	@Autowired
	private SpecialMananger specialMananger;
	
	
	/**
	 * 跳转首页公告管理页面
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView("/admin/notice/list_notice");
		return mv;
	}
	
	
	/**
	 * 查询公告列表
	 */
	@RequestMapping(value="/queryNoticeAllJson")
	@ResponseBody
	public void queryNoticeAllJson(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		List<NoticeVo>  list=noticeManager.findNoticeListPage(pd);
		if(null==list){
			list=new ArrayList<NoticeVo>();
		}
		outEasyUIDataToJson(pd, list, response);
	}
	
	
	/**
	 * 增加或修改公告
	 */
	@RequestMapping(value="/addNotice")
	@ResponseBody
	public Map<String,Object> addNotice(HttpServletRequest request, HttpServletResponse response,NoticeVo notice){
		
		AdminVo admin=Constants.getAdmin(request);
		notice.setCreateBy(admin.getStaffId().toString());
		
		int result=0;
		if(notice.getNoticeId()!=null&&notice.getNoticeId()>0){
			result=noticeManager.updateNoticeByNid(notice);
		}else{
			result=noticeManager.insertNotice(notice);
		}
		
		if(result>0){
			return CallBackConstant.SUCCESS.callback();
		}else{
			return CallBackConstant.FAILED.callback();
		}
	}
	
	
	/**
	 * 公告 类型变化查出相应的实体
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/noticeTypeChange")
	@ResponseBody
	public void noticeTypeChange(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		String type = pd.getString("noticeType");	//公告类型，1：系統文章id，2：预购id，3：专题活动id，4：产品id
		JSONObject json = new JSONObject();
		if(type.equals("2")){
			List<PreProductVo> list = preProductManager.queryList(pd);//2：预购id
			json.put("list", list);
			json.put("noticeUrl", "weixin/prepurchase/toPreProductdetail?productId=");
		}else if(type.equals("3")){//专题活动
			List<SpecialVo> list = new ArrayList<SpecialVo>();
			try {
				list = specialMananger.querySpecialList(pd);
			} catch (ParseException e) {
				logger.error("/admin/notice/noticeTypeChange --error", e);
			}
			json.put("list", list);
			json.put("noticeUrl", "");
		}else if(type.equals("4")){//4：产品id
			pd.put("status", 1);
			List<ProductVo> list = productManager.queryProductList(pd);
			json.put("list", list);
			json.put("noticeUrl", "weixin/product/towxgoodsdetails?productId=");
		}
		this.outObjectToJson(json, response);
	}
	
	/**
	 * 根据id查询活动
	 */
	@RequestMapping(value="/queryNoticeJson")
	public void queryNoticeJson(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		String noticeId=pd.getString("noticeId");
		JSONObject json = new JSONObject();
		if(StringUtil.isEmpty(noticeId)){
			json.put("msg", "noticeId不能为空");
			return;
		}
		
		NoticeVo notice = noticeManager.findNotice(pd);
		if(notice!=null){
			json.put("code", "1010");
			json.put("data", notice);
			this.outObjectToJson(json, response);
		}else{
			json.put("msg", "没有该记录");
			this.outObjectToJson(json, response);
		}
	}
	
	/**
	 * 根据id删除banner
	 */
	@RequestMapping(value="/removeNotice")
	@ResponseBody
	public Map<String,Object> removeNotice(HttpServletRequest request, HttpServletResponse response){
		PageData pd=this.getPageData();
		String noticeId=pd.getString("noticeId");
		if(StringUtil.isEmpty(noticeId)){
			return CallBackConstant.FAILED.callback("noticeId不能为空");
		}
		
		int result = noticeManager.deleteByNid(noticeId);
		if(result>0){
			return CallBackConstant.SUCCESS.callback();
		}else{
			return CallBackConstant.FAILED.callback("没有该记录");
		}
	}
	
	
}
