package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.ShoppingCart;


/**
 * 购物车  dao层
 * 2017-03-11 by chen
 */
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
	
	/**
	 * 新增用户购买商品到购物车
	 * @param shoppingCart
	 * @return
	 */
	int addShoppingCart(ShoppingCart  shoppingCart);       				
	 
	/**
	 * 修改用户 购物车中购买的商品
	 * @param pd
	 * @return
	 */
	int updateShoppingCartByCid(PageData pd);             				
	
	/**
	 * 根据购物车Id和用户ID删除用户购买的指定商品
	 * @param pd
	 * @return
	 */
	int deleteShoppingCartByCidAndCid(PageData pd);						
	
	/**
	 * 根据用户ID删除用户购买的全部商品
	 * @param pd
	 * @return
	 */
	int deleteShoppingCartByCid(PageData pd);                           
	
	/**
	 * 根据条件查询购物全部商品
	 * @param pd
	 * @return
	 */
	List<ShoppingCart> queryListShoppingCart(PageData pd);              
	
	/**
	 * 根据条件分页查询购物全部商品
	 * @param pd
	 * @return
	 */
	List<ShoppingCart> queryShoppingCartListPage(PageData pd);          
	
	/**
	 * 根据用户id查询用户购物车商品总数量
	 * @param pd
	 * @return
	 */
	int queryShoppingCartCnt(int customerId);
	
	
	/**
	 * 合并用户时,需要合并用户的购物车
	 * @param pd
	 * @return
	 */
	int modifyCustomerShopCartBycId(PageData pd);
	
	int findCountByCoupon(PageData pd);

	int queryCartCountByParams(PageData pd);
	
}
