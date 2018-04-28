package com.kingleadsw.betterlive.controller.web.order;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.OtherOrderManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.OtherOrderVo;

/**
 * 非挥货平台订单
 *
 */
@Controller
@RequestMapping(value = "/admin/otherorder")
public class OtherOrderController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(OtherOrderController.class);

	@Autowired
	private OtherOrderManager otherOrderManager;

	/**
	 * 跳转订单管理页面
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/findList")
	public ModelAndView findList(HttpServletRequest httpRequest) {
		ModelAndView modelAndView = new ModelAndView(
				"admin/order/list_otherorder");
		PageData pd = this.getPageData();
		try {
			if (StringUtil.isNotNull(pd.getString("startTime"))) {
				String queryDate = pd.getString("startTime");
				modelAndView.addObject("startTime", queryDate + " 00:00:00");
				if (StringUtil.isNotNull(pd.getString("endTime"))) {
					modelAndView.addObject("endTime", pd.getString("endTime")
							+ " 23:59:59");
				}
				modelAndView.addObject("queryFlag", pd.getString("queryFlag"));
			}
			if (StringUtil.isNotNull(pd.getString("customerSource"))) {
				String cs = new String(pd.getString("customerSource").getBytes(
						"iso8859-1"), "utf-8");
				modelAndView.addObject("customerSource", cs);
			}
			if (StringUtil.isNotNull(pd.getString("orderSource"))) {
				String os = new String(pd.getString("orderSource").getBytes(
						"iso8859-1"), "utf-8");
				modelAndView.addObject("orderSource", os);
			}
			if (StringUtil.isNotNull(pd.getString("productCode"))) {
				modelAndView.addObject("productCode",
						pd.getString("productCode"));
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("/admin/order/findList ---error", e);
		}
		return modelAndView;
	}

	/**
	 * 查询订单管理
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/queryOrderAllJson")
	public void queryOrderAllJson(HttpServletRequest httpRequest,
			HttpServletResponse response) {
		String msg = "queryOrderAllJson";
		logger.info("/admin/otherorder/" + msg + " begin");

		List<OtherOrderVo> list = null;

		try {
			PageData pd = this.getPageData();
			if ("æªç¥æ¥æº".contains(pd.getString("customerSource"))) {
				pd.put("customerSource",
						new String(pd.getString("customerSource").getBytes(
								"iso8859-1"), "utf-8"));
			}
			if ("æªç¥æ¥æº".contains(pd.getString("orderSource"))) {
				pd.put("orderSource", new String(pd.getString("orderSource")
						.getBytes("iso8859-1"), "utf-8"));
			}
			pd.put("productCode", pd.getString("productCode"));
			list = this.otherOrderManager.queryListPage(pd);
			if (list == null) {
				list = new ArrayList<OtherOrderVo>();
			}
			this.outEasyUIDataToJson(pd, list, response);
		} catch (Exception e) {
			logger.error("/admin/otherorder/queryOrderAllJson 出现异常.... ", e);
		}

		logger.info("--->结束调用/admin/otherorder/" + msg);

	}

	@RequestMapping(value = "importOtherOrders", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> importOtherOrders(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		try {
			InputStream in = null;
			MultipartFile file = multipartRequest.getFile("excel");
			if (file == null || file.isEmpty()) {
				return CallBackConstant.FAILED.callbackError("文件不存在");
			}

			in = file.getInputStream();
			List<List<Object>> listob = getBankListByExcel(in,
					file.getOriginalFilename());
			List<OtherOrderVo> list = new ArrayList<OtherOrderVo>();

			for (int i = 0; i < listob.size(); i++) {
				if (i == 0) {
					continue; // 第一行为表头
				}
				List<Object> lo = listob.get(i);
				if (lo == null || lo.get(0) == null || lo.get(1) == null
						|| lo.get(2) == null) {
					continue;
				}
				OtherOrderVo ord = new OtherOrderVo();
				ord.setPayCode(String.valueOf(lo.get(0)).trim());
				ord.setOrderCode(String.valueOf(lo.get(1)).trim());
				ord.setProductCode(String.valueOf(lo.get(2)).trim());
				ord.setCreateTime(String.valueOf(lo.get(3)).trim());
				ord.setPayTime(String.valueOf(lo.get(4)).trim());
				ord.setProductName(String.valueOf(lo.get(5)).trim());
				ord.setSpecName(String.valueOf(lo.get(6)).trim());
				ord.setPrice(String.valueOf(lo.get(7)).trim());
				ord.setTotalPrice(String.valueOf(lo.get(8)).trim());
				ord.setPayMoney(String.valueOf(lo.get(9)).trim());
				ord.setQuantity(Integer.parseInt((String.valueOf(lo.get(10)))
						.trim()));
				ord.setCustomerName(String.valueOf(lo.get(11)).trim());
				ord.setOrderMobile(String.valueOf(lo.get(12)).trim());
				ord.setOrderAddress(String.valueOf(lo.get(13)).trim());
				ord.setOrderStatus(String.valueOf(lo.get(14)).trim());
				ord.setSendTime(String.valueOf(lo.get(15)).trim());
				ord.setLogisticsName(String.valueOf(lo.get(16)).trim());
				ord.setLogisticsCode(String.valueOf(lo.get(17)).trim());
				ord.setCustomerSource(String.valueOf(lo.get(18)).trim());
				ord.setOrderSource(String.valueOf(lo.get(19)).trim());
				
				if (ord.getLogisticsCode().substring((ord.getLogisticsCode().length()-3), ord.getLogisticsCode().length()).equals(".00")) {
					ord.setLogisticsCode(ord.getLogisticsCode().substring(0, ord.getLogisticsCode().length()-3));
				}
				list.add(ord);
			}
			in.close();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("list", list);
			otherOrderManager.createOrders(params);

			return CallBackConstant.SUCCESS.callback();
		} catch (Exception e) {
			logger.error("/admin/orderanaly/importOtherOrders -- error", e);
			return CallBackConstant.FAILED.callback();
		}
	}

	/**
	 * 描述：获取IO流中的数据，组装成List<List<Object>>对象
	 * 
	 * @param in
	 *            ,fileName
	 * @return
	 * @throws IOException
	 */
	public List<List<Object>> getBankListByExcel(InputStream in, String fileName)
			throws Exception {
		List<List<Object>> list = null;
		// 创建Excel工作薄
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		Workbook work = null;
		if (".xls".equals(fileType)) {
			work = this.getWorkbook2003(in, fileType);
		} else {
			work = this.getWorkbook2007(in, fileType);
		}
		if (null == work) {
			throw new Exception("创建Excel工作薄为空！");
		}
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;

		list = new ArrayList<List<Object>>();
		// 遍历Excel中所有的sheet
		for (int i = 0; i < work.getNumberOfSheets(); i++) {
			sheet = work.getSheetAt(i);
			if (sheet == null) {
				continue;
			}

			// 遍历当前sheet中的所有行
			for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
				row = sheet.getRow(j);
				if (row == null || row.getFirstCellNum() == j) {
					continue;
				}

				// 遍历所有的列
				List<Object> li = new ArrayList<Object>();
				for (int y = 0; y <= row.getLastCellNum(); y++) {
					cell = row.getCell(y);
					if (StringUtil.isNull(cell)
							|| cell.toString().length() <= 0) {
						if (10 == y) {
							Object o = new Object();
							o = 0;
							li.add(o);
						} else {
							li.add("");
						}
						continue;
					}
					li.add(this.getCellValue(cell));
				}
				list.add(li);
			}
		}
		return list;
	}

	/**
	 * 描述：根据文件后缀，自适应上传文件的版本
	 * 
	 * @param inStr
	 *            ,fileName
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook getWorkbook2003(InputStream inStr, String fileType)
			throws Exception {
		String excel2003L = ".xls"; // 2003- 版本的excel
		HSSFWorkbook wb = null;
		if (excel2003L.equals(fileType)) {
			wb = new HSSFWorkbook(inStr); // 2003-
		} else {
			throw new Exception("解析的文件格式有误！");
		}
		return wb;
	}

	/**
	 * 描述：根据文件后缀，自适应上传文件的版本
	 * 
	 * @param inStr
	 *            ,fileName
	 * @return
	 * @throws Exception
	 */
	public XSSFWorkbook getWorkbook2007(InputStream inStr, String fileType)
			throws Exception {
		String excel2007U = ".xlsx"; // 2007+ 版本的excel
		XSSFWorkbook wb = null;
		if (excel2007U.equals(fileType)) {
			wb = new XSSFWorkbook(inStr); // 2007+
		} else {
			throw new Exception("解析的文件格式有误！");
		}
		return wb;
	}

	/**
	 * 描述：对表格中数值进行格式化
	 * 
	 * @param cell
	 * @return
	 */
	public Object getCellValue(Cell cell) {
		Object value = null;
		DecimalFormat df = new DecimalFormat("0"); // 格式化number String字符
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
		DecimalFormat df2 = new DecimalFormat("0.00"); // 格式化数字

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if ("General".equals(cell.getCellStyle().getDataFormatString())) {
				value = df.format(cell.getNumericCellValue());
			} else if ("m/d/yy".equals(cell.getCellStyle()
					.getDataFormatString())) {
				value = sdf.format(cell.getDateCellValue());
			} else {
				value = df2.format(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_BLANK:
			value = "";
			break;
		default:
			break;
		}
		return value;
	}

}
