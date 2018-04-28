package com.kingleadsw.betterlive.biz.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.FreightManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.model.Freight;
import com.kingleadsw.betterlive.redis.RedisService;
import com.kingleadsw.betterlive.service.FreightService;
import com.kingleadsw.betterlive.vo.FreightVo;

@Component
@Transactional
public class FreightManagerImpl extends BaseManagerImpl<FreightVo, Freight> implements FreightManager {

	private Logger logger = Logger.getLogger(FreightManagerImpl.class);
	
	@Autowired
	private FreightService freightService;
	
	@Autowired
    private RedisService redisService;
	
	@Override
	protected BaseService<Freight> getService() {
		return freightService;
	}

	@Override
	public Map<String, Object> getFreightByAreaCode(String provinceCode, String areaCode) {
		//1.首先从缓存中读取有没有该地区的运费信息，针对于省内范围（包含本市及省内其它地市）
//		Map<String, Object> freightMap = redisService.getObject("freight_"+areaCode);
//		if (freightMap != null) {
//			return CallBackConstant.SUCCESS.callback(freightMap);
//		}
//		
//		//2.从缓存中读取有没有该省份的运费信息，针对于外省运费
//		freightMap = redisService.getObject("freight_"+provinceCode);
//		if (freightMap != null) {
//			return CallBackConstant.SUCCESS.callback(freightMap);
//		}
		
		Map<String, Object> freightMap = new HashMap<String, Object>();
		//3.缓存中没有，从数据库查询
		Map<String, String> areaMap = new HashMap<String, String>();
		areaMap.put("areaCode", areaCode);
		areaMap.put("provinceCode", provinceCode);
		FreightVo freight = po2voer.transfer(new FreightVo(),freightService.queryFreightByAreaCode(areaMap));
		if (freight != null) {
			freightMap = new HashMap<String, Object>();
			freightMap.put("freight_type", freight.getFreight_type());
			freightMap.put("freight", freight.getFreight());
			freightMap.put("full_cut", freight.getFull_cut());
			redisService.setObject("freight_"+areaCode, freightMap);
			return CallBackConstant.SUCCESS.callback(freightMap);
		} else {
			logger.error("省级【" + provinceCode + "】，地市【" + areaCode + "】，没有设置运费信息");
			return CallBackConstant.DATA_NOT_FOUND.callback("该地区没有设置运费信息，请联系客服");
		}
	}

}
