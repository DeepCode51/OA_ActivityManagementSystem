package org.crazyit.activiti.oa.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.crazyit.activiti.oa.action.bean.BaseForm;
import org.crazyit.activiti.oa.action.bean.CommentVO;
import org.crazyit.activiti.oa.action.bean.ExpenseAccountForm;
import org.crazyit.activiti.oa.action.bean.FormField;
import org.crazyit.activiti.oa.action.bean.ProcessVO;
import org.crazyit.activiti.oa.action.bean.SalaryForm;
import org.crazyit.activiti.oa.action.bean.TaskVO;
import org.crazyit.activiti.oa.action.bean.VacationForm;
import org.crazyit.activiti.oa.dao.ApplicationDao;
import org.crazyit.activiti.oa.entity.ExpenseAccount;
import org.crazyit.activiti.oa.entity.SalaryAdjust;
import org.crazyit.activiti.oa.entity.Vacation;
import org.crazyit.activiti.oa.service.ProcessService;
import org.crazyit.activiti.oa.util.DateUtil;

public class ProcessServiceImpl implements ProcessService {

	private RuntimeService runtimeService;

	private RepositoryService repositoryService;

	private IdentityService identityService;

	private TaskService taskService;

	private ApplicationDao applicationDao;

	private ProcessEngine processEngine;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

	// 启动请假流程
	public ProcessInstance startVacation(VacationForm vacation) {
		// 设置标题
		vacation.setTitle(vacation.getUserName() + " 的请假申请");
		vacation.setBusinessType("请假申请");
		// 查找流程定义
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey("Vacation").singleResult();
		// 初始化任务参数
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("arg", vacation);
		// 启动流程
		ProcessInstance pi = this.runtimeService.startProcessInstanceByKey(pd
				.getKey());
		// 查询第一个任务
		Task firstTask = this.taskService.createTaskQuery()
				.processInstanceId(pi.getId()).singleResult();
		// 设置任务受理人
		taskService.setAssignee(firstTask.getId(), vacation.getUserId());
		// 完成任务
		taskService.complete(firstTask.getId(), vars);
		// 记录请假数据
		saveVacation(vacation, pi.getId());
		return pi;
	}

	// 将一条请假申请保存到OA_VACATION表中
	private void saveVacation(VacationForm vacForm, String piId) {
		Vacation vac = new Vacation();
		vac.setBeginDate(DateUtil.getDate(vacForm.getStartDate()));
		vac.setDays(vacForm.getDays());
		vac.setEndDate(DateUtil.getDate(vacForm.getEndDate()));
		vac.setProcessInstanceId(piId);
		vac.setReason(vacForm.getReason());
		vac.setVacationType(vacForm.getVacationType());
		vac.setUserId(vacForm.getUserId());
		this.applicationDao.saveVacation(vac);
	}

	// 启动薪资调整申请
	public ProcessInstance startSalaryAdjust(SalaryForm salary) {
		salary.setBusinessType("薪资调整");
		salary.setTitle(salary.getEmployeeName() + " 的薪资调整申请");
		// 验证用户是否存在
		User user = this.identityService.createUserQuery()
				.userLastName(salary.getEmployeeName()).singleResult();
		if (user == null) {
			throw new RuntimeException("调薪用户不存在");
		}
		// 查找流程定义
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey("SalaryAdjust").singleResult();
		// 初始化参数
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("arg", salary);
		vars.put("pass", true);
		ProcessInstance pi = this.runtimeService.startProcessInstanceByKey(pd
				.getKey());
		Task task = this.taskService.createTaskQuery()
				.processInstanceId(pi.getId()).singleResult();
		// 完成任务
		taskService.complete(task.getId(), vars);
		// 将数据保存到OA_SALARY_ADJUST表
		saveSalaryAdjust(salary, pi.getId(), user.getId());
		return pi;
	}

	// 保存薪资调整数据
	private void saveSalaryAdjust(SalaryForm salaryForm, String piId,
			String userId) {
		SalaryAdjust salary = new SalaryAdjust();
		salary.setAdjustMoney(new BigDecimal(salaryForm.getMoney()));
		salary.setDate(new Date());
		salary.setDscp(salaryForm.getDscp());
		salary.setUserId(userId);
		salary.setProcessInstanceId(piId);
		this.applicationDao.saveSalaryAdjust(salary);
	}

