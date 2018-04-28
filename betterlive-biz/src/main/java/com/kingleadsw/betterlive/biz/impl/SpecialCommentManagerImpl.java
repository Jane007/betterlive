package com.kingleadsw.betterlive.biz.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.SpecialCommentManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.SpecialComment;
import com.kingleadsw.betterlive.service.SpecialCommentService;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.SpecialCommentVo;

/**
 * 专题评论 交互层
 */
@Component
@Transactional
public class SpecialCommentManagerImpl extends BaseManagerImpl<SpecialCommentVo,SpecialComment> implements SpecialCommentManager{
	@Autowired
	private SpecialCommentService specialCommentService;

	@Override
	protected BaseService<SpecialComment> getService() {
		return specialCommentService;
	}

	@Override
	public List<SpecialCommentVo> queryCommentInfoListPage(PageData pd) {
		List<SpecialComment> listComment=specialCommentService.queryCommentInfoListPage(pd);
		
		List<SpecialCommentVo> listCommentVo=null; 
		if(null!=listComment && listComment.size()>0){
			listCommentVo=new ArrayList<SpecialCommentVo>();
			
			for (int i = 0; i < listComment.size(); i++) {
				
				SpecialComment comment=listComment.get(i);
				SpecialCommentVo commentVo=new SpecialCommentVo();
				commentVo.setCommentId(comment.getCommentId());
				commentVo.setCustomerId(comment.getCustomerId());
				commentVo.setContent(comment.getContent());
				commentVo.setContentImgs(comment.getContentImgs());
				commentVo.setStatus(comment.getStatus());
				commentVo.setIsCustomService(comment.getIsCustomService());
				commentVo.setCreateTime(comment.getCreateTime());
				commentVo.setSpecialId(comment.getSpecialId());
				commentVo.setParentId(comment.getParentId());
				commentVo.setRootId(comment.getRootId());
				commentVo.setIsPraise(comment.getIsPraise());
				commentVo.setPraiseCount(comment.getPraiseCount());
				commentVo.setReplyCount(comment.getReplyCount());
				commentVo.setReplyerId(comment.getReplyerId());
				commentVo.setReplyerName(comment.getReplyerName());
				
				if(comment.getCustomerVo() != null){
					CustomerVo customerVo=new CustomerVo();
					customerVo.setCustomer_id(comment.getCustomerVo().getCustomer_id());
					customerVo.setNickname(comment.getCustomerVo().getNickname());
					customerVo.setMobile(comment.getCustomerVo().getMobile());
					customerVo.setHead_url(comment.getCustomerVo().getHead_url());
					commentVo.setCustomerVo(customerVo);
				}
				
				if(null!=comment.getContentImgs()  &&  !"".equals(comment.getContentImgs())){
					commentVo.setCommentArrayImgs(comment.getContentImgs().split(","));
				}
				
				listCommentVo.add(commentVo);
			}
		}
		
		return listCommentVo;
	}

	@Override
	public SpecialCommentVo queryCommentByCommId(PageData pd) {
		SpecialComment comment=specialCommentService.queryCommentByCommId(pd);
		if(null == comment){
			return null;
		}
		
		SpecialCommentVo commentVo=new SpecialCommentVo();
		commentVo.setCommentId(comment.getCommentId());
		commentVo.setCustomerId(comment.getCustomerId());
		commentVo.setContent(comment.getContent());
		commentVo.setContentImgs(comment.getContentImgs());
		commentVo.setStatus(comment.getStatus());
		commentVo.setIsCustomService(comment.getIsCustomService());
		commentVo.setCreateTime(comment.getCreateTime());
		commentVo.setSpecialId(comment.getSpecialId());
		commentVo.setParentId(comment.getParentId());
		commentVo.setRootId(comment.getRootId());
		commentVo.setIsPraise(comment.getIsPraise());
		commentVo.setPraiseCount(comment.getPraiseCount());
		commentVo.setReplyCount(comment.getReplyCount());
		commentVo.setReplyerId(comment.getReplyerId());
		commentVo.setReplyerName(comment.getReplyerName());

		if(comment.getCustomerVo() != null){
			CustomerVo customerVo=new CustomerVo();
			customerVo.setCustomer_id(comment.getCustomerVo().getCustomer_id());
			customerVo.setNickname(comment.getCustomerVo().getNickname());
			customerVo.setMobile(comment.getCustomerVo().getMobile());
			customerVo.setHead_url(comment.getCustomerVo().getHead_url());
			commentVo.setCustomerVo(customerVo);
		}
		
		if(null!=commentVo.getContentImgs()  &&  !"".equals(commentVo.getContentImgs())){
			commentVo.setCommentArrayImgs(commentVo.getContentImgs().split(","));
		}
		
		return commentVo;
	}
	
