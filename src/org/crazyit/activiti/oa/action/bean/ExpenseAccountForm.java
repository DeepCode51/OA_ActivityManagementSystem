package org.crazyit.activiti.oa.action.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报销表单对象
 * @author yangenxiong
 *
 */
public class ExpenseAccountForm extends BaseForm {
	
	// 发生日期
	private String date;
	
	// 金额
	private String money;
	
	// 说明
	private String dscp;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public void createFormFields(List<FormField> fields) {
		fields.add(super.getFormField("date", "费用发生时间", date));
		fields.add(super.getFormField("money", "报销费用 ", money));
		fields.add(super.getFormField("dscp", "描述", dscp));
	}
	
	
}
