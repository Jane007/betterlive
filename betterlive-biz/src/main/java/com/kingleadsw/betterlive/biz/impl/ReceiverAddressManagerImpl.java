package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.ReceiverAddressManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.ReceiverAddress;
import com.kingleadsw.betterlive.service.ReceiverAddressService;
import com.kingleadsw.betterlive.vo.ReceiverAddressVo;
/**
 * 收货地址
 * @author zhangjing
 * @date 2017年3月16日 下午2:04:23
 */
@Component
@Transactional
public class ReceiverAddressManagerImpl extends BaseManagerImpl<ReceiverAddressVo,ReceiverAddress> implements ReceiverAddressManager {

	@Autowired
	private ReceiverAddressService receiverAddressService;
	
	/**
     * 查询页面列表数据 包含分页
     * @param pd
     * @return
     */
    @Override
	public List<ReceiverAddressVo> queryListPage(PageData pd){
    	return po2voer.transfer(ReceiverAddressVo.class, receiverAddressService.queryListPage(pd));
    }
    /**
     * 查询数据
     * @param pd
     * @return
     */
    @Override
	public List<ReceiverAddressVo> queryList(PageData pd){
    	return po2voer.transfer(ReceiverAddressVo.class, receiverAddressService.queryList(pd));
    }
    
    /**
     * 查询单个记录
     * @param pd
     * @return
     */
    @Override
	public ReceiverAddressVo queryOne(PageData pd){
    	return po2voer.transfer(new ReceiverAddressVo(),receiverAddressService.queryOne(pd));
    }
	/**
	 * 要把
	 */
	@Override
	public ReceiverAddressVo selectReceiverAddressByOption(PageData pageData) {
		return po2voer.transfer(new ReceiverAddressVo(),receiverAddressService.selectReceiverAddressByOption(pageData));
	}

	@Override
	public int insertReceiverAddress(ReceiverAddressVo receiverAddressVo) {
		int res = 0;
		ReceiverAddress receiverAddress=vo2poer.transfer(new ReceiverAddress(),receiverAddressVo);
		res = receiverAddressService.insertReceiverAddress(receiverAddress);
		receiverAddressVo.setReceiverId(receiverAddress.getReceiverId());
		return res;
	}

	@Override
	public int updateReceiverAddress(ReceiverAddressVo receiverAddressVo) {
		int res = 0;
		ReceiverAddress receiverAddress=vo2poer.transfer(new ReceiverAddress(),receiverAddressVo);
		res = receiverAddressService.updateReceiverAddress(receiverAddress);
		return res;
	}

	@Override
	public int deleteReceiverAddressById(PageData pd) {
		int res = 0;
		res = receiverAddressService.deleteReceiverAddressById(pd);
		return res;
	}

	@Override
	protected BaseService<ReceiverAddress> getService() {
		return receiverAddressService;
	}
	
	
	/**
     * 设置默认收货地址
     */
	@Override
	public int updateReceiverToDefault(PageData pd) {
		String receiverId = pd.getString("receiverId");
		//先根据客户ID把该用户所有的地址都设置非默认
		pd.put("isDefault",0);
		pd.put("receiverId", null);
		int result = receiverAddressService.updateReceiverStatusAddressById(pd);
		
		//然后再把客户的某个地址设置为默认
		pd.put("isDefault",1);
		pd.put("receiverId",receiverId);
		result += receiverAddressService.updateReceiverStatusAddressById(pd);
		
		return result;
	}
	
	
	/**
     * 合并用户时,需要合并用户的收货地址  同时设置 is_default=0 
     * @param pd --> 新用户ID newCustomerId      老用户ID customerId
     * @return
     */
	@Override
	public int updateReceiverBycId(PageData pd) {
		return receiverAddressService.updateReceiverBycId(pd);
	}
	@Override
	public int editReceiverAddress(ReceiverAddressVo receiverAddressVo) {
		int res = 0;
		ReceiverAddress receiverAddress=vo2poer.transfer(new ReceiverAddress(),receiverAddressVo);
		res = receiverAddressService.editReceiverAddress(receiverAddress);
		return res;
	}

	
}
