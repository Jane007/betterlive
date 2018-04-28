package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.ProductRedeem;

public interface ProductRedeemService extends BaseService<ProductRedeem> {

	List<ProductRedeem> queryProductRedeemsListPage(PageData pd);

}
