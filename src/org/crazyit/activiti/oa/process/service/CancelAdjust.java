package org.crazyit.activiti.oa.process.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class CancelAdjust implements JavaDelegate {

	public void execute(DelegateExecution execution) {
		System.out.println("取消调整=============");
	}

}
