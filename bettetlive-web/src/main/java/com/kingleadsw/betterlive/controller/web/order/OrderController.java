package com.kingleadsw.betterlive.controller.web.order;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.CouponInfoManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.LogisticsCompanyManager;
import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.biz.PayLogManager;
import com.kingleadsw.betterlive.biz.UserSingleCouponManager;
import com.kingleadsw.betterlive.consts.WebConstant;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.util.alipay.service.OneCardPayService;
import com.kingleadsw.betterlive.util.wx.WxUtil;
import com.kingleadsw.betterlive.util.wx.templatemessage.dto.TemplateMsgBodyDto;
import com.kingleadsw.betterlive.util.wx.templatemessage.dto.TemplateMsgDataDto;
import com.kingleadsw.betterlive.util.wx.templatemessage.util.WxTemplateMessageUtil;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.LogisticsCompanyVo;
import com.kingleadsw.betterlive.vo.MessageVo;
import com.kingleadsw.betterlive.vo.OrderProductVo;
import com.kingleadsw.betterlive.vo.OrderVo;
import com.kingleadsw.betterlive.vo.PayLogVo;
import com.kingleadsw.betterlive.vo.UserSingleCouponVo;

@Controller
@RequestMapping(value = "/admin/order")
public class OrderController extends AbstractWebController {

	private static Logger logger = Logger.getLogger(OrderController.class);

	@Autowired
	private OrderManager orderManager;
	@Autowired
	private CouponInfoManager couponInfoManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private PayLogManager payLogManager;
	@Autowired
	private OrderProductManager orderProductManager;
	@Autowired
	private UserSingleCouponManager userSingleCouponManager;
	@Autowired
	private LogisticsCompanyManager logisticsCompanyManager;

