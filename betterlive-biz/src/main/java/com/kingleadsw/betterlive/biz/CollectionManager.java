package com.kingleadsw.betterlive.biz;

import java.util.List;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Collection;
import com.kingleadsw.betterlive.vo.CollectionVo;

public interface CollectionManager  extends BaseManager<CollectionVo,Collection> {

	/**
	 * 收藏的商品
	 * @param pd
	 * @return
	 */
	List<CollectionVo> queryListByProduct(PageData pd);

	/**
	 * 限时活动
	 * @param pd
	 * @return
	 */
	List<CollectionVo> queryListByRecommend(PageData pd);

	/**
	 * 美食教程
	 * @param pd
	 * @return
	 */
	List<CollectionVo> queryListByTutorial(PageData pd);

	int insertCollection(CollectionVo collection);

	/**
	 * 推荐美食
	 * @param pd
	 * @return
	 */
	List<CollectionVo> queryListBySpecialArticle(PageData pd);

	/**
	 * 获取收藏数量
	 * @param cl
	 * @return
	 */
	int queryCollectionCount(PageData pd);

}
