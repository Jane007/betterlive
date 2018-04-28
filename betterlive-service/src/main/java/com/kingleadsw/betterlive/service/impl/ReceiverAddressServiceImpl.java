package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.ReceiverAddressMapper;
import com.kingleadsw.betterlive.model.ReceiverAddress;
import com.kingleadsw.betterlive.service.ReceiverAddressService;
@Service
public class ReceiverAddressServiceImpl extends BaseServiceImpl<ReceiverAddress> implements ReceiverAddressService{
	@Autowired
	private ReceiverAddressMapper receiverAddressMapper;
	
	/**
     * 查询页面列表数据 包含分页
     * @param pd
     * @return
     */
    @Override
	public List<ReceiverAddress> queryListPage(PageData pd){
    	return receiverAddressMapper.queryListPage(pd);
    }
    /**
     * 查询数据
     * @param pd
     * @return
     */
    @Override
    public List<ReceiverAddress> queryList(PageData pd){
    	return receiverAddressMapper.queryList(pd);
    }
    
    
    /**
     * 查询单个记录
     * @param pd
     * @return
     */
    @Override
    public ReceiverAddress queryOne(PageData pd){
    	return receiverAddressMapper.queryOne(pd);
    }
    
    /**
     * 根据主键查单个
     */
	@Override
	public ReceiverAddress selectReceiverAddressByOption(PageData pageData) {
		return receiverAddressMapper.selectReceiverAddressByOption(pageData);
	}
	/**
	 * 插入收货地址
	 */
	@Override
	public int insertReceiverAddress(ReceiverAddress receiverAddress) {
		return receiverAddressMapper.insertReceiverAddress(receiverAddress);
	}

	/**
	 * 更新
	 */
	@Override
	public int updateReceiverAddress(ReceiverAddress receiverAddress) {
		return receiverAddressMapper.updateReceiverAddress(receiverAddress);
	}
	/**
	 * 逻辑删除
	 */
	@Override
	public int deleteReceiverAddressById(PageData pd) {
		return receiverAddressMapper.deleteReceiverAddressById(pd);
	}

	@Override
	protected BaseMapper<ReceiverAddress> getBaseMapper() {
		return receiverAddressMapper;
	}
	
	 /**
     * 设置默认收货地址
     */
	@Override
	public int updateReceiverStatusAddressById(PageData pd) {
		return receiverAddressMapper.updateReceiverStatusAddressById(pd);
	}
	
	
	/**
     * 合并用户时,需要合并用户的收货地址  同时设置 is_default=0 
     * @param pd --> 新用户ID newCustomerId      老用户ID customerId
     * @return
     */
	@Override
	public int updateReceiverBycId(PageData pd) {
		return receiverAddressMapper.updateReceiverBycId(pd);
	}
	@Override
	public int editReceiverAddress(ReceiverAddress receiverAddress) {
		// TODO Auto-generated method stub
		return receiverAddressMapper.editReceiverAddress(receiverAddress);
	}

}
