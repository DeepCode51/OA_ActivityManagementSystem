package org.crazyit.activiti.oa.action;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.crazyit.activiti.oa.service.GroupService;

/**
 * 用户组Action控制器
 * @author yangenxiong
 *
 */
public class GroupAction extends BaseAction {

	// 用户组服务对象
	private GroupService groupService;

	private List<Group> groups;
	
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
	public List<Group> getGroups() {
		return this.groups;
	}
	
	// 查询全部的用户组
	public String list() {
		this.groups = groupService.list();
		return SUCCESS;
	}
}
