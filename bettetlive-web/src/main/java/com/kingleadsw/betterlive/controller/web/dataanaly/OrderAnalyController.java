package com.kingleadsw.betterlive.controller.web.dataanaly;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.OrderSalesManager;
import com.kingleadsw.betterlive.biz.OtherOrderManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.OrderSalesVo;

/**
 * 订单数据统计
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/admin/orderanaly")
public class OrderAnalyController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(OrderAnalyController.class);

	@Autowired
	private OrderSalesManager orderSalesManager;

	@Autowired
	private OtherOrderManager otherOrderManager;

	/**
	 * 跳转订单营业额统计页
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/hhMonthSales")
	public ModelAndView hhMonthSales(HttpServletRequest httpRequest) {
		ModelAndView modelAndView = new ModelAndView(
				"admin/dataanaly/hh_month_sales");
		PageData pd = this.getPageData();
		String queryDate = "";
		if (StringUtil.isNull(pd.getString("queryDate"))) {
			Calendar calendar = Calendar.getInstance();
			queryDate = String.valueOf(calendar.get(Calendar.YEAR));
		} else {
			queryDate = pd.getString("queryDate");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queryYear", queryDate);
		List<Map<String, Object>> list = orderSalesManager
				.queryMonthCostAmountSum(map);
		String monthTotalMoneys = "";
		String monthTotalPays = "";
		if (CollectionUtils.isNotEmpty(list)) {
			// 初始化并填充每个月的值
			List<String> monthList = new ArrayList<String>(Arrays.asList("01",
					"02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
					"12"));
			int index = 0;
			for (int i = 0, j = monthList.size(); i < j; i++) {
				String str = monthList.get(i);
				if (index < list.size()
						&& str.equals(list.get(index).get("MONTH"))) {
					double monthMoney = Double.valueOf(list.get(index)
							.get("TOTAL_PRICE").toString());
					monthTotalMoneys += monthMoney + ",";
					index++;
				} else {
					monthTotalMoneys += "0,";
				}
			}

			index = 0;
			for (int i = 0, j = monthList.size(); i < j; i++) {
				String str = monthList.get(i);
				if (index < list.size()
						&& str.equals(list.get(index).get("MONTH"))) {
					double monthMoney = Double.valueOf(list.get(index)
							.get("TOTAL_PAY").toString());
					monthTotalPays += monthMoney + ",";
					index++;
				} else {
					monthTotalPays += "0,";
				}
			}
		}
		if (monthTotalMoneys.length() > 0) {
			monthTotalMoneys = monthTotalMoneys.substring(0,
					monthTotalMoneys.length() - 1);
		}
		if (monthTotalPays.length() > 0) {
			monthTotalPays = monthTotalPays.substring(0,
					monthTotalPays.length() - 1);
		}
		modelAndView.addObject("monthTotalMoneys", monthTotalMoneys);
		modelAndView.addObject("monthTotalPays", monthTotalPays);
		modelAndView.addObject("queryDate", queryDate);
		return modelAndView;
	}
	
	
	/**
	 * 跳转订单营业额统计页
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportExcelByhhMonth")
	public void exportExcelByhhMonth(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("/admin/orderanaly/exportExcelByhhMonth/begin");

		PageData pd = this.getPageData();
		try {
			this.exportByhhMonth(null, request, response);
		} catch (Exception e) {
			logger.error("导出挥货平台订单月度营收表报错", e);
		}
		logger.info("/admin/orderanaly/exportExcelByhhMonth/end");
	}

	private void exportByhhMonth(List<OrderSalesVo> list, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HSSFWorkbook work = new HSSFWorkbook();
		InputStream in = null;
		OutputStream os = null;
			PageData pd = this.getPageData();
			String[] monthTotalMoneys = pd.getString("monthTotalMoneys").split(",");
			String[] monthTotalPays = pd.getString("monthTotalPays").split(",");
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
			// headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 头部
			HSSFRow head = sheet.createRow(0);

			sheet.setColumnWidth(0, 20 * 256);
			sheet.setDefaultColumnWidth(20);
			sheet.setColumnWidth(10, 30 * 300);
			head.createCell(0).setCellValue("月份");
			head.createCell(1).setCellValue("月销售额(元)");
			head.createCell(2).setCellValue("月实售额(元)");
			head.getCell(0).setCellStyle(headstyle);
			head.getCell(1).setCellStyle(headstyle);
			head.getCell(2).setCellStyle(headstyle);
			// 从第三行开始填充数据
			int rowNum = 1;
			if (monthTotalMoneys != null && monthTotalMoneys.length > 0) {
				for (int month = 0;month<=11;month++) {
							HSSFRow row = sheet.createRow(rowNum);
							// 月份
							row.createCell(0).setCellValue(month+1);
							// 月销售额
							row.createCell(1).setCellValue(monthTotalMoneys[month]);
							// 月实售额
							row.createCell(2).setCellValue(monthTotalPays[month]);
							rowNum++;
						}
					}
			// 以文件流形式导出
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						work.write(out);
						InputStream inputStream = new ByteArrayInputStream(
								out.toByteArray());
						InputStream fis = new BufferedInputStream(inputStream);
						// 导出excel
						String fileName = "挥货平台订单月度营收表导出.xls";
						String encodingName = new String(fileName.getBytes("GB2312"),
								"ISO8859_1");
						response.reset();
						response.setContentType("application/x-download; charset=UTF-8");
						response.setHeader("Content-Disposition", "attachment; filename="
								+ encodingName);
						os = response.getOutputStream();
						byte[] b = new byte['?'];
						int i = 0;
						while ((i = fis.read(b)) > 0) {
							os.write(b, 0, i);
						}
						os.flush();

				
			}

	/**
	 * 跳转订单日营业额统计页
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/hhDaySales")
	public ModelAndView hhDaySales(HttpServletRequest httpRequest) {
		ModelAndView modelAndView = new ModelAndView(
				"admin/dataanaly/hh_day_sales");
		PageData pd = this.getPageData();
		String queryDate = pd.getString("queryDate");

		// 获取营业数据
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("queryMonth", queryDate);
		List<Map<String, Object>> list = orderSalesManager
				.queryDayCostAmountSum(condition);

		String days = "";
		String dayTotalMoneys = "";
		String dayTotalPays = "";

		if (CollectionUtils.isNotEmpty(list)) {
			// 初始化月份日期列表
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.set(Calendar.YEAR,
					Integer.valueOf(queryDate.subSequence(0, 4).toString()));
			calendar.set(Calendar.MONTH,
					Integer.valueOf(queryDate.subSequence(5, 7).toString()) - 1);
			int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			for (int i = 1; i <= dayCount; i++) {
				if (i < 10) {
					days += "0" + i + ",";
				} else {
					days += i + ",";
				}
			}
			if (days.length() > 0) {
				days = days.substring(0, days.length() - 1);
			}
			int index = 0;
			String[] arrayLen = days.split(",");
			for (int i = 0, j = arrayLen.length; i < j; i++) {
				String str = arrayLen[i];
				if (index < list.size()
						&& str.equals(list.get(index).get("DAY"))) {
					double dayMoney = Double.valueOf(list.get(index)
							.get("TOTAL_PRICE").toString());
					dayTotalMoneys += dayMoney + ",";
					index++;
				} else {
					dayTotalMoneys += "0,";
				}
			}

			index = 0;
			for (int i = 0, j = arrayLen.length; i < j; i++) {
				String str = arrayLen[i];
				if (index < list.size()
						&& str.equals(list.get(index).get("DAY"))) {
					double dayMoney = Double.valueOf(list.get(index)
							.get("TOTAL_PAY").toString());
					dayTotalPays += dayMoney + ",";
					index++;
				} else {
					dayTotalPays += "0,";
				}
			}
		}

		if (dayTotalMoneys.length() > 0) {
			dayTotalMoneys = dayTotalMoneys.substring(0,
					dayTotalMoneys.length() - 1);
		}
		if (dayTotalPays.length() > 0) {
			dayTotalPays = dayTotalPays.substring(0, dayTotalPays.length() - 1);
		}

		modelAndView.addObject("days", days);
		modelAndView.addObject("dayTotalMoneys", dayTotalMoneys);
		modelAndView.addObject("dayTotalPays", dayTotalPays);
		modelAndView.addObject("queryDate", queryDate);
		return modelAndView;
	}

	/**
	 * 跳转其他平台订单营业额统计页
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/otherMonthSales")
	public ModelAndView otherMonthSales(HttpServletRequest httpRequest) {
		ModelAndView modelAndView = new ModelAndView(
				"admin/dataanaly/other_month_sales");
		PageData pd = this.getPageData();
		String queryDate = "";
		if (StringUtil.isNull(pd.getString("queryDate"))) {
			Calendar calendar = Calendar.getInstance();
			queryDate = String.valueOf(calendar.get(Calendar.YEAR));
		} else {
			queryDate = pd.getString("queryDate");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queryYear", queryDate);
		List<Map<String, Object>> list = orderSalesManager
				.queryOtherMonthCostAmountSum(map);
		String monthTotalMoneys = "";
		String monthTotalPays = "";
		if (CollectionUtils.isNotEmpty(list)) {
			// 初始化并填充每个月的值
			List<String> monthList = new ArrayList<String>(Arrays.asList("01",
					"02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
					"12"));
			int index = 0;
			for (int i = 0, j = monthList.size(); i < j; i++) {
				String str = monthList.get(i);
				if (index < list.size()
						&& str.equals(list.get(index).get("MONTH"))) {
					double monthMoney = Double.valueOf(list.get(index)
							.get("TOTAL_PRICE").toString());
					monthTotalMoneys += monthMoney + ",";
					index++;
				} else {
					monthTotalMoneys += "0,";
				}
			}

			index = 0;
			for (int i = 0, j = monthList.size(); i < j; i++) {
				String str = monthList.get(i);
				if (index < list.size()
						&& str.equals(list.get(index).get("MONTH"))) {
					double monthMoney = Double.valueOf(list.get(index)
							.get("TOTAL_PAY").toString());
					monthTotalPays += monthMoney + ",";
					index++;
				} else {
					monthTotalPays += "0,";
				}
			}
		}
		if (monthTotalMoneys.length() > 0) {
			monthTotalMoneys = monthTotalMoneys.substring(0,
					monthTotalMoneys.length() - 1);
		}
		if (monthTotalPays.length() > 0) {
			monthTotalPays = monthTotalPays.substring(0,
					monthTotalPays.length() - 1);
		}
		modelAndView.addObject("monthTotalMoneys", monthTotalMoneys);
		modelAndView.addObject("monthTotalPays", monthTotalPays);
		modelAndView.addObject("queryDate", queryDate);
		return modelAndView;
	}
	
	/**
	 * 其他平台订单营业额统计页
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportExcelByotherMonth")
	public void exportExcelByotherMonth(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("/admin/orderanaly/exportExcelByotherMonth/begin");

		PageData pd = this.getPageData();
		try {
			this.exportByByotherMonth(null, request, response);
		} catch (Exception e) {
			logger.error("导出其他平台订单月度营收表报错", e);
		}
		logger.info("/admin/orderanaly/exportExcelByotherMonth/end");
	}

	private void exportByByotherMonth(List<OrderSalesVo> list, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HSSFWorkbook work = new HSSFWorkbook();
		InputStream in = null;
		OutputStream os = null;
			PageData pd = this.getPageData();
			String[] monthTotalMoneys = pd.getString("monthTotalMoneys").split(",");
			String[] monthTotalPays = pd.getString("monthTotalPays").split(",");
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
			// headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 头部
			HSSFRow head = sheet.createRow(0);

			sheet.setColumnWidth(0, 20 * 256);
			sheet.setDefaultColumnWidth(20);
			sheet.setColumnWidth(10, 30 * 300);
			head.createCell(0).setCellValue("月份");
			head.createCell(1).setCellValue("月销售额(元)");
			head.createCell(2).setCellValue("月实售额(元)");
			head.getCell(0).setCellStyle(headstyle);
			head.getCell(1).setCellStyle(headstyle);
			head.getCell(2).setCellStyle(headstyle);
			// 从第三行开始填充数据
			int rowNum = 1;
			if (monthTotalMoneys != null && monthTotalMoneys.length > 0) {
				for (int month = 0;month<=11;month++) {
							HSSFRow row = sheet.createRow(rowNum);
							// 月份
							row.createCell(0).setCellValue(month+1);
							// 月销售额
							row.createCell(1).setCellValue(monthTotalMoneys[month]);
							// 月实售额
							row.createCell(2).setCellValue(monthTotalPays[month]);
							rowNum++;
						}
					}
			// 以文件流形式导出
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						work.write(out);
						InputStream inputStream = new ByteArrayInputStream(
								out.toByteArray());
						InputStream fis = new BufferedInputStream(inputStream);
						// 导出excel
						String fileName = "挥货平台订单月度营收表导出.xls";
						String encodingName = new String(fileName.getBytes("GB2312"),
								"ISO8859_1");
						response.reset();
						response.setContentType("application/x-download; charset=UTF-8");
						response.setHeader("Content-Disposition", "attachment; filename="
								+ encodingName);
						os = response.getOutputStream();
						byte[] b = new byte['?'];
						int i = 0;
						while ((i = fis.read(b)) > 0) {
							os.write(b, 0, i);
						}
						os.flush();

				
			}

	/**
	 * 跳转其他平台订单日营业额统计页
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/otherDaySales")
	public ModelAndView otherDaySales(HttpServletRequest httpRequest) {
		ModelAndView modelAndView = new ModelAndView(
				"admin/dataanaly/other_day_sales");
		PageData pd = this.getPageData();
		String queryDate = pd.getString("queryDate");

		// 获取营业数据
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("queryMonth", queryDate);
		List<Map<String, Object>> list = orderSalesManager
				.queryOtherDayCostAmountSum(condition);

		String days = "";
		String dayTotalMoneys = "";
		String dayTotalPays = "";

		if (CollectionUtils.isNotEmpty(list)) {
			// 初始化月份日期列表
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.set(Calendar.YEAR,
					Integer.valueOf(queryDate.subSequence(0, 4).toString()));
			calendar.set(Calendar.MONTH,
					Integer.valueOf(queryDate.subSequence(5, 7).toString()) - 1);
			int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			for (int i = 1; i <= dayCount; i++) {
				if (i < 10) {
					days += "0" + i + ",";
				} else {
					days += i + ",";
				}
			}
			if (days.length() > 0) {
				days = days.substring(0, days.length() - 1);
			}
			int index = 0;
			String[] arrayLen = days.split(",");
			for (int i = 0, j = arrayLen.length; i < j; i++) {
				String str = arrayLen[i];
				if (index < list.size()
						&& str.equals(list.get(index).get("DAY"))) {
					double dayMoney = Double.valueOf(list.get(index)
							.get("TOTAL_PRICE").toString());
					dayTotalMoneys += dayMoney + ",";
					index++;
				} else {
					dayTotalMoneys += "0,";
				}
			}

			index = 0;
			for (int i = 0, j = arrayLen.length; i < j; i++) {
				String str = arrayLen[i];
				if (index < list.size()
						&& str.equals(list.get(index).get("DAY"))) {
					double dayMoney = Double.valueOf(list.get(index)
							.get("TOTAL_PAY").toString());
					dayTotalPays += dayMoney + ",";
					index++;
				} else {
					dayTotalPays += "0,";
				}
			}
		}

		if (dayTotalMoneys.length() > 0) {
			dayTotalMoneys = dayTotalMoneys.substring(0,
					dayTotalMoneys.length() - 1);
		}
		if (dayTotalPays.length() > 0) {
			dayTotalPays = dayTotalPays.substring(0, dayTotalPays.length() - 1);
		}

		modelAndView.addObject("days", days);
		modelAndView.addObject("dayTotalMoneys", dayTotalMoneys);
		modelAndView.addObject("dayTotalPays", dayTotalPays);
		modelAndView.addObject("queryDate", queryDate);
		return modelAndView;
	}

	/**
	 * 跳转挥货平台用户来源统计页
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/hhCustomerSourceStatistics")
	public ModelAndView hhCustomerSourceStatistics(
			HttpServletRequest httpRequest) {
		return new ModelAndView("admin/dataanaly/hh_custsource_statistics");
	}

	/**
 * 根据挥货用户来源统计订单金额
 * 
 * @param httpRequest
 * @return
 */
