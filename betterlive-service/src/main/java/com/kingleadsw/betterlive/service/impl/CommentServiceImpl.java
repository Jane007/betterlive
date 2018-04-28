package com.kingleadsw.betterlive.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.CommentMapper;
import com.kingleadsw.betterlive.model.Comment;
import com.kingleadsw.betterlive.service.CommentService;


/**
 * 商品 service 实现 层
 * 2017-03-08 by chen
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment>  implements CommentService{
	@Autowired
	private CommentMapper commentMapper;
	
	
	@Override
	public int insertComment(Comment comment) {
		return commentMapper.insertComment(comment);
	}
	
	@Override
	public int insertCommentByStaff(Comment comment) {
		return commentMapper.insertCommentByStaff(comment);
	}

	@Override
	public int updateReplyById(Map<String, String> map) {
		return commentMapper.updateReplyById(map);
	}
	
	@Override
	public int delCommentBycId(PageData pd) {
		return commentMapper.delCommentBycId(pd);
	}

	@Override
	public List<Comment> queryCommentInfoListPage(PageData pd) {
		return commentMapper.queryCommentInfoListPage(pd);
	}

	@Override
	public List<Comment> queryCommentListByProductId(Integer productId) {
		return commentMapper.queryCommentListByProductId(productId);
	}

	@Override
	public Comment queryCommentListByCommId(PageData pd) {
		return commentMapper.queryCommentListByCommId(pd);
	}

	@Override
	protected BaseMapper<Comment> getBaseMapper() {
		return commentMapper;
	}
	
	//根据条件查询商品评论总数
	@Override
	public int queryCommentCountByPid(PageData pd) {
		return commentMapper.queryCommentCountByPid(pd);
	}
	
	@Override
	public Comment queryCommentById(Integer comment_id) {
		return commentMapper.queryCommentById(comment_id);
	}

	@Override
	public int insertCommentByAdmin(Comment comment) {
		return commentMapper.insertCommentByAdmin(comment);
	}

	@Override
	public List<Comment> queryCommentByRootIdListPage(Integer comment_id) {
		return commentMapper.queryCommentByRootIdListPage(comment_id);
	}

	@Override
	public int addComment(Comment comment) {
		return commentMapper.addComment(comment);
	}

	@Override
	public Comment queryRootCommentById(PageData pd) {
		return commentMapper.queryRootCommentById(pd);
	}

}
