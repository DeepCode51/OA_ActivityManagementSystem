package org.crazyit.activiti.oa.action.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.crazyit.activiti.oa.util.DateUtil;

public class SalaryForm extends BaseForm {
	
	// 薪资调整的员工
	private String employeeName;
	
	// 薪资调整员工id
	private String employeeId;
	
	// 调整金额
	private String money;
	
	// 描述
	private String dscp;

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}


	public String getDscp() {
		return dscp;
	}

	public void setDscp(String dscp) {
		this.dscp = dscp;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public void createFormFields(List<FormField> fields) {
		fields.add(super.getFormField("employeeName", "调薪员工", employeeName));
		fields.add(super.getFormField("money", "调薪金额", money));
		fields.add(super.getFormField("dscp", "描述", dscp));
	}
	
}