	@Autowired
	private MessageManager messageManager;
	@Autowired
	private OneCardPayService oneCardPayService;
	/**
	 * 跳转订单管理页面
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/findList")
	public ModelAndView findList(HttpServletRequest httpRequest) {
		ModelAndView modelAndView = new ModelAndView(
				"admin/order/list_order_manager");
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
			if (StringUtil.isNotNull(pd.getString("productId"))) {
				modelAndView.addObject("productId", pd.getString("productId"));
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
		logger.info("/admin/order/" + msg + " begin");
		List<OrderVo> list = null;

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
				pd.put("productId", pd.getString("productId"));
			// pd.put("statusALL","0");
			list = orderManager.findAllorderListPage(pd);
			if (null == list || list.isEmpty()) {
				list = new ArrayList<OrderVo>();
			} else {
				for (OrderVo orderVo : list) {
					List<OrderProductVo> orderproducts = orderVo
							.getListOrderProductVo();
					BigDecimal couponMoney = BigDecimal.ZERO;
					if (StringUtils.isNotBlank(orderVo.getConpon_money())) {// 红包券使用金额
						couponMoney = couponMoney.add(new BigDecimal(orderVo
								.getConpon_money()));
					}
					for (OrderProductVo orderProductVo : orderproducts) {// 单品红包使用金额

						if (orderProductVo.getStatus() == null
								|| orderProductVo.getStatus() == 0) {
							orderProductVo.setStatus(orderVo.getStatus());
						}
						if (orderProductVo.getCoupon_money() != null) {
							couponMoney = couponMoney.add(orderProductVo
									.getCoupon_money());
						}
					}
					orderVo.setConpon_money(couponMoney.toString());// 设置优惠金额
				}
			}

			this.outEasyUIDataToJson(pd, list, response);
		} catch (Exception e) {
			logger.error("查询优订单列表出现异常.... ");
		}

		logger.info("--->结束调用/admin/order/" + msg);

	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportExcel")
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("/admin/order/exportExcel/begin");

		PageData pd = this.getPageData();
		// pd.put("statusALL","0");
		// pd.remove("hasBuy");
		List<OrderVo> list = orderManager.queryList(pd);
		if (list == null || list.size() <= 0) {
			list = new ArrayList<OrderVo>();
		}
		try {
			this.export(list, request, response);
		} catch (Exception e) {
			logger.error("导出订单列表报错", e);
		}
		logger.info("/admin/order/exportExcel/end");
	}

	private void export(List<OrderVo> list, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
			// headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 头部
			HSSFRow head = sheet.createRow(0);

			sheet.setColumnWidth(0, 20 * 256);
			sheet.setDefaultColumnWidth(20);
			sheet.setColumnWidth(10, 30 * 300);
			head.createCell(0).setCellValue("支付流水号");
			head.createCell(1).setCellValue("订单编码");
			head.createCell(2).setCellValue("商品单号");
			head.createCell(3).setCellValue("支付时间");
			head.createCell(4).setCellValue("姓名");
			head.createCell(5).setCellValue("电话");
			head.createCell(6).setCellValue("项目名称");
			head.createCell(7).setCellValue("购买规格");
			head.createCell(8).setCellValue("份数");
			head.createCell(9).setCellValue("地址");
			head.createCell(10).setCellValue("小计");
			head.createCell(11).setCellValue("优惠金额");
			head.createCell(12).setCellValue("红包金额");
			head.createCell(13).setCellValue("礼品卡支付");
			head.createCell(14).setCellValue("实际支付总金额");
			head.createCell(15).setCellValue("订单状态");
			head.createCell(16).setCellValue("发货时间");
			head.createCell(17).setCellValue("物流公司");
			head.createCell(18).setCellValue("物流编号");
			head.createCell(19).setCellValue("用户来源");
			head.createCell(20).setCellValue("订单留言");
			
			head.getCell(0).setCellStyle(headstyle);
			head.getCell(1).setCellStyle(headstyle);
			head.getCell(2).setCellStyle(headstyle);
			head.getCell(3).setCellStyle(headstyle);
			head.getCell(4).setCellStyle(headstyle);
			head.getCell(5).setCellStyle(headstyle);
			head.getCell(6).setCellStyle(headstyle);
			head.getCell(7).setCellStyle(headstyle);
			head.getCell(8).setCellStyle(headstyle);
			head.getCell(9).setCellStyle(headstyle);
			head.getCell(10).setCellStyle(headstyle);
			head.getCell(11).setCellStyle(headstyle);
			head.getCell(12).setCellStyle(headstyle);
			head.getCell(13).setCellStyle(headstyle);
			head.getCell(14).setCellStyle(headstyle);
			head.getCell(15).setCellStyle(headstyle);
			head.getCell(16).setCellStyle(headstyle);
			head.getCell(17).setCellStyle(headstyle);
			head.getCell(18).setCellStyle(headstyle);
			head.getCell(19).setCellStyle(headstyle);
			head.getCell(20).setCellStyle(headstyle);
			// 从第三行开始填充数据
			int rowNum = 1;
			PageData pd = new PageData();
			if (list != null && list.size() > 0) {
				for (OrderVo orderVo : list) {
					boolean combine = true;
					pd.put("orderId", orderVo.getOrder_id());
					List<OrderProductVo> detail = orderProductManager
							.findListOrderProduct(pd);
					if (detail != null) {
						for (OrderProductVo orderProductVo : detail) {
							HSSFRow row = sheet.createRow(rowNum);
							// 微信支付流水
							row.createCell(0).setCellValue(
									orderVo.getTrans_id());
							// 订单code
							row.createCell(1).setCellValue(
									orderVo.getOrder_code());
							// 订单商品code
							row.createCell(2).setCellValue(
									orderProductVo.getSub_order_code());
							// 订单时间
							row.createCell(3).setCellValue(
									orderVo.getOrder_time());
							// 用户姓名
							row.createCell(4).setCellValue(
									orderVo.getReceiver());
							// 手机
							row.createCell(5).setCellValue(orderVo.getMobile());
							// 项目名称
							row.createCell(6).setCellValue(
									orderProductVo.getProduct_name());
							// 购买规格
							row.createCell(7).setCellValue(
									orderProductVo.getSpec_name());
							// 份数
							row.createCell(8).setCellValue(
									orderProductVo.getQuantity());
							// 收货地址
							row.createCell(9).setCellValue(
									orderVo.getAddress());
							// 小计
							row.createCell(10).setCellValue(
											new BigDecimal(orderProductVo.getPrice())
													.multiply(new BigDecimal(orderProductVo.getQuantity()))
													.doubleValue());

							// 优惠金额
							BigDecimal subs = new BigDecimal("0");
							Double salePrice = 0d;
							if (orderProductVo.getActivity_price() != null) {
								subs = new BigDecimal(orderProductVo.getPrice())
										.subtract(new BigDecimal(orderProductVo.getActivity_price()));
								salePrice = subs.multiply(
										new BigDecimal(orderProductVo.getQuantity())).doubleValue();
							}
							
							if (orderProductVo.getCut_money() != null) {// 满减金额
								salePrice = new BigDecimal(salePrice).add(
										orderProductVo.getCut_money()).doubleValue();
							}
							if(StringUtil.isNotNull(orderProductVo.getDiscount_price()) 
									&& !orderProductVo.getDiscount_price().equals("0")){
								subs = new BigDecimal(0);
								subs = new BigDecimal(orderProductVo.getPrice()).subtract(
										new BigDecimal(orderProductVo.getDiscount_price()));
								subs = subs.multiply(new BigDecimal(orderProductVo.getQuantity()));
								salePrice = new BigDecimal(salePrice).subtract(subs).doubleValue();
							}
							if(salePrice < 0d){
								salePrice = 0d;
							}
							row.createCell(11).setCellValue(salePrice);
							if (combine) {
								// 红包金额
								if (orderVo.getConpon_money() != null) {
									row.createCell(12).setCellValue(
											new BigDecimal(orderVo.getConpon_money())
													.doubleValue());
								} else if (orderProductVo.getCoupon_money() != null) {// 单品红包金额
									row.createCell(12).setCellValue(
											orderProductVo.getCoupon_money()
													.doubleValue());
								} else {
									row.createCell(12).setCellValue("0.00");
								}

								// 礼品卡支付
								row.createCell(13).setCellValue(
										orderVo.getGitf_card_money());
								// 支付总金额
								row.createCell(14).setCellValue(
										orderVo.getPay_money());
							} else {
								// 红包金额
								row.createCell(12).setCellValue("");
								// 礼品卡支付
								row.createCell(13).setCellValue("");
								// 支付总金额
								row.createCell(14).setCellValue("");
							}
							if (orderProductVo.getStatus() == null) {
								row.createCell(15)
										.setCellValue(
												getOrderStatusDesc(orderVo
														.getStatus()));
							} else {
								row.createCell(15).setCellValue(
										getOrderStatusDesc(orderProductVo
												.getStatus()));
							}

							String sendTime = "";
							if (orderProductVo.getSend_time() != null) {
								sendTime = orderProductVo.getSend_time();
							}
							// 设置发货时间
							row.createCell(16).setCellValue(sendTime);
							// 设置物流公司
							String company = "";
							if (StringUtil.isNotNull(orderProductVo.getCompany_name())) {
									company = orderProductVo.getCompany_name();
							}
							row.createCell(17).setCellValue(company);

							// 设置物流编号
							row.createCell(18).setCellValue(
									orderProductVo.getLogistics_code());
							// 设置用户来源
							row.createCell(19).setCellValue(orderVo.getCustomerSource());
							row.createCell(20).setCellValue(orderVo.getMessage_info());
							rowNum++;
							combine = false;
						}
					}

				}
			}

			// 以文件流形式导出
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			work.write(out);
			InputStream inputStream = new ByteArrayInputStream(
					out.toByteArray());
			InputStream fis = new BufferedInputStream(inputStream);

			// 导出excel
			String fileName = "订单导出.xls";
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
		} catch (IOException e) {
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

	/**
	 * 根据订单状态值，获取订单状态描述
	 * 
	 * @param num
	 * @return
	 */
	private String getOrderStatusDesc(int num) {
		// 订单状态，0：已删除；1：待付款；2：待发货；3：待签收；4：待评价；5：已完成
		String str = "";
		if (num == 0) {
			str = "已删除";
		} else if (num == 1) {
			str = "待付款";
		} else if (num == 2) {
			str = "待发货";
		} else if (num == 3) {
			str = "待签收";
		} else if (num == 4) {
			str = "待评价";
		} else if (num == 5) {
			str = "已完成";
		} else if (num == 6) {
			str = "已取消";
		} else if (num == 7) {
			str = "已退款";
		}
		return str;
	}

