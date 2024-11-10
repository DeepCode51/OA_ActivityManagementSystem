package org.crazyit.activiti.oa.service;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.crazyit.activiti.oa.action.bean.UserVO;

/**
 * 用户服务接口
 * @author yangenxiong
 *
 */
public interface UserService {

	/**
	 * 查询全部用户
	 * @return
	 */
	List<UserVO> list();

	/**
	 * 保存一个用户
	 * @param user
	 */
	void save(UserVO userForm);
	
	/**
	 * 删除一个用户
	 * @param userId
	 */
	void delete(String userId);
	
	/**
	 * 验证用户身份（根据用户名称和密码）
	 * @param userForm
	 * @return
	 */
	User loginValidate(UserVO userForm);
	
	/**
	 * 根据用户查询他所在的用户组
	 * @param userId
	 * @return
	 */
	Group getGroup(String userId);
}
