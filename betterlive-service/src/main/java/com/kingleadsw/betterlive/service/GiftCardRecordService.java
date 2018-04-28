package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.GiftCardRecord;

public interface GiftCardRecordService extends BaseService<GiftCardRecord>{
	
	 /**
     * 查询页面列表数据 包含分页礼品卡记录
     * @param pd
     * @return
     */
    @Override
	List<GiftCardRecord> queryListPage(PageData pd);
    /**
     * 查询数据礼品卡记录列表
     * @param pd
     * @return
     */
    @Override
	List<GiftCardRecord> queryList(PageData pd);

    /**
     * 查询单个记录 礼品卡记录
     * @param pd
     * @return
     */
    @Override
	GiftCardRecord queryOne(PageData pd);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    GiftCardRecord selectSpecialByOption(Integer recordId);

    /**
     * 根据id查询 礼品卡记录
     * @param id
     * @return
     */
    @Override
	int deleteByPrimaryKey(Integer id);

    /**
     * 修改礼品卡记录
     */
    int updateGiftCardRecord(GiftCardRecord giftCardRecord);

    /**
     * 插入礼品卡记录
     * @param pd
     * @return
     */
	int insertGiftCardRecord(GiftCardRecord giftCardRecord);
	
	/**
	 * 合并用户时,需要合并用户的礼品卡使用记录 
	 * @param pd
	 * @return
	 */
	int modifyCustomerGiftCardRecordBycId(PageData pd);
	
	
	int insertGiftCard(GiftCardRecord giftCardRecord);

	
}
