package com.kingleadsw.betterlive.biz.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.GiftCardManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.GiftCard;
import com.kingleadsw.betterlive.service.GiftCardService;
import com.kingleadsw.betterlive.vo.GiftCardVo;

/**
 *优惠券管理 实际交互实现层
 * 2017-03-14 by chen
 */
@Component
@Transactional
public class GiftCardManagerImpl extends BaseManagerImpl<GiftCardVo,GiftCard> implements GiftCardManager{
	@Autowired
	private GiftCardService giftCardService;
	
	//增加礼品卡管理
	@Override
	public int insertGiftCard(GiftCardVo giftCard) {
		GiftCard giftCards=vo2poer.transfer(new GiftCard(), giftCard);
		return giftCardService.insertGiftCard(giftCards);
	}
	
	 //修改礼品卡管理
	@Override
	public int updateGiftCardByGid(PageData pd) {
		return giftCardService.updateGiftCardByGid(pd);
	}
	
	//删除礼品卡管理
	@Override
	public int deleteGiftCardByGid(String gId) {
		return giftCardService.deleteGiftCardByGid(gId);
	}

	
	//根据条件分页查询礼品卡管理
	@Override
	public List<GiftCardVo> findGiftCardListPage(PageData pd) {
		return po2voer.transfer(GiftCardVo.class,giftCardService.findGiftCardListPage(pd));
	}
	
	//根据条件查询全部礼品卡管理
	@Override
	public List<GiftCardVo> findListGiftCard(PageData pd) {
		return po2voer.transfer(GiftCardVo.class,giftCardService.findListGiftCard(pd));
	}

	//根据条件查询单个礼品卡管理详细
	@Override
	public GiftCardVo findGiftCard(PageData pd) {	
		return po2voer.transfer(new GiftCardVo(),giftCardService.findGiftCard(pd));
	}

	@Override
	protected BaseService<GiftCard> getService() {
		return giftCardService;
	}

	@Override
	public BigDecimal querySumCardMoney(PageData pd) {
		return giftCardService.querySumCardMoney(pd);
	}

	@Override
	public BigDecimal querySumUsedMoney(PageData pd) {
		return giftCardService.querySumUsedMoney(pd);
	}
	
	
	 // 查询用户剩下的可用余额 
	@Override
	public BigDecimal queryCustomerCanUseTotalMoney(int customerId) {
		return giftCardService.queryCustomerCanUseTotalMoney(customerId);
	}

	
	

}
