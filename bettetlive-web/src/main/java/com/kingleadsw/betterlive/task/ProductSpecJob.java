package com.kingleadsw.betterlive.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingleadsw.betterlive.biz.MessageManager;
import com.kingleadsw.betterlive.biz.ProductSpecManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.util.SendMsgUtil;
import com.kingleadsw.betterlive.vo.ProductSpecVo;

@Component("productSpecJob")
public class ProductSpecJob {

	private static final Logger logger=Logger.getLogger(ProductSpecJob.class);
	
	@Autowired
	private ProductSpecManager productSpecManager;
	@Autowired
	private MessageManager messageManager;
	
	public void execute(){
		try{
//			PageData pd = new PageData();
//			pd.put("pStatus", 1);
//			pd.put("specStatus", 1);
//			pd.put("checkNum", 1);
//			List<ProductSpecVo> list = productSpecManager.queryList(pd);
//			if(list != null && list.size() > 0){
//				for (int i = 0; i < list.size(); i++) {
//					ProductSpecVo specVo = list.get(i);
//					String msg = "[挥货商品库存提醒]"+specVo.getProduct_name()+"商品的"+specVo.getSpec_name()+"规格库存不足20件";
//	        		SendMsgUtil.sendMessage("13600417912", msg);
//				}
//			}
		}catch(Exception e){
			logger.error("productSpecJob--------error", e);
		}
	}
}
