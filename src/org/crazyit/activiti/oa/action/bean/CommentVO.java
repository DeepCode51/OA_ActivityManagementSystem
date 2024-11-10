package org.crazyit.activiti.oa.action.bean;

import java.io.Serializable;

public class CommentVO implements Serializable {

	// 评论人
	private String userName;
	
	// 评论内容
	private String content;
	
	// 评论时间
	private String time;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
