package org.crazyit.activiti.oa.service;

import java.util.List;

import org.activiti.engine.identity.Group;

/**
 * 用户组服务接口
 * @author yangenxiong
 *
 */
public interface GroupService {

	/**
	 * 查询全部的用户组
	 * @return
	 */
	List<Group> list();
}