	/**
	 * 后台查询全部
	 */
	@Override
	public List<SpecialCommentVo> queryCommentByVideoListPage(PageData pd) {
		List<SpecialComment> listComment=specialCommentService.queryCommentByVideoListPage(pd);
		
		List<SpecialCommentVo> listCommentVo=null; 
		if(null!=listComment && listComment.size()>0){
			listCommentVo=new ArrayList<SpecialCommentVo>();
			
			for (int i = 0; i < listComment.size(); i++) {
				
				SpecialComment comment = listComment.get(i);
				SpecialCommentVo commentVo=new SpecialCommentVo();
				
				commentVo.setCommentId(comment.getCommentId());
				commentVo.setCustomerId(comment.getCustomerId());
				commentVo.setContent(comment.getContent());
				commentVo.setContentImgs(comment.getContentImgs());
				commentVo.setStatus(comment.getStatus());
				commentVo.setIsCustomService(comment.getIsCustomService());
				commentVo.setCreateTime(comment.getCreateTime());
				commentVo.setSpecialId(comment.getSpecialId());
				commentVo.setParentId(comment.getParentId());
				commentVo.setRootId(comment.getRootId());
				commentVo.setIsPraise(comment.getIsPraise());
				commentVo.setPraiseCount(comment.getPraiseCount());
				commentVo.setReplyCount(comment.getReplyCount());
				commentVo.setSpecialType(comment.getSpecialType());	
				commentVo.setArticleTitle(comment.getArticleTitle());
				commentVo.setAuthor(comment.getAuthor());
				commentVo.setSpecialTypeChild(comment.getSpecialTypeChild());
				if(comment.getCustomerVo() != null){
					CustomerVo customerVo=new CustomerVo();
					customerVo.setCustomer_id(comment.getCustomerVo().getCustomer_id());
					customerVo.setNickname(comment.getCustomerVo().getNickname());
					customerVo.setMobile(comment.getCustomerVo().getMobile());
					customerVo.setHead_url(comment.getCustomerVo().getHead_url());
					commentVo.setCustomerVo(customerVo);
				}
				
				if(null!=comment.getContentImgs()  &&  !"".equals(comment.getContentImgs())){
					commentVo.setCommentArrayImgs(comment.getContentImgs().split(","));
				}
				
				listCommentVo.add(commentVo);
			}
		}
		
		return listCommentVo;
	}
	
