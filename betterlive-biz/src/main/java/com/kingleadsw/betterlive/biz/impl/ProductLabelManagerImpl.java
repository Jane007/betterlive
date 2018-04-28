package com.kingleadsw.betterlive.biz.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.ProductLabelManager;
import com.kingleadsw.betterlive.biz.ProductManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.ProductLabel;
import com.kingleadsw.betterlive.service.ProductLabelService;
import com.kingleadsw.betterlive.vo.ProductLabelVo;

/**
 * 标签 实际交互实现层
 */
@Component
@Transactional
public class ProductLabelManagerImpl extends BaseManagerImpl<ProductLabelVo,ProductLabel> implements ProductLabelManager{
	@Autowired
	private ProductLabelService productLabelService;
	@Autowired
	private ProductManager productManager;

	@Override
	protected BaseService<ProductLabel> getService() {
		return productLabelService;
	}

	@Override
	public List<ProductLabelVo> queryProductList(PageData pd) {
		List<ProductLabel> listProductlabel=productLabelService.queryListPage(pd);
		List<ProductLabelVo> listProductlabelVos = po2voer.transfer(ProductLabelVo.class,listProductlabel);
		return listProductlabelVos;
	}

	@Override
	public int insertProductlabel(ProductLabelVo productlabelVo) {
		int rst = 0;
		ProductLabel productlabel = vo2poer.transfer(new ProductLabel(), productlabelVo);
		rst = productLabelService.insertProductlabel(productlabel);
		return rst;
	}

	@Override
	public int delProductlabel(int productLabelId) {
		int rst = productLabelService.delProductlabel(productLabelId);
		return rst;
	}

	@Override
	public int editproductlabel(ProductLabelVo productlabelVo) {
		int rst = 0;
		ProductLabel productlabel = vo2poer.transfer(new ProductLabel(), productlabelVo);
		rst = productLabelService.editproductlabel(productlabel);
		return rst;
	}
}
