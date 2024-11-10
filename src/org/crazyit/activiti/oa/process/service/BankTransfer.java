package org.crazyit.activiti.oa.process.service;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.crazyit.activiti.oa.action.bean.ExpenseAccountForm;

public class BankTransfer implements JavaDelegate {

	public void execute(DelegateExecution execution) {
		ExpenseAccountForm account = (ExpenseAccountForm)execution.getVariable("arg");
		if (Integer.parseInt(account.getMoney()) >= 100) {
			throw new BpmnError("to much");
		} else {
			System.out.println("银行转帐成功");
		}
	}

}