	/**
	 * 后台查询全部
	 */
	@Override
	public List<SpecialCommentVo> queryCommentByTypeListPage(PageData pd) {
		List<SpecialComment> listComment=specialCommentService.queryCommentByTypeListPage(pd);
		
		List<SpecialCommentVo> listCommentVo=null; 
		if(null!=listComment && listComment.size()>0){
			listCommentVo=new ArrayList<SpecialCommentVo>();
			
			for (int i = 0; i < listComment.size(); i++) {
				
				SpecialComment comment = listComment.get(i);
				SpecialCommentVo commentVo=new SpecialCommentVo();
				
				commentVo.setCommentId(comment.getCommentId());
				commentVo.setCustomerId(comment.getCustomerId());
				commentVo.setContent(comment.getContent());
				commentVo.setContentImgs(comment.getContentImgs());
				commentVo.setStatus(comment.getStatus());
				commentVo.setIsCustomService(comment.getIsCustomService());
				commentVo.setCreateTime(comment.getCreateTime());
				commentVo.setSpecialId(comment.getSpecialId());
				commentVo.setParentId(comment.getParentId());
				commentVo.setRootId(comment.getRootId());
				commentVo.setIsPraise(comment.getIsPraise());
				commentVo.setPraiseCount(comment.getPraiseCount());
				commentVo.setReplyCount(comment.getReplyCount());
				commentVo.setSpecialType(comment.getSpecialType());	
				commentVo.setArticleTitle(comment.getArticleTitle());
				commentVo.setAuthor(comment.getAuthor());
				commentVo.setSpecialTypeChild(comment.getSpecialTypeChild());
				if(comment.getCustomerVo() != null){
					CustomerVo customerVo=new CustomerVo();
					customerVo.setCustomer_id(comment.getCustomerVo().getCustomer_id());
					customerVo.setNickname(comment.getCustomerVo().getNickname());
					customerVo.setMobile(comment.getCustomerVo().getMobile());
					customerVo.setHead_url(comment.getCustomerVo().getHead_url());
					commentVo.setCustomerVo(customerVo);
				}
				
				if(null!=comment.getContentImgs()  &&  !"".equals(comment.getContentImgs())){
					commentVo.setCommentArrayImgs(comment.getContentImgs().split(","));
				}
				
				listCommentVo.add(commentVo);
			}
		}
		
		return listCommentVo;
	}
	
	@Override
	public int insertComment(SpecialCommentVo commentVo) {
			int ret=0;
		
		SpecialComment comments=new SpecialComment();
		comments.setCustomerId(commentVo.getCustomerId());
		comments.setContent(commentVo.getContent());
		comments.setContentImgs(commentVo.getContentImgs());
		comments.setStatus(2);//默认审核通过
		comments.setIsCustomService(commentVo.getIsCustomService());
		comments.setSpecialId(commentVo.getSpecialId());
		comments.setSpecialType(commentVo.getSpecialType());
		if(commentVo.getParentId() > 0){
			comments.setParentId(commentVo.getParentId());
		}
		if(commentVo.getRootId() > 0){
			comments.setRootId(commentVo.getRootId());
		}
		ret=specialCommentService.insertComment(comments);
		commentVo.setCommentId(comments.getCommentId());
		if(ret>0){
			return ret;
		}
		
		return 0;
	}

	public int updateReplyById(Map<String, String> map) {
    	return specialCommentService.updateReplyById(map);
	}

