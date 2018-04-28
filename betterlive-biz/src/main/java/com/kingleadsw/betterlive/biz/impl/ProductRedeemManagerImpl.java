package com.kingleadsw.betterlive.biz.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.ProductRedeemManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.model.ActivityProduct;
import com.kingleadsw.betterlive.model.ProductLabel;
import com.kingleadsw.betterlive.model.ProductRedeem;
import com.kingleadsw.betterlive.model.ProductRedeemSpec;
import com.kingleadsw.betterlive.service.ActivityProductService;
import com.kingleadsw.betterlive.service.OrderProductService;
import com.kingleadsw.betterlive.service.ProductLabelService;
import com.kingleadsw.betterlive.service.ProductRedeemService;
import com.kingleadsw.betterlive.service.ProductRedeemSpecService;
import com.kingleadsw.betterlive.service.ProductSpecService;
import com.kingleadsw.betterlive.vo.ProductRedeemVo;

@Component
@Transactional
public class ProductRedeemManagerImpl extends BaseManagerImpl<ProductRedeemVo, ProductRedeem> implements ProductRedeemManager {
	
	protected Logger logger = Logger.getLogger(ProductRedeemManagerImpl.class);
	
	@Autowired
	private ProductRedeemService productRedeemService;
	
	@Autowired
	private ProductRedeemSpecService productRedeemSpecService;
	@Autowired
	private OrderProductService orderProductService;
	@Autowired
	private ActivityProductService activityProductService;
	@Autowired
	private ProductLabelService productLabelService;
	@Autowired
	private ProductSpecService productSpecService;

	@Override
	protected BaseService<ProductRedeem> getService() {
		return productRedeemService;
	}

	@Override
	public List<ProductRedeemVo> queryProductRedeemsListPage(PageData pd) {
		List<ProductRedeemVo> voList = new ArrayList<ProductRedeemVo>();
		
		try {
			List<ProductRedeem> list = productRedeemService.queryProductRedeemsListPage(pd);
			if(list == null || list.size() <= 0){
				return new ArrayList<ProductRedeemVo>();
			}
			
			for (ProductRedeem productRedeem : list) {
				PageData ordParams = new PageData();
				
				ProductRedeemVo vo = po2voer.transfer(new ProductRedeemVo(), productRedeem);
				
				ordParams.put("status", 0);
				ordParams.put("redeemId", vo.getRedeemId());
				ProductRedeemSpec redeemSpec = productRedeemSpecService.queryMinRedeemSpec(ordParams);
				if(redeemSpec == null){
					continue;
				}
				//商品标签
				ordParams.put("productId", productRedeem.getProductId());
				ordParams.put("status", 0);
				ordParams.put("nowTime", DateUtil.getCurrentDate());
				ProductLabel proLabel = productLabelService.queryOne(ordParams);
				
				//商品销量
				ordParams.clear();
				ordParams.put("checkStatus", "2,3,4,5");
				int salesVolume = this.orderProductService.queryOrderProductQuantity(ordParams);
				
				if(proLabel != null){
					vo.setProductLabel(proLabel.getLabelTitle());
				}
				
				vo.setSpecId(redeemSpec.getSpecId());
				vo.setSpecPrice(redeemSpec.getSpecPrice());
				if(redeemSpec.getDiscountPrice() != null){
					vo.setDiscountPrice(redeemSpec.getDiscountPrice());
				}
				if(productRedeem.getFakeSalesCopy() > 0){
					salesVolume = salesVolume + productRedeem.getFakeSalesCopy();
				}
				
				vo.setProductStatus(1);
				vo.setSalesVolume(salesVolume);
				vo.setNeedIntegral(redeemSpec.getNeedIntegral());
				vo.setDeductibleAmount(redeemSpec.getDeductibleAmount());
				
				//活动价
				ordParams.clear();
				ordParams.put("productId", vo.getProductId());
				ordParams.put("specId", vo.getSpecId());
				ordParams.put("specialStatus", 1);
				ordParams.put("nowTime", DateUtil.getCurrentDate());
				ActivityProduct actPro = activityProductService.queryActivityProductByParams(ordParams);
				if(actPro == null){
					voList.add(vo);
					continue;
				}
				
				vo.setActivityPrice(new BigDecimal(actPro.getActivity_price()));
				vo.setActivityType(actPro.getActivity_type());
				vo.setActivityId(actPro.getActivity_id());
				voList.add(vo);
				
			}
			return voList;
		} catch (Exception e) {
			logger.error("ProductRedeemManagerImpl.queryProductRedeemsListPage --error", e);
			return voList;
		}
	}

}
