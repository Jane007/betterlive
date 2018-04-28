package com.kingleadsw.betterlive.biz;

import java.math.BigDecimal;
import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.GiftCard;
import com.kingleadsw.betterlive.vo.GiftCardVo;


/**
 *优惠券管理 实际交互层
 * 2017-03-14 by chen
 */
public interface GiftCardManager extends BaseManager<GiftCardVo,GiftCard>{

	int insertGiftCard(GiftCardVo giftCard);              //增加礼品卡管理
	
	int updateGiftCardByGid(PageData pd);                 //修改礼品卡管理
	 
	int deleteGiftCardByGid(String gId);               	  //删除礼品卡管理

    List<GiftCardVo> findGiftCardListPage(PageData pd);   //根据条件分页查询礼品卡管理
    
    List<GiftCardVo> findListGiftCard(PageData pd);       //根据条件查询全部礼品卡管理
    
    GiftCardVo findGiftCard(PageData pd);                 //根据条件查询单个礼品卡管理详细
    
    BigDecimal querySumCardMoney(PageData pd);					  //根据条件查询个人总的钱；
    
    BigDecimal querySumUsedMoney(PageData pd);					  //根据条件查询用过的钱
    
    BigDecimal queryCustomerCanUseTotalMoney(int customerId);     // 查询用户剩下的可用余额 
}
