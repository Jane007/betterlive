package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.ProductRedeem;
import com.kingleadsw.betterlive.vo.ProductRedeemVo;

public interface ProductRedeemManager extends BaseManager<ProductRedeemVo, ProductRedeem> {

	/**
	 * 积分活动
	 * @param pd
	 * @return
	 */
	List<ProductRedeemVo> queryProductRedeemsListPage(PageData pd);
}
