package org.crazyit.activiti.oa.action;

import com.opensymphony.xwork2.Action;

public class BaseAction implements Action {

	public String execute() throws Exception {
		return "";
	}
	
	// 测试方法
	public String test() {
		System.out.println("test method");
		return SUCCESS;
	}
}
