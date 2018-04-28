package com.kingleadsw.betterlive.dao;

import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.ProductRedeem;

public interface ProductRedeemMapper extends BaseMapper<ProductRedeem> {

	List<ProductRedeem> queryProductRedeemsListPage(PageData pd);

}
