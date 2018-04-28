package com.kingleadsw.betterlive.biz;

import java.util.Map;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.model.Freight;
import com.kingleadsw.betterlive.vo.FreightVo;

/**
 * 运费业务处理逻辑类
 * @author ltp
 * @date 2017年3月10日16:48:03
 *
 */
public interface FreightManager extends BaseManager<FreightVo, Freight> {
	
	/**
	 * 根据地区编码，查询运费对象信息
	 * @param provinceCode 省编码
	 * @param arecCode  地区编码
	 * @return Map，freight_type：运费类型；freight：运费价格；full_cut：满多少包邮
	 */
	public Map<String, Object> getFreightByAreaCode(String provinceCode, String areaCode);

}
