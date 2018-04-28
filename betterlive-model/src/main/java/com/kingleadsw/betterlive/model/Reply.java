package com.kingleadsw.betterlive.model;


import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 商品 实体类
 * 2017-03-08 by chen
 * @author Coder
 *
 */
public class Reply  extends BasePO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private  String replay_staff_id; //回复评论的客服id
	private  String reply_msg;       //客服回复消息内容
	private  String replay_time;     // 客服回复时间
	
	public String getReplay_staff_id() {
		return replay_staff_id;
	}
	public void setReplay_staff_id(String replay_staff_id) {
		this.replay_staff_id = replay_staff_id;
	}
	public String getReply_msg() {
		return reply_msg;
	}
	public void setReply_msg(String reply_msg) {
		this.reply_msg = reply_msg;
	}
	public String getReplay_time() {
		return replay_time;
	}
	public void setReplay_time(String replay_time) {
		this.replay_time = replay_time;
	}
	
}
