package com.kingleadsw.betterlive.biz;


import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.SpecialArticleType;
import com.kingleadsw.betterlive.vo.SpecialArticleTypeVo;

public interface SpecialArticleTypeManager extends BaseManager<SpecialArticleTypeVo,SpecialArticleType> {

	int updateStatusByPrimaryKey(PageData pd);

}
