package com.kingleadsw.betterlive.controller.app.version;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.VersionInfoManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.vo.VersionInfoVo;

/**
 * 版本更新信息
 *
 */
@Controller
@RequestMapping(value = "/app/versioninfo")
public class AppVersionInfoController extends AbstractWebController{
	@Autowired
	private VersionInfoManager versionInfoManager;
	
	/**
	 * 获取当前最新版本
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/queryNewVersionInfo")
	@ResponseBody
	public Map<String, Object> queryNewVersionInfo(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		VersionInfoVo version = versionInfoManager.queryOne(pd);
		if(version == null){
			version = new VersionInfoVo();
		}
		map.put("versionVo", version);
		return CallBackConstant.SUCCESS.callback(map);
	}
}
