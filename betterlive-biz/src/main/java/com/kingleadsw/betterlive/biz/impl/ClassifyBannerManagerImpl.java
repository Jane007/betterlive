package com.kingleadsw.betterlive.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.ClassifyBannerManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.ClassifyBanner;
import com.kingleadsw.betterlive.service.ClassifyBannerService;
import com.kingleadsw.betterlive.vo.ClassifyBannerVo;
/**
 * ClassifyBanner 实际交互实现层
 * @author zhangjing
 * @date 2017年3月8日 下午6:48:21
 */
@Component
@Transactional
public class ClassifyBannerManagerImpl  extends BaseManagerImpl<ClassifyBannerVo,ClassifyBanner> implements ClassifyBannerManager{

	@Autowired
	 private ClassifyBannerService classifyBannerService;
	@Override
	protected BaseService<ClassifyBanner> getService() {
		return classifyBannerService;
	}
	/**
	 * 分页查询classifybanner
	 */
	@Override
	public List<ClassifyBannerVo> queryclassifybannerListPage(PageData pd) {
		// TODO Auto-generated method stub
		List<ClassifyBanner> listclassifybanner=classifyBannerService.queryclassifybannerListPage(pd);
		List<ClassifyBannerVo> listclassifybannerVo = po2voer.transfer(ClassifyBannerVo.class,listclassifybanner);
		return listclassifybannerVo;
	}
	@Override
	public int addclassifybanner(ClassifyBannerVo record) {
		// TODO Auto-generated method stub
		int res = 0;
		ClassifyBanner banner = new ClassifyBanner();
		banner.setBannerImg(record.getBannerImg());
		banner.setBannerTitle(record.getBannerTitle());
		banner.setBannerType(record.getBannerType());
		banner.setStatus(record.getStatus());
		res = classifyBannerService.addclassifybanner(banner);
		return res;
	}
}
