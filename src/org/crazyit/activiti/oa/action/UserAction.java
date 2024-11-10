package org.crazyit.activiti.oa.action;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.struts2.ServletActionContext;
import org.crazyit.activiti.oa.action.bean.UserVO;
import org.crazyit.activiti.oa.service.GroupService;
import org.crazyit.activiti.oa.service.UserService;

public class UserAction extends BaseAction {

	private UserService userService;
	
	private GroupService groupService;

	private List<UserVO> users;
	
	private List<Group> groups;
	
	private UserVO userForm;
	
	private String userId;
	
	// 用户登录信息
	private String loginMsg;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserForm(UserVO userForm) {
		this.userForm = userForm;
	}
	
	public UserVO getUserForm() {
		return this.userForm;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
	public List<Group> getGroups() {
		return this.groups;
	}
	
	public List<UserVO> getUsers() {
		return this.users;
	}
	
	public String getLoginMsg() {
		return loginMsg;
	}
	
	public String list() {
		this.users = this.userService.list();
		return SUCCESS;
	}
	
	// 打开添加用户界面时，查询全部用户组
	public String openAddUser() {
		this.groups = this.groupService.list();
		return "openAddUser";
	}
	
	public String add() {
		// 保存一个用户
		this.userService.save(getUserForm());
		return "add";
	}
	
	public String delete() {
		this.userService.delete(this.userId);
		return "delete";
	}
	
	// 用户登录
	public String login() {
		User user = this.userService.loginValidate(userForm);
		if (user != null) {
			// 将用户放到session中
			ServletActionContext.getContext().getSession().put("user", user);
			// 将用户组放到session中
			Group group = this.userService.getGroup(user.getId());
			ServletActionContext.getContext().getSession().put("group", group);
			return "loginSuccess";
		} else {
			this.loginMsg = "用户名或密码错误";
			return "loginFail";
		}
	}
}
