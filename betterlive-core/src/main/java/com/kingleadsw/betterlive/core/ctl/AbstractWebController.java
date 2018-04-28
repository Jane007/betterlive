package com.kingleadsw.betterlive.core.ctl;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.kingleadsw.betterlive.core.page.EasyUIPageBean;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.page.PageView;
import com.kingleadsw.betterlive.core.util.JacksonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public abstract class AbstractWebController extends MultiActionController implements WebController {

	private static Logger logger = Logger.getLogger(AbstractWebController.class);
	
	/**
	 * 实现父类抽象方法
	 */
	public void init() {

	}

	public void onFinishedProcessContext() {

	}
	/**
	 * 
	 * onRefreshContext:(此方法在创建或刷新WebApplicationContext时被调用).
	 * 
	 * @throws BeansException
	 */
	public void onRefreshContext() throws BeansException {
		initWebConfiguration();
	}

	/**
	 * 描述.
	 */
	private void initWebConfiguration() {
		
	}
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	public Object getSessionObject(String key) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getSession().getAttribute(key);
	}


	/**
	 * 
	 * initBinder:(初始化binder的回调函数).
	 * 
	 * @param request
	 * @param binder
	 * @throws Exception
	 * @see MultiActionController
	 *      #initBinder(javax.servlet.http.HttpServletRequest,
	 *      org.springframework.web.bind.ServletRequestDataBinder)
	 */
	@Override
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(Short.class, new CustomNumberEditor(
				Short.class, true));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(
				Integer.class, true));
		binder.registerCustomEditor(Long.class, new CustomNumberEditor(
				Long.class, true));
		binder.registerCustomEditor(Float.class, new CustomNumberEditor(
				Float.class, true));
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(
				Double.class, true));
		binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(
				BigDecimal.class, true));
		binder.registerCustomEditor(BigInteger.class, new CustomNumberEditor(
				BigInteger.class, true));
		// java.util.Date需要根据具体使用的格式不同在子类做不同的bind。
	}

	/**
	 * object 类型 转化为 json 输出.
	 *
	 * @param obj
	 * @param response
	 *
	 */
	protected void outObjectToJson(Object obj, HttpServletResponse response) {
		try {
			outJson(JacksonUtil.serializeObjectToJson(obj), response);
		} catch (Exception e) {
			logger.error("AbstractWebController/outObjectToJson --error", e);
		}
	}


	/**
	 * 将easyui需要的List数据分页并输出
	 * @param pd request封装后的参数对象Map
	 * @param dataList 分页数据
	 * @param response
	 *
	 */
	public void outEasyUIDataToJson(PageData pd, List dataList, HttpServletResponse response) {
		try {
			PageView pageView = (PageView) pd.get("pageView");
			if(pageView == null){
				pageView = new PageView();
			}
			String dataJson = EasyUIPageBean.createEasyUIPageBean(pageView.getRowCount(), dataList).toJsonString();
			outJson(dataJson, response);
		} catch (Exception e) {
			logger.error("AbstractWebController/outEasyUIDataToJson --error", e);
		}
	}

	/**
	 * 将easyui需要的List数据分页并输出
	 * @param pd request封装后的参数对象Map
	 * @param dataList 分页数据
	 * @param model
	 *
	 */
	public void outDataToModel(PageData pd, List dataList,Model model) {
		try {
			PageView pageView = (PageView) pd.get("pageView");
			if(pageView == null){
				pageView = new PageView();
			}
			model.addAttribute("pageData", EasyUIPageBean.createEasyUIPageBean(pageView.getRowCount(), dataList));
		} catch (Exception e) {
			logger.error("AbstractWebController/outDataToModel --error", e);
		}
	}
	/**
	 * Object 转换json并输出
	 * 时间默认转换为： yyyy-MM-dd HH:mm:ss
	 * @param obj
	 * @param response
	 *
	 */
	public void outToJson(Object obj, HttpServletResponse response) {
		try {
			outJson(JacksonUtil.serializeObjectToJson(obj), response);
		} catch (Exception e) {
			logger.error("AbstractWebController/outToJson --error", e);
		}
	}
	/**
	 * 输出json以html格式解析.
	 * 
	 * @param str
	 * @param response
	 * 
	 */
	public void outString(String str, HttpServletResponse response) {
		try {
			if (response != null) {
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.write(str);
			}
		} catch (IOException e) {
		}
	}

	/**
	 * 输出json.
	 * @param str json字符串
	 * @param response
	 */
	protected void outJson(String str, HttpServletResponse response) {
		try {
			if (response != null) {
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.write(str);
				out.close();
			}
		} catch (IOException e) {
			logger.error("AbstractWebController/outJson --error", e);
		}
	}

    /**
	 * form表单提交 Date类型数据绑定
	 * <功能详细描述>
	 * @param binder
	 * @see [类、类#方法、类#成员]
	 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    dateFormat.setLenient(false);  
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
	}

	/**
	 * 获取当前项目的物理位置
	 * @return
	 */
	public String realPath(){
		return getRequest().getRealPath("/WEB-INF");
	}

	/**
	 * 获取指定长度的随机数
	 * @param rndIndex
	 * @return
	 */
	public String getInvitedCode(int rndIndex){
		StringBuffer buffer = new StringBuffer("");
		Random rnd = new Random();
		for(int i = 0 ; i < rndIndex ; i ++){
			buffer.append(rnd.nextInt(9));
		}
		return buffer.toString();
	}

	public static String generateOrderCode(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String str=sdf.format(new Date());
		int result=new Random().nextInt(1000);
		return str+result;
	}
}
