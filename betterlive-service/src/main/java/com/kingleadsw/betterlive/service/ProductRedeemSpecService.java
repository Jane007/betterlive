package com.kingleadsw.betterlive.service;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.ProductRedeemSpec;

public interface ProductRedeemSpecService extends BaseService<ProductRedeemSpec> {

	ProductRedeemSpec queryMinRedeemSpec(PageData ordParams);

}
