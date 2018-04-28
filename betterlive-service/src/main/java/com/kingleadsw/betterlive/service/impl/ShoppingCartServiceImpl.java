package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.ShoppingCartMapper;
import com.kingleadsw.betterlive.model.ShoppingCart;
import com.kingleadsw.betterlive.service.ShoppingCartService;


/**
 * 购物che service 层
 * 2017-03-11 by chen
 */
@Service
public class ShoppingCartServiceImpl extends BaseServiceImpl<ShoppingCart>  implements ShoppingCartService{
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;
	
	
	//新增用户购买商品到购物车
	@Override
	public int addShoppingCart(ShoppingCart shoppingCart) {
		return shoppingCartMapper.addShoppingCart(shoppingCart);
	}
	
	
	 //修改用户 购物车中购买的商品
	@Override
	public int updateShoppingCartByCid(PageData pd) {
		return shoppingCartMapper.updateShoppingCartByCid(pd);
	}
	
	
	////根据购物车Id和用户ID删除用户购买的指定商品
	@Override
	public int deleteShoppingCartByCidAndCid(PageData pd) {
		return shoppingCartMapper.deleteShoppingCartByCidAndCid(pd);
	}

	////根据用户ID删除用户购买的全部商品
	@Override
	public int deleteShoppingCartByCid(PageData pd) {
		return shoppingCartMapper.deleteShoppingCartByCid(pd);
	}

	 //根据条件查询购物全部商品
	@Override
	public List<ShoppingCart> queryListShoppingCart(PageData pd) {
		return shoppingCartMapper.queryListShoppingCart(pd);
	}
	
	//根据条件分页查询购物全部商品
	@Override
	public List<ShoppingCart> queryShoppingCartListPage(PageData pd) {
		return shoppingCartMapper.queryShoppingCartListPage(pd);
	}

	@Override
	protected BaseMapper<ShoppingCart> getBaseMapper() {
		return shoppingCartMapper;
	}

	@Override
	public int queryShoppingCartCnt(int customerId) {
		return shoppingCartMapper.queryShoppingCartCnt(customerId);
	}


	
	/**
	 * 合并用户时,需要合并用户的购物车
	 * @param pd
	 * @return
	 */
	@Override
	public int modifyCustomerShopCartBycId(PageData pd) {
		return shoppingCartMapper.modifyCustomerShopCartBycId(pd);
	}


	@Override
	public int findCountByCoupon(PageData pd) {
		return shoppingCartMapper.findCountByCoupon(pd);
	}


	@Override
	public int queryCartCountByParams(PageData pd) {
		return shoppingCartMapper.queryCartCountByParams(pd);
	} 
	

}
