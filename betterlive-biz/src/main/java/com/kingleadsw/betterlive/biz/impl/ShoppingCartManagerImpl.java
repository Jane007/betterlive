package com.kingleadsw.betterlive.biz.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.ShoppingCartManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.DeliverArea;
import com.kingleadsw.betterlive.model.Product;
import com.kingleadsw.betterlive.model.ShoppingCart;
import com.kingleadsw.betterlive.service.DeliverAreaService;
import com.kingleadsw.betterlive.service.ProductService;
import com.kingleadsw.betterlive.service.ShoppingCartService;
import com.kingleadsw.betterlive.vo.ShoppingCartVo;

/**
 * 商品  实际交互实现层
 * 2017-03-08 by chen
 */
@Component
@Transactional
public class ShoppingCartManagerImpl extends BaseManagerImpl<ShoppingCartVo,ShoppingCart> implements ShoppingCartManager{
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private DeliverAreaService deliverAreaService;
	
	@Autowired
	private ProductService productService;

	//新增用户购买商品到购物车
	@Override
	public int addShoppingCart(ShoppingCartVo shoppingCartVo) {
		
		PageData pd = new PageData();
		pd.put("customer_id", shoppingCartVo.getCustomer_id());
		pd.put("product_id", shoppingCartVo.getProduct_id());
		pd.put("spec_id", shoppingCartVo.getSpec_id());
		
		ShoppingCart shoppingCart = shoppingCartService.queryOne(pd);
		if (shoppingCart == null) {  //购物车中没有此规格的商品
			shoppingCart = vo2poer.transfer(new ShoppingCart(),shoppingCartVo);
			//将此商品添加到购物车
			return shoppingCartService.addShoppingCart(shoppingCart);
		} else {
			pd.put("customerId", shoppingCart.getCustomer_id());
			pd.put("cartId", shoppingCart.getCart_id());
			pd.put("amount", shoppingCart.getAmount() + shoppingCartVo.getAmount());
			pd.put("editType", 2);//更新类型为：数量
			return shoppingCartService.updateShoppingCartByCid(pd);
		}
	}
	
	
	//修改用户 购物车中购买的商品
	@Override
	public int updateShoppingCartByCid(PageData pd) {
		return shoppingCartService.updateShoppingCartByCid(pd);
	}
	
	
	//根据购物车Id和用户ID删除用户购买的指定商品
	@Override
	public int deleteShoppingCartByCidAndCid(PageData pd) {
		return shoppingCartService.deleteShoppingCartByCidAndCid(pd);
	}
	
	//根据用户ID删除用户购买的全部商品
	@Override
	public int deleteShoppingCartByCid(PageData pd) {
		return shoppingCartService.deleteShoppingCartByCid(pd);
	}
	
	//根据条件查询购物全部商品
	@Override
	public List<ShoppingCartVo> queryListShoppingCart(PageData pd) {
		return  po2voer.transfer(ShoppingCartVo.class, shoppingCartService.queryListShoppingCart(pd));
	}
	
	//根据条件分页查询购物全部商品
	@Override
	public List<ShoppingCartVo> queryShoppingCartListPage(PageData pd) {
		return  po2voer.transfer(ShoppingCartVo.class, shoppingCartService.queryShoppingCartListPage(pd));
	}
	

	/* (non-Javadoc)
	 * @see com.kingleadsw.betterlive.biz.ShoppingCartManager#queryDeliver(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> queryDeliver(String product_ids, String province_id, String city_id,
			String area_id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuffer noDeliverProdSb = new StringBuffer();
		String msg = "";
		
		//TODO:订单运费计算
		BigDecimal freight = BigDecimal.ZERO;
		PageData pd = new PageData();
		String [] productArr = product_ids.split(",");
		DeliverArea deiverArea = null;
		for (int i = 0; i < productArr.length; i++) {
			pd.put("productId", productArr[i]);
			pd.put("areaCode", province_id);
			//1、首先根据省去查询，判断该省是不是全部配送
			deiverArea = deliverAreaService.queryDeliverByAreaCode(pd);
			//该省是配送的,并且包含了该省下所有的地区
			if (deiverArea != null && deiverArea.getAllChild() == 1) {
				continue;
			}
			
			pd.put("areaCode", city_id);
			//2、再根据市去查询，判断该市是不是全部配送
			deiverArea = deliverAreaService.queryDeliverByAreaCode(pd);
			//该市是配送的,并且包含了该市下所有的地区
			if (deiverArea != null && deiverArea.getAllChild() == 1) {
				continue;
			}
			
			pd.put("areaCode", area_id);
			//3、最后根据区县去查询，判断该区县是否配送
			deiverArea = deliverAreaService.queryDeliverByAreaCode(pd);
			//该区县配送
			if (deiverArea != null) {
				continue;
			}
			
			//省市区都查询过了,没有数据,说明该商品在此地区不支持配送
			//查询商品名称,反馈给用户,让用户知道哪个商品不支持配送
			Product product = productService.selectProductByOption(pd);
			if (product != null) {
				//记录不配送的商
				noDeliverProdSb.append(product.getProduct_name());
				noDeliverProdSb.append("、");
			}
		}
		
		if (noDeliverProdSb.length() > 0) {  //有不支持配送的地址
			String noDeilverGoods = noDeliverProdSb.substring(0, noDeliverProdSb.length()-1);
			msg = "您下单的商品【" + noDeilverGoods + "】不支持配送到您选定的收货地址";
			resultMap.put("msg", msg);
			resultMap.put("vilidate", "false");
			resultMap.put("freight", 0);
		} else {
			msg = "恭喜，所有商品均支持配送";
			resultMap.put("msg", msg);
			resultMap.put("vilidate", "true");
			resultMap.put("freight", 0);
		}
		
		return resultMap;
	}
	
	@Override
	protected BaseService<ShoppingCart> getService() {
		return shoppingCartService;
	}


	@Override
	public int queryShoppingCartCnt(int customerId) {
		return shoppingCartService.queryShoppingCartCnt(customerId);
	}


	@Override
	public int findCountByCoupon(PageData pd) {
		return shoppingCartService.findCountByCoupon(pd);
	}


	@Override
	public int queryCartCountByParams(PageData pd) {
		return shoppingCartService.queryCartCountByParams(pd);
	}
}
