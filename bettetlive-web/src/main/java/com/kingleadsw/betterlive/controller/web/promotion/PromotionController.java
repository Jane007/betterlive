package com.kingleadsw.betterlive.controller.web.promotion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.PromotionSpecManager;
import com.kingleadsw.betterlive.biz.SalePromotionManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.util.wx.WxMassMessageUtil;
import com.kingleadsw.betterlive.util.wx.WxUtil;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.PromotionSpecVo;
import com.kingleadsw.betterlive.vo.SalePromotionVo;
/**
 * 活动后台管理
 * @author zhangjing
 *
 * @date 2017年5月2日
 */
@Controller
@RequestMapping(value = "/admin/promotion")
public class PromotionController extends AbstractWebController{
	
	protected Logger logger = Logger.getLogger(PromotionController.class);
	@Autowired
	private SalePromotionManager salePromotionManager;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private PromotionSpecManager promotionSpecManager;
	@Autowired
	private ProductSpecManager productSpecManager;

	@RequestMapping(value="/findList")
	public ModelAndView findList(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView mv = new ModelAndView("admin/promotion/list_promotion");
		return mv;
	}
	@RequestMapping(value="/queryPromotionAllJson")
	@ResponseBody
	public void queryPromotionAllJson(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		
		List<SalePromotionVo> listPromotion =salePromotionManager.queryListPage(pd);
		
		if(listPromotion!=null && listPromotion.size()>0){
			this.outEasyUIDataToJson(pd, listPromotion, response);
		}else{
			this.outEasyUIDataToJson(pd, new ArrayList<SalePromotionVo>(), response);
		}
	}
	@RequestMapping(value="/toAddPromotion")
	public ModelAndView toAddPromotion(HttpServletRequest req, HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/promotion/add_promotion");
		PageData pd = this.getPageData();
		
		pd.put("productStatus", 1);
		pd.put("salePromotion", "YES");
		List<ProductVo> products  =productManager.queryProductList(pd);    
		model.addAttribute("products", products);
		return mv;
	}
	/**
	 * 添加促销活动
	 * @param request
	 * @param response
	 * @param salePromotionVo
	 */
	@RequestMapping(value="/addPromotion")
	public void addPromotion(HttpServletRequest request, HttpServletResponse response,SalePromotionVo salePromotionVo){
		logger.info("连接地址： /admin/promotion/addPromotion ,操作：增加促销活动信息。  操作状态： 开始");
		JSONObject json = new JSONObject();
		//PageData pd = this.getPageData();
		String specIds = request.getParameter("speclist");
		if(salePromotionVo.getPromotionId()!=null&&salePromotionVo.getPromotionId()>0){//修改
			int result = salePromotionManager.updateByPrimaryKey(salePromotionVo);
			if(result>0){
				int count  = this.updatePromotionSpec(specIds,salePromotionVo);
				if(count<=0){
					json.put("result", "fail");
					json.put("msg", "修改促销活动规格失败");
				}else{
					json.put("result", "success");
					json.put("msg", "修改促销活动规格成功");
				}
			}else{
				json.put("result", "fail");
				json.put("msg", "修改促销活动失败");
			}
			
		}else{//新增
			PageData pdt = new PageData();
    		pdt.put("pStatus", 1);
    		pdt.put("specStatus", 1);
    		String[] specIdList = null;
    		if(StringUtils.isNotBlank(specIds)){
    			specIdList = specIds.split(",");
        		pdt.put("specIds", specIds);
    		}
    		List<ProductSpecVo> pss = productSpecManager.queryListProductSpec(pdt);
    		if(pss == null || pss.size() < specIdList.length){
    			json.put("result", "fail");
    			json.put("msg", "存在已下架的商品");
    			this.outJson(json.toString(), response);
        		return;
    		}
			int result=salePromotionManager.insert(salePromotionVo);
			if(result>0){
				if(StringUtils.isNotBlank(specIds)){
					int count = this.AddPromotionSpec(specIds, salePromotionVo);
						if(count<=0){
							json.put("result", "fail");
							json.put("msg", "新增促销活动规格失败");
						}else{
//							if(StringUtil.isNotNull(massMsg) && "1".equals(massMsg)){
//								try {
//									request.setCharacterEncoding("UTF-8");
//									response.setCharacterEncoding("UTF-8");
//									//推送消息给微信公众号
//									this.sendMassMessage(salePromotionVo, massUrl);
//								} catch (UnsupportedEncodingException e) {
//									e.printStackTrace();
//								}
//							}							
							json.put("result", "success");
							json.put("msg", "新增促销活动规格成功");
						}
					}else{
						json.put("result", "fail");
						json.put("msg", "新增促销活动规格失败");
					}
				}else{//插入失败
					json.put("result", "fail");
					json.put("msg", "新增促销活动失败");
				}
		}
		
			this.outJson(json.toString(), response);
			logger.info("连接地址： /admin/promotion/addPromotion ,操作：增加促销活动信息。  操作状态： 结束");
		}
	