	@Override
	public List<SpecialCommentVo> queryCommentByRootListPage(Integer rootId) {
		
		List<SpecialComment> listSpecial = specialCommentService.queryCommentByRootListPage(rootId);
		
		List<SpecialCommentVo> listSpecialVo=null;
		if(null!=listSpecial && listSpecial.size()>0){
			
			listSpecialVo=new ArrayList<SpecialCommentVo>();
			
			for (int i = 0; i < listSpecial.size(); i++) {
				SpecialComment comment = listSpecial.get(i);
				SpecialCommentVo commentVo = new SpecialCommentVo();
				
				commentVo.setCommentId(comment.getCommentId());
				commentVo.setCustomerId(comment.getCustomerId());
				commentVo.setContent(comment.getContent());
				commentVo.setStatus(comment.getStatus());
				commentVo.setCreateTime(comment.getCreateTime());
				commentVo.setSpecialId(comment.getSpecialId());
				commentVo.setParentId(comment.getParentId());
				commentVo.setRootId(comment.getRootId());
				commentVo.setArticleTitle(comment.getArticleTitle());			
				commentVo.setReplyerName(comment.getReplyerName());
				if(comment.getCustomerVo() != null){
					
					CustomerVo customerVo=new CustomerVo();
					customerVo.setCustomer_id(comment.getCustomerVo().getCustomer_id());
					customerVo.setNickname(comment.getCustomerVo().getNickname());
					customerVo.setMobile(comment.getCustomerVo().getMobile());
					commentVo.setCustomerVo(customerVo);
				}

				listSpecialVo.add(commentVo);
			}
		}
		
		return listSpecialVo;
	}
	
	
	@Override
	public SpecialCommentVo queryCommentById(Integer comment_id) {
		
		SpecialComment speciacomment = specialCommentService.queryCommentById(comment_id);
		SpecialCommentVo speciacommentVo =new SpecialCommentVo();
		speciacommentVo.setSpecialType(speciacomment.getSpecialType());
		speciacommentVo.setSpecialId(speciacomment.getSpecialId());
		speciacommentVo.setStatus(speciacomment.getStatus());
		speciacommentVo.setCommentId(speciacomment.getCommentId());
		speciacommentVo.setCustomerId(speciacomment.getCustomerId());		
		speciacommentVo.setContent(speciacomment.getContent());
		speciacommentVo.setParentId(speciacomment.getParentId());
		speciacommentVo.setRootId(speciacomment.getRootId());	
		speciacommentVo.setCreateTime(speciacomment.getCreateTime());
		speciacommentVo.setOpinion(speciacomment.getOpinion());
		speciacommentVo.setContent(speciacomment.getContent());
		speciacommentVo.setSpecialTypeChild(speciacomment.getSpecialTypeChild());
	
		if(speciacomment.getCustomerVo() != null){
			
			CustomerVo customerVo=new CustomerVo();
			customerVo.setCustomer_id(speciacomment.getCustomerVo().getCustomer_id());
			customerVo.setNickname(speciacomment.getCustomerVo().getNickname());
			customerVo.setMobile(speciacomment.getCustomerVo().getMobile());
			customerVo.setHead_url(speciacomment.getCustomerVo().getHead_url());
			speciacommentVo.setCustomerVo(customerVo);
		}
		return speciacommentVo;
	}
	
	@Override
	public SpecialCommentVo queryCommentVideoById(Integer comment_id) {
		
		SpecialComment speciacomment = specialCommentService.queryCommentVideoById(comment_id);
		SpecialCommentVo speciacommentVo =new SpecialCommentVo();
		speciacommentVo.setSpecialType(speciacomment.getSpecialType());
		speciacommentVo.setSpecialId(speciacomment.getSpecialId());
		speciacommentVo.setStatus(speciacomment.getStatus());
		speciacommentVo.setCommentId(speciacomment.getCommentId());
		speciacommentVo.setCustomerId(speciacomment.getCustomerId());		
		speciacommentVo.setContent(speciacomment.getContent());
		speciacommentVo.setParentId(speciacomment.getParentId());
		speciacommentVo.setRootId(speciacomment.getRootId());	
		speciacommentVo.setCreateTime(speciacomment.getCreateTime());
		speciacommentVo.setOpinion(speciacomment.getOpinion());
		speciacommentVo.setContent(speciacomment.getContent());
	
	
		if(speciacomment.getCustomerVo() != null){
			
			CustomerVo customerVo=new CustomerVo();
			customerVo.setCustomer_id(speciacomment.getCustomerVo().getCustomer_id());
			customerVo.setNickname(speciacomment.getCustomerVo().getNickname());
			customerVo.setMobile(speciacomment.getCustomerVo().getMobile());
			customerVo.setHead_url(speciacomment.getCustomerVo().getHead_url());
			speciacommentVo.setCustomerVo(customerVo);
		}
		return speciacommentVo;
	}

	@Override
	public int delCommentBycId(PageData pd) {
		
		return specialCommentService.delCommentBycId(pd);
	}
}
