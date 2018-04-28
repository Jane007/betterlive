package com.kingleadsw.betterlive.biz;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Share;
import com.kingleadsw.betterlive.vo.ShareVo;

public interface ShareManager extends BaseManager<ShareVo,Share> {

	int insertShare(ShareVo shareVo);
	
	/**
	 * 分享成功后领取金币积分奖励
	 * @param pd
	 * 必需参数：customerId，integralType（积分类型），objId
	 * @return 领取状态：0成功1失败
	 */
	int shareIntegralRecord(PageData pd);

}
