package com.kingleadsw.betterlive.service;

import java.math.BigDecimal;
import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.GiftCard;


/**
 * 礼品卡管理service 层
 * 2017-03-14 by chen
 */
public interface GiftCardService extends BaseService<GiftCard> {
	
	/**
	 * 增加礼品卡管理
	 * @param giftCard
	 * @return
	 */
	int insertGiftCard(GiftCard giftCard);              
	
	/**
	 * 修改礼品卡管理
	 * @param pd
	 * @return
	 */
	int updateGiftCardByGid(PageData pd);               
	 
	/**
	 * 删除礼品卡管理
	 * @param gId
	 * @return
	 */
	int deleteGiftCardByGid(String gId);               

	/**
	 * 根据条件分页查询礼品卡管理
	 * @param pd
	 * @return
	 */
    List<GiftCard> findGiftCardListPage(PageData pd);   
    
    /**
     * 根据条件查询全部礼品卡管理
     * @param pd
     * @return
     */
    List<GiftCard> findListGiftCard(PageData pd);     	
    
    /**
     * 根据条件查询单个礼品卡管理详细
     * @param pd
     * @return
     */
    GiftCard findGiftCard(PageData pd);                 
    
    /**
     * 根据条件查询个人总的钱
     * @param pd
     * @return
     */
    BigDecimal querySumCardMoney(PageData pd);					 
    
    /**
     * 根据条件查询用过的钱
     * @param pd
     * @return
     */
    BigDecimal querySumUsedMoney(PageData pd);				
   
    /**
     * 查询用户剩下的可用余额
     * @param customerId
     * @return
     */
    BigDecimal queryCustomerCanUseTotalMoney(int customerId);     
    
    /**
     * 合并用户时,需要合并用户的礼品卡
     * @param pd
     * @return
     */
    int modifyCustomerGiftCardBycId(PageData pd);     
}
