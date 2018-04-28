package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.GiftCardRecord;
import com.kingleadsw.betterlive.vo.GiftCardRecordVo;

public interface GiftCardRecordManager extends BaseManager<GiftCardRecordVo,GiftCardRecord>{
	
	/**
     * 查询页面列表数据 包含分页礼品卡记录
     * @param pd
     * @return
     */
    @Override
	List<GiftCardRecordVo> queryListPage(PageData pd);
    /**
     * 查询数据礼品卡记录列表
     * @param pd
     * @return
     */
    @Override
	List<GiftCardRecordVo> queryList(PageData pd);

    /**
     * 查询单个记录 礼品卡记录
     * @param pd
     * @return
     */
    @Override
    GiftCardRecordVo queryOne(PageData pd);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    GiftCardRecordVo selectSpecialByOption(Integer recordId);

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
    int updateGiftCardRecord(GiftCardRecordVo giftCardRecordVo);

    /**
     * 插入礼品卡记录
     * @param pd
     * @return
     */
	int insertGiftCardRecord(GiftCardRecordVo giftCardRecordVo);
	
	
	int insertGiftCard(GiftCardRecordVo giftCardRecordVo);

}
