package org.crazyit.activiti.oa.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.crazyit.activiti.oa.action.bean.UserVO;
import org.crazyit.activiti.oa.service.UserService;

/**
 * 用户服务实现类
 * 
 * @author yangenxiong
 * 
 */
public class UserServiceImpl implements UserService {

	// Activiti 身份服务组件
	private IdentityService identityService;

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	// 查询全部用户
	public List<UserVO> list() {
		List<User> users = this.identityService.createUserQuery().list();
		List<UserVO> result = new ArrayList<UserVO>();
		for (User user : users) {
			UserVO userVO = new UserVO();
			userVO.setUserId(user.getId());
			userVO.setLastName(user.getLastName());
			// 查询用户组
			Group group = this.identityService.createGroupQuery()
					.groupMember(user.getId()).singleResult();
			// 查询年龄信息
			String age = identityService.getUserInfo(user.getId(), "age");
			userVO.setGroupName(group.getName());
			userVO.setAge(Integer.parseInt(age));
			result.add(userVO);
		}
		return result;
	}

	// 新建一个用户
	public void save(UserVO userForm) {
		// 生成一个唯一的用户id
		String uuid = UUID.randomUUID().toString();
		User user = this.identityService.newUser(uuid);
		user.setLastName(userForm.getLastName());
		user.setPassword(userForm.getPasswd());
		this.identityService.saveUser(user);
		// 加入年龄信息
		this.identityService.setUserInfo(user.getId(), "age",
				String.valueOf(userForm.getAge()));
		// 设置与用户组的关系
		this.identityService.createMembership(user.getId(),
				userForm.getGroupId());
	}

	// 验证用户
	public User loginValidate(UserVO userForm) {
		// 根据用户的名称查询用户
		User user = this.identityService.createUserQuery()
				.userLastName(userForm.getLastName()).singleResult();
		if (user == null)
			return null;
		// 验证密码
		if (userForm.getPasswd().equals(user.getPassword())) {
			return user;
		}
		return null;
	}

	public void delete(String userId) {
		this.identityService.deleteUser(userId);
	}

	public Group getGroup(String userId) {
		return this.identityService.createGroupQuery().groupMember(userId)
				.singleResult();
	}

}
