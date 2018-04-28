package com.kingleadsw.betterlive.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.CommentManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.model.Comment;
import com.kingleadsw.betterlive.service.CommentService;
import com.kingleadsw.betterlive.vo.CommentVo;
import com.kingleadsw.betterlive.vo.CustomerVo;

/**
 * 订单商品评论 交互层
 * 2017-03-10 by chen
 */
@Component
@Transactional
public class CommentManagerImpl extends BaseManagerImpl<CommentVo,Comment> implements CommentManager{
	@Autowired
	private CommentService commentService;
	
	
	@Override
	public int insertComment(CommentVo comment) {
		int ret=0;
		
		Comment comments=new Comment();
		comments.setCustomer_id(comment.getCustomer_id());
		comments.setOrder_code(comment.getOrder_code());
		comments.setOrder_id(comment.getOrder_id());
		comments.setProduct_id(comment.getProduct_id());
		comments.setContent(comment.getContent());
		comments.setContent_imgs(comment.getContent_imgs());
		comments.setStatus(2);//默认审核通过
		comments.setRoot_id(comment.getRoot_id());
		if(comment.getIs_custom_service() != null){
			comments.setIs_custom_service(comment.getIs_custom_service());
		}
		comments.setParent_id(comment.getParent_id());
		ret=commentService.insertComment(comments);
		comment.setComment_id(comments.getComment_id());
		if(ret>0){
			return ret;
		}
		
		return 0;
	}

	@Override
	public int insertCommentByStaff(CommentVo comment) {
		int ret=0;
		
		Comment comments=new Comment();
        comments.setReplay_staff_id(comment.getReplay_staff_id());
		comments.setOrder_code(comment.getOrder_code());
		comments.setOrder_id(comment.getOrder_id());
		comments.setProduct_id(comment.getProduct_id());
		comments.setReply_msg(comment.getReply_msg());
		comments.setContent_imgs(comment.getContent_imgs());
		ret=commentService.insertComment(comments);
		if(ret>0){
			return ret;
		}
		
		return 0;
	}

	@Override
	public int updateReplyById(Map<String, String> map) {
		int ret=0;
		
		ret=commentService.updateReplyById(map);
		
		if(ret>0){
			return ret;
		}
		return 0;
	}

	@Override
	public int delCommentBycId(PageData pd) {
		return commentService.delCommentBycId(pd);
	}

	@Override
	public CommentVo queryCommentListByCommId(PageData pd) {
		 
		Comment comment=commentService.queryCommentListByCommId(pd);
		if(null == comment){
			return null;
		}
		
		CommentVo commentVo=po2voer.transfer(new CommentVo(),comment);
//		if(null!=comment.getAdmin()){
//			if(null!=comment.getReplay_staff_id() && comment.getReplay_staff_id()>0){
//				AdminVo adminVo=new AdminVo();
//				adminVo.setStaffId(comment.getAdmin().getStaffId());
//				adminVo.setUsername(comment.getAdmin().getUsername());
//				adminVo.setLoginname(comment.getAdmin().getLoginname());
//				adminVo.setSex(comment.getAdmin().getSex());
//				adminVo.setMobile(comment.getAdmin().getMobile());
//				adminVo.setHeadUrl(comment.getAdmin().getHeadUrl());
//				commentVo.setAdminVo(adminVo);
//			}
//		}
		if(comment.getCustomer() != null){
			CustomerVo customerVo=new CustomerVo();
			customerVo.setCustomer_id(comment.getCustomer().getCustomer_id());
			customerVo.setNickname(comment.getCustomer().getNickname());
			customerVo.setMobile(comment.getCustomer().getMobile());
			customerVo.setHead_url(comment.getCustomer().getHead_url());
			commentVo.setCustomerVo(customerVo);
		}
		
//		ReplyVo replyVo=new ReplyVo();
//		replyVo.setReplay_staff_id(comment.getReply().getReplay_staff_id());
//		replyVo.setReplay_time(comment.getReply().getReplay_time());
//		replyVo.setReply_msg(comment.getReply().getReply_msg());
//		commentVo.setReplyVo(replyVo);
		
		if(null!=commentVo.getContent_imgs()  &&  !"".equals(commentVo.getContent_imgs())){
			commentVo.setCommentArrayImgs(commentVo.getContent_imgs().split(","));
		}
		
		return commentVo;
	}

