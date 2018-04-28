package com.kingleadsw.betterlive.biz.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.AgentProductManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.model.ActivityProduct;
import com.kingleadsw.betterlive.model.AgentProduct;
import com.kingleadsw.betterlive.model.ProductLabel;
import com.kingleadsw.betterlive.model.ProductSpec;
import com.kingleadsw.betterlive.service.ActivityProductService;
import com.kingleadsw.betterlive.service.AgentProductService;
import com.kingleadsw.betterlive.service.OrderProductService;
import com.kingleadsw.betterlive.service.ProductLabelService;
import com.kingleadsw.betterlive.service.ProductSpecService;
import com.kingleadsw.betterlive.vo.AgentProductVo;

@Component
@Transactional
public class AgentProductManagerImpl extends BaseManagerImpl<AgentProductVo, AgentProduct> implements AgentProductManager {

	@Autowired
	private AgentProductService agentProductService;
	@Autowired
	private ProductSpecService productSpecService;
	@Autowired
	private OrderProductService orderProductService;
	@Autowired
	private ActivityProductService activityProductService;
	@Autowired
	private ProductLabelService productLabelService;

	@Override
	protected BaseService<AgentProduct> getService() {
		return agentProductService;
	}

	@Override
	public List<AgentProductVo> queryAgentProductListPage(PageData pd) {
		List<AgentProductVo> voList = new ArrayList<AgentProductVo>();
		
		List<AgentProduct> agentProducts = this.agentProductService.queryAgentProductListPage(pd);
		if(agentProducts == null || agentProducts.size() <= 0){
			return new ArrayList<AgentProductVo>();
		}
		
		for (AgentProduct agentProduct : agentProducts) {
			PageData ordParams = new PageData();
			
			AgentProductVo vo = po2voer.transfer(new AgentProductVo(), agentProduct);

			//商品标签
			ordParams.put("productId", agentProduct.getProductId());
			ordParams.put("status", 0);
			ordParams.put("nowTime", DateUtil.getCurrentDate());
			ProductLabel proLabel = productLabelService.queryOne(ordParams);
			
			//商品销量
			ordParams.clear();
			ordParams.put("checkStatus", "2,3,4,5");
			int salesVolume = this.orderProductService.queryOrderProductQuantity(ordParams);
			
			ordParams.clear();
			ordParams.put("productId", vo.getProductId());
			ordParams.put("specStatus", IntegralConstants.COMMON_STATUS_YES);
			ordParams.put("proStatus", IntegralConstants.COMMON_STATUS_YES);
			ProductSpec productSpec = productSpecService.queryProductSpecByOption(ordParams);
			
			if(proLabel != null){
				vo.setProductLabel(proLabel.getLabelTitle());
			}
			
			vo.setSpecId(productSpec.getSpec_id());
			vo.setSpecPrice(new BigDecimal(productSpec.getSpec_price()));
			if(productSpec.getDiscount_price() != null){
				vo.setDiscountPrice(productSpec.getDiscount_price());
			}
			if(agentProduct.getFakeSalesCopy() > 0){
				salesVolume = salesVolume + agentProduct.getFakeSalesCopy();
			}
			vo.setProductStatus(1);
			vo.setSalesVolume(salesVolume);
			
			//活动价
			ordParams.clear();
			ordParams.put("productId", vo.getProductId());
			ordParams.put("specId", productSpec.getSpec_id());
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
	}

}
