package com.kingleadsw.betterlive.biz;

import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.SysInvite;
import com.kingleadsw.betterlive.vo.SysInviteVo;

public interface SysInviteManager  extends BaseManager<SysInviteVo, SysInvite> {

	/**
	 * 系统列表数据
	 * @param pd
	 * @return
	 */
	List<SysInviteVo> queryBySortListPage(PageData pd);
	
	/**
	 * 邀请好友的优惠券信息 
	 * @param pd
	 * @return
	 */
	List<SysInviteVo> queryInviteCouponsInfo(PageData pd);

	/**
	 * 新人注册红包信息
	 * @param pd
	 * @return
	 */
	List<Map<String, Object>> queryRegCouponListPage(PageData pd);

}
