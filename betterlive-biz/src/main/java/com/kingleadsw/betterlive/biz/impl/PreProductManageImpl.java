package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.PreProductManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.PreProduct;
import com.kingleadsw.betterlive.service.PreProductService;
import com.kingleadsw.betterlive.vo.PreProductVo;
/**
 * 预售商品  实际交互实现层
 * @author zhangjing
 * @date 2017年3月11日 下午16:48:21
 */
@Component
@Transactional
public class PreProductManageImpl extends BaseManagerImpl<PreProductVo, PreProduct> implements
		PreProductManager {
	@Autowired
	private PreProductService preProductService;
	
	
	@Override
	public List<PreProductVo> queryListPage(PageData pd){
		return po2voer.transfer(PreProductVo.class,preProductService.queryListPage(pd));
	}

	@Override
	public List<PreProductVo> queryList(PageData pd){
		return po2voer.transfer(PreProductVo.class,preProductService.queryList(pd));
	}
	@Override
	public PreProductVo selectPreProductByOption(PageData pageData) {
		return po2voer.transfer(new PreProductVo(),preProductService.selectPreProductByOption(pageData));
	}

	@Override
	public int insertPreProduct(PreProductVo preProductVo) {
		int res = 0;
		PreProduct preProduct = vo2poer.transfer(new PreProduct(), preProductVo);
		res = preProductService.insertPreProduct(preProduct);
		preProductVo.setPreId(preProduct.getPreId());
		return res;
	}

	@Override
	public int updatePreProduct(PreProductVo preProductVo) {
		int res = 0;
		PreProduct preProduct = vo2poer.transfer(new PreProduct(), preProductVo);
		res = preProductService.updatePreProduct(preProduct);
		return res;
	}

	@Override
	public int deletePreProductById(int preId) {
		int res = 0;
		res = preProductService.deletePreProductById(preId);
		return res;
	}

	@Override
	protected BaseService<PreProduct> getService() {
		return preProductService;
	}

	
}
