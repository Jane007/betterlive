package com.kingleadsw.betterlive.biz.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.ShareManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.CustomerIntegralRecord;
import com.kingleadsw.betterlive.model.Share;
import com.kingleadsw.betterlive.service.CustomerIntegralRecordService;
import com.kingleadsw.betterlive.service.ShareService;
import com.kingleadsw.betterlive.vo.ShareVo;

/**
 * 分享 实际交互实现层
 */
@Component
@Transactional
public class ShareManagerImpl extends BaseManagerImpl<ShareVo,Share> implements ShareManager{
	@Autowired
	private ShareService shareService;
	@Autowired
	private CustomerIntegralRecordService customerIntegralRecordService;
	
    @Autowired
    private CustomerManager customerManager;

	@Override
	protected BaseService<Share> getService() {
		return shareService;
	}

	@Override
	public int insertShare(ShareVo shareVo) {
		Share share = new Share();
		share.setCustomerId(shareVo.getCustomerId());
		share.setObjId(shareVo.getObjId());
		share.setShareType(shareVo.getShareType());
		int count = shareService.insertShare(share);
		shareVo.setShareId(share.getShareId());
		return count;
	}

	@Override
	public int shareIntegralRecord(PageData pd) {
		pd.put("recordType", IntegralConstants.RECORD_INCOME_YES);
		pd.put("checkDay", 1);
		int integralType = pd.getInteger("integralType");
		int checkType = IntegralConstants.COMMON_STATUS_NO;
		int objId = pd.getInteger("objId");
		pd.remove("objId");
		
		if (integralType == IntegralConstants.INTEGRAL_RECORD_TYPE_THREE 
				|| integralType == IntegralConstants.INTEGRAL_RECORD_TYPE_FOUR) {
			pd.remove("integralType");
			checkType = IntegralConstants.COMMON_STATUS_YES;
			pd.put("checkType", checkType);
			pd.put("status", IntegralConstants.RECORD_INCOME_YES);
		}
		
		int result = customerIntegralRecordService.queryIntegralRecordCount(pd);
		if (checkType != IntegralConstants.COMMON_STATUS_YES
				|| result >= IntegralConstants.LIMIT_ARTICLE_TIMES) { //分享
			return IntegralConstants.COMMON_STATUS_NO;
		}
		
//		else if (integralType == IntegralConstants.INTEGRAL_RECORD_TYPE_TWO 
//				&& result >= IntegralConstants.LIMIT_DYNAMIC_TIMES) {	//发动态
//			return 1;
//		}
		
		int custId = pd.getInteger("customerId");
		CustomerIntegralRecord record = new CustomerIntegralRecord();
		record.setCustomerId(custId);
		record.setIntegral(IntegralConstants.SHARE_PROFIT_INTEGRAL);
		record.setIntegralType(integralType);
		record.setRecordType(IntegralConstants.RECORD_INCOME_YES);
		record.setStatus(IntegralConstants.RECORD_STATUS_YES);
		record.setObjId(objId);
		record.setShareId(pd.getInteger("myShareId").longValue());
		result = customerIntegralRecordService.insert(record);
		if (result <= 0) {
			return IntegralConstants.COMMON_STATUS_NO;
		}
		
		//校验是否升级、更新积分
		customerManager.upgradeCustomerLevel(custId, record.getIntegral());
		
		return IntegralConstants.COMMON_STATUS_YES;
	}
	
}
