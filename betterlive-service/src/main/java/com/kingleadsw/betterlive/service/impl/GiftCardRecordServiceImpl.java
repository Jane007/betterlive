package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.GiftCardRecordMapper;
import com.kingleadsw.betterlive.model.GiftCardRecord;
import com.kingleadsw.betterlive.service.GiftCardRecordService;
@Service
public class GiftCardRecordServiceImpl extends BaseServiceImpl<GiftCardRecord> implements GiftCardRecordService{
	@Autowired
	private GiftCardRecordMapper giftCardRecordMapper;
	
	 /**
     * 查询页面列表数据 包含分页礼品卡记录
     * @param pd
     * @return
     */
    @Override
    public List<GiftCardRecord> queryListPage(PageData pd){
    	return giftCardRecordMapper.queryListPage(pd);
    }
    /**
     * 查询数据礼品卡记录列表
     * @param pd
     * @return
     */
    @Override
    public List<GiftCardRecord> queryList(PageData pd){
    	return giftCardRecordMapper.queryList(pd);
    	
    }

    /**
     * 查询单个记录 礼品卡记录
     * @param pd
     * @return
     */
    @Override
    public GiftCardRecord queryOne(PageData pd){
    	return giftCardRecordMapper.queryOne(pd);
    }
    /**
     * 查询单个的礼品卡记录
     */
	@Override
	public GiftCardRecord selectSpecialByOption(Integer recordId) {
		return giftCardRecordMapper.selectSpecialByOption(recordId);
	}
	/**
	 * 更新礼品卡记录
	 */
	@Override
	public int updateGiftCardRecord(GiftCardRecord giftCardRecord) {
		return giftCardRecordMapper.updateGiftCardRecord(giftCardRecord);
	}
	/**
	 * 插入礼品卡记录
	 */
	@Override
	public int insertGiftCardRecord(GiftCardRecord giftCardRecord) {
		return giftCardRecordMapper.insertGiftCardRecord(giftCardRecord);
	}

	
	@Override
	protected BaseMapper<GiftCardRecord> getBaseMapper() {
		return giftCardRecordMapper;
	}
	
	
	/**
	 * 合并用户时,需要合并用户的礼品卡使用记录 
	 * @param pd
	 * @return
	 */
	@Override
	public int modifyCustomerGiftCardRecordBycId(PageData pd) {
		return giftCardRecordMapper.modifyCustomerGiftCardRecordBycId(pd);
	}
	@Override
	public int insertGiftCard(GiftCardRecord giftCardRecord) {
		// TODO Auto-generated method stub
		return giftCardRecordMapper.insertGiftCard(giftCardRecord);
	}

}
