package com.kingleadsw.betterlive.biz;

import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.SpecialComment;
import com.kingleadsw.betterlive.vo.SpecialCommentVo;


/**
 * 专题评论 交互层
 */
public interface SpecialCommentManager extends BaseManager<SpecialCommentVo, SpecialComment>{

	List<SpecialCommentVo> queryCommentInfoListPage(PageData replypd);

	SpecialCommentVo queryCommentByCommId(PageData cpd);

	int insertComment(SpecialCommentVo replyVo);
	
	/**
	 * 修改客服回复
	 * @param map
	 * @return
	 */
	int updateReplyById(Map<String, String> map);
	
	/**
	 * 全部评论
	 * @param pd
	 * @return
	 */
	List<SpecialCommentVo> queryCommentByTypeListPage(PageData pd);  

	/**
	 * 查询单个评论的全部回复
	 * @param rootId
	 * @return
	 */
	List<SpecialCommentVo> queryCommentByRootListPage(Integer rootId);
	

	/**
	 * 根据commen_id
	 * @param comment_id
	 * @return
	 */                             
	SpecialCommentVo queryCommentById(Integer comment_id);
	
	/**
	 * 根据commen_id
	 * @param comment_id
	 * @return
	 */                             
	SpecialCommentVo queryCommentVideoById(Integer comment_id);
	
	/**
	 * 根据评论ID和专题ID删除评论
	 * @param pd
	 * @return
	 */
	int delCommentBycId(PageData pd);
	
	/**
	 * 全部视频评论
	 * @param pd
	 * @return
	 */
	List<SpecialCommentVo> queryCommentByVideoListPage(PageData pd);
}
