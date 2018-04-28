package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.InviteRecord;

public interface InviteRecordService extends BaseService<InviteRecord> {

	/**
	 * 检查是否当天首次邀请
	 * @param customerId
	 * @return
	 */
	int checkInviteByDay(int customerId);

	/**
	 * 修改邀请记录的状态
	 * @param pd
	 * @return
	 */
	int updateRecordByParam(PageData pd);

	/**
	 * 72小时内邀请注册数
	 * @param customerId
	 * @return
	 */
	 List<InviteRecord> queryInviteRecordListByFinish(int customerId);

}
