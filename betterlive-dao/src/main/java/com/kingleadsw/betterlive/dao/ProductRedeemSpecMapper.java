package com.kingleadsw.betterlive.dao;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.ProductRedeemSpec;

public interface ProductRedeemSpecMapper extends BaseMapper<ProductRedeemSpec> {

	ProductRedeemSpec queryMinRedeemSpec(PageData ordParams);

}
