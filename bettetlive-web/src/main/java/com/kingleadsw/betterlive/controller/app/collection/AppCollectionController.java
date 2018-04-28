package com.kingleadsw.betterlive.controller.app.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingleadsw.betterlive.biz.CollectionManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.vo.CollectionVo;
import com.kingleadsw.betterlive.vo.CustomerVo;

@Controller
@RequestMapping(value = "/app/collection")
public class AppCollectionController extends AbstractWebController {
	@Autowired
	private CollectionManager collectionManager;

	@Autowired
	private CustomerManager customerManager;

	@Autowired
	private ProductManager productManager;

	@Autowired
	private SpecialMananger specialManager;
	
	@Autowired
	private SpecialArticleManager specialArticleManager;

	/**
	 * 添加收藏内容
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/addCollection")
	@ResponseBody
	public Map<String, Object> addCollection(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();

		String token = pd.getString("token");
		int customerId = 0;

		// 用户标识
		if (StringUtil.isNull(token)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("用户信息为空");
		} else {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (customer == null) {
				return CallBackConstant.LOGIN_TIME_OUT.callbackError("用户信息不存在");
			}
			customerId = customer.getCustomer_id();
		}

		if (StringUtil.isNull(pd.getString("collectionType"))) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("收藏类型为空");
		}
		if (StringUtil.isNull(pd.getString("objId"))) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("业务主键为空");
		}

		pd.put("collectionType", pd.getString("collectionType"));
		pd.put("customerId", customerId);
		pd.put("objId", pd.getInteger("objId"));
		CollectionVo checkCollect = this.collectionManager.queryOne(pd);
		if (checkCollect != null && checkCollect.getCollectionId() > 0) {
			return CallBackConstant.FAILED.callbackError("您已收藏该内容");
		}

		CollectionVo collection = new CollectionVo();
		collection.setCollectionType(pd.getInteger("collectionType"));
		collection.setCustomerId(customerId);
		collection.setObjId(pd.getInteger("objId"));
		int count = this.collectionManager.insertCollection(collection);
		if (count > 0) {
			return CallBackConstant.SUCCESS.callback(collection
					.getCollectionId());
		}
		return CallBackConstant.FAILED.callback();
	}

	/**
	 * 取消收藏
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/canselCollection")
	@ResponseBody
	public Map<String, Object> canselCollection(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();

		String token = pd.getString("token");

		// 用户标识
		if (StringUtil.isNull(token)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("用户信息为空");
		} else {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (customer == null) {
				return CallBackConstant.FAILED.callbackError("用户信息不存在");
			}
		}

		if (StringUtil.isNull(pd.getString("collectionId"))) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("id为空");
		}

		int count = this.collectionManager.deleteByPrimaryKey(pd
				.getInteger("collectionId"));
		if (count > 0) {
			return CallBackConstant.SUCCESS.callback();
		}
		return CallBackConstant.FAILED.callback();
	}

	/**
	 * 批量取消收藏
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/canselCollectionAll")
	@ResponseBody
	public Map<String, Object> canselCollectionAll(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();

		String token = pd.getString("token");

		// 用户标识
		if (StringUtil.isNull(token)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("用户信息为空");
		} else {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (customer == null) {
				return CallBackConstant.FAILED.callbackError("用户信息不存在");
			}
		}

		if (StringUtil.isNull(pd.getString("collectionIds"))) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("收藏主键为空");
		}
		String[] colls = pd.getString("collectionIds").split(",");
		if (colls.length > 0) {
			for (int i = 0; i < colls.length; i++) {
				int cid = Integer.parseInt(colls[i]);
				this.collectionManager.deleteByPrimaryKey(cid);
			}
		}
		return CallBackConstant.SUCCESS.callback();
	}

	/**
	 * 收藏的商品
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/queryListByProduct")
	@ResponseBody
	public Map<String, Object> queryListByProduct(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();

		String token = pd.getString("token");
		int customerId = 0;

		// 用户标识
		if (StringUtil.isNull(token)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("用户信息为空");
		} else {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (customer == null) {
				return CallBackConstant.FAILED.callbackError("用户信息不存在");
			}
			customerId = customer.getCustomer_id();
		}

		pd.put("collectionType", 1);
		pd.put("customerId", customerId);
		List<CollectionVo> list = this.collectionManager.queryListByProduct(pd);
		if (list == null) {
			list = new ArrayList<CollectionVo>();
		}
		map.put("list", list);
		return CallBackConstant.SUCCESS.callback(map);
	}

	/**
	 * 限时活动（新版本没用到）
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/queryListByRecommend")
	@ResponseBody
	public Map<String, Object> queryListByRecommend(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();

		String token = pd.getString("token");
		int customerId = 0;

		// 用户标识
		if (StringUtil.isNull(token)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("用户信息为空");
		} else {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (customer == null) {
				return CallBackConstant.FAILED.callbackError("用户信息不存在");
			}
			customerId = customer.getCustomer_id();
		}

		pd.put("collectionType", 2);
		pd.put("customerId", customerId);
		List<CollectionVo> list = this.collectionManager.queryListByRecommend(pd);
		if (list == null) {
			list = new ArrayList<CollectionVo>();
		}
		map.put("list", list);
		return CallBackConstant.SUCCESS.callback(map);
	}

	/**
	 * 美食教程
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/queryListByTutorial")
	@ResponseBody
	public Map<String, Object> queryListByTutorial(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();

		String token = pd.getString("token");
		int customerId = 0;

		// 用户标识
		if (StringUtil.isNull(token)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("用户信息为空");
		} else {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (customer == null) {
				return CallBackConstant.FAILED.callbackError("用户信息不存在");
			}
			customerId = customer.getCustomer_id();
		}

		pd.put("collectionType", 3);
		pd.put("customerId", customerId);
		List<CollectionVo> list = this.collectionManager.queryListByTutorial(pd);
		if (list == null) {
			list = new ArrayList<CollectionVo>();
		}
		map.put("list", list);
		return CallBackConstant.SUCCESS.callback(map);
	}
	
	/**
	 * 美食推荐
	 * 
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/queryListBySpecialArticle")
	@ResponseBody
	public Map<String, Object> queryListBySpecialArticle(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();

		String token = pd.getString("token");
		int customerId = 0;

		// 用户标识
		if (StringUtil.isNull(token)) {
			return CallBackConstant.PARAMETER_ERROR.callbackError("用户信息为空");
		} else {
			CustomerVo customer = customerManager.queryCustomerByToken(token);
			if (customer == null) {
				return CallBackConstant.FAILED.callbackError("用户信息不存在");
			}
			customerId = customer.getCustomer_id();
		}

		pd.put("collectionType", 4);
		pd.put("customerId", customerId);
		List<CollectionVo> list = this.collectionManager.queryListBySpecialArticle(pd);
		if (list == null) {
			list = new ArrayList<CollectionVo>();
		}
		map.put("list", list);
		return CallBackConstant.SUCCESS.callback(map);
	}
}
