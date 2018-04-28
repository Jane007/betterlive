/**
 * @author ptz
 * 2016年11月18日 上午10:45:20
 */
package com.kingleadsw.betterlive.controller.web.audit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.AdminManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.MenuManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.PicturesManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.consts.Constants;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.vo.AdminVo;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.PicturesVo;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;

/**
 * @author fz
 * 2017年11月11日 上午10:45:20
 */
@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractWebController{

	protected Logger logger = Logger.getLogger(AuditController.class);

	@Autowired
	private MenuManager menuManager;
	
	@Autowired
    private AdminManager adminManager;
	
	
	@Autowired
    private CustomerManager customerManager;
	
	
    @Autowired
    private RedisService redisService;
    
    @Autowired
	private SpecialArticleManager specialArticleManager;
    
    @Autowired
	private PicturesManager picturesManager;
	
	@Autowired
	private MessageManager messageManager;
	
	@RequestMapping(value="/toMobileLogin")
	public String toMobileLogin(HttpServletRequest request){
		return "admin/mobile_login";
	}
	
	@RequestMapping("/checklogin")
	@ResponseBody
	public Map<String,Object> checkLogin(HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		logger.info("后台管理员登陆开始");
		String loginname=request.getParameter("loginname");
		String password=request.getParameter("password");
		if(StringUtil.isNull(loginname)){
			result.put("result", "failure");
			result.put("msg", "用户名不能为空");
			return result;
		}
		
		if(StringUtil.isNull(password)){
			result.put("result", "failure");
			result.put("msg", "密码不能为空");
			return result;
		}
		AdminVo admin=adminManager.queryAdminByLoginname(loginname);
		
		if(admin == null){
			result.put("result", "failure");
			result.put("msg", "用户名不存在");
			return result;
		}
		
		if(!password.equals(admin.getPassword())){
			result.put("result", "failure");
			result.put("msg", "密码不正确");
			return result;
		}
	
		//线上数据，不能更改
		if (admin.getRoleId() != 1 && admin.getRoleId() != 5 && admin.getRoleId() != 7) {
			result.put("result", "failure");
			result.put("msg", "您没有权限进入");
			return result;
		}
		result.put("result", "success");
		result.put("msg", "登录成功");
		HttpSession session=request.getSession();
		
		session.setAttribute(WebConstant.SESSION_ADMIN_USER, admin);
		
		Constants.setAdmin(request, admin);
		logger.info("后台管理员审核动态页面登陆结束");
		return result;
	}
	
	@RequestMapping("/auditArticle")
	public String auditArticle(HttpServletRequest request,Model model){
		AdminVo admin=(AdminVo)getSessionObject(WebConstant.SESSION_ADMIN_USER);
		PageData pd = this.getPageData();
		if (null == admin) {
			return "admin/mobile_login";
		}
		
		pd.put("status", 1);
		pd.put("articleFrom", 1);
		int auditPassed = specialArticleManager.querySpecialArticleCount(pd);
		
		pd.clear();
		pd.put("status", 3);
		pd.put("articleFrom", 1);
		int pendingAudit = specialArticleManager.querySpecialArticleCount(pd);
		
		pd.clear();
		pd.put("status", 4);
		pd.put("articleFrom", 1);
		int noPassed = specialArticleManager.querySpecialArticleCount(pd);
		model.addAttribute("auditPassed", auditPassed);
		model.addAttribute("pendingAudit", pendingAudit);
		model.addAttribute("noPassed", noPassed);
		return "admin/audit/audit_article";
	}
	
	/**
	 * 查询全部订单
	 */                     
	@RequestMapping(value="/queryAuditAllJson")
	@ResponseBody
	public Map<String, Object> queryAuditAllJson(HttpServletRequest req, HttpServletResponse resp){
		
		PageData pd = this.getPageData();
		
		pd.put("articleFrom", 1);
		List<SpecialArticleVo> listSpecial = specialArticleManager.queryCircleArticleListPage(pd);
		if(listSpecial == null){
			listSpecial = new ArrayList<SpecialArticleVo>();
		}else{
			PageData specialParams = new PageData();
			for (SpecialArticleVo vo : listSpecial) {
				specialParams.clear();
				specialParams.put("objectId", vo.getArticleId());
				specialParams.put("pictureType", 5);
				specialParams.put("status", 1);
				List<PicturesVo> pictures = this.picturesManager.queryList(specialParams);
				vo.setPictures(pictures);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", listSpecial);
		map.put("code", 1010);
		map.put("msg", "操作成功");
		map.put("currtime",System.currentTimeMillis());
		map.put("pageInfo", pd.get("pageView"));
		return map;
	}
	
	/**
	 * 审核动态文章
	 * 
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/editStatus")
	@ResponseBody
	public Map<String, Object> editStatus(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String articleId = request.getParameter("articleId");
			String status = request.getParameter("status");
			String opinion = request.getParameter("opinion");
			String customerId= request.getParameter("customerId");
			String articleTitle = request.getParameter("articleTitle");
			
			if (StringUtil.isNull(articleId) || StringUtil.isNull(status) || StringUtil.isNull(opinion) || StringUtil.isNull(customerId)) {
				map.put("result", "fail");
				map.put("msg", "审核失败");
				return map;
			}
			int iret = 0;
			SpecialArticleVo vo = new SpecialArticleVo();
			vo.setArticleId(Integer.parseInt(articleId));
			vo.setStatus(Integer.parseInt(status));
			vo.setOpinion(opinion);
			vo.setArticleTitle(articleTitle);
			iret = specialArticleManager.auditArticle(vo);
			
			if (iret <= 0) {
				map.put("result", "fail");
				map.put("msg", "审核失败");
				return map;
			}
			
			
			PageData pd = this.getPageData();
			pd.put("articleId", articleId);
			SpecialArticleVo special = specialArticleManager.queryOne(pd);
			MessageVo msgVo = new MessageVo();
			msgVo.setMsgType(MessageVo.MSGTYPE_SYS);
			msgVo.setSubMsgType(11);
			if(vo.getStatus()==1){
				msgVo.setMsgTitle(String.format("您的[%s]审核通过。", special.getArticleTitle()));
			}else if(vo.getStatus()==4){
				msgVo.setMsgTitle(String.format("您的[%s]未通过审核。", special.getArticleTitle()));
			}
			
			msgVo.setMsgDetail(vo.getOpinion());
			msgVo.setIsRead(0);
			msgVo.setCustomerId(Integer.parseInt(customerId));
			msgVo.setObjId(vo.getArticleId());
			
			messageManager.insert(msgVo);
			
		} catch (Exception e) {
			logger.error("/audit/editStatus------error", e);
		}
		map.put("result", "succ");
		map.put("msg", "审核成功");
		return map;
	}
	
}
