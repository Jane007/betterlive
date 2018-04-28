package com.kingleadsw.betterlive.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.GiftCardMapper;
import com.kingleadsw.betterlive.model.GiftCard;
import com.kingleadsw.betterlive.service.GiftCardService;


/**
 * 礼品卡管理service 实现层
 * 2017-03-14 by chen
 */
@Service
public class GiftCardServiceImpl extends BaseServiceImpl<GiftCard>  implements GiftCardService{
	@Autowired
	private GiftCardMapper giftCardMapper;

	
	//增加礼品卡管理
	@Override
	public int insertGiftCard(GiftCard giftCard) {
		return giftCardMapper.insertGiftCard(giftCard);
	}
	
	
	 //修改礼品卡管理
	@Override
	public int updateGiftCardByGid(PageData pd) {
		return giftCardMapper.updateGiftCardByGid(pd);
	}

	
	//删除礼品卡管理
	@Override
	public int deleteGiftCardByGid(String gId) {
		return giftCardMapper.deleteGiftCardByGid(gId);
	}

	
	 //根据条件分页查询礼品卡管理
	@Override
	public List<GiftCard> findGiftCardListPage(PageData pd) {
		return giftCardMapper.findGiftCardListPage(pd);
	}
	
	//根据条件查询全部礼品卡管理
	@Override
	public List<GiftCard> findListGiftCard(PageData pd) {
		return giftCardMapper.findListGiftCard(pd);
	}
	
	 //根据条件查询单个礼品卡管理详细
	@Override
	public GiftCard findGiftCard(PageData pd) {
		return giftCardMapper.findGiftCard(pd);
	}

	@Override
	protected BaseMapper<GiftCard> getBaseMapper() {
		return giftCardMapper;
	}

	//根据条件查询个人总的钱；
	@Override
	public BigDecimal querySumCardMoney(PageData pd) {
		return giftCardMapper.querySumCardMoney(pd);
	}

	/**
	 * 根据条件查询用过的钱
	 */
	@Override
	public BigDecimal querySumUsedMoney(PageData pd) {
		return giftCardMapper.querySumUsedMoney(pd);
	}

	
	// 查询用户剩下的可用余额 
	@Override
	public BigDecimal queryCustomerCanUseTotalMoney(int customerId) {
		return giftCardMapper.queryCustomerCanUseTotalMoney(customerId);
	}

	
	/**
     * 合并用户时,需要合并用户的礼品卡
     * @param pd
     * @return
     */
	@Override
	public int modifyCustomerGiftCardBycId(PageData pd) {
		return giftCardMapper.modifyCustomerGiftCardBycId(pd);
	}
	
	
}
