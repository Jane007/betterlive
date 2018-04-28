package com.kingleadsw.betterlive.biz;

import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.biz.BaseManager;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.model.Comment;
import com.kingleadsw.betterlive.vo.CommentVo;


/**
 * 订单商品评论 交互层
 * 2017-03-10 by chen
 */
public interface CommentManager extends BaseManager<CommentVo,Comment>{

	/**
	 * 用户增加商品评论
	 * @param comment
	 * @return
	 */
	int insertComment(CommentVo comment);     						
	
	/**
	 * 店员回复商品评论
	 * @param comment
	 * @return
	 */
	int insertCommentByStaff(CommentVo comment);     
	
	int addComment(CommentVo comment);   
	
	/**
	 * 修改客服回复
	 * @param map
	 * @return
	 */
	int updateReplyById(Map<String, String> map);                   
	
	/**
	 * 根据评论ID删除商品评论
	 * @param pd
	 * @return
	 */
	int delCommentBycId(PageData pd);  					   			
	
	/**
	 * 根据评论ID查询详细
	 * @param pd
	 * @return
	 */
	CommentVo queryCommentListByCommId(PageData pd);           
	
	/**
	 * 根据条件查询商品评论的详细信息
	 * @param pd
	 * @return
	 */
	List<CommentVo> queryCommentInfoListPage(PageData pd);   			
	
	/**
	 * 根据商品id查询审核通过的商品评论
	 * @param productId
	 * @return
	 */
	List<CommentVo> queryCommentListByProductId(Integer productId);    
	
	/**
	 * 根据条件查询商品评论总数
	 * @param pd
	 * @return
	 */
	int queryCommentCountByPid(PageData pd);        
	
	CommentVo queryCommentById(Integer comment_id);
	
	
	/**
	 * 客服回复
	 */
	
	int insertCommentByAdmin(CommentVo comment);
	
	
	List<CommentVo> queryCommentByRootIdListPage(Integer rootId);

	/**
	 * 根据当前评论ID查询根节点
	 * @param cpd
	 * @return
	 */
	CommentVo queryRootCommentById(PageData pd); 
	
}
