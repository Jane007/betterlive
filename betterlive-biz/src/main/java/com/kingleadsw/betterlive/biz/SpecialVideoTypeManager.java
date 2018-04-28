package com.kingleadsw.betterlive.biz;


import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.SpecialVideoType;
import com.kingleadsw.betterlive.vo.SpecialVideoTypeVo;

public interface SpecialVideoTypeManager extends BaseManager<SpecialVideoTypeVo,SpecialVideoType> {

	int updateStatusByPrimaryKey(PageData pd);

	List<SpecialVideoTypeVo> queryVideoTypeListPage(PageData pd);

}
