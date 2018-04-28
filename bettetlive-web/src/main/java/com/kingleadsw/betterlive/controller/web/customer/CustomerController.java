package com.kingleadsw.betterlive.controller.web.customer;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.OrderVo;

@Controller
@RequestMapping(value = "/admin/customer")
public class CustomerController extends AbstractWebController{
	
	private static Logger logger = Logger.getLogger(CustomerController.class);
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private OrderManager orderManager;
	
	/**
	 * 跳转礼品卡管理页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/findList")
	public ModelAndView findList(HttpServletRequest httpRequest) {
		ModelAndView modelAndView =new ModelAndView("admin/customer/list_customer");
		return modelAndView;
	}
	
	@RequestMapping(value="/queryCustomerAllJson",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void queryCustomerAllJson(HttpServletRequest httpRequest,HttpServletResponse response){
		String msg = "queryCustomerAllJson";
		logger.info("/admin/customer/"+msg+" begin");
		
		PageData pd = this.getPageData();
		List<CustomerVo> list=customerManager.getListPage(pd); 
		if(list==null){
			list = new ArrayList<CustomerVo>();
		}
		this.outEasyUIDataToJson(pd, list, response);
		logger.info("/admin/customer/"+msg+" end");
	}
	/**
	 * 跳转到历史订单里面
	 * @param httpRequest
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/findBuyList")
	public ModelAndView findBuyList(HttpServletRequest httpRequest,HttpServletResponse response){
		PageData pd = this.getPageData();
		ModelAndView modelAndView =new ModelAndView("admin/customer/list_historybuy");
		modelAndView.addObject("customerId", pd.getString("customerId"));
		return modelAndView;
	}
	/**
	 * 历史订单填充数据
	 * @param httpRequest
	 * @param response
	 */
	@RequestMapping(value="/queryOrderAllJson",method={RequestMethod.POST},produces = "text/html;charset=UTF-8")
	public void queryOrderAllJson(HttpServletRequest httpRequest,HttpServletResponse response){
		String msg = "queryOrderAllJson";
		logger.info("/admin/customer/"+msg+" begin");
		PageData pd = this.getPageData();
		List<OrderVo> orderlist = orderManager.findAllorderListPage(pd);
		if(orderlist==null){
			orderlist = new ArrayList<OrderVo>();
		}
		this.outEasyUIDataToJson(pd, orderlist, response);
		logger.info("/admin/customer/"+msg+" end");
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response){
		logger.info("/admin/customer/exportExcel/begin");
		PageData pd = this.getPageData();
		List<CustomerVo> list=customerManager.findListCustomer(pd);
		if(list==null){
			list = new ArrayList<CustomerVo>();
		}
		try {
			this.export(list,request, response);
		} catch (Exception e) {
			logger.error("导出失败", e);
		}
		logger.info("/admin/customer/exportExcel/end");
		
	}
	
	private void export(List<CustomerVo> list,HttpServletRequest request,HttpServletResponse response) throws Exception{
		HSSFWorkbook work = new HSSFWorkbook();
		InputStream in = null;
		OutputStream os = null;
		
		try {
			
			work = new HSSFWorkbook();

			// 获取模板第一个sheet页
			HSSFSheet sheet = work.createSheet();
			 // 设置字体  
		    HSSFFont headfont = work.createFont();  
		
		    headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    headfont.setFontName("宋体");
		    headfont.setFontHeightInPoints((short) 15);
		  
		    // 另一个样式  
		    HSSFCellStyle headstyle = work.createCellStyle();  
		    headstyle.setFont(headfont);
		    //headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			//头部
			HSSFRow head =sheet.createRow(0);
		
			sheet.setColumnWidth(0, 20 * 256);
			sheet.setDefaultColumnWidth(20);
			sheet.setColumnWidth(10, 30 * 300);
			head.createCell(0).setCellValue("用户名称");
			head.createCell(1).setCellValue("用户手机号");
			head.createCell(2).setCellValue("用户来源");
			head.createCell(3).setCellValue("注册时间");
			head.getCell(0).setCellStyle(headstyle);
			head.getCell(1).setCellStyle(headstyle);
			head.getCell(2).setCellStyle(headstyle);
			head.getCell(3).setCellStyle(headstyle);
			
			// 从第三行开始填充数据
			int rowNum = 1;
			PageData pd = new PageData();
			if(list!=null&&list.size()>0){
				for (CustomerVo customerVo : list) {
					HSSFRow row = sheet.createRow(rowNum);
					//用户名称
					row.createCell(0).setCellValue(customerVo.getNickname());
					//用户手机号
					row.createCell(1).setCellValue(customerVo.getMobile());
					//用户来源
					row.createCell(2).setCellValue(customerVo.getSource());
					//注册时间
					row.createCell(3).setCellValue(customerVo.getCreate_time());
					rowNum++;
				}
		
			}
			// 以文件流形式导出
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			work.write(out);
			InputStream inputStream = new ByteArrayInputStream(out.toByteArray());
			InputStream fis = new BufferedInputStream(inputStream);

			// 导出excel
			String fileName = "用户基本信息.xls";
			String encodingName = new String(fileName.getBytes("GB2312"), "ISO8859_1");
			response.reset();
			response.setContentType("application/x-download; charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + encodingName);
			os = response.getOutputStream();
			byte[] b = new byte['?'];
			int i = 0;
			while ((i = fis.read(b)) > 0) {
				os.write(b, 0, i);
			}
			os.flush();
		}  catch (IOException e) {
			logger.error("订单导出IO报错", e);
			throw e;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (os != null) {
					os.close();
				}
				
			} catch (IOException e) {
				logger.error("finally关闭流异常");
			}
		}
	
	}
	
}