		private void sendMassMessage(SalePromotionVo vo, String local){
			try {
				String accessToken = WxUtil.getAccessToken();
				WxMassMessageUtil wmmu = new WxMassMessageUtil();
				SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
				String huihuoTitle = "挥货活动通知\n";
				String nowDate = sdf.format(new Date())+"\n";
				String huihuoNotice = "尊敬的挥货会员，挥货有新活动上线啦！\n";
				String promotionTitle = "活动名称："+vo.getPromotionName()+"\n";
				String startDate = vo.getStartTime();
				String endDate = vo.getEndTime();
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				Date dt = sdf.parse(startDate);
				Date enddt = sdf.parse(endDate);
				sdf = new SimpleDateFormat("MM月dd日");
				startDate = sdf.format(dt);
				endDate = sdf.format(enddt);
				String promotionTime = "活动时间：" + startDate + "至" + endDate + "\n";
				String cutInfo = "活动力度：满"+vo.getFullMoney()+"减"+vo.getCutMoney()+"\n";
				String pinContent = huihuoTitle+nowDate+huihuoNotice+promotionTitle+promotionTime+cutInfo;
				if(StringUtil.isNotNull(local)){
					String endInfo = "\n<a href='"+local+"'>点击查看活动</a>";
					pinContent = pinContent + endInfo ;
				}
	
				String sendMsg = "{\"filter\":{\"is_to_all\":true},\"text\":{\"content\":\""+pinContent+"\"},\"msgtype\":\"text\"}\"";
				wmmu.sendMessage(accessToken, sendMsg);
			} catch (ParseException e) {
				logger.error("/admin/promotion/sendMassMessage --error", e);
			}
	}	
	
	private int updatePromotionSpec(String specIds,SalePromotionVo salePromotionVo){
		int count=0;
		salePromotionVo = salePromotionManager.selectByPrimaryKey(salePromotionVo.getPromotionId());
		List<PromotionSpecVo> listSpecs = salePromotionVo.getListSpec();
		
		if(StringUtils.isNotBlank(specIds)){
			if(specIds.contains(",")){
				String[] array = specIds.split(",");
				
					if(array.length>=listSpecs.size()){//要新增
						for (String specId : array) {
							PageData spcpd = new PageData();
							spcpd.put("specId", Integer.parseInt(specId));
							spcpd.put("promotionId", salePromotionVo.getPromotionId());
							PromotionSpecVo psv = promotionSpecManager.queryOne(spcpd);
							if(psv==null){
								ProductSpecVo psvo = productSpecManager.queryProductSpecByOption(spcpd);
								psv = new PromotionSpecVo();
								psv.setProductId(psvo.getProduct_id());
								psv.setSpecId(psvo.getSpec_id());
								psv.setPromotionId(salePromotionVo.getPromotionId());
								count = promotionSpecManager.insert(psv);
							}else{//查出有数据,不做任何修改
								count=1;
							}
						}
					}else{//要删除
						PageData spcpd = new PageData();
						spcpd.put("promotionId", salePromotionVo.getPromotionId());
						spcpd.put("editSpec", "YES");
						spcpd.put("ids", array);
						count = promotionSpecManager.delete(spcpd);
					}
				
			}else{//一个规格做活动
//				String[] array ={specIds};
				PageData spcpd = new PageData();
				spcpd.put("promotionId", salePromotionVo.getPromotionId());
//				spcpd.put("editSpec", "YES");
//				spcpd.put("ids", array);
				spcpd.put("specId", Integer.parseInt(specIds));
				PromotionSpecVo psv = promotionSpecManager.queryOne(spcpd);
				if(psv ==null){
					ProductSpecVo psvo = productSpecManager.queryProductSpecByOption(spcpd);
					psv = new PromotionSpecVo();
					psv.setProductId(psvo.getProduct_id());
					psv.setSpecId(psvo.getSpec_id());
					psv.setPromotionId(salePromotionVo.getPromotionId());
					count = promotionSpecManager.insert(psv);
				}else{
					count = 1;
				}
				
			}
		}else{//所有的规格都删除了
			PageData spcpd = new PageData();
			spcpd.put("promotionId", salePromotionVo.getPromotionId());
			count = promotionSpecManager.delete(spcpd);
		}
		return count;
	}
	
