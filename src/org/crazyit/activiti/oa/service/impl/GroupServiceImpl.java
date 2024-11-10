package org.crazyit.activiti.oa.service.impl;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.crazyit.activiti.oa.service.GroupService;

/**
 * 用户组服务实现类
 * @author yangenxiong
 *
 */
public class GroupServiceImpl implements GroupService {

	private IdentityService identityService;

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	public List<Group> list() {
		return identityService.createGroupQuery().list();
	}	
}
