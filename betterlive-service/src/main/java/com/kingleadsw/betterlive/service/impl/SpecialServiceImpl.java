package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SpecialMapper;
import com.kingleadsw.betterlive.model.Special;
import com.kingleadsw.betterlive.service.SpecialService;
/**
 * service 管理层
 * @author zhangjing
 * @date 2017年3月8日 下午6:36:43
 */
@Service
public class SpecialServiceImpl extends BaseServiceImpl<Special> implements SpecialService{
	@Autowired
	private SpecialMapper specialMapper;

	@Override
	protected BaseMapper<Special> getBaseMapper() {
		return specialMapper;
	}

	/**
	 *根据条件查询单个专题
     * @param pageData
     * @return 
	 */
	@Override
	public Special selectSpecialByOption(PageData pageData) {
		return specialMapper.selectSpecialByOption(pageData);
	}
	/**
	 *根据条件查询专题
     * @param pageData
     * @return 
	 */
	@Override
	public List<Special> querySpecialList(PageData pd) {
		return specialMapper.querySpecialList(pd);
	}
	/**
	 *根据条件分页查询专题
     * @param pageData
     * @return 
	 */
	@Override
	public List<Special> querySpecialListPage(PageData pd) {
		return specialMapper.querySpecialListPage(pd);
	}
	 /**
     * 增加专题
     * @param special
     * @return
     */
	@Override
	public int insertSpecial(Special special) {
		return specialMapper.insertSpecial(special);
	}

	 /**
     * 修改专题
     * @param special
     * @return
     */
	@Override
	public int updateSpecial(Special special) {
		return specialMapper.updateSpecial(special);
	}

	/***
	 * 根据id删除专题
	 */
	@Override
	public int deleteSpecialById(int specialId) {
		return specialMapper.deleteSpecialById(specialId);
	}

	@Override
	public int hideSpecialHomeFlag(PageData hideSpeParam) {
		return specialMapper.hideSpecialHomeFlag(hideSpeParam);
	}

	@Override
	public Special queryOneByTutorial(PageData specialParams) {
		return specialMapper.queryOneByTutorial(specialParams);
	}

	@Override
	public Special queryOneByProductId(PageData specialParams) {
		return specialMapper.queryOneByProductId(specialParams);
	}

	@Override
	public Special queryOneSpecByProductId(PageData specialParams) {
		return specialMapper.queryOneSpecByProductId(specialParams);
	}

	@Override
	public List<Special> queryTutorialListPage(PageData pd) {
		return specialMapper.queryTutorialListPage(pd);
	}

}