@RequestMapping(value = "/hhQueryTotalByCust")
public void hhQueryTotalByCust(HttpServletRequest httpRequest,
		HttpServletResponse response) {
	PageData pd = this.getPageData();
	List<OrderSalesVo> list = null;
	List<OrderSalesVo> listNew = new ArrayList<OrderSalesVo>();
	try {
		list = this.orderSalesManager.queryTotalByCustSourceListPage(pd);
		if (list == null) {
			list = new ArrayList<OrderSalesVo>();
		}
		for (int i = 0; i < list.size(); i++) {
			OrderSalesVo vo = list.get(i);
			pd.put("source", vo.getCustomerSource());
			List orderIdList = this.orderSalesManager.queryTotalOrderIdByCustSourceList(pd);
			String orderIds = orderIdList.toString();
			orderIds = orderIds.substring(1, orderIds.length()-1);
			pd.put("orderIds", orderIds);
			List<OrderSalesVo> productCountList = this.orderSalesManager.queryTotalQuantityByCustSourceList(pd);
			vo.setProductCount(productCountList.get(0).getProductCount());
			listNew.add(vo);
			}
	} catch (Exception e) {
		logger.info("/admin/orderanaly/hhQueryTotalByCust --error", e);
	}

	this.outEasyUIDataToJson(pd, listNew, response);

}

	/**
	 * 根据挥货用户来源统计订单金额
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportExcelByCust")
	public void exportExcelByCust(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("/admin/orderanaly/exportExcelByCust/begin");

		PageData pd = this.getPageData();
		List<OrderSalesVo> listNew = new ArrayList<OrderSalesVo>();
		List<OrderSalesVo> list = orderSalesManager.queryTotalByCustSourceListPage(pd);
		if (list == null) {
			list = new ArrayList<OrderSalesVo>();
		}
		for (int i = 0; i < list.size(); i++) {
			OrderSalesVo vo = list.get(i);
			pd.put("source", vo.getCustomerSource());
			List orderIdList = this.orderSalesManager.queryTotalOrderIdByCustSourceList(pd);
			String orderIds = orderIdList.toString();
			orderIds = orderIds.substring(1, orderIds.length()-1);
			pd.put("orderIds", orderIds);
			List<OrderSalesVo> productCountList = this.orderSalesManager.queryTotalQuantityByCustSourceList(pd);
			vo.setProductCount(productCountList.get(0).getProductCount());
			listNew.add(vo);
			}
		try {
			this.exportByCust(listNew, request, response);
		} catch (Exception e) {
			logger.error("导出用户来源订单统计列表报错", e);
		}
		logger.info("/admin/orderanaly/exportExcelByCust/end");
	}

	private void exportByCust(List<OrderSalesVo> list, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HSSFWorkbook work = new HSSFWorkbook();
		InputStream in = null;
		OutputStream os = null;

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
			// headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 头部
			HSSFRow head = sheet.createRow(0);

			sheet.setColumnWidth(0, 20 * 256);
			sheet.setDefaultColumnWidth(20);
			sheet.setColumnWidth(10, 30 * 300);
			head.createCell(0).setCellValue("用户来源");
			head.createCell(1).setCellValue("订单金额");
			head.createCell(2).setCellValue("实付金额");
			head.createCell(3).setCellValue("订单份数");
			head.createCell(4).setCellValue("商品份数");
			head.getCell(0).setCellStyle(headstyle);
			head.getCell(1).setCellStyle(headstyle);
			head.getCell(2).setCellStyle(headstyle);
			head.getCell(3).setCellStyle(headstyle);
			head.getCell(4).setCellStyle(headstyle);
			// 从第三行开始填充数据
			int rowNum = 1;
			if (list != null && list.size() > 0) {
				for (OrderSalesVo orderSalesVo : list) {
							HSSFRow row = sheet.createRow(rowNum);
							// 用户来源
							row.createCell(0).setCellValue(
									orderSalesVo.getCustomerSource());
							// 订单金额
							row.createCell(1).setCellValue(
									orderSalesVo.getTotalPrice());
							// 实付金额
							row.createCell(2).setCellValue(
									orderSalesVo.getTotalPay());
							// 订单份数
							row.createCell(3).setCellValue(
									orderSalesVo.getOrderCount());
							// 商品份数
							row.createCell(4).setCellValue(
									orderSalesVo.getProductCount());
							rowNum++;
						}
					}
			// 以文件流形式导出
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						work.write(out);
						InputStream inputStream = new ByteArrayInputStream(
								out.toByteArray());
						InputStream fis = new BufferedInputStream(inputStream);
						// 导出excel
						String fileName = "用户来源订单统计列表导出.xls";
						String encodingName = new String(fileName.getBytes("GB2312"),
								"ISO8859_1");
						response.reset();
						response.setContentType("application/x-download; charset=UTF-8");
						response.setHeader("Content-Disposition", "attachment; filename="
								+ encodingName);
						os = response.getOutputStream();
						byte[] b = new byte['?'];
						int i = 0;
						while ((i = fis.read(b)) > 0) {
							os.write(b, 0, i);
						}
						os.flush();

				
			}

	/**
	 * 跳转其他平台用户来源统计页
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/otherCustomerSourceStatistics")
	public ModelAndView otherCustomerSourceStatistics(
			HttpServletRequest httpRequest) {
		return new ModelAndView("/admin/dataanaly/other_custsource_statistics");
	}

	/**
	 * 根据挥货用户来源统计其他平台订单金额
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/otherQueryTotalByCust")
	public void otherQueryTotalByCust(HttpServletRequest httpRequest,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<OrderSalesVo> list = null;

		try {
			list = this.orderSalesManager
					.queryOtherTotalByCustSourceListPage(pd);
			if (list == null) {
				list = new ArrayList<OrderSalesVo>();
			}
		} catch (Exception e) {
			logger.info("/admin/orderanaly/otherQueryTotalByCust --error", e);
		}

		this.outEasyUIDataToJson(pd, list, response);

	}
	
	/**
	 * 根据挥货用户来源统计其他平台订单金额
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportExcelByOtherCust")
	public void exportExcelByOtherCust(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("/admin/orderanaly/exportExcelByOtherCust/begin");

		PageData pd = this.getPageData();
		List<OrderSalesVo> list = orderSalesManager.queryOtherTotalByCustSourceListPage(pd);
		if (list == null || list.size() <= 0) {
			list = new ArrayList<OrderSalesVo>();
		}
		try {
			this.exportByOtherCust(list, request, response);
		} catch (Exception e) {
			logger.error("导出其他平台用户来源订单统计列表报错", e);
		}
		logger.info("/admin/orderanaly/exportExcelByCust/end");
	}

	private void exportByOtherCust(List<OrderSalesVo> list, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HSSFWorkbook work = new HSSFWorkbook();
		InputStream in = null;
		OutputStream os = null;

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
			// headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 头部
			HSSFRow head = sheet.createRow(0);

			sheet.setColumnWidth(0, 20 * 256);
			sheet.setDefaultColumnWidth(20);
			sheet.setColumnWidth(10, 30 * 300);
			head.createCell(0).setCellValue("用户来源");
			head.createCell(1).setCellValue("订单金额");
			head.createCell(2).setCellValue("实付金额");
			head.createCell(3).setCellValue("订单份数");
			head.createCell(4).setCellValue("商品份数");
			head.getCell(0).setCellStyle(headstyle);
			head.getCell(1).setCellStyle(headstyle);
			head.getCell(2).setCellStyle(headstyle);
			head.getCell(3).setCellStyle(headstyle);
			head.getCell(4).setCellStyle(headstyle);
			// 从第三行开始填充数据
			int rowNum = 1;
			if (list != null && list.size() > 0) {
				for (OrderSalesVo orderSalesVo : list) {
							HSSFRow row = sheet.createRow(rowNum);
							// 用户来源
							row.createCell(0).setCellValue(
									orderSalesVo.getCustomerSource());
							// 订单金额
							row.createCell(1).setCellValue(
									orderSalesVo.getTotalPrice());
							// 实付金额
							row.createCell(2).setCellValue(
									orderSalesVo.getTotalPay());
							// 订单份数
							row.createCell(3).setCellValue(
									orderSalesVo.getOrderCount());
							// 商品份数
							row.createCell(4).setCellValue(
									orderSalesVo.getProductCount());
							rowNum++;
						}
					}
			// 以文件流形式导出
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						work.write(out);
						InputStream inputStream = new ByteArrayInputStream(
								out.toByteArray());
						InputStream fis = new BufferedInputStream(inputStream);
						// 导出excel
						String fileName = "其他平台用户来源订单统计列表导出.xls";
						String encodingName = new String(fileName.getBytes("GB2312"),
								"ISO8859_1");
						response.reset();
						response.setContentType("application/x-download; charset=UTF-8");
						response.setHeader("Content-Disposition", "attachment; filename="
								+ encodingName);
						os = response.getOutputStream();
						byte[] b = new byte['?'];
						int i = 0;
						while ((i = fis.read(b)) > 0) {
							os.write(b, 0, i);
						}
						os.flush();

				
			}
	
	

	/**
	 * 跳转挥货平台订单来源统计页
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/hhOrderSourceStatistics")
	public ModelAndView hhOrderSourceStatistics(HttpServletRequest httpRequest) {
		return new ModelAndView("/admin/dataanaly/hh_ordersource_statistics");
	}

	/**
	 * 根据挥货订单来源统计订单金额
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/hhQueryTotalByOrderSource")
	public void hhQueryTotalByOrderSource(HttpServletRequest httpRequest,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<OrderSalesVo> list = null;
		List<OrderSalesVo> listNew = new ArrayList<OrderSalesVo>();
		try {
			list = this.orderSalesManager.queryTotalByOrderSourceListPage(pd);
			if (list == null) {
				list = new ArrayList<OrderSalesVo>();
			}
			for (int i = 0; i < list.size(); i++) {
				OrderSalesVo vo = list.get(i);
				pd.put("source", vo.getOrderSource());
				List orderIdList = this.orderSalesManager.queryTotalOrderIdByOrderSourceList(pd);
				String orderIds = orderIdList.toString();
				orderIds = orderIds.substring(1, orderIds.length()-1);
				pd.put("orderIds", orderIds);
				List<OrderSalesVo> productCountList = this.orderSalesManager.queryProductCountByCustSourceList(pd);
				vo.setProductCount(productCountList.get(0).getProductCount());
				listNew.add(vo);
				}
		} catch (Exception e) {
			logger.info("/admin/orderanaly/hhQueryTotalByOrderSource --error",
					e);
		}

		this.outEasyUIDataToJson(pd, listNew, response);
	}
	
	/**
	 * 根据挥货订单来源统计订单金额
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportExcelByOrderSource")
	public void exportExcelByOrderSource(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("/admin/orderanaly/exportExcelByOrderSource/begin");

		PageData pd = this.getPageData();
		List<OrderSalesVo> listNew = new ArrayList<OrderSalesVo>();
		List<OrderSalesVo> list = orderSalesManager.queryTotalByOrderSourceListPage(pd);
		if (list == null) {
			list = new ArrayList<OrderSalesVo>();
		}
		for (int i = 0; i < list.size(); i++) {
			OrderSalesVo vo = list.get(i);
			pd.put("source", vo.getOrderSource());
			List orderIdList = this.orderSalesManager.queryTotalOrderIdByOrderSourceList(pd);
			String orderIds = orderIdList.toString();
			orderIds = orderIds.substring(1, orderIds.length()-1);
			pd.put("orderIds", orderIds);
			List<OrderSalesVo> productCountList = this.orderSalesManager.queryProductCountByCustSourceList(pd);
			vo.setProductCount(productCountList.get(0).getProductCount());
			listNew.add(vo);
			}
		try {
			this.exportByOrderSource(listNew, request, response);
		} catch (Exception e) {
			logger.error("导出其他平台用户来源订单统计列表报错", e);
		}
		logger.info("/admin/orderanaly/exportExcelByCust/end");
	}

	private void exportByOrderSource(List<OrderSalesVo> list, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HSSFWorkbook work = new HSSFWorkbook();
		InputStream in = null;
		OutputStream os = null;

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
			// headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 头部
			HSSFRow head = sheet.createRow(0);

			sheet.setColumnWidth(0, 20 * 256);
			sheet.setDefaultColumnWidth(20);
			sheet.setColumnWidth(10, 30 * 300);
			head.createCell(0).setCellValue("订单来源");
			head.createCell(1).setCellValue("订单金额");
			head.createCell(2).setCellValue("实付金额");
			head.createCell(3).setCellValue("订单份数");
			head.createCell(4).setCellValue("商品份数");
			head.getCell(0).setCellStyle(headstyle);
			head.getCell(1).setCellStyle(headstyle);
			head.getCell(2).setCellStyle(headstyle);
			head.getCell(3).setCellStyle(headstyle);
			head.getCell(4).setCellStyle(headstyle);
			// 从第三行开始填充数据
			int rowNum = 1;
			if (list != null && list.size() > 0) {
				for (OrderSalesVo orderSalesVo : list) {
							HSSFRow row = sheet.createRow(rowNum);
							// 用户来源
							row.createCell(0).setCellValue(
									orderSalesVo.getOrderSource());
							// 订单金额
							row.createCell(1).setCellValue(
									orderSalesVo.getTotalPrice());
							// 实付金额
							row.createCell(2).setCellValue(
									orderSalesVo.getTotalPay());
							// 订单份数
							row.createCell(3).setCellValue(
									orderSalesVo.getOrderCount());
							// 商品份数
							row.createCell(4).setCellValue(
									orderSalesVo.getProductCount());
							rowNum++;
						}
					}
			// 以文件流形式导出
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						work.write(out);
						InputStream inputStream = new ByteArrayInputStream(
								out.toByteArray());
						InputStream fis = new BufferedInputStream(inputStream);
						// 导出excel
						String fileName = "订单来源订单统计列表导出.xls";
						String encodingName = new String(fileName.getBytes("GB2312"),
								"ISO8859_1");
						response.reset();
						response.setContentType("application/x-download; charset=UTF-8");
						response.setHeader("Content-Disposition", "attachment; filename="
								+ encodingName);
						os = response.getOutputStream();
						byte[] b = new byte['?'];
						int i = 0;
						while ((i = fis.read(b)) > 0) {
							os.write(b, 0, i);
						}
						os.flush();

				
			}

	/**
	 * 跳转其他平台订单来源统计页
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/otherOrderSourceStatistics")
	public ModelAndView otherOrderSourceStatistics(
			HttpServletRequest httpRequest) {
		return new ModelAndView("/admin/dataanaly/other_ordersource_statistics");
	}

	/**
	 * 根据挥货订单来源统计其他平台订单金额
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/otherQueryTotalByOrderSource")
	public void otherQueryTotalByOrderSource(HttpServletRequest httpRequest,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<OrderSalesVo> list = null;

		try {
			list = this.orderSalesManager
					.queryOtherTotalByOrderSourceListPage(pd);
			if (list == null) {
				list = new ArrayList<OrderSalesVo>();
			}
		} catch (Exception e) {
			logger.info(
					"/admin/orderanaly/otherQueryTotalByOrderSource --error", e);
		}

		this.outEasyUIDataToJson(pd, list, response);

	}
	
	/**
	 * 根据挥货订单来源统计其他平台订单金额
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportExcelByOtherOrderSource")
	public void exportExcelByOtherOrderSource(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("/admin/orderanaly/exportExcelByOtherOrderSource/begin");

		PageData pd = this.getPageData();
		List<OrderSalesVo> list = orderSalesManager.queryOtherTotalByOrderSourceListPage(pd);
		if (list == null || list.size() <= 0) {
			list = new ArrayList<OrderSalesVo>();
		}
		try {
			this.exportByOtherOrderSource(list, request, response);
		} catch (Exception e) {
			logger.error("导出其他平台订单来源订单统计列表报错", e);
		}
		logger.info("/admin/orderanaly/exportExcelByCust/end");
	}

	private void exportByOtherOrderSource(List<OrderSalesVo> list, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HSSFWorkbook work = new HSSFWorkbook();
		InputStream in = null;
		OutputStream os = null;

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
			// headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 头部
			HSSFRow head = sheet.createRow(0);

			sheet.setColumnWidth(0, 20 * 256);
			sheet.setDefaultColumnWidth(20);
			sheet.setColumnWidth(10, 30 * 300);
			head.createCell(0).setCellValue("订单来源");
			head.createCell(1).setCellValue("订单金额");
			head.createCell(2).setCellValue("实付金额");
			head.createCell(3).setCellValue("订单份数");
			head.createCell(4).setCellValue("商品份数");
			head.getCell(0).setCellStyle(headstyle);
			head.getCell(1).setCellStyle(headstyle);
			head.getCell(2).setCellStyle(headstyle);
			head.getCell(3).setCellStyle(headstyle);
			head.getCell(4).setCellStyle(headstyle);
			// 从第三行开始填充数据
			int rowNum = 1;
			if (list != null && list.size() > 0) {
				for (OrderSalesVo orderSalesVo : list) {
							HSSFRow row = sheet.createRow(rowNum);
							// 用户来源
							row.createCell(0).setCellValue(
									orderSalesVo.getOrderSource());
							// 订单金额
							row.createCell(1).setCellValue(
									orderSalesVo.getTotalPrice());
							// 实付金额
							row.createCell(2).setCellValue(
									orderSalesVo.getTotalPay());
							// 订单份数
							row.createCell(3).setCellValue(
									orderSalesVo.getOrderCount());
							// 商品份数
							row.createCell(4).setCellValue(
									orderSalesVo.getProductCount());
							rowNum++;
						}
					}
			// 以文件流形式导出
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						work.write(out);
						InputStream inputStream = new ByteArrayInputStream(
								out.toByteArray());
						InputStream fis = new BufferedInputStream(inputStream);
						// 导出excel
						String fileName = "其他平台订单来源订单统计列表导出.xls";
						String encodingName = new String(fileName.getBytes("GB2312"),
								"ISO8859_1");
						response.reset();
						response.setContentType("application/x-download; charset=UTF-8");
						response.setHeader("Content-Disposition", "attachment; filename="
								+ encodingName);
						os = response.getOutputStream();
						byte[] b = new byte['?'];
						int i = 0;
						while ((i = fis.read(b)) > 0) {
							os.write(b, 0, i);
						}
						os.flush();

				
			}

	/**
	 * 跳转挥货平台商品销量
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/hhProductSalseStatistics")
	public ModelAndView hhProductSalseStatistics(HttpServletRequest httpRequest) {
		return new ModelAndView("admin/dataanaly/hh_productsales_statistics");
	}

	/**
	 * 挥货平台商品销量
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/hhQueryProductSales")
	public void hhQueryProductSales(HttpServletRequest httpRequest,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<OrderSalesVo> list = null;

		try {
			list = this.orderSalesManager.queryProductSalesListPage(pd);
			if (list == null) {
				list = new ArrayList<OrderSalesVo>();
			}
		} catch (Exception e) {
			logger.info("/admin/orderanaly/hhQueryProductSales --error", e);
		}

		this.outEasyUIDataToJson(pd, list, response);

	}
	
	/**
	 * 跳转挥货平台商品销量
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportExcelhhPro")
	public void exportExcelhhPro(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("/admin/orderanaly/exportExcelhhPro/begin");

		PageData pd = this.getPageData();
		List<OrderSalesVo> list = orderSalesManager.queryProductSalesListPage(pd);
		if (list == null || list.size() <= 0) {
			list = new ArrayList<OrderSalesVo>();
		}
		try {
			this.exporthhPro(list, request, response);
		} catch (Exception e) {
			logger.error("导出挥货平台商品销量列表报错", e);
		}
		logger.info("/admin/orderanaly/exportExcelhhPro/end");
	}

	private void exporthhPro(List<OrderSalesVo> list, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HSSFWorkbook work = new HSSFWorkbook();
		InputStream in = null;
		OutputStream os = null;

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
			// headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 头部
			HSSFRow head = sheet.createRow(0);

			sheet.setColumnWidth(0, 20 * 256);
			sheet.setDefaultColumnWidth(20);
			sheet.setColumnWidth(10, 30 * 300);
			head.createCell(0).setCellValue("商品名称");
			head.createCell(1).setCellValue("商品销量");
			head.createCell(2).setCellValue("商品金额");
			head.createCell(3).setCellValue("实销金额");
			head.getCell(0).setCellStyle(headstyle);
			head.getCell(1).setCellStyle(headstyle);
			head.getCell(2).setCellStyle(headstyle);
			head.getCell(3).setCellStyle(headstyle);
			// 从第三行开始填充数据
			int rowNum = 1;
			if (list != null && list.size() > 0) {
				for (OrderSalesVo orderSalesVo : list) {
							HSSFRow row = sheet.createRow(rowNum);
							// 商品名称
							row.createCell(0).setCellValue(
									orderSalesVo.getProductName());
							// 商品销量
							row.createCell(1).setCellValue(
									orderSalesVo.getQuantity());
							// 商品金额
							row.createCell(2).setCellValue(
									orderSalesVo.getTotalPrice());
							//实销金额
							row.createCell(3).setCellValue(
									orderSalesVo.getTotalPay());
							rowNum++;
						}
					}
			// 以文件流形式导出
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						work.write(out);
						InputStream inputStream = new ByteArrayInputStream(
								out.toByteArray());
						InputStream fis = new BufferedInputStream(inputStream);
						// 导出excel
						String fileName = "挥货平台商品销量列表导出.xls";
						String encodingName = new String(fileName.getBytes("GB2312"),
								"ISO8859_1");
						response.reset();
						response.setContentType("application/x-download; charset=UTF-8");
						response.setHeader("Content-Disposition", "attachment; filename="
								+ encodingName);
						os = response.getOutputStream();
						byte[] b = new byte['?'];
						int i = 0;
						while ((i = fis.read(b)) > 0) {
							os.write(b, 0, i);
						}
						os.flush();

				
			}
	

	/**
	 * 跳转挥货其他平台商品销量
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/otherProductSalseStatistics")
	public ModelAndView otherProductSalseStatistics(
			HttpServletRequest httpRequest) {
		return new ModelAndView("admin/dataanaly/other_productsales_statistics");
	}

	/**
	 * 其他平台商品销量
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/otherQueryProductSales")
	public void otherQueryProductSales(HttpServletRequest httpRequest,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<OrderSalesVo> list = null;

		try {
			list = this.orderSalesManager.queryOtherProductSalesListPage(pd);
			if (list == null) {
				list = new ArrayList<OrderSalesVo>();
			}
		} catch (Exception e) {
			logger.error("/admin/orderanaly/otherQueryProductSales --error", e);
		}

		this.outEasyUIDataToJson(pd, list, response);

	}
	
	/**
	 * 跳转其他平台商品销量
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportExcelOtherPro")
	public void exportExcelOtherPro(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("/admin/orderanaly/exportExcelOtherPro/begin");

		PageData pd = this.getPageData();
		List<OrderSalesVo> list = orderSalesManager.queryOtherProductSalesListPage(pd);
		if (list == null || list.size() <= 0) {
			list = new ArrayList<OrderSalesVo>();
		}
		try {
			this.exportOtherPro(list, request, response);
		} catch (Exception e) {
			logger.error("导出其他平台商品销量列表报错", e);
		}
		logger.info("/admin/orderanaly/exportExcelOtherPro/end");
	}

	private void exportOtherPro(List<OrderSalesVo> list, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HSSFWorkbook work = new HSSFWorkbook();
		InputStream in = null;
		OutputStream os = null;

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
			// headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 头部
			HSSFRow head = sheet.createRow(0);

			sheet.setColumnWidth(0, 20 * 256);
			sheet.setDefaultColumnWidth(20);
			sheet.setColumnWidth(10, 30 * 300);
			head.createCell(0).setCellValue("商品名称");
			head.createCell(1).setCellValue("商品销量");
			head.createCell(2).setCellValue("商品金额");
			head.createCell(3).setCellValue("实销金额");
			head.getCell(0).setCellStyle(headstyle);
			head.getCell(1).setCellStyle(headstyle);
			head.getCell(2).setCellStyle(headstyle);
			head.getCell(3).setCellStyle(headstyle);
			// 从第三行开始填充数据
			int rowNum = 1;
			if (list != null && list.size() > 0) {
				for (OrderSalesVo orderSalesVo : list) {
							HSSFRow row = sheet.createRow(rowNum);
							// 商品名称
							row.createCell(0).setCellValue(
									orderSalesVo.getProductName());
							// 商品销量
							row.createCell(1).setCellValue(
									orderSalesVo.getQuantity());
							// 商品金额
							row.createCell(2).setCellValue(
									orderSalesVo.getTotalPrice());
							//实销金额
							row.createCell(3).setCellValue(
									orderSalesVo.getTotalPay());
							rowNum++;
						}
					}
			// 以文件流形式导出
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						work.write(out);
						InputStream inputStream = new ByteArrayInputStream(
								out.toByteArray());
						InputStream fis = new BufferedInputStream(inputStream);
						// 导出excel
						String fileName = "其他平台商品销量列表导出.xls";
						String encodingName = new String(fileName.getBytes("GB2312"),
								"ISO8859_1");
						response.reset();
						response.setContentType("application/x-download; charset=UTF-8");
						response.setHeader("Content-Disposition", "attachment; filename="
								+ encodingName);
						os = response.getOutputStream();
						byte[] b = new byte['?'];
						int i = 0;
						while ((i = fis.read(b)) > 0) {
							os.write(b, 0, i);
						}
						os.flush();

				
			}
}
