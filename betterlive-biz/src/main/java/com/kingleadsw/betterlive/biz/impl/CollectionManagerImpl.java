package com.kingleadsw.betterlive.biz.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.ActivityProductManager;
import com.kingleadsw.betterlive.biz.CollectionManager;
import com.kingleadsw.betterlive.biz.ProductLabelManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.model.Collection;
import com.kingleadsw.betterlive.service.CollectionService;
import com.kingleadsw.betterlive.vo.CollectionVo;
import com.kingleadsw.betterlive.vo.ProductLabelVo;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.ProductVo;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;
import com.kingleadsw.betterlive.vo.SpecialVo;

/**
 * 我的收藏 实际交互实现层
 */
@Component
@Transactional
public class CollectionManagerImpl extends BaseManagerImpl<CollectionVo,Collection> implements CollectionManager{
	@Autowired
	private CollectionService collectionService;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private ActivityProductManager activityProductManager;
	@Autowired
	private SpecialMananger specialMananger;
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private ProductLabelManager productLabelManager;
	@Autowired
	private SpecialArticleManager specialArticleManager;

	@Override
	protected BaseService<Collection> getService() {
		return collectionService;
	}

	/**
	 * 收藏的商品
	 */
	@Override
	public List<CollectionVo> queryListByProduct(PageData pd) {
		List<Collection> list = collectionService.queryListPage(pd);
		List<CollectionVo> translist = po2voer.transfer(CollectionVo.class, list);
		if(translist == null){
			translist = new ArrayList<CollectionVo>();
			return translist;
		}

		List<CollectionVo> results = new ArrayList<CollectionVo>();
		for (CollectionVo collectionVo : translist) {
			ProductVo productVo = new ProductVo();
			
			//专题
			PageData specialParams = new PageData();
			specialParams.put("status", 1);
			specialParams.put("starttime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			specialParams.put("endTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			specialParams.put("productId", collectionVo.getObjId());
			SpecialVo specialVo = specialMananger.queryOneByProductId(specialParams);
			
			PageData appd = new PageData();
			appd.put("productId", collectionVo.getObjId());
			//普通价
			if(specialVo != null){
				appd.put("activityId", specialVo.getSpecialId());
			}
			ProductSpecVo proSpec = this.productSpecManager.queryProductSpecByOption(appd);
			if(proSpec == null){
				continue;
			}
			productVo.setStatus(proSpec.getProductStatus());
			productVo.setPrice(proSpec.getSpec_price());
			if (proSpec.getDiscount_price() != null && proSpec.getDiscount_price().compareTo(BigDecimal.ZERO) >= 0){
				productVo.setDiscountPrice(proSpec.getDiscount_price()+"");
			}
			productVo.setProduct_name(proSpec.getProduct_name());
			productVo.setShare_explain(proSpec.getShare_explain());
			if(proSpec.getTotal_sales_copy() != null){
				productVo.setSalesVolume(proSpec.getTotal_sales_copy());
			}
			productVo.setProduct_id(proSpec.getProduct_id());
			productVo.setProduct_code(proSpec.getProduct_code());
			productVo.setProduct_logo(proSpec.getProduct_logo());
			
			if(specialVo == null){
				specialVo = new SpecialVo();
				specialVo.setSpecialId(0);
			}else{

				productVo.setActivityType(specialVo.getSpecialType());
				productVo.setIsActivity("NO");
				if(StringUtil.isNotNull(proSpec.getActivity_price()) && new BigDecimal(proSpec.getActivity_price()).compareTo(BigDecimal.ZERO) >= 0){
					productVo.setActivityPrice(proSpec.getActivity_price());
					productVo.setIsActivity("YES");
				}
				productVo.setActivity_id(specialVo.getSpecialId());
			}
			PageData apParams = new PageData();
			apParams.put("productId", productVo.getProduct_id());
			apParams.put("status", 0);
			apParams.put("nowTime", DateUtil.convertDatetoMyString("yyyy-MM-dd HH:mm:ss", new Date()));
			ProductLabelVo plvo = productLabelManager.queryOne(apParams);
			if(plvo != null && StringUtil.isNotNull(plvo.getLabelTitle())){
				productVo.setLabelName(plvo.getLabelTitle());
			}
			collectionVo.setProductVo(productVo);
			results.add(collectionVo);
		}
		return results;
	}

	/**
	 * 收藏的专题限时活动
	 */
	@Override
	public List<CollectionVo> queryListByRecommend(PageData pd) {
		List<Collection> list = collectionService.queryListPage(pd);
		List<CollectionVo> translist = po2voer.transfer(CollectionVo.class, list);
		if(translist == null){
			translist = new ArrayList<CollectionVo>();
			return translist;
		}
		
		PageData specialParams = new PageData();
		specialParams.put("specialType", 1);
		
		for (CollectionVo collectionVo : translist) {
			specialParams.put("specialId", collectionVo.getObjId());
			SpecialVo specialVo = specialMananger.queryOne(specialParams);
			if(specialVo != null){
				collectionVo.setSpecialVo(specialVo);
			}
		}
		return translist;
	}

	/**
	 * 收藏的美食教程
	 */
	@Override
	public List<CollectionVo> queryListByTutorial(PageData pd) {
		List<Collection> list = collectionService.queryListPage(pd);
		List<CollectionVo> translist = po2voer.transfer(CollectionVo.class, list);
		if(translist == null){
			translist = new ArrayList<CollectionVo>();
			return translist;
		}
		PageData specialParams = new PageData();
		specialParams.put("specialType", 4);
		
		specialParams.put("collectionType", 3);
		specialParams.put("praiseType", 2);
		specialParams.put("shareType", 3);
		specialParams.put("customerId", pd.getInteger("customerId"));
		for (CollectionVo collectionVo : translist) {
			specialParams.put("specialId", collectionVo.getObjId());
			SpecialVo specialVo = specialMananger.queryOneByTutorial(specialParams);
			if(specialVo != null){
				collectionVo.setSpecialVo(specialVo);
			}
		}
		return translist;
	}

	@Override
	public int insertCollection(CollectionVo collection) {
		Collection c = new Collection();
		c.setCollectionType(collection.getCollectionType());
		c.setCustomerId(collection.getCustomerId());
		c.setObjId(collection.getObjId());
		int count = collectionService.insertCollection(c);
		collection.setCollectionId(c.getCollectionId());
		return count;
	}

	@Override
	public List<CollectionVo> queryListBySpecialArticle(PageData pd) {
		List<Collection> list = collectionService.queryListPage(pd);
		List<CollectionVo> translist = po2voer.transfer(CollectionVo.class, list);
		if(translist == null){
			translist = new ArrayList<CollectionVo>();
			return translist;
		}
		
		PageData specialParams = new PageData();
		
		for (CollectionVo collectionVo : translist) {
			specialParams.put("articleId", collectionVo.getObjId());
			specialParams.put("customerId", pd.getInteger("customerId"));
			specialParams.put("homeFlagShow", 1);
			SpecialArticleVo specialArticleVo = specialArticleManager.queryOne(specialParams);
			if(specialArticleVo != null){
				collectionVo.setSpecialArticleVo(specialArticleVo);
			}
		}
		return translist;
	}

	@Override
	public int queryCollectionCount(PageData pd) {
		return collectionService.queryCollectionCount(pd);
	}

}
