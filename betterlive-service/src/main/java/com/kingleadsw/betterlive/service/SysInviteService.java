package com.kingleadsw.betterlive.service;

import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SysInvite;

public interface SysInviteService extends BaseService<SysInvite> {

	List<SysInvite> queryBySortListPage(PageData pd);

	List<SysInvite> queryInviteCouponsInfo(PageData pd);

	List<Map<String, Object>> queryRegCouponListPage(PageData pd);

}