	@Override
	public List<CommentVo> queryCommentInfoListPage(PageData pd) {
		
		List<Comment> listComment=commentService.queryCommentInfoListPage(pd);
		
		List<CommentVo> listCommentVo=null; 
		if(null!=listComment && listComment.size()>0){
			listCommentVo=new ArrayList<CommentVo>();
			
			for (int i = 0; i < listComment.size(); i++) {
				
				Comment comment=listComment.get(i);
				CommentVo commentVo=po2voer.transfer(new CommentVo(),comment);
//				if(null!=comment.getAdmin()){
//					if(null!=comment.getReplay_staff_id() && comment.getReplay_staff_id()>0){
//						AdminVo adminVo=new AdminVo();
//						adminVo.setStaffId(comment.getAdmin().getStaffId());
//						adminVo.setUsername(comment.getAdmin().getUsername());
//						adminVo.setLoginname(comment.getAdmin().getLoginname());
//						adminVo.setSex(comment.getAdmin().getSex());
//						adminVo.setMobile(comment.getAdmin().getMobile());
//						adminVo.setHeadUrl(comment.getAdmin().getHeadUrl());
//						commentVo.setAdminVo(adminVo);
//					}
//				}
				if(comment.getCustomer() != null){
					CustomerVo customerVo=new CustomerVo();
					customerVo.setCustomer_id(comment.getCustomer().getCustomer_id());
					customerVo.setNickname(comment.getCustomer().getNickname());
					customerVo.setMobile(comment.getCustomer().getMobile());
					customerVo.setHead_url(comment.getCustomer().getHead_url());
					commentVo.setCustomerVo(customerVo);
				}
//				ReplyVo replyVo=new ReplyVo();
//				//这里判断是为了，用户刚评论完商品，不可能有后台工作人员的反馈，所以需要判断。
//				//当后台评论完成了，这里不会为空
//				Reply reply = comment.getReply();
//				if(null!=reply){
//					if(null!=reply.getReplay_staff_id() &&  !"".equals(comment.getContent_imgs()) ){
//					    replyVo.setReplay_staff_id(reply.getReplay_staff_id());
//					}
//					if(null!=reply.getReplay_time() &&  !"".equals(comment.getContent_imgs())){
//					    replyVo.setReplay_time(reply.getReplay_time());
//					}
//					if(null!=reply.getReply_msg() &&  !"".equals(comment.getContent_imgs())){
//					    replyVo.setReply_msg(reply.getReply_msg());
//					}
//				}
//				
//				commentVo.setReplyVo(replyVo);
				
				if(null!=comment.getContent_imgs()  &&  !"".equals(comment.getContent_imgs())){
					commentVo.setCommentArrayImgs(comment.getContent_imgs().split(","));
				}
				
				listCommentVo.add(commentVo);
			}
		}
		
		return listCommentVo;
	}

	@Override
	public List<CommentVo> queryCommentListByProductId(Integer productId) {
		List<Comment> listComment=commentService.queryCommentListByProductId(productId);
		
		List<CommentVo> listCommentVo=null;
		if(null!=listComment && listComment.size()>0){
			listCommentVo=new ArrayList<CommentVo>();
			
			for (int i = 0; i < listComment.size(); i++) {
				
				Comment comment=listComment.get(i);
				CommentVo commentVo=po2voer.transfer(new CommentVo(),comment);
//				if(null!=comment.getAdmin()){
//					if(null!=comment.getReplay_staff_id() && comment.getReplay_staff_id()>0){
//						AdminVo adminVo=new AdminVo();
//						adminVo.setStaffId(comment.getAdmin().getStaffId());
//						adminVo.setUsername(comment.getAdmin().getUsername());
//						adminVo.setLoginname(comment.getAdmin().getLoginname());
//						adminVo.setSex(comment.getAdmin().getSex());
//						adminVo.setMobile(comment.getAdmin().getMobile());
//						adminVo.setHeadUrl(comment.getAdmin().getHeadUrl());
//						commentVo.setAdminVo(adminVo);
//					}
//				}
				if(comment.getCustomer() != null){
					CustomerVo customerVo=new CustomerVo();
					customerVo.setCustomer_id(comment.getCustomer().getCustomer_id());
					customerVo.setNickname(comment.getCustomer().getNickname());
					customerVo.setMobile(comment.getCustomer().getMobile());
					customerVo.setHead_url(comment.getCustomer().getHead_url());
					commentVo.setCustomerVo(customerVo);
				}
//				ReplyVo replyVo=new ReplyVo();
//				if (comment.getReply() != null) {
//					replyVo.setReplay_staff_id(comment.getReply().getReplay_staff_id());
//					replyVo.setReplay_time(comment.getReply().getReplay_time());
//					replyVo.setReply_msg(comment.getReply().getReply_msg());
//					commentVo.setReplyVo(replyVo);
//				}
//				
				if(null!=comment.getContent_imgs()  &&  !"".equals(comment.getContent_imgs())){
					commentVo.setCommentArrayImgs(comment.getContent_imgs().split(","));
				}
				
				listCommentVo.add(commentVo);
			}
		}
		
		return listCommentVo;
		
	}

	
	@Override
	protected BaseService<Comment> getService() {
		return commentService;
	}