	private int AddPromotionSpec(String specIds,SalePromotionVo salePromotionVo){
		int count=0;
		if(specIds.contains(",")){
			String[] array = specIds.split(",");
			for (String sid : array) {
				PageData spcpd = new PageData();
				spcpd.put("specId", Integer.parseInt(sid));
				ProductSpecVo psvo = productSpecManager.queryProductSpecByOption(spcpd);
				spcpd.put("promotionId", salePromotionVo.getPromotionId());
				PromotionSpecVo psv = new PromotionSpecVo();
				psv.setProductId(psvo.getProduct_id());
				psv.setSpecId(psvo.getSpec_id());
				psv.setPromotionId(salePromotionVo.getPromotionId());
				count = promotionSpecManager.insert(psv);
				
			}
		}else{//一个规格
			PageData spcpd = new PageData();
			spcpd.put("specId", specIds);
			ProductSpecVo psvo = productSpecManager.queryProductSpecByOption(spcpd);
			PromotionSpecVo psv = new PromotionSpecVo();
			psv.setProductId(psvo.getProduct_id());
			psv.setSpecId(psvo.getSpec_id());
			psv.setPromotionId(salePromotionVo.getPromotionId());
			count = promotionSpecManager.insert(psv);
		}
		return count;
	}
	/**
	 * 逻辑删除促销活动
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/delPromotion")
	public void delPromotion(HttpServletRequest request, HttpServletResponse response){
		logger.info("连接地址： /admin/promotion/delPromotion ,操作：删除促销活动信息。  操作状态： 开始");
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		String promotionIdArray = pd.getString("promotionIdArray");
		int result=0;
		if(StringUtils.isNotBlank(promotionIdArray)){
			String[] promotionIdArr = promotionIdArray.split(",");
			for (String pid : promotionIdArr) {
				if(StringUtils.isNotBlank(pid)){
					SalePromotionVo spvo = salePromotionManager.selectByPrimaryKey(Integer.parseInt(pid));
					spvo.setStatus(0);//1正常，0删除
					 result = salePromotionManager.updateByPrimaryKey(spvo);
					
				}
			}
			if(result>0){
				json.put("result", "succ");
			}else{
				json.put("result", "fail");
				json.put("msg", "删除活动失败！");
			}
			
		}
		logger.info("连接地址： /admin/promotion/delPromotion ,操作：删除促销活动信息。  操作状态： 结束");
		this.outObjectToJson(json, response);
	}
	
	@RequestMapping(value="/toEditPromotion")
	public ModelAndView toEditPromotion(HttpServletRequest req, HttpServletResponse resp,Model model){
		ModelAndView mv = new ModelAndView("admin/promotion/edit_promotion");
		PageData pd = this.getPageData();
		
		String promotionId = pd.getString("promotionId");
		SalePromotionVo spvo = new SalePromotionVo();
		if(StringUtils.isNotBlank(promotionId)){
			spvo = salePromotionManager.selectByPrimaryKey(Integer.parseInt(promotionId));
			model.addAttribute("salePromotionVo", spvo);
		}
		pd.put("productStatus", 1);
		//pd.put("salePromotion", "YES");
		List<ProductVo> products  =productManager.queryProductList(pd);  
		String productIds = "";
		String productName = "";
		String specIds = "";
		List<PromotionSpecVo> psvo = spvo.getListSpec();
		if(psvo!=null && psvo.size()>0){
			for (PromotionSpecVo promotionSpecVo : psvo) {
				if(!productName.contains(promotionSpecVo.getProductName())){
					productName+=promotionSpecVo.getProductName()+",";
				}
				productIds+=promotionSpecVo.getProductId()+",";
				specIds+=promotionSpecVo.getSpecId()+",";
			}
		}
		model.addAttribute("specIds", specIds);
		model.addAttribute("products", products);
		model.addAttribute("productIds", productIds);
		model.addAttribute("productName", productName);
		return mv;
	}
	
}
