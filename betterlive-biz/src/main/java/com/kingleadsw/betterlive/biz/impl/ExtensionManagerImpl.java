package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.ExtensionManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Extension;
import com.kingleadsw.betterlive.service.ExtensionService;
import com.kingleadsw.betterlive.vo.ExtensionVo;
@Component
@Transactional
public class ExtensionManagerImpl extends BaseManagerImpl<ExtensionVo, Extension> implements ExtensionManager{
	@Autowired
	private ExtensionService extensionService;

	@Override
	public List<ExtensionVo> queryListPage(PageData pd){
		return po2voer.transfer(ExtensionVo.class,extensionService.queryListPage(pd));
	}
	
	@Override
	public List<ExtensionVo> queryList(PageData pd){
		return po2voer.transfer(ExtensionVo.class,extensionService.queryList(pd));
	}
	@Override
	public ExtensionVo selectExtensionByOption(PageData pageData) {
		return po2voer.transfer(new ExtensionVo(),extensionService.selectExtensionByOption(pageData));
	}

	@Override
	public int insertExtension(ExtensionVo extension) {
		int res = 0;
		Extension po = vo2poer.transfer(new Extension(),extension);
		res = extensionService.insertExtension(po);
		return res;
	}


	@Override
	public int deleteExtensionById(int extensionId) {
		int res = 0;
		res = extensionService.deleteExtensionById(extensionId);
		return res;
	}

	@Override
	protected BaseService<Extension> getService() {
		return extensionService;
	}

	public int updateExtension(ExtensionVo extension) {
		int res = 0;
		Extension po = vo2poer.transfer(new Extension(),extension);
		res = extensionService.updateExtension(po);
		return res;
	}

}
