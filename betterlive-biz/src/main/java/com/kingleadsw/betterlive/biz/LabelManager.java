package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Label;
import com.kingleadsw.betterlive.vo.LabelVo;

public interface LabelManager  extends BaseManager<LabelVo,Label> {

	List<LabelVo> queryReportListPage(PageData pd);

	int updateHomeFlag();
}
