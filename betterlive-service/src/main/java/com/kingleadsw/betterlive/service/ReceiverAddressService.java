package com.kingleadsw.betterlive.service;

import java.util.List;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.ReceiverAddress;

public interface ReceiverAddressService extends BaseService<ReceiverAddress>{
	/**
     * 查询页面列表数据 包含分页
     * @param pd
     * @return
     */
    @Override
	List<ReceiverAddress> queryListPage(PageData pd);
    /**
     * 查询数据
     * @param pd
     * @return
     */
    @Override
	List<ReceiverAddress> queryList(PageData pd);
    
    
    /**
     * 查询单个记录
     * @param pd
     * @return
     */
    @Override
	ReceiverAddress queryOne(PageData pd);
    
    
    /**
     * 根据条件查询单个收货地址
     * @param pageData
     * @return
     */
	public ReceiverAddress selectReceiverAddressByOption(PageData pageData);
    
    
  
    /**
     * 增加收货地址
     * @param Special
     * @return
     */
    public int insertReceiverAddress(ReceiverAddress receiverAddress);

    /**
     * 修改收货地址
     * 
     * @param Special
     * @return
     */
    public int updateReceiverAddress(ReceiverAddress receiverAddress);

   
    /**
     *  删除收货地址
     */
    public int deleteReceiverAddressById(PageData pd);
    
    
    /**
     * 设置默认收货地址
     */
    public int updateReceiverStatusAddressById(PageData pd);
    
    /**
     * 合并用户时,需要合并用户的收货地址  同时设置 is_default=0 
     * @param pd --> 新用户ID newCustomerId      老用户ID customerId
     * @return
     */
    int updateReceiverBycId(PageData pd);
    
    int editReceiverAddress(ReceiverAddress receiverAddress);
    
}
