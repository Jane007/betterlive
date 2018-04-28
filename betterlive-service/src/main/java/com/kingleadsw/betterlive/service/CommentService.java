package com.kingleadsw.betterlive.service;

import java.util.List;
import java.util.Map;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Comment;

/**
 * 商品 service 层
 * 2017-03-08 by chen
 */
public interface CommentService extends BaseService<Comment> {
	
	/**
	 * 用户增加商品评论
	 * @param comment
	 * @return
	 */
	int insertComment(Comment comment);     						
	
	/**
	 * 店员回复商品评论
	 * @param comment
	 * @return
	 */
	int insertCommentByStaff(Comment comment);     					
	
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
	Comment queryCommentListByCommId(PageData pd);           
	
	/**
	 * 根据条件查询商品评论的详细信息
	 * @param pd
	 * @return
	 */
	List<Comment> queryCommentInfoListPage(PageData pd);   			
	
	/**
	 * 根据商品id查询审核通过的商品评论
	 * @param productId
	 * @return
	 */
	List<Comment> queryCommentListByProductId(Integer productId);    
	
	/**
	 * 根据条件查询商品评论总数
	 * @param pd
	 * @return
	 */
	int queryCommentCountByPid(PageData pd);                       
	
	
	Comment queryCommentById(Integer comment_id);
	
	/**
	 * 客服回复
	 */
	
	int insertCommentByAdmin(Comment comment);
	
	
	List<Comment> queryCommentByRootIdListPage(Integer rootId); 
	
	int addComment(Comment comment);

	Comment queryRootCommentById(PageData cpd);     
	
}
