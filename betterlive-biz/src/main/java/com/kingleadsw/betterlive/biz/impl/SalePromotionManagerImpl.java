package com.kingleadsw.betterlive.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SalePromotionManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.PromotionSpec;
import com.kingleadsw.betterlive.model.SalePromotion;
import com.kingleadsw.betterlive.service.SalePromotionService;
import com.kingleadsw.betterlive.vo.PromotionSpecVo;
import com.kingleadsw.betterlive.vo.SalePromotionVo;
/**
 * 促销活动服务层
 * @author zhangjing
 *
 * @date 2017年5月3日
 */
@Component
@Transactional
public class SalePromotionManagerImpl extends BaseManagerImpl<SalePromotionVo,SalePromotion> implements SalePromotionManager{
	@Autowired
	private SalePromotionService salePromotionService;
	
	
	
	
	@Override
	public SalePromotionVo selectByPrimaryKey(Integer id) {
		SalePromotion salePromotion = salePromotionService.selectByPrimaryKey(id);
		SalePromotionVo salePromotionVo = po2voer.transfer(new SalePromotionVo(), salePromotion);
		List<PromotionSpecVo> listSpecs = new ArrayList<PromotionSpecVo>(salePromotion.getListSpec().size());
		if(salePromotion.getListSpec()!=null&&salePromotion.getListSpec().size()>0){
			for (PromotionSpec promotionSpec : salePromotion.getListSpec()) {
				PromotionSpecVo promotionSpecVo = new PromotionSpecVo();
				promotionSpecVo.setPromotionSpecId(promotionSpec.getPromotionSpecId());
				promotionSpecVo.setPromotionId(promotionSpec.getPromotionId());
				promotionSpecVo.setProductId(promotionSpec.getProductId());
				promotionSpecVo.setSpecId(promotionSpec.getSpecId());
				promotionSpecVo.setProductName(promotionSpec.getProductName());
				promotionSpecVo.setSpecName(promotionSpec.getSpecName());
				listSpecs.add(promotionSpecVo);
			}
			salePromotionVo.setListSpec(listSpecs);
		}
		return salePromotionVo;
	}




	@Override
	protected BaseService<SalePromotion> getService() {
		return salePromotionService;
	}

}
