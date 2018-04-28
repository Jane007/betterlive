package com.kingleadsw.betterlive.controller.web.pictures;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.PicturesManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.vo.PicturesVo;

/***
 * 图片管理
 */
@Controller
@RequestMapping("/admin/pictures")
public class PicturesController extends AbstractWebController {
	@Autowired
	private PicturesManager picturesManager;
	/**
	 * 查询图片
	 * @param  request
	 * @param  response
	 */
	@RequestMapping(value = "/queryPictureList")
	@ResponseBody
	public void queryPictureList(HttpServletRequest request,HttpServletResponse response) {
		PageData pd = this.getPageData();
		List<PicturesVo> list = picturesManager.queryList(pd);
		if(null !=list && list.size()>0){
			this.outEasyUIDataToJson(pd, list, response);
		}else{
			this.outEasyUIDataToJson(pd,new ArrayList<PicturesVo>(), response);
		}
	}
}
