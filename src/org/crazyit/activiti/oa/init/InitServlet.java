package org.crazyit.activiti.oa.init;

import java.io.InputStream;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class InitServlet extends HttpServlet {

	public void init(ServletConfig servletConfig) throws ServletException {
		ServletContext servletContext = servletConfig.getServletContext();
		// 获取Spring上下文
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		// 用户组和用户数据
		IdentityService identityService = (IdentityService) webApplicationContext
				.getBean("identityService");
		initGroupsAndUsers(identityService);
		// 部署流程
		RepositoryService repositoryService = (RepositoryService) webApplicationContext
				.getBean("repositoryService");
		initProcessDefinition(repositoryService);
	}

	// 部署流程定义
	private void initProcessDefinition(RepositoryService repositoryService) {
		repositoryService.createDeployment().addClasspathResource("bpmn/CountSalary.bpmn").deploy();
		repositoryService.createDeployment().addClasspathResource("bpmn/ExpenseAccount.bpmn").deploy();
		repositoryService.createDeployment().addClasspathResource("bpmn/SalaryAdjust.bpmn").deploy();
		repositoryService.createDeployment().addClasspathResource("bpmn/Vacation.bpmn").deploy();
	}

	// 创建用户组及其用户
	private void createGroup(IdentityService identityService, String groupId,
			String groupName, String groupType, String userId, String userName,
			String passwd) {
		// 用户组
		Group g1 = identityService.newGroup(groupId);
		g1.setName(groupName);
		g1.setType(groupType);
		identityService.saveGroup(g1);
		// 用户
		User u = identityService.newUser(userId);
		u.setLastName(userName);
		u.setPassword(passwd);
		identityService.saveUser(u);
		identityService.setUserInfo(u.getId(), "age", String.valueOf(30));
		// 绑定关系
		identityService.createMembership(u.getId(), g1.getId());
	}

	// 初始化用户组
	private void initGroupsAndUsers(IdentityService identityService) {
		// 用户组
		createGroup(identityService, "employee", "员工组", "employee", UUID
				.randomUUID().toString(), "员工甲", "123456");
		createGroup(identityService, "manager", "经理组", "manager", UUID
				.randomUUID().toString(), "经理甲 ", "123456");
		createGroup(identityService, "director", "总监组", "director", UUID
				.randomUUID().toString(), "总监甲 ", "123456");
		createGroup(identityService, "hr", "人事组", "hr", UUID.randomUUID()
				.toString(), "人事甲 ", "123456");
		createGroup(identityService, "boss", "老板组", "boss", UUID.randomUUID()
				.toString(), "老板甲 ", "123456");
		createGroup(identityService, "finance", "财务组", "finance", UUID
				.randomUUID().toString(), "财务甲 ", "123456");
	}
}
