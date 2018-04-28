package com.kingleadsw.betterlive.biz;

import java.text.ParseException;
import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Special;
import com.kingleadsw.betterlive.vo.SpecialVo;

public interface SpecialMananger extends BaseManager<SpecialVo,Special>{
	 /**
	    * 根据条件查询单个专题
	    * @param pageData
	    * @return
	    */
		public SpecialVo selectSpecialByOption(PageData pageData);
	   
	   
	   /**
	    * 查询所有的专题
	    * @param pd
	    * @return
	 * @throws ParseException 
	    */
		public List<SpecialVo> querySpecialList(PageData pd) throws ParseException;
		
		/**
	     * 分页查询专题
	     * @param pd
	     * @return
		 * @throws ParseException 
	     */
		public List<SpecialVo> querySpecialListPage(PageData pd);
		
		
	    /**
		  * 增加专题
		  * @param product
		  * @return
	    */
	     public int insertSpecial(SpecialVo specialVo);

		   /**
		    * 修改专题
		    * 
		    * @param sysDictVo
		    * @return
		    */
	    public int updateSpecial(SpecialVo specialVo);

		  
		   /**
		    *  删除专题
		    */
		public int deleteSpecialById(int specialId);


		/**
		 * 设置非当前ID的专题都取消推荐至首页
		 * @param hideSpeParam
		 * @return
		 */
		public int hideSpecialHomeFlag(PageData hideSpeParam);


		/**
		 * 美食教程信息
		 * @param specialParams
		 * @return
		 */
		public SpecialVo queryOneByTutorial(PageData specialParams);

		/**
		 * 根据商品查询活动
		 * @param specialParams
		 * @return
		 */
		public SpecialVo queryOneByProductId(PageData specialParams);

		/**
		 * 根据商品查询活动以及最低价
		 * @param specialParams
		 * @return
		 */
		public SpecialVo queryOneSpecByProductId(PageData specialParams);


		/**
		 * 查询美食教程列表
		 * @param pd
		 * @return
		 */
		public List<SpecialVo> queryTutorialListPage(PageData pd);

}