	@RequestMapping(value = "/queryLogisticsCompany")
	@ResponseBody
	public Map<String, Object> queryLogisticsCompany(
			HttpServletRequest httpRequest) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<LogisticsCompanyVo> logisticsCompanyList = logisticsCompanyManager
				.queryList(new PageData());
		PageData pd = new PageData();
		pd.put("orderpro_id", httpRequest.getParameter("order_product_id"));
		OrderProductVo orderProductVo = orderProductManager.queryOne(pd);
		map.put("code", 1010);
		map.put("data", logisticsCompanyList);
		if (orderProductVo != null) {
			map.put("company_code", orderProductVo.getCompany_code());
			map.put("logistics_code", orderProductVo.getLogistics_code());
		}

		return map;
	}

	@RequestMapping(value = "/saveLogisticsInfo")
	@ResponseBody
	public Map<String, Object> saveLogisticsInfo(HttpServletRequest request) {
		PageData pd = this.getPageData();
		Integer order_product_id = pd.getInteger("order_product_id");
		String company_code = pd.getString("company_code");
		String logistics_code = pd.getString("logistics_code");

		if (order_product_id == null || order_product_id == 0) {
			return CallBackConstant.FAILED.callback("order_product_id不能为空");
		}

		if (StringUtils.isEmpty(company_code)) {
			return CallBackConstant.FAILED.callback("快递公司不能为空");
		}

		if (StringUtils.isEmpty(logistics_code)) {
			return CallBackConstant.FAILED.callback("物流单号不能为空");
		}

		Map<String, Object> map = logisticsCompanyManager.saveLogisticsInfo(pd);
		if ((int) map.get("code") == 1010) {
			PageData pd2 = new PageData();
			pd2.put("orderpro_id", order_product_id);
			OrderProductVo orderProduct = orderProductManager.queryOne(pd2);
			pd2.clear();
			pd2.put("orderId", orderProduct.getOrder_id());
			OrderVo orderVo = orderManager.queryOne(pd2);
			MessageVo msgVo = new MessageVo();
			msgVo.setMsgType(MessageVo.MSGTYPE_TRANS);
			msgVo.setSubMsgType(6);
			msgVo.setMsgTitle("订单已发货");
			msgVo.setMsgDetail("亲，您的挥货订单" + orderProduct.getSub_order_code()
					+ "已发货，您可以在[我的订单]通过[查看物流]查看最新配送进度");
			msgVo.setIsRead(0);
			msgVo.setCustomerId(orderVo.getCustomer_id());
			msgVo.setObjId(order_product_id);
			messageManager.insert(msgVo);
			
			
			
			
		}
		
		  //保存成功，给用户发送微信信息 
		if((int)map.get("code")==1010){ 
			PageData pd2=new PageData(); 
			pd2.put("orderpro_id", order_product_id); 
			OrderProductVo orderProduct=orderProductManager.queryOne(pd2);
		  
			PageData pd3=new PageData(); 
			pd3.put("orderId",orderProduct.getOrder_id()); 
			List<OrderVo> orders=orderManager.findAllorderListPage(pd3);
			
			if(orders!=null&&orders.size()>0){
				//查询所用物流公司
			  PageData pd4=new PageData();
			  pd4.put("companyCode", company_code); 
			  List<LogisticsCompanyVo> list=logisticsCompanyManager.queryList(pd4);
			  pd3.put("customer_id", orders.get(0).getCustomer_id());
			  String many = "";
			  if(orders.get(0).getListOrderProductVo().size()>1){
				  many = " 等多件";
			  }
			  CustomerVo customerVo = customerManager.queryOne(pd3); 
			  String openid=customerVo.getOpenid(); 
			  	if(customerVo!=null&&StringUtil.isNotNull(openid)){
			  			List<TemplateMsgDataDto> data=new ArrayList<TemplateMsgDataDto>();
			  			TemplateMsgDataDto first=new TemplateMsgDataDto("first","亲，您的宝贝已经启程了，好想快点来到你身边","#000");
			  			TemplateMsgDataDto delivername=new TemplateMsgDataDto("delivername",list.get(0).getCompanyName()+many,"#000"); TemplateMsgDataDto ordername=new
			  			TemplateMsgDataDto("ordername",logistics_code,"#000");
			  			
			  			TemplateMsgDataDto remark=new TemplateMsgDataDto("remark","商品信息："+orderProduct.getProduct_name()+"\n商品数量：共"+orderProduct.getQuantity()+"\n备注：如有问题请致电"+WebConstant.SERVICE_PHONE+"或直接在微信留言，我们将第一时间为您服务！","#000");
			  			data.add(first); 
			  			data.add(delivername);
			  			data.add(ordername); 
			  			data.add(remark); 
			  			String url = WebConstant.MAIN_SERVER+"/weixin/order/queryLogiticsInfo?type=3&orderpro_id="+orderProduct.getOrderpro_id();
			  			TemplateMsgBodyDto dto=new TemplateMsgBodyDto(openid,WebConstant.FAHUO_TEMPLATEID,url,"#000",data,WxUtil.getAccessToken()); 
			  			try { 
			  				WxTemplateMessageUtil.send(dto); 
			  			}catch (JSONException e) {
			  				logger.error("/admin/order/queryLogiticsInfo(wechartSendMessage)", e);
						} catch (IOException e) {
							logger.error("/admin/order/queryLogiticsInfo(wechartSendMessage)", e);
						}
			  			
		  
		  
			  	} 
			} 
		}
		
		return map;
	}

	/**
	 * 查看单个订单详细信息
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/queryOrderDetails")
	public ModelAndView queryOrderByKey1(HttpServletRequest httpRequest) {
		String msg = "queryOrderDetails";
		logger.info("/admin/order/" + msg + " begin");
		String type = httpRequest.getParameter("type");

		ModelAndView modelAndView = new ModelAndView(
				"admin/order/list_order_details");

		if (StringUtils.isNotEmpty(type) && Integer.parseInt(type) == 2) {
			modelAndView = new ModelAndView("admin/order/list_order_tofahuo");

		}
		PageData pd = this.getPageData();

		pd.put("statusALL", "0");
		OrderVo orderInfo = null;
		CustomerVo customerVo = null;
		PayLogVo payLogVo = null;
		try {
			orderInfo = orderManager.findOrder(pd);
		} catch (Exception e) {
			logger.error("/admin/order/queryOrderDetails --error", e);
		}
		if (null != orderInfo) {
			String ordercode = orderInfo.getOrder_code();
			PageData pagedata = new PageData();
			pagedata.put("orderCode", ordercode);

			pd.put("customer_id", orderInfo.getCustomer_id());
			customerVo = customerManager.queryOne(pd);

			payLogVo = payLogManager.findPayLog(pd);

			Integer singlecouponid = payLogVo.getUse_single_coupon_id();
			if (singlecouponid != null && singlecouponid > 0) {
				UserSingleCouponVo userSingleCouponVo = userSingleCouponManager
						.selectByPrimaryKey(singlecouponid);
				if (userSingleCouponVo != null) {
					modelAndView.addObject("userSingleCouponVo",
							userSingleCouponVo);
				}

			}
		}

		modelAndView.addObject("orderInfo", orderInfo);
		modelAndView.addObject("customerInfo", customerVo);
		modelAndView.addObject("paylogInfo", payLogVo);

		logger.info("--->结束调用/admin/order/" + msg + " end");
		return modelAndView;
	}

	/**
	 * 更新订单状态 1. 目前只做确认完成
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateOrderStatus", method = { RequestMethod.POST }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateOrderStatus(HttpServletRequest httpRequest,
			HttpServletResponse response) {
		logger.info("_________________________________________________________________________");
		logger.info(" 修改订单状态  method: updateOrderStatus ..................开始");
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();

		String status = pd.getString("status");

		String orderIdArray = (String) pd.get("orderIdArray");

		logger.info("startting uodate order..............  options:  orderId "
				+ orderIdArray + "  ,status:" + status + " ");

		try {
			if (status.equals("3")) {// 将状态改成待签收，可批量操作
				if (StringUtils.isNotBlank(orderIdArray)) {
					String[] orderIds = orderIdArray.split(",");
					for (int i = 0; i < orderIds.length; i++) {
						PageData pageData = new PageData();
						pageData.put("orderId", Integer.parseInt(orderIds[i]));
						OrderVo orderVo = orderManager.findOrder(pageData);
						if (null == orderVo) {
							throw new Exception();
						} else {
							pd.put("status", Integer.parseInt(status));
							pd.put("orderId", orderIds[i]);
							pd.put("sendTime", new Date());
							int result = orderManager.editOder(pd);
							if (result > 0) {
								json.put("result", "succ");
								json.put("msg", "操作成功!");
							} else {
								json.put("result", "false");
								json.put("msg", "操作失败!");
							}
						}
					}

				}
				return json.toString();
			}

			int orderId = pd.getInteger("orderId");
			String tradeNo = pd.getString("orderCode");
			PageData pageData = new PageData();
			pageData.put("orderId", orderId);
			pageData.put("orderCode", tradeNo);
			OrderVo orderVo = orderManager.findOrder(pageData);
			if (null == orderVo) {
				throw new Exception();
			} else {

				if (orderVo.getStatus().equals("4")) { // 订单状态已完成不能 直接跳出
					json.put("result", "succ");
					json.put("msg", "已确认收货,请勿重复操作!");
				} else {

					int result = orderManager.editOder(pd);

					logger.info("startting uodate order   method-->updateByOrdercode   result  "
							+ result + "  ,flag  " + orderVo.getStatus());

					if (result > 0) {

						/*
						 * if(status.equals("4")){ PayLog
						 * payLogInfo=paymentServiceImpl.findByOrderCode(pd);
						 * 
						 * logger.info("startting uodate order  class-->"+
						 * IntegralAndCouponService.class
						 * +"  method-->updateCustomerInfo   options：   payLogInfo:"
						 * +payLogInfo +"  ,orderInfo"+orderInfo);
						 * 
						 * boolean
						 * bool=integralAndCouponService.updateCustomerInfo
						 * (payLogInfo, orderInfo, tradeNo,orderVo.getFlag(),
						 * logger); if(!bool){ throw new Exception(); } }
						 */

						json.put("result", "succ");
						json.put("msg", "操作成功!");
					} else {
						json.put("result", "false");
						json.put("msg", "操作失败!");
					}
				}
			}
			logger.info("ending uodate order..............  options:  orderId "
					+ orderId + "  ,status:" + status + "  ,tradeNo" + tradeNo);
		} catch (Exception e) {
			logger.error("/admin/order/updateOrderStatus --error", e);

			json.put("result", "false");
			json.put("msg", "操作失败,出现异常!");
		}
		return json.toString();
	}

	@RequestMapping(value = "importLogistics", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> importLogistics(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		InputStream in = null;
		MultipartFile file = multipartRequest.getFile("excel");
		if (file == null || file.isEmpty()) {
			return CallBackConstant.FAILED.callbackError("文件不存在");
		}

		in = file.getInputStream();
		List<List<Object>> listob = getBankListByExcel(in,
				file.getOriginalFilename());

		Map<String, Object> result = null;
		PageData pd = new PageData();
		// 该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
		for (int i = 0; i < listob.size(); i++) {
			List<Object> lo = listob.get(i);
			if (lo.size() < 3) {
				continue;
			}
			if (null == lo || null == lo.get(0) || null == lo.get(1)
					|| null == lo.get(2)) {
				continue;
			}
			pd.put("company_code", String.valueOf(lo.get(0)).trim());
			pd.put("logistics_code", String.valueOf(lo.get(1)).trim());
			pd.put("sub_order_code", String.valueOf(lo.get(2)).trim());
			result = this.logisticsCompanyManager.saveLogisticsInfo(pd);
			if (!String.valueOf(result.get("code")).equals("1010")) {
				continue;
			}
			String subOrderCode = String.valueOf(lo.get(2)).trim();
			PageData pd2 = new PageData();
			pd2.put("sub_order_code", subOrderCode);
			OrderProductVo orderProduct = orderProductManager.queryOne(pd2);
			pd2.clear();
			pd2.put("orderId", orderProduct.getOrder_id());
			OrderVo orderVo = orderManager.queryOne(pd2);

			MessageVo msgVo = new MessageVo();
			msgVo.setMsgType(MessageVo.MSGTYPE_TRANS);
			msgVo.setSubMsgType(6);
			msgVo.setMsgTitle("订单已发货");
			msgVo.setMsgDetail("亲，您的挥货订单" + subOrderCode
					+ "已发货，您可以在[我的订单]通过[查看物流]查看最新配送进度");
			msgVo.setIsRead(0);
			msgVo.setCustomerId(orderVo.getCustomer_id());
			msgVo.setObjId(orderProduct.getOrderpro_id());
			messageManager.insert(msgVo);
		}
		in.close();
		return result;
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
				for (int y = row.getFirstCellNum(); y <= row.getLastCellNum(); y++) {
					cell = row.getCell(y);
					if (cell == null) {
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
	
	/**
	 * 跳转订单管理页面
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/queryOneCardOrder")
	public ModelAndView queryOneCardOrder(HttpServletRequest httpRequest) {
		ModelAndView modelAndView = new ModelAndView(
				"admin/order/query_one_card");
		return modelAndView;
	}
	
	@RequestMapping(value="/querySingleOrder",method={RequestMethod.POST},produces="application/json;charset=utf-8")
	@ResponseBody
	public String querySingleOrder(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		OrderVo orderVo = this.orderManager.findOrder(pd);
		if (null == orderVo) {
			json.put("result", "fail");
			json.put("resultQuery", "订单编号错误");
			return json.toString();
		}
		String resultQuery = oneCardPayService.uploadParam(oneCardPayService.querySingleOrder(orderVo),WebConstant.YKT_QUERY_INGLE_ORDER_API,"UTF-8");
		json.put("result", "succ");
		json.put("resultQuery", resultQuery);
		return json.toString();
	}
}