	 //根据条件查询商品评论总数
	@Override
	public int queryCommentCountByPid(PageData pd) {
		return commentService.queryCommentCountByPid(pd);
	}

	@Override
	public CommentVo queryCommentById(Integer comment_id) {
		// TODO Auto-generated method stub
		Comment comment = commentService.queryCommentById(comment_id);
		CommentVo commentVo = po2voer.transfer(new CommentVo(), comment);
		return commentVo;
	}

	@Override
	public int insertCommentByAdmin(CommentVo comment) {
		int ret=0;
		
		Comment comments=new Comment();
		comments.setOrder_code(comment.getOrder_code());
		comments.setOrder_id(comment.getOrder_id());
		comments.setProduct_id(comment.getProduct_id());
		comments.setContent(comment.getContent());
		comments.setStatus(comment.getStatus());//默认审核通过
		comments.setRoot_id(comment.getRoot_id());
		comments.setIs_custom_service(comment.getIs_custom_service());
		comments.setParent_id(comment.getParent_id());
		comments.setCustomer_id(comment.getCustomer_id());
		ret=commentService.insertCommentByAdmin(comments);
		if(ret>0){
			return ret;
		}
		
		return 0;
	}

	@Override
	public List<CommentVo> queryCommentByRootIdListPage(Integer rootId) {
		List<Comment> listComment=commentService.queryCommentByRootIdListPage(rootId);
		
		List<CommentVo> listCommentVo=null;
		if(null!=listComment && listComment.size()>0){
			listCommentVo=new ArrayList<CommentVo>();
			
			for (int i = 0; i < listComment.size(); i++) {
				Comment comment=listComment.get(i);
				CommentVo commentVo=po2voer.transfer(new CommentVo(),comment);
				
				if(comment.getCustomer() != null){
					CustomerVo customerVo=new CustomerVo();
					customerVo.setCustomer_id(comment.getCustomer().getCustomer_id());
					customerVo.setNickname(comment.getCustomer().getNickname());
					customerVo.setMobile(comment.getCustomer().getMobile());
					customerVo.setHead_url(comment.getCustomer().getHead_url());
					commentVo.setCustomerVo(customerVo);
				}
				
				if(null!=comment.getContent_imgs()  &&  !"".equals(comment.getContent_imgs())){
					commentVo.setCommentArrayImgs(comment.getContent_imgs().split(","));
				}
				
				listCommentVo.add(commentVo);
			}
		}
		
		return listCommentVo;
	}

	//@Override
	//public CommentVo getIsCustom(Integer comment_id) {
	//	Comment comment = commentService.getIsCustom(comment_id);
	//	CommentVo commentVo = po2voer.transfer(new CommentVo(), comment);
	//	return commentVo;
	//}


	@Override
	public int addComment(CommentVo comment) {
		int ret=0;
		
		Comment comments=new Comment();
        comments.setReplay_staff_id(comment.getReplay_staff_id());
		comments.setRoot_id(comment.getRoot_id());
		comments.setParent_id(comment.getParent_id());
		comments.setReply_msg(comment.getReply_msg());
		comments.setIs_custom_service(comment.getIs_custom_service());
		ret=commentService.addComment(comments);
		if(ret>0){
			return ret;
		}
		
		return 0;
	}

	@Override
	public CommentVo queryRootCommentById(PageData cpd) {
		Comment comment=commentService.queryRootCommentById(cpd);
		if(null == comment){
			return null;
		}
		
		CommentVo commentVo=po2voer.transfer(new CommentVo(),comment);
		if(comment.getCustomer() != null){
			CustomerVo customerVo=new CustomerVo();
			customerVo.setCustomer_id(comment.getCustomer().getCustomer_id());
			customerVo.setNickname(comment.getCustomer().getNickname());
			customerVo.setMobile(comment.getCustomer().getMobile());
			customerVo.setHead_url(comment.getCustomer().getHead_url());
			commentVo.setCustomerVo(customerVo);
		}
		
//		if(null!=commentVo.getContent_imgs()  &&  !"".equals(commentVo.getContent_imgs())){
//			commentVo.setCommentArrayImgs(commentVo.getContent_imgs().split(","));
//		}
		
		return commentVo;
	}

}
