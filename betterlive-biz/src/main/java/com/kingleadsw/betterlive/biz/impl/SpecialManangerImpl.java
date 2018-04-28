package com.kingleadsw.betterlive.biz.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.model.ProductSpec;
import com.kingleadsw.betterlive.model.Special;
import com.kingleadsw.betterlive.service.SpecialService;
import com.kingleadsw.betterlive.vo.ProductSpecVo;
import com.kingleadsw.betterlive.vo.SpecialVo;
import com.kingleadsw.betterlive.vo.SysGroupVo;

/**
 * 专题  实际交互实现层
 * @author zhangjing
 * @date 2017年3月8日 下午6:48:21
 */
@Component
@Transactional
public class SpecialManangerImpl extends BaseManagerImpl<SpecialVo,Special> implements SpecialMananger{
     private static Logger log = Logger.getLogger(SpecialManangerImpl.class);
    
	 @Autowired
	 private SpecialService specialService;
	
	

	@Override
	public SpecialVo selectSpecialByOption(PageData pageData) {
		return po2voer.transfer(new SpecialVo(),specialService.selectSpecialByOption(pageData));
	}

	@Override
	public List<SpecialVo> querySpecialList(PageData pd) throws ParseException {
		List<Special> list = specialService.querySpecialList(pd);
		List<SpecialVo> listSpecialVo = new ArrayList<SpecialVo>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Special special : list) {
			SpecialVo specialVo = new SpecialVo();
			specialVo.setSpecialId(special.getSpecialId());
			specialVo.setSpecialName(special.getSpecialName());
			specialVo.setCreateTime(special.getCreateTime());
			specialVo.setSpecialTitle(special.getSpecialTitle());
			specialVo.setSpecialType(special.getSpecialType());
			specialVo.setSpecialPage(special.getSpecialPage());
			if(special.getStartTime() != null){
				specialVo.setStartTime(special.getStartTime());
				specialVo.setLongStart(sdf.parse(special.getStartTime()).getTime());
			}
			if(special.getEndTime() != null){
				specialVo.setEndTime(special.getEndTime());
				specialVo.setLongEnd(sdf.parse(special.getEndTime()).getTime());
			}
			specialVo.setSpecialCover(special.getSpecialCover());
			specialVo.setSpecialIntroduce(special.getSpecialIntroduce());
			specialVo.setStatus(special.getStatus());
			specialVo.setHomeFlag(special.getHomeFlag());
			specialVo.setIsCollection(special.getIsCollection());
			specialVo.setSysGroupVo(new SysGroupVo());
			specialVo.setProductSpecVo(new ProductSpecVo());
			listSpecialVo.add(specialVo);
		}
		return listSpecialVo;
	
	}

	@Override
	public List<SpecialVo> querySpecialListPage(PageData pd) {
		List<SpecialVo> listSpecialVo = new ArrayList<SpecialVo>();
			try {
				List<Special> listSpecial = specialService.querySpecialListPage(pd);
				SpecialVo specialVo = null;
				ProductSpecVo pspec = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (Special special : listSpecial) {
					specialVo = new SpecialVo();
					specialVo.setSpecialId(special.getSpecialId());
					specialVo.setSpecialName(special.getSpecialName());
					specialVo.setCreateTime(special.getCreateTime());
					specialVo.setSpecialTitle(special.getSpecialTitle());
					specialVo.setSpecialType(special.getSpecialType());
					specialVo.setSpecialPage(special.getSpecialPage());
					if(StringUtil.isNotNull(special.getStartTime())){
						specialVo.setStartTime(special.getStartTime());
						specialVo.setLongStart(sdf.parse(special.getStartTime()).getTime());
					}
					if(StringUtil.isNotNull(special.getEndTime())){
						specialVo.setEndTime(special.getEndTime());
						specialVo.setLongEnd(sdf.parse(special.getEndTime()).getTime());
					}
					specialVo.setSpecialCover(special.getSpecialCover());
					specialVo.setSpecialIntroduce(special.getSpecialIntroduce());
					specialVo.setStatus(special.getStatus());
					specialVo.setHomeFlag(special.getHomeFlag());
					specialVo.setIsCollection(special.getIsCollection());
					specialVo.setCollectionCount(special.getCollectionCount());
					specialVo.setSpecialSort(special.getSpecialSort());
					if(StringUtil.isNoNull(special.getListImg())){
						specialVo.setListImg(special.getListImg());
					}
					if(special.getProductSpecVo() != null){
						ProductSpec spec = special.getProductSpecVo();
						pspec = new ProductSpecVo();
						pspec.setProduct_id(spec.getProduct_id());
						pspec.setSpec_id(spec.getSpec_id());
						pspec.setSpec_price(spec.getSpec_price());
						pspec.setProduct_name(spec.getProduct_name());
						pspec.setProduct_code(spec.getProduct_code());
						pspec.setSales_copy(spec.getSales_copy());
						pspec.setStock_copy(spec.getStock_copy());
						pspec.setProduct_logo(spec.getProduct_logo());
						int salesCopy = 0;
						if(spec.getTotal_sales_copy() != null){
							salesCopy = spec.getTotal_sales_copy();
						}
						pspec.setTotal_sales_copy(salesCopy);
						pspec.setTotal_stock_copy(spec.getTotal_stock_copy());
						pspec.setPackage_desc(spec.getPackage_desc());
						if(StringUtil.isNotNull(spec.getActivity_price())){
							pspec.setActivity_price(spec.getActivity_price());
						}
						specialVo.setFakeSalesCopy(spec.getFake_sales_copy()+salesCopy);
						specialVo.setProductSpecVo(pspec);
					}else{
						ProductSpecVo sp = new ProductSpecVo();
						sp.setSpec_price("0");
						sp.setActivity_price("0");
						specialVo.setProductSpecVo(sp);
					}
					listSpecialVo.add(specialVo);
				}
			} catch (Exception e) {
				log.error("querySpecialListPage--error", e);
			}
		return listSpecialVo;
	}

	@Override
	public int insertSpecial(SpecialVo specialVo) {
		int rst = 0;
		Special special = new Special();
		special.setSpecialName(specialVo.getSpecialName());
		special.setSpecialTitle(specialVo.getSpecialTitle());
		special.setSpecialType(specialVo.getSpecialType());
		special.setSpecialPage(specialVo.getSpecialPage());
		special.setStartTime(specialVo.getStartTime());
		special.setEndTime(specialVo.getEndTime());
		special.setSpecialCover(specialVo.getSpecialCover());
		special.setSpecialIntroduce(specialVo.getSpecialIntroduce());
		special.setStatus(specialVo.getStatus());
		special.setHomeFlag(specialVo.getHomeFlag());
//		special.setFakeSalesCopy(specialVo.getFakeSalesCopy());
		special.setListImg(specialVo.getListImg());
		special.setSpecialSort(specialVo.getSpecialSort());
		special.setObjTypeId(specialVo.getObjTypeId());
		special.setObjValue(specialVo.getObjValue());
		rst = specialService.insertSpecial(special);
		specialVo.setSpecialId(special.getSpecialId());
		return rst;
	}

	@Override
	public int updateSpecial(SpecialVo specialVo) {
		int rst = 0;
		Special special = new Special();
		special.setSpecialName(specialVo.getSpecialName());
		special.setSpecialTitle(specialVo.getSpecialTitle());
		special.setSpecialType(specialVo.getSpecialType());
		special.setSpecialPage(specialVo.getSpecialPage());
		special.setStartTime(specialVo.getStartTime());
		special.setEndTime(specialVo.getEndTime());
		special.setSpecialCover(specialVo.getSpecialCover());
		special.setSpecialIntroduce(specialVo.getSpecialIntroduce());
		special.setSpecialId(specialVo.getSpecialId());
		special.setStatus(specialVo.getStatus());
		special.setHomeFlag(specialVo.getHomeFlag());
//		special.setFakeSalesCopy(specialVo.getFakeSalesCopy());
		special.setListImg(specialVo.getListImg());
		special.setSpecialSort(specialVo.getSpecialSort());
		special.setObjTypeId(specialVo.getObjTypeId());
		special.setObjValue(specialVo.getObjValue());
		rst = specialService.updateSpecial(special);
		return rst;
	}

	@Override
	public int deleteSpecialById(int specialId) {
		int rst = specialService.deleteSpecialById(specialId);
		return rst;
	}

	@Override
	protected BaseService<Special> getService() {
		return specialService;
	}

	@Override
	public int hideSpecialHomeFlag(PageData hideSpeParam) {
		return specialService.hideSpecialHomeFlag(hideSpeParam);
	}

	@Override
	public SpecialVo queryOneByTutorial(PageData specialParams) {
		return po2voer.transfer(new SpecialVo(),specialService.queryOneByTutorial(specialParams));
	}

	@Override
	public List<SpecialVo> queryTutorialListPage(PageData pd) {
		return po2voer.transfer(SpecialVo.class, specialService.queryTutorialListPage(pd));
	}

	@Override
	public SpecialVo queryOneByProductId(PageData specialParams) {
		return po2voer.transfer(new SpecialVo(),specialService.queryOneByProductId(specialParams));
	}

	@Override
	public SpecialVo queryOneSpecByProductId(PageData specialParams) {
		Special special = specialService.queryOneSpecByProductId(specialParams);
		if(special == null){
			return null;
		}
		SpecialVo specialVo = new SpecialVo();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			specialVo.setSpecialId(special.getSpecialId());
			specialVo.setSpecialName(special.getSpecialName());
			specialVo.setCreateTime(special.getCreateTime());
			specialVo.setSpecialTitle(special.getSpecialTitle());
			specialVo.setSpecialType(special.getSpecialType());
			specialVo.setSpecialPage(special.getSpecialPage());
			specialVo.setStartTime(special.getStartTime());
			specialVo.setEndTime(special.getEndTime());
			specialVo.setLongStart(sdf.parse(special.getStartTime()).getTime());
			specialVo.setLongEnd(sdf.parse(special.getEndTime()).getTime());
			specialVo.setSpecialCover(special.getSpecialCover());
			specialVo.setSpecialIntroduce(special.getSpecialIntroduce());
			specialVo.setStatus(special.getStatus());
			specialVo.setHomeFlag(special.getHomeFlag());
			specialVo.setIsCollection(special.getIsCollection());
			if(special.getProductSpecVo() != null){
				ProductSpec spec = special.getProductSpecVo();
				ProductSpecVo pspec = new ProductSpecVo();
				pspec.setProduct_id(spec.getProduct_id());
				pspec.setSpec_id(spec.getSpec_id());
				pspec.setSpec_price(spec.getSpec_price());
				pspec.setProduct_name(spec.getProduct_name());
				pspec.setProduct_code(spec.getProduct_code());
				pspec.setSales_copy(spec.getSales_copy());
				pspec.setStock_copy(spec.getStock_copy());
				int salesCopy = 0;
				if(spec.getTotal_sales_copy() != null){
					salesCopy = spec.getTotal_sales_copy();
				}
				pspec.setTotal_sales_copy(salesCopy);
				pspec.setTotal_stock_copy(spec.getTotal_stock_copy());
				pspec.setPackage_desc(spec.getPackage_desc());
				pspec.setActivity_price(spec.getActivity_price());
				specialVo.setFakeSalesCopy(spec.getFake_sales_copy()+salesCopy);
				specialVo.setProductSpecVo(pspec);
			}else{
				ProductSpecVo sp = new ProductSpecVo();
				sp.setSpec_price("0");
				sp.setActivity_price("0");
				specialVo.setProductSpecVo(sp);
			}
		} catch (Exception e) {
			log.error("queryOneSpecByProductId--error", e);
		}
		return specialVo;
	}

}
