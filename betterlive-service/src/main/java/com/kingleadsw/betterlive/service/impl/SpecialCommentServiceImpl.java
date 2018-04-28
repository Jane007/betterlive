package com.kingleadsw.betterlive.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingleadsw.betterlive.core.dao.BaseMapper;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseServiceImpl;
import com.kingleadsw.betterlive.dao.SpecialCommentMapper;
import com.kingleadsw.betterlive.model.SpecialComment;
import com.kingleadsw.betterlive.service.SpecialCommentService;

@Service("/specialCommentServiceImpl")
public class SpecialCommentServiceImpl extends BaseServiceImpl<SpecialComment>  implements SpecialCommentService {

	@Autowired
	private SpecialCommentMapper specialCommentMapper;
	@Override
	protected BaseMapper<SpecialComment> getBaseMapper() {
		return specialCommentMapper;
	}
	@Override
	public SpecialComment queryCommentByCommId(PageData pd) {
		return specialCommentMapper.queryCommentByCommId(pd);
	}
	@Override
	public List<SpecialComment> queryCommentInfoListPage(PageData pd) {
		return specialCommentMapper.queryCommentInfoListPage(pd);
	}
	@Override
	public int insertComment(SpecialComment comment) {
		return specialCommentMapper.insertComment(comment);
	}
	@Override
	public int updateReplyById(Map<String, String> map) {
	
		return specialCommentMapper.updateReplyById(map);
	}
	
	@Override
	public List<SpecialComment> queryCommentByTypeListPage(PageData pd) {
		
		return specialCommentMapper.queryCommentByTypeListPage(pd);
	}
	
	@Override
	public List<SpecialComment> queryCommentByVideoListPage(PageData pd) {
		
		return specialCommentMapper.queryCommentByVideoListPage(pd);
	}
	
	@Override
	public List<SpecialComment> queryCommentByRootListPage(Integer rootId) {
		
		return specialCommentMapper.queryCommentByRootListPage(rootId);
	}
	@Override
	public SpecialComment queryCommentById(Integer comment_id) {
		return specialCommentMapper.queryCommentById(comment_id);
	}
	
	@Override
	public SpecialComment queryCommentVideoById(Integer comment_id) {
		return specialCommentMapper.queryCommentVideoById(comment_id);
	}
	@Override
	public int delCommentBycId(PageData pd) {
		return specialCommentMapper.delCommentBycId(pd);
	}
}
