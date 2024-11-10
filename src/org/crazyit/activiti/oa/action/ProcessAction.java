package org.crazyit.activiti.oa.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.struts2.ServletActionContext;
import org.crazyit.activiti.oa.action.bean.BaseForm;
import org.crazyit.activiti.oa.action.bean.CommentVO;
import org.crazyit.activiti.oa.action.bean.ExpenseAccountForm;
import org.crazyit.activiti.oa.action.bean.FormField;
import org.crazyit.activiti.oa.action.bean.ProcessVO;
import org.crazyit.activiti.oa.action.bean.SalaryForm;
import org.crazyit.activiti.oa.action.bean.TaskVO;
import org.crazyit.activiti.oa.action.bean.VacationForm;
import org.crazyit.activiti.oa.service.ProcessService;

public class ProcessAction extends BaseAction {

	private ProcessService processService;

	private VacationForm vacationForm;

	private SalaryForm salaryForm;

	private ExpenseAccountForm expenseAccountForm;

	// 流程类型
	private String processType;

	// 任务类型
	private String taskType;

	private String errorMsg;

	private List<ProcessVO> processes;

	private List<TaskVO> tasks;

	private String taskId;

	// 任务评论
	private List<CommentVO> comments;

	// 需要显示的表单域
	private List<FormField> formFields;

	// 评论内容
	private String content;

	private String processInstanceId;

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<FormField> getFormFields() {
		return formFields;
	}

	public void setFormFields(List<FormField> formFields) {
		this.formFields = formFields;
	}

	public List<CommentVO> getComments() {
		return comments;
	}

	public void setComments(List<CommentVO> comments) {
		this.comments = comments;
	}

	public List<ProcessVO> getProcesses() {
		return processes;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public List<TaskVO> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskVO> tasks) {
		this.tasks = tasks;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}

	public VacationForm getVacationForm() {
		return vacationForm;
	}

	public void setVacationForm(VacationForm vacationForm) {
		this.vacationForm = vacationForm;
	}

	public SalaryForm getSalaryForm() {
		return salaryForm;
	}

	public void setSalaryForm(SalaryForm salaryForm) {
		this.salaryForm = salaryForm;
	}

	public ExpenseAccountForm getExpenseAccountForm() {
		return expenseAccountForm;
	}

	public void setExpenseAccountForm(ExpenseAccountForm expenseAccountForm) {
		this.expenseAccountForm = expenseAccountForm;
	}

	// 启动请假流程
	public String startVacation() {
		ProcessInstance pi = this.processService
				.startVacation(this.vacationForm);
		this.processType = BaseForm.VACATION;
		return "startVacation";
	}

	// 启动薪资申请
	public String startSalaryAdjust() {
		try {
			ProcessInstance pi = this.processService
					.startSalaryAdjust(salaryForm);
			this.processType = BaseForm.SALARY;
			return "startSalaryAdjust";
		} catch (Exception e) {
			this.errorMsg = e.getMessage();
			return "startSalaryAdjustFail";
		}
	}

	// 启动报销申请
	public String startExpenseAccount() {
		this.processService.startExpenseAccount(this.expenseAccountForm);
		this.processType = BaseForm.EXPENSE;
		return "startExpenseAccount";
	}

	// 读取登录用户的全部申请
	public String listProcessInstance() {
		// 从session中拿回登录的用户
		User user = (User) ServletActionContext.getContext().getSession()
				.get("user");
		// 获取流程类型
		if (BaseForm.VACATION.equals(this.processType)) {
			this.processes = this.processService.listVacation(user.getId());
		} else if (BaseForm.EXPENSE.equals(this.processType)) {
			this.processes = this.processService.listExpenseAccount(user
					.getId());
		} else if (BaseForm.SALARY.equals(this.processType)) {
			this.processes = this.processService.listSalaryAdjust(user.getId());
		}
		return "listProcessInstance";
	}

	public String listTask() {
		// 从session中拿回登录的用户
		User user = (User) ServletActionContext.getContext().getSession()
				.get("user");
		if (TaskVO.CANDIDATE.equals(this.taskType)) {
			// 查询待办任务
			this.tasks = this.processService.listTasks(user.getId());
		} else if (TaskVO.ASSIGNEE.equals(this.taskType)) {
			// 查询受理的任务
			this.tasks = this.processService.listAssigneeTasks(user.getId());
		}
		return "listTask";
	}

	// "领取任务"
	public String claim() {
		User user = (User) ServletActionContext.getContext().getSession()
				.get("user");
		this.processService.claim(this.taskId, user.getId());
		this.taskType = TaskVO.CANDIDATE;
		return "claim";
	}

	// 打开办理页面
	public String perform() {
		this.comments = this.processService.getComments(taskId);
		this.formFields = this.processService.getFormFields(taskId);
		return "perform";
	}

	// 完成任务
	public String complete() {
		User user = (User) ServletActionContext.getContext().getSession()
				.get("user");
		this.processService.complete(this.taskId, content, user.getId());
		this.taskType = TaskVO.ASSIGNEE;
		return "complete";
	}

	// 取消薪资调整
	public String cancelAdjust() {
		User user = (User) ServletActionContext.getContext().getSession()
				.get("user");
		this.processService.cancelAdjust(this.taskId, this.content,
				user.getId());
		this.taskType = TaskVO.ASSIGNEE;
		return "cancelAdjust";
	}

	// 将输入流转换为byte数组
	private byte[] getImgByte(InputStream is) throws IOException {
//		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
//		int b;
//		while ((b = is.read()) != -1) {
//			bytestream.write(b);
//		}
//		byte[] bs = bytestream.toByteArray();
//		bytestream.close();
//		return bs;
		
		
        BufferedInputStream bufin = new BufferedInputStream(is);  
        int buffSize = 1024;  
        ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize); 
  
        byte[] temp = new byte[buffSize];  
        int size = 0;  
        while ((size = bufin.read(temp)) != -1) {  
            out.write(temp, 0, size);  
        }  
        bufin.close();  
        is.close();  
        byte[] content = out.toByteArray();  
        out.close();  
        return content; 
	}

	// 显示流程图
	public String showDiagram() {
		OutputStream out = null;
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			InputStream is = this.processService.getDiagram(this.processInstanceId);
			response.setContentType("multipart/form-data;charset=utf8");
            out = response.getOutputStream();
            out.write(getImgByte(is));
            out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
		return null;
	}
}
