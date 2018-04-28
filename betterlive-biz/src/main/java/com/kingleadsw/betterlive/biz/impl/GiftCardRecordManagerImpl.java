package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.GiftCardRecordManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.GiftCardRecord;
import com.kingleadsw.betterlive.service.GiftCardRecordService;
import com.kingleadsw.betterlive.vo.GiftCardRecordVo;
/**
 * 礼品卡记录
 * @author zhangjing
 *
 * 2017年3月17日
 */
@Component
@Transactional
public class GiftCardRecordManagerImpl extends BaseManagerImpl<GiftCardRecordVo, GiftCardRecord> implements GiftCardRecordManager{
	@Autowired
	private GiftCardRecordService giftCardRecordService;
	@Override
	protected BaseService<GiftCardRecord> getService() {
		return null;
	}
	
	
	/**
     * 查询页面列表数据 包含分页礼品卡记录
     * @param pd
     * @return
     */
    @Override
    public List<GiftCardRecordVo> queryListPage(PageData pd){
    	return po2voer.transfer(GiftCardRecordVo.class, giftCardRecordService.queryListPage(pd));
    }
    /**
     * 查询数据礼品卡记录列表
     * @param pd
     * @return
     */
    @Override
    public List<GiftCardRecordVo> queryList(PageData pd){
    	return po2voer.transfer(GiftCardRecordVo.class, giftCardRecordService.queryList(pd));
    }

    /**
     * 查询单个记录 礼品卡记录
     * @param pd
     * @return
     */
    @Override
    public GiftCardRecordVo queryOne(PageData pd){
    	return po2voer.transfer(new GiftCardRecordVo(), giftCardRecordService.queryOne(pd));
    }
	
	
	@Override
	public GiftCardRecordVo selectSpecialByOption(Integer recordId) {
		return po2voer.transfer(new GiftCardRecordVo(), giftCardRecordService.selectSpecialByOption(recordId));
	}
	@Override
	public int updateGiftCardRecord(GiftCardRecordVo giftCardRecordVo) {
		int res = 0;
		GiftCardRecord record = vo2poer.transfer(new GiftCardRecord(),giftCardRecordVo);
		res = giftCardRecordService.updateGiftCardRecord(record);
		return res;
	}
	@Override
	public int insertGiftCardRecord(GiftCardRecordVo giftCardRecordVo) {
		int res = 0;
		GiftCardRecord record = vo2poer.transfer(new GiftCardRecord(),giftCardRecordVo);
		res = giftCardRecordService.insertGiftCardRecord(record);
		return res;
	}


	@Override
	public int insertGiftCard(GiftCardRecordVo giftCardRecordVo) {
		int res = 0;
		GiftCardRecord record = vo2poer.transfer(new GiftCardRecord(),giftCardRecordVo);
		res = giftCardRecordService.insertGiftCard(record);
		return res;
	}

}
