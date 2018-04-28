package com.kingleadsw.betterlive.biz;

import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.ShoppingCart;
import com.kingleadsw.betterlive.vo.ShoppingCartVo;



/**
 * 商品 实际交互层
 * 2017-03-08 by chen
 */
public interface ShoppingCartManager extends BaseManager<ShoppingCartVo,ShoppingCart>{

	int addShoppingCart(ShoppingCartVo  shoppingCartVo);       				//新增用户购买商品到购物车
	 
	int updateShoppingCartByCid(PageData pd);             					//修改用户 购物车中购买的商品
	
	int deleteShoppingCartByCidAndCid(PageData pd);							//根据购物车Id和用户ID删除用户购买的指定商品
	
	int deleteShoppingCartByCid(PageData pd);                          	 	//根据用户ID删除用户购买的全部商品
	
	List<ShoppingCartVo> queryListShoppingCart(PageData pd);              	//根据条件查询购物全部商品
	
	List<ShoppingCartVo> queryShoppingCartListPage(PageData pd);         	 //根据条件分页查询购物全部商品
	
	/**
	 * @param product_ids 产品id列表，逗号分割
	 * @param province_id 省编码
	 * @param city_id 市编码
	 * @param area_id 区编码
	 * @return vilidate:验证结果（true|false），当前配送地址的运费
	 */
	Map<String, Object> queryDeliver(String product_ids, String province_id, 
			String city_id, String area_id);

	int queryShoppingCartCnt(int customerId);
	
	int queryCartCountByParams(PageData pd);
	
	int findCountByCoupon(PageData pd);
}
