package com.kingleadsw.betterlive.controller.wx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kingleadsw.betterlive.bean.MessageHandler;
import com.kingleadsw.betterlive.common.util.HttpClientTool;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.page.ResultBean;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.util.wx.SignUtil;
import com.kingleadsw.betterlive.util.wx.WxUtil;
import com.kingleadsw.betterlive.util.wx.bean.Message;
import com.kingleadsw.betterlive.util.wx.bean.ScanMessage;
import com.kingleadsw.betterlive.util.wx.bean.SubscribeMessage;
import com.kingleadsw.betterlive.util.wx.bean.TextMessage;
import com.kingleadsw.betterlive.util.wx.service.WeixinService;

@Controller
@RequestMapping("/wx/wxmgr")
public class WxMgrController extends AbstractWebController {
	private final static Logger logger = Logger.getLogger(WxMgrController.class);
	
	@Autowired
	private WeixinService weixinService;

	@RequestMapping("/register")
	public void register(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("----微信请求-----");

		// 微信加密签名
		String signature = request.getParameter("signature");

		// 时间戳
		String timestamp = request.getParameter("timestamp");

		// 随机数
		String nonce = request.getParameter("nonce");

		// 随机字符串
		String echostr = request.getParameter("echostr");

		try {
			PrintWriter out;
			if (null != timestamp && null != nonce && null != echostr
					&& null != signature) {
				out = response.getWriter();

				// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
				if (SignUtil.checkSignature(signature, timestamp, nonce)) {
					out.print(echostr);
				}
				out.close();
			} else {
				ResultBean result = new ResultBean("success", "成功");
				try {
					MessageHandler handler = new MessageHandler();
					handler.process(request);
					Message message = handler.getMessage();
					if (message instanceof SubscribeMessage) {  //关注事件
						SubscribeMessage subMessage = (SubscribeMessage) message;
						result = weixinService.updateSubscribeUser(subMessage);
					}else if(message instanceof ScanMessage) {  //扫码事件
						ScanMessage scanMessage = (ScanMessage) message;
						result = weixinService.processScanEvent(scanMessage);
					}else if(message instanceof TextMessage){ //文本消息
						TextMessage textMessage = (TextMessage)message;
						result = weixinService.processTextMsg(textMessage);
					}
				} catch (Exception e) {
					result = ResultBean.createResultBean("success", "操作成功");
					logger.error(e, e);
				}
				logger.info("响应给微信服务器的消息：   " + result.getRespData());
				out = response.getWriter();  
		        out.print(result.getRespData());
		        out.close();
			}
		} catch (IOException e) {
			logger.error("/wx/wxmgr/register --error", e);
		}
	}

	@RequestMapping("/createMenus")
	public void createMenus(HttpServletRequest request,
			HttpServletResponse response) {

		String menus;
		String result = "";
		try {
			menus = getMenu();
			String token = WxUtil.getAccessToken();
			createMenu(menus, token);
			result = "success";
		} catch (IOException e) {
			logger.error("/wx/wxmgr/createMenus --error", e);
			result = "failure";
		}

		Writer out;
		try {
			out = response.getWriter();
			out.append(result);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error(e, e);
		}

	}

	public String createMenu(String menu, String access_token) {
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
				+ access_token;
		try {
			String result = HttpClientTool.post(url, menu, "utf-8");

			return result;
		} catch (IOException e) {
			logger.error("创建菜单请求异常：", e);
			return null;
		}
	}

	public String getMenu() throws IOException {

		StringBuffer buffer = new StringBuffer("");
		InputStream input = this.getClass().getResourceAsStream("/menu.txt");
		BufferedReader dr = new BufferedReader(new InputStreamReader(input));
		String str = "";
		while ((str = dr.readLine()) != null) {
			buffer.append(str);
		}
		return buffer.toString();
	}
	
	@RequestMapping("/reCreateMenu")
	public void createMenuBynew(HttpServletRequest request,
			HttpServletResponse response){
		try {
			PageData pd = this.getPageData();
			if(StringUtil.isNotNull(pd.getString("createFlag")) && "lidong".equals(pd.getString("createFlag"))){
				String token = WxUtil.getAccessToken();
				createMenu(getMenu(), token);
			}
		} catch (IOException e) {
			logger.error("/wx/wxmgr/reCreateMenu --error", e);
		}
	}

	public static void main(String[] args) {
		WxMgrController wxmgr = new WxMgrController();
		try {
			String token = WxUtil.getAccessToken();
			wxmgr.createMenu(wxmgr.getMenu(), token);
		} catch (IOException e) {
			logger.error("/wx/wxmgr/main --error", e);
		}
	}
}
