package com.kingleadsw.betterlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.ExtensionMapper;
import com.kingleadsw.betterlive.model.Extension;
import com.kingleadsw.betterlive.service.ExtensionService;
@Service
public class ExtensionServiceImpl extends BaseServiceImpl<Extension> implements ExtensionService{
	@Autowired
	private ExtensionMapper extensionMapper;

	/**
	 * 分页查询列表
	 */
	@Override
	public List<Extension> queryListPage(PageData pd){
		return extensionMapper.queryListPage(pd);
	}
	/**
	 * 查询列表数据
	 */
	@Override
	public List<Extension> queryList(PageData pd){
		return extensionMapper.queryList(pd);
	}
	/**
	 * 根据条件查询单个数据
	 */
	@Override
	public Extension selectExtensionByOption(PageData pageData) {
		return extensionMapper.selectExtensionByOption(pageData);
	}
	/**
	 * 插入推荐/人气商品
	 */
	@Override
	public int insertExtension(Extension extension) {
		return extensionMapper.insertExtension(extension);
	}
	/**
	 * 更新推荐/人气商品
	 */
	@Override
	public int updateExtension(Extension extension) {
		return extensionMapper.updateExtension(extension);
	}
	/**
	 * 删除推荐/人气商品
	 */
	@Override
	public int deleteExtensionById(int extensionId) {
		return extensionMapper.deleteExtensionById(extensionId);
	}

	@Override
	protected BaseMapper<Extension> getBaseMapper() {
		return extensionMapper;
	}

}