	// 启动报销流程
	public ProcessInstance startExpenseAccount(
			ExpenseAccountForm expenseAccountForm) {
		expenseAccountForm.setTitle(expenseAccountForm.getUserName() + " 报销申请");
		expenseAccountForm.setBusinessType("报销申请");
		// 查找流程定义
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey("ExpenseAccount").singleResult();
		// 初始化流程参数
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("arg", expenseAccountForm);
		// 启动流程
		ProcessInstance pi = this.runtimeService.startProcessInstanceByKey(pd
				.getKey());
		Task task = this.taskService.createTaskQuery()
				.processInstanceId(pi.getId()).singleResult();
		// 完成任务
		taskService.complete(task.getId(), vars);
		// 保存到业务系统的数据库
		saveExpenseAccount(expenseAccountForm, pi.getId());
		return pi;
	}

	// 保存报销申请到OA_EXPENSE_ACCOUNT表
	private void saveExpenseAccount(ExpenseAccountForm form, String piId) {
		ExpenseAccount account = new ExpenseAccount();
		account.setDate(new Date());
		account.setMoney(new BigDecimal(form.getMoney()));
		account.setProcessInstanceId(piId);
		account.setUserId(form.getUserId());
		this.applicationDao.saveExpenseAccount(account);
	}

	// 查询报销申请
	public List<ProcessVO> listExpenseAccount(String userId) {
		List<ExpenseAccount> accounts = this.applicationDao
				.listExpenseAccount(userId);
		List<ProcessVO> result = new ArrayList<ProcessVO>();
		for (ExpenseAccount account : accounts) {
			// 查询流程实例
			ProcessInstance pi = this.runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(account.getProcessInstanceId())
					.singleResult();
			if (pi != null) {
				// 查询流程参数
				BaseForm var = (BaseForm) this.runtimeService.getVariable(
						pi.getId(), "arg");
				// 封装界面对象
				ProcessVO vo = new ProcessVO();
				vo.setTitle(var.getTitle());
				vo.setRequestDate(var.getRequestDate());
				vo.setId(pi.getId());
				result.add(vo);
			}
		}
		return result;
	}

	// 查询用户的薪资调整申请
	public List<ProcessVO> listSalaryAdjust(String userId) {
		List<SalaryAdjust> salarys = this.applicationDao
				.listSalaryAdjust(userId);
		List<ProcessVO> result = new ArrayList<ProcessVO>();
		for (SalaryAdjust salary : salarys) {
			// 查询流程实例
			ProcessInstance pi = this.runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(salary.getProcessInstanceId())
					.singleResult();
			if (pi != null) {
				// 查询流程参数
				BaseForm var = (BaseForm) this.runtimeService.getVariable(
						pi.getId(), "arg");
				// 封装界面对象
				ProcessVO vo = new ProcessVO();
				vo.setTitle(var.getTitle());
				vo.setRequestDate(var.getRequestDate());
				vo.setId(pi.getId());
				result.add(vo);
			}

		}
		return result;
	}

	// 查询请假申请
	public List<ProcessVO> listVacation(String userId) {
		// 查询OA_VACATION表的数据
		List<Vacation> vacs = this.applicationDao.listVacation(userId);
		List<ProcessVO> result = new ArrayList<ProcessVO>();
		for (Vacation vac : vacs) {
			// 查询流程实例
			ProcessInstance pi = this.runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(vac.getProcessInstanceId())
					.singleResult();
			if (pi != null) {
				// 查询流程参数
				BaseForm var = (BaseForm) this.runtimeService.getVariable(
						pi.getId(), "arg");
				// 封装界面对象
				ProcessVO vo = new ProcessVO();
				vo.setTitle(var.getTitle());
				vo.setRequestDate(var.getRequestDate());
				vo.setId(pi.getId());
				result.add(vo);
			}
		}
		return result;
	}

	// 查询用户的待办任务
	public List<TaskVO> listTasks(String userId) {
		// 查询用户所属的用户组
		Group group = this.identityService.createGroupQuery()
				.groupMember(userId).singleResult();
		// 根据用户组查询任务
		List<Task> tasks = this.taskService.createTaskQuery()
				.taskCandidateGroup(group.getId()).list();
		return createTaskVOList(tasks);
	}

