package org.crazyit.activiti.oa.service;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.crazyit.activiti.oa.action.bean.CommentVO;
import org.crazyit.activiti.oa.action.bean.ExpenseAccountForm;
import org.crazyit.activiti.oa.action.bean.FormField;
import org.crazyit.activiti.oa.action.bean.ProcessVO;
import org.crazyit.activiti.oa.action.bean.SalaryForm;
import org.crazyit.activiti.oa.action.bean.TaskVO;
import org.crazyit.activiti.oa.action.bean.VacationForm;

public interface ProcessService {

	/**
	 * 启动一个请假流程
	 * @param vacation
	 * @return
	 */
	ProcessInstance startVacation(VacationForm vacation);
	
	/**
	 * 启动一个薪资调整流程
	 * @param salary
	 * @return
	 */
	ProcessInstance startSalaryAdjust(SalaryForm salary);
	
	/**
	 * 启动一个薪资调整流程
	 * @return
	 */
	ProcessInstance startExpenseAccount(ExpenseAccountForm expenseAccountForm);
	
	/**
	 * 查询一个用户全部的请假申请
	 * @param userId
	 * @return
	 */
	List<ProcessVO> listVacation(String userId);
	
	/**
	 * 查询一个用户全部的报销申请
	 * @param userId
	 * @return
	 */
	List<ProcessVO> listExpenseAccount(String userId);
	
	/**
	 * 查询一个用户全部的薪资调整申请
	 * @param userId
	 * @return
	 */
	List<ProcessVO> listSalaryAdjust(String userId);
	
	/**
	 * 查询一个用户相关的全部候选任务
	 * @param userId
	 * @return
	 */
	List<TaskVO> listTasks(String userId);
	
	/**
	 * 查询用户受理的全部任务
	 * @param userId
	 * @return
	 */
	List<TaskVO> listAssigneeTasks(String userId);
	
	/**
	 * 分配任务受理人
	 * @param taskId
	 * @param userId
	 */
	void claim(String taskId, String userId);
	
	/**
	 * 根据任务ID，查询用户填写的表单数据
	 * @param taskId
	 * @return
	 */
	List<FormField> getFormFields(String taskId);
	
	/**
	 * 查询一个流程的全部评论
	 * @param taskId
	 * @return
	 */
	List<CommentVO> getComments(String taskId);
	
	/**
	 * 完成任务并添加评论
	 */
	void complete(String taskId, String content, String userid);
	
	/**
	 * 取消薪资调整
	 * @param taskId
	 * @param comment
	 */
	void cancelAdjust(String taskId, String comment, String userId);
	
	/**
	 * 获取流程图
	 * @param processInstanceId
	 * @return
	 */
	InputStream getDiagram(String processInstanceId);
	
	/**
	 * 系统记录薪资，由ServiceTask调用
	 * @param exe
	 */
	void recordSalary(Execution exe);
	
	/**
	 * 银行转帐，由ServiceTask调用
	 * @param exe
	 */
	void bankTransfer(Execution exe);
	
	/**
	 * 取消薪资调整，ServiceTask调用
	 * @param exe
	 */
	void cancelAdjust(Execution exe);
}
