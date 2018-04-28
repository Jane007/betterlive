package com.kingleadsw.betterlive.biz;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.model.Praise;
import com.kingleadsw.betterlive.vo.PraiseVo;

public interface PraiseManager extends BaseManager<PraiseVo,Praise> {

	int insertPraise(PraiseVo praiseVo);

}