	// 将Task集合转为TaskVO集合
	private List<TaskVO> createTaskVOList(List<Task> tasks) {
		List<TaskVO> result = new ArrayList<TaskVO>();
		for (Task task : tasks) {
			// 查询流程实例
			ProcessInstance pi = this.runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(task.getProcessInstanceId())
					.singleResult();
			// 查询流程参数
			BaseForm arg = (BaseForm) this.runtimeService.getVariable(
					pi.getId(), "arg");
			// 封装值对象
			TaskVO vo = new TaskVO();
			vo.setProcessInstanceId(task.getProcessInstanceId());
			vo.setRequestDate(arg.getRequestDate());
			vo.setRequestUser(arg.getUserName());
			vo.setTitle(arg.getTitle());
			vo.setTaskId(task.getId());
			vo.setProcessInstanceId(pi.getId());
			result.add(vo);
		}
		return result;
	}

	// 查询用户所受理的全部任务
	public List<TaskVO> listAssigneeTasks(String userId) {
		List<Task> tasks = this.taskService.createTaskQuery()
				.taskAssignee(userId).list();
		// 将Task集合转为TaskVO集合
		return createTaskVOList(tasks);
	}

	// 领取任务
	public void claim(String taskId, String userId) {
		this.taskService.claim(taskId, userId);
	}

	// 查询一个任务所在流程的开始表单信息
	public List<FormField> getFormFields(String taskId) {
		// 根据任务查询流程实例
		ProcessInstance pi = getProcessInstance(taskId);
		// 获取流程参数
		BaseForm baseForm = (BaseForm) this.runtimeService.getVariable(
				pi.getId(), "arg");
		// 返回表单集合
		List<FormField> formFields = baseForm.getFormFields();
		return formFields;
	}

	// 查询一个任务所在流程的全部评论
	public List<CommentVO> getComments(String taskId) {
		ProcessInstance pi = getProcessInstance(taskId);
		List<CommentVO> result = new ArrayList<CommentVO>();
		List<Comment> comments = this.taskService.getProcessInstanceComments(pi
				.getId());
		for (Comment c : comments) {
			// 查询用户
			User u = this.identityService.createUserQuery()
					.userId(c.getUserId()).singleResult();
			CommentVO vo = new CommentVO();
			vo.setContent(c.getFullMessage());
			vo.setTime(DateUtil.getDateString(c.getTime()));
			vo.setUserName(u.getLastName());
			result.add(vo);
		}
		return result;
	}

	private ProcessInstance getProcessInstance(String taskId) {
		Task task = this.taskService.createTaskQuery().taskId(taskId)
				.singleResult();
		// 根据任务查询流程实例
		ProcessInstance pi = this.runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		return pi;
	}

	// 审批通过任务
	public void complete(String taskId, String content, String userid) {
		ProcessInstance pi = getProcessInstance(taskId);
		this.identityService.setAuthenticatedUserId(userid);
		// 添加评论
		this.taskService.addComment(taskId, pi.getId(), content);
		// 完成任务
		this.taskService.complete(taskId);
	}

	// 薪资调整时审批不通过
	public void cancelAdjust(String taskId, String comment, String userId) {
		this.identityService.setAuthenticatedUserId(userId);
		ProcessInstance pi = getProcessInstance(taskId);
		// 添加评论
		this.taskService.addComment(taskId, pi.getId(), comment);

		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("pass", false);
		this.taskService.complete(taskId, vars);

	}

	public InputStream getDiagram(String processInstanceId) {
		// 查询流程实例
		ProcessInstance pi = this.runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		// 查询流程实例
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(pi.getProcessDefinitionId()).singleResult();
		// 获取BPMN模型对象
		BpmnModel model = repositoryService.getBpmnModel(pd.getId());
		// 定义使用宋体
		String fontName = "宋体";
		// 获取流程实实例当前点的节点，需要高亮显示
		List<String> currentActs = runtimeService.getActiveActivityIds(pi.getId());
		// BPMN模型对象、图片类型、显示的节点
		InputStream is = this.processEngine
				.getProcessEngineConfiguration()
				.getProcessDiagramGenerator()
				.generateDiagram(model, "png", currentActs, new ArrayList<String>(), 
				fontName, fontName, fontName,null, 1.0);
		return is;
	}

	public void recordSalary(Execution exe) {
		System.out.println("记录薪资调整");
	}

	public void bankTransfer(Execution exe) {
		ExpenseAccountForm account = (ExpenseAccountForm) this.runtimeService
				.getVariable(exe.getProcessInstanceId(), "arg");
		if (Integer.parseInt(account.getMoney()) >= 100) {
			throw new BpmnError("to much");
		} else {
			System.out.println("银行转帐成功");
		}
	}

	public void cancelAdjust(Execution exe) {
		System.out.println("取消薪资调整");
	}

}
