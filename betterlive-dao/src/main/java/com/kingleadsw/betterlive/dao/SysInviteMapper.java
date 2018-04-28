package com.kingleadsw.betterlive.dao;

import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.SysInvite;

public interface SysInviteMapper extends BaseMapper<SysInvite>{

	List<SysInvite> queryBySortListPage(PageData pd);

	List<SysInvite> queryInviteCouponsInfo(PageData pd);

	List<Map<String, Object>> queryRegCouponListPage(PageData pd);

}
