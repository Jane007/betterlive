package com.kingleadsw.betterlive.dao;


import java.util.List;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.InviteRecord;


/**
 * 邀请记录  dao层
 */
public interface InviteRecordMapper extends BaseMapper<InviteRecord>{

	int checkInviteByDay(int customerId);

	int updateRecordByParam(PageData pd);

	List<InviteRecord> queryInviteRecordListByFinish(int customerId);

}